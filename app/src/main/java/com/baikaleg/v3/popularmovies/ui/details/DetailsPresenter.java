package com.baikaleg.v3.popularmovies.ui.details;

import android.support.annotation.NonNull;

import com.baikaleg.v3.popularmovies.dagger.scopes.ActivityScoped;
import com.baikaleg.v3.popularmovies.data.model.Movie;

import javax.inject.Inject;

@ActivityScoped
public class DetailsPresenter implements DetailsContract.Presenter {

    private DetailsContract.View detailsView;

    private Movie movie;

    @Inject
    DetailsPresenter() {
    }

    @Override
    public void takeView(DetailsContract.View view) {
        detailsView = view;
        detailsView.populateUI(movie);
    }

    @Override
    public void dropView() {
        detailsView = null;
    }

    @Override
    public void setMovie(@NonNull Movie movie) {
        this.movie = movie;
    }
}