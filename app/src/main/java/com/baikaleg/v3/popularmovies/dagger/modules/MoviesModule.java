package com.baikaleg.v3.popularmovies.dagger.modules;

import com.baikaleg.v3.popularmovies.dagger.scopes.ActivityScoped;
import com.baikaleg.v3.popularmovies.dagger.scopes.FragmentScoped;
import com.baikaleg.v3.popularmovies.ui.movies.MoviesContract;
import com.baikaleg.v3.popularmovies.ui.movies.MoviesFragment;
import com.baikaleg.v3.popularmovies.ui.movies.MoviesPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface MoviesModule {

    @FragmentScoped
    @ContributesAndroidInjector
    MoviesFragment moviesFragment();

    @ActivityScoped
    @Binds
    MoviesContract.Presenter moviesPresenter(MoviesPresenter presenter);
} 