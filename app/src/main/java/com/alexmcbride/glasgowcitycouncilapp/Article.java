package com.alexmcbride.glasgowcitycouncilapp;

import java.util.Date;

public class Article {
    private long mId;
    private String mTitle;
    private Date mPosted;
    private String mContent;

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
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
