package com.alexmcbride.glasgowcitycouncilapp;

import java.util.Date;

public class Comment {
    private long mId;
    private long mPostId;
    private String mUsername;
    private Date mPosted;
    private String mContent;

    public Comment() {

    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public long getPostId() {
        return mPostId;
    }

    public void setPostId(long articleId) {
        mPostId = articleId;
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

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }
}
