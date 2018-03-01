package com.baikaleg.v3.popularmovies.data;

import com.baikaleg.v3.popularmovies.data.model.Movie;

import java.util.List;

import io.reactivex.Observable;

public interface DataSource {

    interface OnRepositoryResponse {

        void successfulResponse();

        void failedResponse();
    }

    Observable<List<Movie>> getMovies();

    void saveMovie(Movie movie);

    void deleteAllMovies();
}