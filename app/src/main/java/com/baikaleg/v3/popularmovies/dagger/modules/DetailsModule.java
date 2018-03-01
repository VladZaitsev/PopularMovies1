package com.baikaleg.v3.popularmovies.dagger.modules;

import com.baikaleg.v3.popularmovies.dagger.scopes.ActivityScoped;
import com.baikaleg.v3.popularmovies.dagger.scopes.FragmentScoped;
import com.baikaleg.v3.popularmovies.ui.details.DetailsContract;
import com.baikaleg.v3.popularmovies.ui.details.DetailsFragment;
import com.baikaleg.v3.popularmovies.ui.details.DetailsPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface DetailsModule {
    @FragmentScoped
    @ContributesAndroidInjector
    DetailsFragment detailsFragment();

    @ActivityScoped
    @Binds
    DetailsContract.Presenter detailsPresenter(DetailsPresenter presenter);
} 