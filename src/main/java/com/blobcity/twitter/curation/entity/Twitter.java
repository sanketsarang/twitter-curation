/*
 * Copyright 2015, BlobCity iSolutions Pvt. Ltd.
 */

package com.blobcity.twitter.curation.entity;

import com.blobcity.db.CloudStorage;
import com.blobcity.db.annotations.Entity;
import com.blobcity.db.annotations.Primary;

/**
 *
 * @author Soumya P M
 * @author Sanket Sarang
 */
@Entity
public class Twitter extends CloudStorage{
    
    @Primary
    private String twitterId;
    private String text;
    private long createdAt;
    private String publisher;
    private String originalSource;
    private String category;
    
    public Twitter() {
        //do nothing
    }
    
    public Twitter(final String twitterId, final String text, final long createdAt, 
            final String publisher, final String originalSource, final String category) {
        this.twitterId = twitterId;
        this.text = text;
        this.createdAt = createdAt;
        this.publisher = publisher;
        this.originalSource = originalSource;
        this.category = category;
    }

    public String getTwitterId() {
        return twitterId;
    }

    public void setTwitterId(String twitterId) {
        this.twitterId = twitterId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }    

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getOriginalSource() {
        return originalSource;
    }

    public void setOriginalSource(String originalSource) {
        this.originalSource = originalSource;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}