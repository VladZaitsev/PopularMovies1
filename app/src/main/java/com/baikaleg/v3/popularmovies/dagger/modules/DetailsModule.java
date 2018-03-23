package com.baikaleg.v3.popularmovies.dagger.modules;

import com.baikaleg.v3.popularmovies.dagger.scopes.ActivityScoped;
import com.baikaleg.v3.popularmovies.dagger.scopes.FragmentScoped;
import com.baikaleg.v3.popularmovies.data.model.Movie;
import com.baikaleg.v3.popularmovies.ui.details.DetailsActivity;
import com.baikaleg.v3.popularmovies.ui.details.DetailsContract;
import com.baikaleg.v3.popularmovies.ui.details.DetailsFragment;
import com.baikaleg.v3.popularmovies.ui.details.DetailsPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public interface DetailsModule {

    @Provides
    @ActivityScoped
    static Movie provideMovie(DetailsActivity activity) {
        return activity.getIntent().getParcelableExtra(DetailsActivity.EXTRA_MOVIE);
    }

    @FragmentScoped
    @ContributesAndroidInjector
    DetailsFragment detailsFragment();

    @ActivityScoped
    @Binds
    DetailsContract.Presenter detailsPresenter(DetailsPresenter presenter);
} 