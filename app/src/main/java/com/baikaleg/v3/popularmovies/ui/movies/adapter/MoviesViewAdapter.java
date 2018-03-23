package com.baikaleg.v3.popularmovies.ui.movies.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.baikaleg.v3.popularmovies.R;
import com.baikaleg.v3.popularmovies.databinding.ItemMoviesAdapterBinding;
import com.baikaleg.v3.popularmovies.data.model.Movie;
import com.baikaleg.v3.popularmovies.ui.movies.MoviesContract;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MoviesViewAdapter extends RecyclerView.Adapter<MoviesViewHolder> {

    private final List<Movie> movies = new ArrayList<>();

    private final MoviesContract.OnMovieClickListener listener;

    private final int viewHeight;
    private final int viewWidth;
    private final Context context;

    public MoviesViewAdapter(Context context, int viewHeight, int viewWidth, MoviesContract.OnMovieClickListener listener) {
        this.listener = listener;
        this.viewHeight = viewHeight;
        this.viewWidth = viewWidth;
        this.context = context;
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemMoviesAdapterBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.item_movies_adapter, parent, false);
        binding.getRoot().setLayoutParams(new ViewGroup.LayoutParams(viewWidth, viewHeight));

        return new MoviesViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {
        Movie movie = movies.get(position);
        Picasso.with(context)
                .load(context.getString(R.string.image_base_url) + movie.getPosterPath())
                .fit()
                .placeholder(context.getResources().getDrawable(R.drawable.ic_image))
                .into(holder.binding.movieItemImg);
        holder.binding.movieItemImg.setOnClickListener(view -> listener.onMovieClick(movie));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void refreshAdapter(@NonNull List<Movie> movies) {
        this.movies.clear();
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }
}