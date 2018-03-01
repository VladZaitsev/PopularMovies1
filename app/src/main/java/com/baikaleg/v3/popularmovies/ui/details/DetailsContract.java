package com.baikaleg.v3.popularmovies.ui.details;

import android.support.annotation.NonNull;

import com.baikaleg.v3.popularmovies.data.model.Movie;
import com.baikaleg.v3.popularmovies.ui.BasePresenter;
import com.baikaleg.v3.popularmovies.ui.BaseView;

public interface DetailsContract {

    interface View extends BaseView<Presenter> {

        void populateUI(@NonNull Movie movie);
    }

    interface Presenter extends BasePresenter<View> {

        void setMovie(@NonNull Movie movie);

    }
} 