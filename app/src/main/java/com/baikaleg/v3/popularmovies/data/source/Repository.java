package com.baikaleg.v3.popularmovies.data.source;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.baikaleg.v3.popularmovies.data.DataSource;
import com.baikaleg.v3.popularmovies.data.model.Movie;
import com.baikaleg.v3.popularmovies.data.source.MoviePersistenceContract.MovieEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;

public class Repository implements DataSource {

    private SQLiteDatabase database;
    private final MoviesDBHelper dbHelper;

    @Inject
    public Repository(Context context) {
        dbHelper = new MoviesDBHelper(context);
    }

    @Override
    public Observable<List<Movie>> getMovies() {
        return makeObservable(() -> {
            List<Movie> movies = new ArrayList<>();
            try (MovieCursorWrapper cursor = queryItems()) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    movies.add(cursor.getMovie());
                    cursor.moveToNext();
                }
            } finally {
                database.close();
            }
            return movies;
        });
    }

    @Override
    public void saveMovie(Movie movie) {
        try {
            database = dbHelper.getWritableDatabase();
            database.insert(MovieEntry.TABLE_NAME, null, getContentValues(movie));
        } finally {
            database.close();
        }
    }

    @Override
    public void deleteAllMovies() {
        database = dbHelper.getWritableDatabase();
        try {
            database.delete(MovieEntry.TABLE_NAME, null, null);
        } finally {
            database.close();
        }
    }

    private static <T> Observable<T> makeObservable(final Callable<T> func) {
        return Observable.create(emitter -> {
            try {
                emitter.onNext(func.call());
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    private ContentValues getContentValues(Movie movie) {
        ContentValues values = new ContentValues();
        values.put(MovieEntry.ID, movie.getId());
        values.put(MovieEntry.ORIGINAL_TITLE, movie.getOriginalTitle());
        values.put(MovieEntry.OVERVIEW, movie.getOverview());
        values.put(MovieEntry.POSTER_PATH, movie.getPosterPath());
        values.put(MovieEntry.RELEASE_DATE, movie.getReleaseDate());
        values.put(MovieEntry.VOTE_AVERAGE, movie.getVoteAverage());
        return values;
    }

    private MovieCursorWrapper queryItems() {
        database = dbHelper.getReadableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = database.query(
                MovieEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        return new MovieCursorWrapper(cursor);
    }
}