package com.baikaleg.v3.popularmovies.data.source;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.baikaleg.v3.popularmovies.data.source.MoviePersistenceContract.MovieEntry;

public class MoviesDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "movies_db";

    private static final int DATABASE_VERSION = 1;

    private static final String COMMA_SEP = ",";

    private static final String TEXT_TYPE = " TEXT";

    private static final String INTEGER_TYPE = " INTEGER";

    private static final String DOUBLE_TYPE = " DOUBLE";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
                    MovieEntry.ID + INTEGER_TYPE + " PRIMARY KEY," +
                    MovieEntry.POSTER_PATH + TEXT_TYPE + COMMA_SEP +
                    MovieEntry.ORIGINAL_TITLE + TEXT_TYPE + COMMA_SEP +
                    MovieEntry.OVERVIEW + TEXT_TYPE + COMMA_SEP +
                    MovieEntry.RELEASE_DATE + TEXT_TYPE + COMMA_SEP +
                    MovieEntry.VOTE_AVERAGE + DOUBLE_TYPE +
                    " )";


    MoviesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Not required as at version 1
    }
}