package com.alexmcbride.glasgowcitycouncilapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;

import com.alexmcbride.glasgowcitycouncilapp.DbSchema.MuseumTable;
import com.alexmcbride.glasgowcitycouncilapp.DbSchema.ArticleTable;
import com.alexmcbride.glasgowcitycouncilapp.DbSchema.UserTable;
import com.alexmcbride.glasgowcitycouncilapp.DbSchema.LoginTable;
import com.alexmcbride.glasgowcitycouncilapp.DbSchema.PostTable;
import com.alexmcbride.glasgowcitycouncilapp.DbSchema.CommentTable;

import java.util.Date;

public class DbHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "gcc.db";
    private static final int DB_VERSION = 13;

    public DbHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + MuseumTable.NAME + " ("
                + MuseumTable.Columns.ID + " INTEGER PRIMARY KEY,"
                + MuseumTable.Columns.NAME + " TEXT,"
                + MuseumTable.Columns.DESCRIPTION + " TEXT,"
                + MuseumTable.Columns.IMAGE_SRC + " TEXT"
                + ");");

        db.execSQL("CREATE TABLE " + LoginTable.NAME + "("
                + LoginTable.Columns.USERNAME + " TEXT,"
                + LoginTable.Columns.PASSWORD + " TEXT"
                + ");");

        db.execSQL("CREATE TABLE " + UserTable.NAME + " ("
                + UserTable.Columns.USERNAME + " TEXT,"
                + UserTable.Columns.FIRST_NAME + " TEXT,"
                + UserTable.Columns.LAST_NAME + " TEXT,"
                + UserTable.Columns.EMAIL + " TEXT,"
                + "FOREIGN KEY(" + UserTable.Columns.USERNAME + ") REFERENCES " + LoginTable.NAME + "(" + LoginTable.Columns.USERNAME + ")"
                + ");");

        db.execSQL("CREATE TABLE " + ArticleTable.NAME + " ("
                + ArticleTable.Columns.ID + " INTEGER PRIMARY KEY,"
                + ArticleTable.Columns.TITLE + " TEXT,"
                + ArticleTable.Columns.POSTED + " INTEGER,"
                + ArticleTable.Columns.CONTENT + " TEXT"
                + ");");

        db.execSQL("CREATE TABLE " + PostTable.NAME + "("
                + PostTable.Columns.ID + " INTEGER PRIMARY KEY,"
                + PostTable.Columns.USERNAME + " TEXT,"
                + PostTable.Columns.POSTED + " INTEGER,"
                + PostTable.Columns.TITLE + " TEXT,"
                + PostTable.Columns.CONTENT + " TEXT,"
                + PostTable.Columns.COMMENT_COUNT + " INTEGER"
                + ");");

        db.execSQL("CREATE TABLE " + CommentTable.NAME + "("
                + CommentTable.Columns.ID + " INTEGER PRIMARY KEY,"
                + CommentTable.Columns.POST_ID + " INTEGER,"
                + CommentTable.Columns.USERNAME + " TEXT,"
                + CommentTable.Columns.POSTED + " INTEGER,"
                + CommentTable.Columns.CONTENT + " CONTENT,"
                + "FOREIGN KEY(" + CommentTable.Columns.POST_ID + ") REFERENCES " + PostTable.NAME + "(" + PostTable.Columns.ID + ")"
                + "FOREIGN KEY(" + CommentTable.Columns.USERNAME + ") REFERENCES " + UserTable.NAME + "(" + UserTable.Columns.USERNAME + ")"
                + ");");

        addMuseums(db);
        addArticles(db);
    }

    private void addMuseums(SQLiteDatabase db) {
        Museum museum = new Museum();
        museum.setName("Kelvingrove Art Gallery and Museum");
        museum.setDescription("Kelvingrove Art Gallery and Museum houses one of Europe's great art collections. It is amongst the top three free-to-enter visitor attractions in Scotland and one of the most visited museums in the United Kingdom outside of London.");
        museum.setImageSrc("museum_kelvin");
        db.insert(MuseumTable.NAME, null, getContentValues(museum));

        museum = new Museum();
        museum.setName("Riverside Museum");
        museum.setDescription("Riverside is home to some of the world’s finest cars, bicycles, ship models, trams and locomotives. Interactive displays and the hugely popular historic Glasgow street scene bring the objects and stories to life.");
        museum.setImageSrc("museum_riverside");
        db.insert(MuseumTable.NAME, null, getContentValues(museum));
    }

    private void addArticles(SQLiteDatabase db) {
        Article article = new Article();
        article.setTitle("GoMA work with pupils from Holybrook Acadmey to curate, arrange and open their own exhibition");
        article.setPosted(new Date());
        article.setContent("The Gallery of Modern Art, Glasgow (GoMA) collects, borrows and shows artwork that highlights the interests, influences and working methods contemporary artists from around the world share with those from the city.\n" +
                "To exhibit in Scotland's most popular modern art gallery is an ambition held by many, but achieved by only a few.  A group of pupils from Hollybrook Academy, a school for children with additional support needs, will join this select group of artists, as they will curate and open their own exhibition at GoMA.  The exhibition is called Scribbles by Hollybrook, it opens on Friday 13th May and runs until Sunday 29th May 2016.\n\n" +
                "In 2014 the school established a Social Enterprise company called Scribbles by Hollybrook. The purpose of this Social Enterprise is to frame and sell pupils' artwork, with all profits raised donated to Glasgow Children's Hospital, where most of the pupils involved have been treated at some stage in their lives.\n\n" +
                "Glasgow Museums will introduce the young people who are part of Scribbles by Hollybrook to the many different roles that make up the wider creative industries sector.  A project team consisting of pupils and museums staff will select pupils artwork for display, design the exhibition layout, draft interpretation panels to accompany the artwork, plan the logistics and take part in the set up and promote the display.  The overall ambition is that the Business Studies students may be able to work towards achieving an SQA in Creative Industries.\n" +
                "Victor Cannon, Teacher of Business Education and ICT, at Hollybrook Academy said: \"Scribbles has gone from strength to strength since it started in 2014.  Glasgow Museums helped get the social enterprise up and running, so it's wonderful to be able to show off some of the fruits of the project in GoMA no less. \n\n" +
                "The children have learned so much about the different jobs which make up the creative industries and of course they are chuffed to bits to have their artwork hanging in Scotland's most visited art gallery.  We hope lots of people will come and see the show and maybe even pick a piece of art of take home at the end of it.  The children see it as an opportunity to give something back to Yorkhill.\"\n\n" +
                "The project has received tremendous community backing.  After hosting an exhibition last year at IKEA, the company has continued their support by donating all the frames for the artwork to be exhibited and then sold.  Natasha Rankin, a familiar face on BBC One's Bargain Hunt, has worked with the pupils during the last year and will be guest speaker at the opening of Scribbles by Hollybrook on Friday 13th May.\n\n" +
                "Chair of Glasgow Life, Councillor Archie Graham, said: \"GoMA has hosted many high profile, popular exhibitions, which have often addressed challenging issues through the medium of art.  I think Scribbles by Hollybrook is a worthy addition to the exhibition programme for 2016.  Not only has it been curated and displayed by some very talented young people, many of whom have to deal with a range of challenging issues themselves each day, but because it displays the very work they have created.  I hope the many visitors to GoMA will take time to wander onto the balcony and enjoy the show.");
        db.insert(ArticleTable.NAME, null, getContentValues(article));

        article = new Article();
        article.setTitle("Share Your Views on Begging in Glasgow City Centre");
        article.setPosted(new Date());
        article.setContent("People who live, work, shop and socialise in Glasgow are being asked for their views on begging in the city centre.\n\n" +
                "Community Safety Glasgow (CSG) is asking people about their experiences of and reactions to beggars and their opinions on what should be done about the issue.\n\n" +
                "They are consulting the public and businesses as part of a wider project to create an up-to-date picture of the extent of the issue. The information provided will help shape future strategies to assist those involved in begging.\n\n" +
                "People are being asked how they react to beggars - for example, do they buy them food, give them money, check if they are OK or just walk past?\n\n" +
                "They are also asking people if they have experienced or witnessed aggressive begging or other problems such as antisocial behaviour related to begging and if so, did they report them to anyone?\n\n" +
                "CSG works with partners such as the Simon Community's RSVP team which provides an outreach service to homeless people on behalf of Glasgow City Council and gives them information on support services such as where they can access food, shelter and help them register for accommodation.\n\n" +
                "Staff are currently conducting on-street interviews with visitors to the city centre as part of the survey which is also available on-line.\n\n" +
                "A spokesman for Community Safety Glasgow said: \"The results of the survey will help us build up a clear, up-to-date picture of the nature and extent of begging in the city centre, the type of people involved and the problems they have. It will also tell us how people feel about begging and offer them an opportunity to contribute their ideas on how it might be addressed.\"\n\n" +
                "People can contribute to the online survey until midnight on 20th May at https://www.surveymonkey.co.uk/r/publicsurveyaprmay16");
        db.insert(ArticleTable.NAME, null, getContentValues(article));

        article = new Article();
        article.setTitle("Sow and grow and let Glasgow's children flourish");
        article.setPosted(new Date());
        article.setContent("Almost 20,000 packets of seeds are being handed out to every P1 - P3 Glasgow school child this week to highlight the importance of the council's current fostering and adoption campaign - Let Glasgow Children Flourish.\n\n" +
                "The successful campaign uses a growing tree to illustrate how, with the right foster carers or adoptive parents, Glasgow's vulnerable children can get the chance to blossom and grow.\n\n" +
                "Now all young children across the city are being given the opportunity to nurture and grow their own basil plant from seed and their own 'family tree' with the free packet of seeds.  \n\n" +
                "Basil seeds have been chosen as they are easy to grow indoors in a pot, as well as outdoors once they are established.\n\n" +
                "The seeds are being sent home to families with an information letter and links to the Families for Children website for more information on the current fostering and adopting marketing campaign.\n\n" +
                "As Celia Gray, Service Manager, Families for Children, Glasgow City Council explains: \"Just as with seeds, children need care and nurturing if they are to flourish: giving these seeds to all Primary 1-3 children is a good way to raise awareness of the urgent need for carers and adopters, whilst at the same time giving children the chance to have some fun taking care of their own plant.\n\n" +
                "'Fostering and adopting is a great way to grow your own family tree. We aim to recruit more than 100 carers and adopters this year and would like to hear from people who feel they can help the city's vulnerable children to put down roots.'  ");
        db.insert(ArticleTable.NAME, null, getContentValues(article));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MuseumTable.NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + UserTable.NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + LoginTable.NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + ArticleTable.NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + PostTable.NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + CommentTable.NAME + ";");
        onCreate(db);
    }

    public Cursor getMuseumsCursor() {
        return getReadableDatabase().query(MuseumTable.NAME, null, null, null, null, null,  null);
    }

    public Museum getMuseum(long id) {
        Cursor cursor = null;
        try {
            SQLiteDatabase db = getReadableDatabase();

            cursor = db.query(MuseumTable.NAME,
                    null,
                    MuseumTable.Columns.ID + "=?",
                    new String[]{String.valueOf(id)},
                    null, null, null);

            if (cursor.moveToFirst()) {
                return new DbCursorWrapper(cursor).getMuseum();
            }

            return null;
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private ContentValues getContentValues(Museum museum) {
        ContentValues values = new ContentValues();
        values.put(MuseumTable.Columns.NAME, museum.getName());
        values.put(MuseumTable.Columns.DESCRIPTION, museum.getDescription());
        values.put(MuseumTable.Columns.IMAGE_SRC, museum.getImageSrc());
        return values;
    }

    public void addLogin(Login login) {
        ContentValues values = new ContentValues();
        values.put(LoginTable.Columns.USERNAME, login.getUsername());
        values.put(LoginTable.Columns.PASSWORD, login.getPassword());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(LoginTable.NAME, null, values);
    }

    public void addUser(User user) {
        ContentValues values = new ContentValues();
        values.put(UserTable.Columns.USERNAME, user.getLogin().getUsername());
        values.put(UserTable.Columns.FIRST_NAME, user.getFirstName());
        values.put(UserTable.Columns.LAST_NAME, user.getLastName());
        values.put(UserTable.Columns.EMAIL, user.getEmail());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(UserTable.NAME, null, values);
    }

    public boolean validateLogin(String username, String password) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(LoginTable.NAME,
                    new String[]{LoginTable.Columns.USERNAME},
                    LoginTable.Columns.USERNAME + "=? AND " + LoginTable.Columns.PASSWORD + "=?",
                    new String[]{username, password},
                    null, null, null);

            return cursor.getCount() == 1;
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

//    public User getUser(String username) {
//        SQLiteDatabase db = getWritableDatabase();
//        Cursor cursor = null;
//        try {
//            String sql = "SELECT * " +
//                    " FROM " + LoginTable.NAME + " AS l" +
//                    " JOIN " + UserTable.NAME + " AS u" +
//                    " ON l." + LoginTable.Columns.USERNAME+ "=u." + UserTable.Columns.USERNAME +
//                    " WHERE l." + LoginTable.Columns.USERNAME + "=?";
//
//            cursor = db.rawQuery(sql, new String[]{username});
//            if (cursor.moveToFirst()) {
//                DbCursorWrapper wrapper = new DbCursorWrapper(cursor);
//
//                Login login = wrapper.getLogin();
//
//                User user = wrapper.getUser();
//                user.setLogin(login);
//
//                return user;
//            }
//
//            return null;
//        }
//        finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//    }

    public boolean userExists(String username) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(LoginTable.NAME,
                    new String[]{LoginTable.Columns.USERNAME},
                    "((" + LoginTable.Columns.USERNAME + "=?))",
                    new String[]{username},
                    null, null, null);

            return cursor.getCount() == 1;
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public Cursor getArticlesCursor() {
        return getReadableDatabase().query(ArticleTable.NAME, null, null, null, null, null, null);
    }

    public Article getArticle(long id) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = db.query(ArticleTable.NAME,
                    null,
                    ArticleTable.Columns.ID + "=?",
                    new String[]{String.valueOf(id)},
                    null, null, null);

            if (cursor.moveToFirst()) {
                return new DbCursorWrapper(cursor).getArticle();
            }

            return null;
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private ContentValues getContentValues(Article article) {
        ContentValues values = new ContentValues();
        values.put(ArticleTable.Columns.TITLE, article.getTitle());
        values.put(ArticleTable.Columns.POSTED, article.getPosted().getTime());
        values.put(ArticleTable.Columns.CONTENT, article.getContent());
        return values;
    }

    public Cursor getPostsCursor() {
        return getReadableDatabase().query(PostTable.NAME,
                null, null, null, null, null,
                PostTable.Columns.POSTED + " DESC");
    }

    public Post getPost(long id) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = db.query(PostTable.NAME,
                    null,
                    PostTable.Columns.ID + "=?",
                    new String[]{String.valueOf(id)},
                    null, null, null);

            if (cursor.moveToFirst()) {
                return new DbCursorWrapper(cursor).getPost();
            }

            return null;
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public Cursor getCommentsCursor(long postId) {
        return getReadableDatabase().query(CommentTable.NAME,
                null,
                CommentTable.Columns.POST_ID + "=?",
                new String[]{String.valueOf(postId)},
                null, null,
                CommentTable.Columns.POSTED + " DESC");
    }

    public void addPost(Post post) {
        ContentValues values = getContentValues(post);
        SQLiteDatabase db = getWritableDatabase();
        long id = db.insert(PostTable.NAME, null, values);
        post.setId(id);
    }

    public void updatePost(Post post) {
        ContentValues values = getContentValues(post);
        SQLiteDatabase db = getWritableDatabase();
        db.update(PostTable.NAME,
                values,
                PostTable.Columns.ID + "=?",
                new String[]{String.valueOf(post.getId())});
    }

    private ContentValues getContentValues(Post post) {
        ContentValues values = new ContentValues();
        values.put(PostTable.Columns.USERNAME, post.getUsername());
        values.put(PostTable.Columns.TITLE, post.getTitle());
        values.put(PostTable.Columns.POSTED, post.getPosted().getTime());
        values.put(PostTable.Columns.CONTENT, post.getContent());
        values.put(PostTable.Columns.COMMENT_COUNT, post.getCommentCount());
        return values;
    }

    public void addComment(Comment comment) {
        ContentValues values = getContentValues(comment);

        SQLiteDatabase db = getWritableDatabase();
        long id = db.insert(CommentTable.NAME, null, values);
        comment.setId(id);
    }

    private ContentValues getContentValues(Comment comment) {
        ContentValues values = new ContentValues();
        values.put(CommentTable.Columns.POST_ID, comment.getPostId());
        values.put(CommentTable.Columns.USERNAME, comment.getUsername());;
        values.put(CommentTable.Columns.POSTED, comment.getPosted().getTime());
        values.put(CommentTable.Columns.CONTENT, comment.getContent());
        return values;
    }
}

