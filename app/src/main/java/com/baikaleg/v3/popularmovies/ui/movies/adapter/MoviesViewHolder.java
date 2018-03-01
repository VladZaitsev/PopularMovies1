package com.baikaleg.v3.popularmovies.ui.movies.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.baikaleg.v3.popularmovies.databinding.ItemMoviesAdapterBinding;

class MoviesViewHolder extends RecyclerView.ViewHolder {

    final ItemMoviesAdapterBinding binding;

    MoviesViewHolder(View itemView) {
        super(itemView);
        binding =DataBindingUtil.bind(itemView);
    }
}