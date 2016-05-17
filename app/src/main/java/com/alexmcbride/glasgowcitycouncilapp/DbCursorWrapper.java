package com.alexmcbride.glasgowcitycouncilapp;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.alexmcbride.glasgowcitycouncilapp.DbSchema.MuseumTable;
import com.alexmcbride.glasgowcitycouncilapp.DbSchema.LoginTable;
import com.alexmcbride.glasgowcitycouncilapp.DbSchema.UserTable;
import com.alexmcbride.glasgowcitycouncilapp.DbSchema.ArticleTable;
import com.alexmcbride.glasgowcitycouncilapp.DbSchema.CommentTable;
import com.alexmcbride.glasgowcitycouncilapp.DbSchema.PostTable;

import java.util.Date;

public class DbCursorWrapper extends CursorWrapper {
    public DbCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Museum getMuseum() {
        Museum museum = new Museum();;
        museum.setId(getLong(getColumnIndex(MuseumTable.Columns.ID)));
        museum.setName(getString(getColumnIndex(MuseumTable.Columns.NAME)));
        museum.setDescription(getString(getColumnIndex(MuseumTable.Columns.DESCRIPTION)));
        museum.setImageSrc(getString(getColumnIndex(MuseumTable.Columns.IMAGE_SRC)));
        return museum;
    }

    public Login getLogin() {
        Login login = new Login();
        login.setUsername(getString(getColumnIndex(LoginTable.Columns.USERNAME)));
        login.setPassword(getString(getColumnIndex(LoginTable.Columns.PASSWORD)));
        return login;
    }

    public User getUser () {
        User user = new User();
        user.setFirstName(getString(getColumnIndex(DbSchema.UserTable.Columns.USERNAME)));
        user.setLastName(getString(getColumnIndex(DbSchema.UserTable.Columns.FIRST_NAME)));
        user.setEmail(getString(getColumnIndex(DbSchema.UserTable.Columns.LAST_NAME)));
        return user;
    }

    public Article getArticle() {
        Article article = new Article();
        article.setId(getLong(getColumnIndex(ArticleTable.Columns.ID)));
        article.setTitle(getString(getColumnIndex(ArticleTable.Columns.TITLE)));
        article.setContent(getString(getColumnIndex(ArticleTable.Columns.CONTENT)));
        article.setPosted(new Date(getLong(getColumnIndex(ArticleTable.Columns.POSTED))));
        return article;
    }

    public Comment getComment() {
        Comment comment = new Comment();
        comment.setId(getLong(getColumnIndex(CommentTable.Columns.ID)));
        comment.setPostId(getLong(getColumnIndex(CommentTable.Columns.POST_ID)));
        comment.setUsername(getString(getColumnIndex(CommentTable.Columns.USERNAME)));
        comment.setPosted(new Date((getLong(getColumnIndex(CommentTable.Columns.POSTED)))));
        comment.setContent(getString(getColumnIndex(CommentTable.Columns.CONTENT)));
        return comment;
    }

    public Post getPost() {
        Post post = new Post();
        post.setId(getLong(getColumnIndex(PostTable.Columns.ID)));
        post.setUsername(getString(getColumnIndex(PostTable.Columns.USERNAME)));
        post.setPosted(new Date((getLong(getColumnIndex(PostTable.Columns.POSTED)))));
        post.setTitle(getString(getColumnIndex(PostTable.Columns.TITLE)));
        post.setContent(getString(getColumnIndex(PostTable.Columns.CONTENT)));
        return post;
    }
}
