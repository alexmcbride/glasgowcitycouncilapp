package com.alexmcbride.glasgowcitycouncilapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.alexmcbride.glasgowcitycouncilapp.DbSchema.MuseumTable;
import com.alexmcbride.glasgowcitycouncilapp.DbSchema.ArticleTable;
import com.alexmcbride.glasgowcitycouncilapp.DbSchema.UserTable;
import com.alexmcbride.glasgowcitycouncilapp.DbSchema.LoginTable;
import com.alexmcbride.glasgowcitycouncilapp.DbSchema.PostTable;
import com.alexmcbride.glasgowcitycouncilapp.DbSchema.CommentTable;

import java.util.Date;

public class DbHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "gcc.db";
    private static final int DB_VERSION = 19;

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

        Log.d("DbHandler", "fart");

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

        museum = new Museum();
        museum.setName("The Burrell Collection");
        museum.setDescription("The Burrell Collection is one of the greatest art collections in the world, consisting of more than 9,000 antiquities, objects, tapestries and paintings.\n" +
                "\n" +
                "This incredible collection was created by Sir William Burrell over 80 years.  Sir William was a sophisticated collector who meticulously sought out fine craftsmanship in the objects he acquired.  In 1944 Sir William and his wife, Constance gifted the collection to the city of Glasgow to be enjoyed and admired by all.  \n" +
                "\n" +
                "The Collection is housed in an award winning museum in the heart of Pollok Country Park, surrounded by a beautiful woodland setting.  \n" +
                "\n" +
                "The areas covered by the collection are outstanding representative samples of their kind and the quiet setting  allows visitors to explore everything from stain-glass panels, arms and armour, architectural features from buildings around the world, gothic art, chinese ceramics, sculpture, furniture, tapestries, medieval art and over 300 paintings from some of the worlds most well known painters; Degas, Manet, Rembrant, Maris brothers and Joseph Crawhall.  \n" +
                "\n" +
                "The Burrell regularly hosts temporary exhibitions, and runs an extensive programme of events and activities for both adults and families with children.");
        museum.setImageSrc("museum_burrell");
        db.insert(MuseumTable.NAME, null, getContentValues(museum));

        museum = new Museum();
        museum.setName("Gallery of Modern Art");
        museum.setDescription("It is housed in an iconic building in the heart of Glasgow, which it shares with the city centre library. GoMA plays an important part in the city’s rich heritage.\n" +
                "\n" +
                "For over 100 years the building was a centre for business and commercial exchange where information and goods were traded. GoMA continues that philosophy of exchange by being a centre for people to gather, discuss and learn, inspired by the art it collects and shows.\n" +
                "\n" +
                "Glasgow is world famous for the artists that live and study here. GoMA collects and borrows work that highlights the interests, influences and working methods artists from around the world share with those from Glasgow.\n" +
                " \n" +
                "As the centre for Glasgow’s modern art collection, its changing displays are inspired by what the City owns. \n" +
                "\n" +
                "The Library at GoMA, houses a café, free internet access terminals, and an extensive collection of art and design books alongside its general books for loan.");
        museum.setImageSrc("museum_goma");
        db.insert(MuseumTable.NAME, null, getContentValues(museum));

        museum = new Museum();
        museum.setName("The People’s Palace");
        museum.setDescription("The People’s Palace, set in historic Glasgow Green, tells the story of the people and city of Glasgow from 1750 to the end of the 20th century.\n" +
                "\n" +
                "Explore the city’s social history through a wealth of historic artefacts, paintings, prints and photographs, film and interactive computer displays. Get a wonderful insight into how Glaswegians lived, worked and played in years gone by. ");
        museum.setImageSrc("museum_palace");
        db.insert(MuseumTable.NAME, null, getContentValues(museum));

        museum = new Museum();
        museum.setName("St Mungo Museum");
        museum.setDescription("The award-winning St Mungo Museum is a haven of tranquillity in a bustling city.  This museum is named after Glasgow's patron saint, who brought the Christian faith to Scotland in the 6th century.\n" +
                "\n" +
                "The building was built in 1989 in Scottish baronial style by Ian Begg.  It was designed to reflect the architecture of the Bishops’ Castle, the site of which is occupied by the museum.  Its galleries are full of displays, artefacts and stunning works of art.  They explore the importance of religion in peoples’ lives across the world and across time.\n" +
                "\n" +
                "The venue aims to promote understanding and respect between people of different faiths and of none, and offers something for everyone.\n" +
                "\n" +
                "You can find out more about some of the world’s major religions, and the story of religion in the west of Scotland. Or you can relax in the museum café, which opens out into the first Zen garden in Britain.\n" +
                "\n" +
                "St Mungo Museum regularly hosts temporary exhibitions and a variety of events, from family-friendly activities to talks relating to religion in Scotland today.\n" +
                "\n" +
                "This museum sits across from Provand’s Lordship, which is the oldest house in Glasgow, and alongside the medieval Glasgow Cathedral.  Why not take a trip to visit all three?");
        museum.setImageSrc("museum_mungo");
        db.insert(MuseumTable.NAME, null, getContentValues(museum));

        museum = new Museum();
        museum.setName("Provand’s Lordship");
        museum.setDescription("Provand’s Lordship, which was built in 1471 as part of a hospital in the cathedral precinct, is one of only four surviving medieval buildings in Glasgow. Beautifully preserved, the “auld hoose” is furnished with a fine selection of 17th-century Scottish furniture donated by Sir William Burrell, and a series of historic royal portraits. The room settings give a flavour of household interiors around 1500 and 1700, so you can immerse yourself in medieval Glasgow with a visit to this fascinating building. \n" +
                "\n" +
                "Behind the building sits the St Nicholas Garden, a medicinal herb garden which is an oasis of calm, away from the hustle and bustle of the city. Find \u200Bout more here.\n" +
                "\n" +
                "Provand’s Lordship Guidebook\n" +
                "This special guidebook gives you an insight into the building and history surrounding it. You can purchase this book for only £5.99 by calling St Mungo Museum Shop on 0141 276 1625. ");
        museum.setImageSrc("museum_provand");
        db.insert(MuseumTable.NAME, null, getContentValues(museum));

        museum = new Museum();
        museum.setName("Scotland Street School Museum");
        museum.setDescription("Scotland Street School was designed by Charles Rennie Mackintosh between 1903-1906 for the School Board of Glasgow. Now as a museum, it tells the story of 100 years of education in Scotland, from the late 19th century to the late 20th century.\n" +
                "\n" +
                "The building is a must see for Mackintosh fans, as a fantastic example of his architectural\u200B style. With many features built into the stonework and staircases, there is something to admire around every corner!\n" +
                "\n" +
                "When Scotland Street School first opened on 15 August 1906, it served a growing population employed by the then vast shipbuilding industry and the many engineering works, in and around the River Clyde. At its peak it could accommodate 1250 pupils and for 73 years generations of Glasgow children from the Kingston and Tradeston areas on the south side of the city were educated within these walls. \n" +
                "\n" +
                "The area started to change after the Second World War as the shipping industry began to decline and improvements were made to housing and transport across the city.  Residents were gradually relocated to the new towns being built outside Glasgow and the tenement flats they left behind were demolished to make way for the inner-city ring road. Gradually, the community moved away from Kingston and Scotland Street School found itself isolated in a wilderness of roads and industrial warehouses.  It closed as a school in 1979 with only 89 pupils remaining on its roll.  \n" +
                "\n" +
                "In telling the story of education during this time, Scotland Street School Museum offers a fascinating glimpse into the past. Find out what school days were like in the reign of Queen Victoria, during World War II, and in the 1950s and 60s, in our three reconstructed classrooms. You can even dress up as a pupil from the past!");
        museum.setImageSrc("museum_school");
        db.insert(MuseumTable.NAME, null, getContentValues(museum));

        museum = new Museum();
        museum.setName("Glasgow Museums Resource Centre");
        museum.setDescription("Glasgow Museums Resource Centre (GMRC) is the store for the museums' collections when they're not on display at our venues.\n" +
                "\n" +
                "It's a vast building with rooms full of fantastic objects, from animals to armour, fine art to fossils, and much, much more. The main collections stored here are Archaeology, Art and Painting, Arms and Armour, Natural History, Transport and Technology and World Cultures.\n" +
                "\n" +
                "The 17 purpose-built and environmentally controlled storage ‘pods’ house around 1.4 million objects. In most museums a tiny proportion of objects are on display and museum stores are rarely accessible to the public. Glasgow is not the only city to have a store like this, but our store is exceptional. For one thing, it is the store for one of the finest museum collections in Europe.  GMRC is different – you can explore this very special building and its’ collections through a wide range of tours, talks and activities for all ages, including school visits and events for families with children.\n" +
                "\n" +
                "It is FREE to visit GMRC, but all visits must be booked in advance.");
        museum.setImageSrc("museum_resource");
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

        article = new Article();
        article.setTitle("Senior Centre Castlemilk receives royal seal of approval");
        article.setPosted(new Date());
        article.setContent("The Senior Centre in Castlemilk has been given the royal seal of approval after receiving a Queen's Award for Voluntary Service. The highest award a voluntary group can obtain.\n" +
                "\n" +
                "The well-known centre for senior citizens has a proud record of addressing the loneliness and isolation older people can experience by offering social events, excursions and a range of learning opportunities.\n" +
                "\n" +
                "The Senior Centre is among 193 charities, social enterprises and voluntary groups that will receive this special award this year. The number of awards presented this year has risen demonstrating that the voluntary sector continues to thrive and is full innovative ideas to tackle community challenges.\n" +
                "\n" +
                "The Senior Centre is run by a team of 10 staff, including manager Melanie O'Donnell, and 35 volunteers who work hard to make members feel welcome offering highly valued advice, company, friendship and activities.\n" +
                "\n" +
                "Husband and wife volunteers John Millmaker (76) and Margaret (70) will represent the centre at a garden party at Holyrood on 5th July 2016 where they will meet the Queen and other Queen's Award recipients.\n" +
                "\n" +
                "The centre caters for more than 400 members providing a variety of classes ranging from IT and reminiscence to arts and crafts.\n" +
                "\n" +
                "It also has a subsidised café serving up to 100 meals per day. And there are regular excursions and sightseeing trips organised for members.\n" +
                "\n" +
                "The Queen's Award for Voluntary Service is the highest award that can be given recognising outstanding work in communities across the UK. The awards were created in 2002 to celebrate the Queen's Golden Jubilee. Winners are announced each year on 2 June - the anniversary of the Queen's Coronation.  \n" +
                "\n" +
                "This year's recipients range from a War Memorial Community Trust, providing social facilities for the benefit of the community in Clwyd, Wales, to a club enhancing its community by developing individuals through athletics, in Ayrshire and Arran, Scotland.\n" +
                "\n" +
                "The Senior Centre, Castlemilk, will receive its award from the Lord Lieutenant of Glasgow, Lord Provost Sadie Docherty, later this summer.\n" +
                "\n" +
                "The Queen's Award for Voluntary Service Committee Chair, former broadcast journalist Sir Martyn Lewis said: \"I warmly congratulate all of the inspirational voluntary groups who have been rewarded for their community work with a Queen's Award for Voluntary Service. The judging panel for this year's awards were struck by the quality and breadth of all the successful groups.The thousands of volunteers who give up spare time to help others in their community and to help solve problems demonstrate the very best of democracy in action.\"\n" +
                "\n" +
                "Minister for Civil Society, Rob Wilson, said: \"I would like to congratulate all groups who received this year's Queen's Award for Voluntary Service, in recognition of their fantastic achievements. The huge amount of work and commitment these organisations put into their local communities is surpassed only by the passion and motivation of the individuals who volunteer. I hope these groups continue to inspire others to get involved and make a positive impact so that we can continue to build a more compassionate society.\"\n" +
                "\n" +
                "The Lord Provost in her role as Lord Lieutenant nominated the Seniors Centre for this Queen's Award for Voluntary Service. \n" +
                "\n" +
                "The Lord Provost said: \"I'm delighted that the centre has received recognition from Her Majesty the Queen for the sterling work of its volunteers. The centre is well known and loved in Castlemilk offering company, friendship and fun activities for elderly people across the community. It really is an important lifeline for older people who can feel isolated and anxious. The centre also assist members by helping them access benefits and other help and entitlements. It also provides meals and organises social events. My thanks, on behalf of the city, to all the volunteers and everyone involved in the centre for making it such a great place.\"\n" +
                "\n" +
                "Melanie O'Donnell, Manager of The Senior Centre said: \"I am absolutely delighted that our team of hardworking, dedicated volunteers have been recognised in such a special way for all that they do within our organisation.  Most of our volunteers are members of the Centre themselves and contribute so much without realising how vital their help is.\n" +
                "\n" +
                "Our older volunteers have vast life experience, skills and knowledge that is utilised in a variety of ways across our centre. It's a pleasure to lead and work with them for they bring happiness to so many older people who depend on us daily for socialisation and friendship.\" ");
        db.insert(ArticleTable.NAME, null, getContentValues(article));

        article = new Article();
        article.setTitle("Glasgow looks to become a dementia friendly city");
        article.setPosted(new Date());
        article.setContent("Glasgow is aiming to become a friendly city as part of a new strategy to support people with dementia to live well with the condition in the community.\n" +
                "\n" +
                "Created through a collaboration between Glasgow City Health and Social Care Partnership (HSCP) and Alzheimer Scotland, the new, three-year strategy outlines a range of commitments designed to improve health and social care services for people with dementia in Glasgow.\n" +
                "\n" +
                "The new strategy was launched on May 31 in Silverburn Shopping Centre, which itself is aiming to become Scotland's first ever dementia-friendly shopping centre.\n" +
                "\n" +
                "With the number of people with dementia predicted to double in the next 25-years, the strategy intends to ensure this growing phenomena can be met with improved access to information, support, care and treatment.\n" +
                "\n" +
                "One of the main themes of the strategy is to encourage and develop resilience within communities so that the impact of dementia is recognised throughout all walks of life, whether that be a large organisation, a local service, a shop, a business, a neighbour or a friend.\n" +
                "\n" +
                "The intention is to create the kind of capacity in the community that will enable people with dementia to enjoy the best quality of life possible and to ensure they are treated with dignity and respect.\n" +
                "\n" +
                "Councillor Archie Graham, Chair of the Glasgow HSCP, said: \"Dementia is a growing issue that we can ill-afford to ignore. By putting in place longer-term strategies now, we can begin to build the kind of wide-ranging response we need to address the issue of dementia in future.\n" +
                "\n" +
                "\"Health and social care services have a vital role to play in making sure families have access to the best treatment and support there is available. But if we are to avoid people with dementia being disadvantaged even further as they go about their daily lives, then all sectors of society must play a part also.\n" +
                "\n" +
                "\"This is why I am so encouraged by Silverburn taking on board the need for us all to be more responsive to those affected by dementia.  Silverburn's aspiration for their staff and the centre environment to be dementia friendly is something I hope will be replicated across the city in the years ahead.  Our hope is that all Glaswegians recognise they can do their bit to help our city become dementia friendly.\"\n" +
                "\n" +
                "Alzheimer Scotland will be working closely with Silverburn over the next three years as the centre seeks to become a dementia-friendly community. This will involve staff training to raise awareness of the needs of those with dementia, but also addressing environmental issues such as lighting, signage and the use of colour.\n" +
                "\n" +
                "Other companies within the Silverburn complex have already started to get involved in the dementia friendly scheme and it is hoped that this process will be repeated with organisations, businesses or communities across Glasgow to see what can realistically be achieved to make their environments as dementia friendly as possible.\n" +
                "\n" +
                "Jim Pearson, Director of Policy & Research, Alzheimer Scotland, said:\"I am delighted to welcome this new dementia strategy for Glasgow which has been developed by Glasgow City Health and Social Care Partnership (GCHSPC) in collaboration with ourselves and co-produced with people with dementia, their families, carers and a wide range of stakeholders. The strategy provides a framework for action for the whole community and I look forward to seeing positive advances across the city over the next three years.\n" +
                "\n" +
                "\"There are 90,000 people living with dementia in Scotland and the number is on the rise.  If you have any questions about dementia and of the services available in Scotland call Alzheimer Scotland's 24 Helpline on 0808 808 3000.\"\n" +
                "\n" +
                "David Pierotti, General Manager at Silverburn added: \"Silverburn is committed to becoming a dementia friendly shopping centre to ensure that we're equipped to welcome all of our shoppers. We are proud to be a part of the local community and we will look forward to enhancing our facilities to keep Silverburn the region's leading shopping, dining and leisure destination.\"\n" +
                "\n" +
                "Dementia is a progressive illness caused by damage to and destruction of brain cells which gives rise to a number of symptoms including the gradual loss of memory, reasoning and communication skills. There are different types of dementia with the most common being Alzheimer's disease and vascular dementia.\n" +
                "\n" +
                "Around 4,500 people are currently known to be living in Glasgow with a diagnosis of dementia, although research suggests the true number of people with dementia in Glasgow is around 8,000. Each year around 800 people in the city receive a diagnosis of dementia.\n" +
                "\n" +
                "Alzheimer Scotland is Scotland's leading dementia organisation, providing care, activities, support, information and advice to people with dementia, their carers and their families.   The charity aims to improve public policies relating to dementia and be a voice for the 90,000 people in Scotland currently living with dementia.  For further information on Alzheimer Scotland visit www.alzscot.org.");
        db.insert(ArticleTable.NAME, null, getContentValues(article));

        article = new Article();
        article.setTitle("Good Food Glasgow to continue at the Broomielaw");
        article.setPosted(new Date());
        article.setContent("Lovers of street food in Glasgow will be able to enjoy fantastic dishes from around the world in the city centre for a little while longer with the news that Good Food Glasgow will continue at the Broomielaw.  \n" +
                "\n" +
                "This street food event came to the city centre each Saturday in the Merchant City for six weeks from 16 April and on each Friday in the Broomielaw from 22 April.  This initial pilot period has proved so successful that the decision was taken to extend the stay of Good Food Glasgow on the banks of the Clyde.\n" +
                "\n" +
                "As a result, colourful street food vehicles from operators in central Scotland will continue to offer everything from burgers, curries, American baking and gourmet sausage rolls to shellfish, fried chicken and vegan baking between 12pm - 7pm each Friday to bring a taste of the exotic to the Broomielaw.\n" +
                "\n" +
                "Other culinary delights at Good Food Glasgow include Indian, Korean, Mexican and Scottish street food, as well as home baking, seafood, ice cream, crepes and gluten- & dairy-free food.\n" +
                "\n" +
                "Councillor George Redmond, Executive Member for Job, Business and Investment at Glasgow City Council, said: \"I am delighted to say that Good Food Glasgow will be at the Broomielaw for an extended period.  These events have proven very successful, with people coming from outside the city to sample what's on offer, and we can continue to enjoy amazing street food down by the Clyde in the weeks to come.\"\n" +
                "\n" +
                "An online survey on Good Food Glasgow carried out towards the end of the initial pilot period has demonstrated the success of the event: 98% of those who visited Good Food Glasgow would like to see it continue as a regular, weekly event; an average 4 out of 5 rating for quality of food, range of choices, cost, atmosphere and facilities was given; and 19% of those who visited came from outside Glasgow.\n" +
                "\n" +
                "The city council had more than 2000 responses to a city centre survey in the summer of 2015, and a huge number of these expressed an interest in street food and street food events in what they wanted to see take place in the area.  The idea for a pilot period for Good Food Glasgow grew from this response.\n" +
                "\n" +
                "Good Food Glasgow is supported by Glasgow City Council, City Markets (Glasgow), Scottish Enterprise and the Community Food Fund.");
        db.insert(ArticleTable.NAME, null, getContentValues(article));

        article = new Article();
        article.setTitle("Exhibition in Glasgow to show stunning designs for energy project on canal banks");
        article.setPosted(new Date());
        article.setContent("A new exhibition at The Lighthouse in Glasgow will showcase stunning designs, created collaboratively by Scottish and international agencies, for a potential renewable energy project to be located at Dundashill in Port Dundas, on the banks of the Forth & Clyde canal in the city.  \n" +
                "\n" +
                "The designs were based on the idea that such a project would be developed through a collaboration between local artists, architects, landscape architects and urban planners, working in collaboration with engineers and scientists.  The aim of the project was to 'explore different ways in which energy generation can be beautiful and iconic as well as practical and integrated into communities and placemaking'.\n" +
                "\n" +
                "The exhibition - LAGI Glasgow - runs at The Lighthouse between 9 June - 29 July.  LAGI is an acronym for Land Art Generator Initiative, and the designs on display at the exhibition were created through an open design competition in 2015.  Previous LAGI competitions have taken place in the United Arab Emirates (2010), New York (2012), Copenhagen (2014) with another in Los Angeles this year.\n" +
                "\n" +
                "Port Dundas was chosen as the potential location for the project by Glasgow City Council and the founding directors of LAGI, and the site is currently being developed through the Canal Regeneration Partnership, made up of Glasgow City Council, Scottish Canals, and Igloo Regeneration.  The partnership plans to create an area with new homes as well as creative and leisure industries to complement neighbouring existing facilities such as the Whisky Bond and Pinkston Watersports.\n" +
                "\n" +
                "Following a briefing session on the Port Dundas LAGI project at the Whisky Bond, high quality expressions of interest were received from 12 teams comprised of different practitioners, including landscape architects, architects, artists, poets, engineers and science educators.  A selection panel, comprising representatives of the Canal Regeneration Partnership, Ian Gilzean, Chief Architect for Scotland, and others, selected a shortlist of three teams to move to the project's next stage.");
        db.insert(ArticleTable.NAME, null, getContentValues(article));

        article = new Article();
        article.setTitle("Positive report card for Glasgow's Psychological Services");
        article.setPosted(new Date());
        article.setContent("Education Scotland in partnership with the council have published a very positive validated self-evaluation (VSE) report for Glasgow's psychological services.\n" +
                "\n" +
                "The report, which involved an extensive number of staff from across Education Services, stakeholders and partners has identified a number of key strengths and outlined some priorities for improvement.");
        db.insert(ArticleTable.NAME, null, getContentValues(article));
    }
}

