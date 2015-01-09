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
public class Categories extends CloudStorage {

    @Primary
    private String categoryName;
    private String photoIcon;

    public Categories() {
        //do nothing
    }

    public Categories(final String categoryName, final String photoIcon) {
        this.categoryName = categoryName;
        this.photoIcon = photoIcon;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getPhotoIcon() {
        return photoIcon;
    }

    public void setPhotoIcon(String photoIcon) {
        this.photoIcon = photoIcon;
    }
}
