package com.baikaleg.v3.popularmovies.data;

import com.baikaleg.v3.popularmovies.data.model.Movie;

import java.util.List;

import io.reactivex.Observable;

public interface DataSource {

    Observable<List<Movie>> getMovies(MoviesFilterType type);

    void refreshMovies();

}