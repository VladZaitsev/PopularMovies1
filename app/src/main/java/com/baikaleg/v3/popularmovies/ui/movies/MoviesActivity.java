package com.baikaleg.v3.popularmovies.ui.movies;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.baikaleg.v3.popularmovies.R;
import com.baikaleg.v3.popularmovies.data.MoviesFilterType;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MoviesActivity extends DaggerAppCompatActivity {

    @Inject
    MoviesFragment fragment;

    @Inject
    MoviesPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MoviesFragment moviesFragment = (MoviesFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (moviesFragment == null) {
            // Get the fragment from dagger
            moviesFragment = fragment;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment, moviesFragment);
            transaction.commit();
        }

        if (savedInstanceState != null && savedInstanceState.containsKey(getString(R.string.type_movies_key))) {
            String currentType =  savedInstanceState.getString(getString(R.string.type_movies_key));
            if (currentType != null) {
                presenter.setMoviesType(currentType);
            }
        }
        titleSetting();
    }

    @SuppressWarnings("ConstantConditions")
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(getString(R.string.type_movies_key), presenter.getMoviesType());
        super.onSaveInstanceState(outState);
    }

    private void titleSetting() {
        if(presenter.getMoviesType().equals(MoviesFilterType.POPULAR_MOVIES)){
            setTitle(getString(R.string.popular));
        }else if(presenter.getMoviesType().equals(MoviesFilterType.TOP_RATED_MOVIES)){
            setTitle(getString(R.string.top_rated));
        }
    }
}
