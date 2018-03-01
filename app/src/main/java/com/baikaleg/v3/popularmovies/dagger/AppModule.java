package com.baikaleg.v3.popularmovies.dagger;

import android.app.Application;
import android.content.Context;

import com.baikaleg.v3.popularmovies.dagger.modules.DetailsModule;
import com.baikaleg.v3.popularmovies.dagger.modules.MoviesModule;
import com.baikaleg.v3.popularmovies.dagger.scopes.ActivityScoped;
import com.baikaleg.v3.popularmovies.ui.details.DetailsActivity;
import com.baikaleg.v3.popularmovies.ui.movies.MoviesActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Module(includes = AndroidSupportInjectionModule.class)
public interface AppModule {
    @Binds
    Context bindContext(Application application);

    @ActivityScoped
    @ContributesAndroidInjector(modules = {MoviesModule.class})
    MoviesActivity moviesActivityInjector();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {DetailsModule.class})
    DetailsActivity detailsActivityInjector();
} 