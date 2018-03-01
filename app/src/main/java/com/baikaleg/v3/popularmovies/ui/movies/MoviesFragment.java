package com.baikaleg.v3.popularmovies.ui.movies;

import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.baikaleg.v3.popularmovies.R;
import com.baikaleg.v3.popularmovies.dagger.scopes.ActivityScoped;
import com.baikaleg.v3.popularmovies.data.MoviesFilterType;
import com.baikaleg.v3.popularmovies.data.model.Movie;
import com.baikaleg.v3.popularmovies.databinding.FragmentMoviesBinding;
import com.baikaleg.v3.popularmovies.ui.details.DetailsActivity;
import com.baikaleg.v3.popularmovies.ui.movies.adapter.MoviesViewAdapter;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * Display a grid of{@link Movie}. User can choose popular or top rated movie list.
 */
@ActivityScoped
public class MoviesFragment extends DaggerFragment implements
        MoviesContract.View,
        MoviesContract.OnMovieClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    private MoviesViewAdapter adapter;

    private int rows, columns;

    private FragmentMoviesBinding binding;

    @Inject
    MoviesPresenter moviesPresenter;

    @Inject
    public MoviesFragment() {
        // Requires empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        moviesPresenter.takeView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        moviesPresenter.dropView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        setRowsAndColumnsQuantity();

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false);

        // Set up movies view
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), columns);
        adapter = createAdapter();
        binding.moviesRv.setLayoutManager(layoutManager);
        binding.moviesRv.setAdapter(adapter);

        // Set up progress indicator
        binding.moviesRefresh.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );
        binding.moviesRefresh.setOnRefreshListener(this);
        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.movies_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_popular:
                moviesPresenter.setMoviesType(MoviesFilterType.POPULAR_MOVIES);
                ((MoviesActivity) getActivity()).setActionBarTitle(getString(R.string.popular));
                break;
            case R.id.menu_top_rated:
                moviesPresenter.setMoviesType(MoviesFilterType.TOP_RATED_MOVIES);
                ((MoviesActivity) getActivity()).setActionBarTitle(getString(R.string.top_rated));
                break;
            default:
                moviesPresenter.setMoviesType(MoviesFilterType.POPULAR_MOVIES);
                ((MoviesActivity) getActivity()).setActionBarTitle(getString(R.string.popular));
                break;
        }
        moviesPresenter.loadMovies();
        return true;
    }

    @Override
    public void onMovieClick(Movie movie) {
        moviesPresenter.openMovieDetails(movie);
    }

    @Override
    public void showMovies(@NonNull List<Movie> movies) {
        binding.moviesRv.setVisibility(View.VISIBLE);
        binding.moviesNoInternetView.setVisibility(View.GONE);
        adapter.refreshAdapter(movies);
    }

    @Override
    public void showNoInternetView() {
        binding.moviesRv.setVisibility(View.GONE);
        binding.moviesNoInternetView.setVisibility(View.VISIBLE);

        setLoadingIndicator(false);
    }

    @Override
    public void showMovieDetails(Movie movie) {
        //Shown in it's own Activity
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(getString(R.string.movie_key), movie);
        getActivity().startActivity(intent);
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (getView() != null) {
            // Make sure setRefreshing() is called after the layout is done with everything else.
            binding.moviesRefresh.post(() -> binding.moviesRefresh.setRefreshing(active));
        }
    }

    @Override
    public void onRefresh() {
        moviesPresenter.loadMovies();
    }

    @NonNull
    private MoviesViewAdapter createAdapter() {
        TypedValue tv = new TypedValue();
        int actionBarHeight = getActivity().getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)
                ? TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics())
                : 0;

        int imageHeight = (getResources().getDisplayMetrics().heightPixels - actionBarHeight) / rows;
        int imageWidth = getResources().getDisplayMetrics().widthPixels / columns;

        return new MoviesViewAdapter(getActivity(), imageHeight, imageWidth, this);
    }

    private void setRowsAndColumnsQuantity() {
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            rows = getResources().getInteger(R.integer.rows_portrait_mode);
            columns = getResources().getInteger(R.integer.columns_portrait_mode);
        } else if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rows = getResources().getInteger(R.integer.rows_landscape_mode);
            columns = getResources().getInteger(R.integer.columns_landscape_mode);
        }
    }
}