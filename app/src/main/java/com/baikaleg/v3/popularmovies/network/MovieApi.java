package com.baikaleg.v3.popularmovies.network;

import android.content.Context;
import android.support.annotation.NonNull;

import com.baikaleg.v3.popularmovies.R;
import com.baikaleg.v3.popularmovies.dagger.scopes.ActivityScoped;

import javax.inject.Inject;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

@ActivityScoped
public class MovieApi {

    private final Context context;

    @Inject
    MovieApi(Context context) {
        this.context = context;
    }

    @NonNull
    public MovieService createService() {
        return new Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(MovieService.class);
    }
} 