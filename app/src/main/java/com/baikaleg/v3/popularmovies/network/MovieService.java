package com.baikaleg.v3.popularmovies.network;

import com.baikaleg.v3.popularmovies.BuildConfig;
import com.baikaleg.v3.popularmovies.data.model.MoviesResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface MovieService {

    @GET("popular/?api_key=" + BuildConfig.API_KEY)
    Observable<MoviesResponse> getPopularMovies();

    @GET("top_rated/?api_key="+ BuildConfig.API_KEY)
    Observable<MoviesResponse> getTopRatedMovies();
} 