package com.alexmcbride.glasgowcitycouncilapp;

public class Museum {
    private long mId;
    private String mName;
    private String mDescription;
    private String mImageSrc;

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getImageSrc() {
        return mImageSrc;
    }

    public void setImageSrc(String imageSrc) {
        mImageSrc = imageSrc;
    }
}
