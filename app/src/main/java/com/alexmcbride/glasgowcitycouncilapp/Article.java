package com.alexmcbride.glasgowcitycouncilapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Article {
    private long mId;
    private String mTitle;
    private Date mPosted;
    private String mContent;
    private String mImageSrc;
    private List<Comment> mComments;

    public Article() {
        mComments = new ArrayList<>();
    }

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

    public String getImageSrc() {
        return mImageSrc;
    }

    public void setImageSrc(String imageSrc) {
        mImageSrc = imageSrc;
    }

    public List<Comment> getComments() {
        return mComments;
    }
}
