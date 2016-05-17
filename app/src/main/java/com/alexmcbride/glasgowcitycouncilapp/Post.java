package com.alexmcbride.glasgowcitycouncilapp;

import java.util.Date;

public class Post {
    private long mId;
    private String mUsername;
    private Date mPosted;
    private String mTitle;
    private String mContent;

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public Date getPosted() {
        return mPosted;
    }

    public void setPosted(Date posted) {
        mPosted = posted;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }
}
