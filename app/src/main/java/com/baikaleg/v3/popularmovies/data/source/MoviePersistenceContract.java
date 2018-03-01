package com.baikaleg.v3.popularmovies.data.source;

import android.provider.BaseColumns;

class MoviePersistenceContract {

    private MoviePersistenceContract() {
    }

    static abstract class MovieEntry implements BaseColumns {
        static final String TABLE_NAME = "movies";

        static final String ID = "id";

        static final String ORIGINAL_TITLE = "originalTitle";

        static final String OVERVIEW = "overview";

        static final String RELEASE_DATE = "releaseDate";

        static final String VOTE_AVERAGE = "voteAverage";

        static final String POSTER_PATH = "posterPath";
    }
}