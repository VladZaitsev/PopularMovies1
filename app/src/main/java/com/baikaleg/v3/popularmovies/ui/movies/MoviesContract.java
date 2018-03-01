package com.baikaleg.v3.popularmovies.ui.movies;

import android.support.annotation.NonNull;

import com.baikaleg.v3.popularmovies.data.model.Movie;
import com.baikaleg.v3.popularmovies.ui.BasePresenter;
import com.baikaleg.v3.popularmovies.ui.BaseView;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface MoviesContract {

    /**
     * Listener for clicks on movies in the RecyclerView.
     */
    interface OnMovieClickListener {
        void onMovieClick(Movie movie);
    }

    interface View extends BaseView<Presenter> {

        void showMovies(@NonNull List<Movie> movies);

        void showNoInternetView();

        void showMovieDetails(Movie movie);

        void setLoadingIndicator(final boolean active);
    }

    interface Presenter extends BasePresenter<View> {
        void loadMovies();

        void openMovieDetails(Movie movie);

        void setMoviesType(@NonNull String type);

        String getMoviesType();
    }
} 