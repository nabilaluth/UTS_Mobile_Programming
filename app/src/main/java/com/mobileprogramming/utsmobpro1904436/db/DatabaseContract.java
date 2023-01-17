package com.mobileprogramming.utsmobpro1904436.db;

import android.provider.BaseColumns;

public class DatabaseContract {
    public static final class NoteColumns implements BaseColumns {
        public static final String TABLE_NAME = "Notes";
        public static final String TITLE = "title";
        public static final String CATEGORY = "category";
        public static final String DESC = "description";
        public static final String FOOD = "food";


    }
}
