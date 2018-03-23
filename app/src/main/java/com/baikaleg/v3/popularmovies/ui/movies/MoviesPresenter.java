package com.baikaleg.v3.popularmovies.ui.movies;

import android.support.annotation.NonNull;

import com.baikaleg.v3.popularmovies.dagger.scopes.ActivityScoped;
import com.baikaleg.v3.popularmovies.data.MoviesFilterType;
import com.baikaleg.v3.popularmovies.data.model.Movie;
import com.baikaleg.v3.popularmovies.data.source.Repository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@ActivityScoped
public class MoviesPresenter implements MoviesContract.Presenter {

    private MoviesContract.View moviesView;

    @NonNull
    private final CompositeDisposable compositeDisposable;

    private final Repository repository;

    private MoviesFilterType currentType = MoviesFilterType.POPULAR_MOVIES;

    @Inject
    MoviesPresenter(Repository repository) {
        this.repository = repository;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void loadMovies(final boolean forceUpdate) {
        loadMovies(forceUpdate, true);
    }

    private void loadMovies(final boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            moviesView.setLoadingIndicator(true);
        }
        if (forceUpdate) {
            repository.refreshMovies();
        }
        compositeDisposable.clear();
        Disposable disposable = repository.getMovies(currentType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movies -> {
                    moviesView.setLoadingIndicator(false);
                    moviesView.showMovies(movies);
                }, throwable -> {
                    moviesView.setLoadingIndicator(false);
                    moviesView.showNoInternetView();
                });

        compositeDisposable.add(disposable);
    }

    @Override
    public void openMovieDetails(Movie movie) {
        moviesView.showMovieDetails(movie);
    }

    @Override
    public void setMoviesType(@NonNull MoviesFilterType type) {
        currentType = type;
    }

    @Override
    public MoviesFilterType getMoviesType() {
        return currentType;
    }

    @Override
    public void takeView(MoviesContract.View view) {
        moviesView = view;
        loadMovies(false);
    }

    @Override
    public void dropView() {
        moviesView = null;
        compositeDisposable.clear();
    }
}