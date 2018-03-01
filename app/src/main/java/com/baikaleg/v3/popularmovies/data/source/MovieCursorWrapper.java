package com.baikaleg.v3.popularmovies.data.source;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.baikaleg.v3.popularmovies.data.model.Movie;
import com.baikaleg.v3.popularmovies.data.source.MoviePersistenceContract.MovieEntry;

class MovieCursorWrapper extends CursorWrapper {

    MovieCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    Movie getMovie() {
        int id = getInt(getColumnIndex(MovieEntry.ID));
        String originalTitle = getString(getColumnIndex(MovieEntry.ORIGINAL_TITLE));
        String overview = getString(getColumnIndex(MovieEntry.OVERVIEW));
        String posterPath = getString(getColumnIndex(MovieEntry.POSTER_PATH));
        String releaseDate = getString(getColumnIndex(MovieEntry.RELEASE_DATE));
        double voteAverage = getDouble(getColumnIndex(MovieEntry.VOTE_AVERAGE));

        return new Movie(id, originalTitle, overview, posterPath, releaseDate, voteAverage);
    }
}