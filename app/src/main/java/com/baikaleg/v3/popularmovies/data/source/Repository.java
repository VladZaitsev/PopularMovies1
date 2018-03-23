package com.baikaleg.v3.popularmovies.data.source;

import android.support.annotation.Nullable;

import com.baikaleg.v3.popularmovies.data.DataSource;
import com.baikaleg.v3.popularmovies.data.MoviesFilterType;
import com.baikaleg.v3.popularmovies.data.model.Movie;
import com.baikaleg.v3.popularmovies.data.model.MoviesResponse;
import com.baikaleg.v3.popularmovies.network.MovieApi;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class Repository implements DataSource {
    private final MovieApi movieApi;
    private boolean cacheIsDirty = false;

    @Nullable
    private Map<Integer, Movie> cachedMovies;

    @Inject
    public Repository(MovieApi movieApi) {
        this.movieApi = movieApi;
    }

    @Override
    public Observable<List<Movie>> getMovies(MoviesFilterType type) {
        if (cachedMovies != null && !cacheIsDirty) {
            return Observable.fromIterable(cachedMovies.values()).toList().toObservable();
        }
        if (cachedMovies == null) {
            cachedMovies = new LinkedHashMap<>();
        }
        if (cacheIsDirty) {
            cachedMovies.clear();
        }
        if (type == MoviesFilterType.POPULAR_MOVIES) {
            return getPopularMovies();
        } else if (type == MoviesFilterType.TOP_RATED_MOVIES) {
            return getTopRatedMovies();
        }
        return null;
    }

    @Override
    public void refreshMovies() {
        cacheIsDirty = true;
    }

    private Observable<List<Movie>> getPopularMovies() {
        return movieApi.createService()
                .getPopularMovies()
                .map(MoviesResponse::getMovies)
                .flatMap(movies -> Observable.fromIterable(movies)
                        .doOnNext(movie -> cachedMovies.put(movie.getId(), movie))
                        .toList()
                        .toObservable()
                );
    }

    private Observable<List<Movie>> getTopRatedMovies() {
        return movieApi.createService()
                .getTopRatedMovies()
                .map(MoviesResponse::getMovies)
                .flatMap(movies -> Observable.fromIterable(movies)
                        .doOnNext(movie -> cachedMovies.put(movie.getId(), movie))
                        .toList()
                        .toObservable()
                );
    }
}