package com.alexmcbride.glasgowcitycouncilapp;

public class DbSchema {
    public class MuseumTable {
        public static final String NAME = "Museum";

        public class Columns {
            public static final String ID = "_id";
            public static final String NAME = "name";
            public static final String DESCRIPTION = "description";
            public static final String IMAGE_SRC = "imageSrc";
        }
    }

    public class ArticleTable {
        public static final String NAME = "Article";

        public class Columns {
            public static final String ID = "_id";
            public static final String TITLE = "title";
            public static final String POSTED = "posted";
            public static final String CONTENT = "content";
        }
    }

    public class LoginTable {
        public static final String NAME = "Login";

        public class Columns {
            public static final String USERNAME = "username";
            public static final String PASSWORD = "password";
        }
    }

    public class UserTable {
        public static final String NAME = "User";

        public class Columns {
            public static final String USERNAME = "username";
            public static final String FIRST_NAME = "firstName";
            public static final String LAST_NAME = "lastName";
            public static final String EMAIL = "email";
        }
    }

    public class PostTable {
        public static final String NAME = "Post";

        public class Columns {
            public static final String ID = "_id";
            public static final String USERNAME = "username";
            public static final String POSTED = "posted";
            public static final String TITLE = "title";
            public static final String CONTENT = "content";
            public static final String COMMENT_COUNT = "commentCount";
        }
    }

    public class CommentTable {
        public static final String NAME = "Comment";

        public class Columns {
            public static final String ID = "_id";
            public static final String POST_ID = "postId";
            public static final String USERNAME = "username";
            public static final String POSTED = "posted";
            public static final String CONTENT = "content";
        }
    }
}
