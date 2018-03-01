package com.baikaleg.v3.popularmovies.ui.movies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.baikaleg.v3.popularmovies.R;
import com.baikaleg.v3.popularmovies.dagger.scopes.ActivityScoped;
import com.baikaleg.v3.popularmovies.data.MoviesFilterType;
import com.baikaleg.v3.popularmovies.data.model.Movie;
import com.baikaleg.v3.popularmovies.data.model.MoviesResponse;
import com.baikaleg.v3.popularmovies.data.source.Repository;
import com.baikaleg.v3.popularmovies.network.MovieApi;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@ActivityScoped
public class MoviesPresenter implements MoviesContract.Presenter {

    private MoviesContract.View moviesView;

    @NonNull
    private final CompositeDisposable compositeDisposable;

    private final MovieApi movieApi;

    private final Repository repository;

    private boolean isTypeChanged = true;

    private String currentType = MoviesFilterType.POPULAR_MOVIES;

    private Context context;

    @Inject
    MoviesPresenter(Context context, MovieApi movieApi, Repository repository) {
        this.context = context;
        this.movieApi = movieApi;
        this.repository = repository;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void loadMovies() {
        moviesView.setLoadingIndicator(true);
        compositeDisposable.clear();
        loadMovies(currentType);
    }

    private void loadMovies(String type) {
        Observable<MoviesResponse> response = null;
        if (type.equals(MoviesFilterType.POPULAR_MOVIES)) {
            response = movieApi.createService()
                    .getPopularMovies();
        } else if (type.equals(MoviesFilterType.TOP_RATED_MOVIES)) {
            response = movieApi.createService()
                    .getTopRatedMovies();
        }
        if (response != null) {
            compositeDisposable.add(response.map(MoviesResponse::getMovies)
                    .flatMap(movies -> {
                        repository.deleteAllMovies();
                        isTypeChanged = false;
                        for (int i = 0; i < movies.size(); i++) {
                            repository.saveMovie(movies.get(i));
                        }
                        return Observable.just(movies);
                    })
                    .onErrorResumeNext(throwable -> {
                        if (!isTypeChanged) {
                            Toast.makeText(context, R.string.no_internet, Toast.LENGTH_SHORT).show();
                            return repository.getMovies();
                        } else {
                            return null;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(movies -> {
                        moviesView.setLoadingIndicator(false);
                        moviesView.showMovies(movies);
                    }, throwable -> {
                        moviesView.setLoadingIndicator(false);
                        moviesView.showNoInternetView();
                    }));
        }
    }

    @Override
    public void openMovieDetails(Movie movie) {
        moviesView.showMovieDetails(movie);
    }

    @Override
    public void setMoviesType(@NonNull String type) {
        currentType = type;
        isTypeChanged = true;
    }

    @Override
    public String getMoviesType() {
        return currentType;
    }

    @Override
    public void takeView(MoviesContract.View view) {
        moviesView = view;
        loadMovies();
    }

    @Override
    public void dropView() {
        moviesView = null;
        compositeDisposable.clear();
    }
}