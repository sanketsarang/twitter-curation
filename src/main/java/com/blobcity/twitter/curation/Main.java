
package com.blobcity.twitter.curation;

import com.blobcity.db.config.Credentials;
import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.event.Event;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author Sanket Sarang
 */
public class Main {

    public static void main(String[] args) {
        
        Credentials.init(com.blobcity.twitter.curation.constants.Constants.SERVER, 
                com.blobcity.twitter.curation.constants.Constants.USERNAME,
                com.blobcity.twitter.curation.constants.Constants.PASSWORD,
                com.blobcity.twitter.curation.constants.Constants.DB);
        /**
         * Set up your blocking queues: Be sure to size these properly based on expected TPS of your stream
         */
        BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(100000);
        BlockingQueue<Event> eventQueue = new LinkedBlockingQueue<Event>(100000);

        /**
         * Declare the host you want to connect to, the endpoint, and authentication (basic auth or oauth)
         */
        Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();
// Optional: set up some followings and track terms
        List<Long> followings = Lists.newArrayList(1234L, 566788L);
        List<String> terms = Lists.newArrayList("technology","mobile");
        hosebirdEndpoint.followings(followings);
        hosebirdEndpoint.trackTerms(terms);

// These secrets should be read from a config file
        Authentication hosebirdAuth = new OAuth1(com.blobcity.twitter.curation.constants.Constants.CONSUMER_KEY,
                com.blobcity.twitter.curation.constants.Constants.CONSUMER_SECRET,
                com.blobcity.twitter.curation.constants.Constants.TOKEN,
                com.blobcity.twitter.curation.constants.Constants.TOKEN_SECRET);

        ClientBuilder builder = new ClientBuilder()
                .name("Hosebird-Client-01") // optional: mainly for the logs
                .hosts(hosebirdHosts)
                .authentication(hosebirdAuth)
                .endpoint(hosebirdEndpoint)
                .processor(new StringDelimitedProcessor(msgQueue))
                .eventMessageQueue(eventQueue);                          // optional: use this if you want to process client events

        Client hosebirdClient = builder.build();
// Attempts to establish a connection.
        hosebirdClient.connect();

        AtomicRate atomicRate = new AtomicRate(10, TimeUnit.SECONDS);
        long time = 0;
        while (!hosebirdClient.isDone()) {
            String msg = null;
            try {
                msg = msgQueue.take();
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            JSONObject jsonObject = new JSONObject(msg);
            
            atomicRate.count();

            if (System.currentTimeMillis() - time > 1000) {
                System.out.println("Rate: " + atomicRate.getRate());
                time = System.currentTimeMillis();
            }
        }
    }
}
