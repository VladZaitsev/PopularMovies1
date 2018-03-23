package com.baikaleg.v3.popularmovies.ui.details;

import android.content.Context;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baikaleg.v3.popularmovies.R;
import com.baikaleg.v3.popularmovies.dagger.scopes.ActivityScoped;
import com.baikaleg.v3.popularmovies.databinding.FragmentDetailsBinding;
import com.baikaleg.v3.popularmovies.data.model.Movie;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

@ActivityScoped
public class DetailsFragment extends DaggerFragment implements DetailsContract.View {

    private FragmentDetailsBinding binding;
    private int screenHeight, screenWidth;
    @Inject
    DetailsPresenter presenter;

    @Inject
    public DetailsFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.takeView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dropView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null && cm.getActiveNetworkInfo() == null) {
            Toast.makeText(getActivity(), R.string.no_internet, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false);
        setHeightAndWidthOfImage();
        return binding.getRoot();
    }

    @Override
    public void populateUI(@NonNull Movie movie) {
        binding.setMovie(movie);
        showImage(movie.getPosterPath());
    }

    private void showImage(String uri) {
        Picasso.with(getActivity())
                .load(getString(R.string.image_base_url) + uri)
                .placeholder(getContext().getResources().getDrawable(R.drawable.ic_image))
                .into(binding.detailsImg);
    }

    private void setHeightAndWidthOfImage() {
        TypedValue tv = new TypedValue();
        int actionBarHeight = getActivity().getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)
                ? TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics())
                : 0;
        int imageHeight = 0, imageWidth = 0;
        screenHeight = (getResources().getDisplayMetrics().heightPixels - actionBarHeight);
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            imageHeight = screenHeight / 2;
            imageWidth = screenWidth / 2;
        } else if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            imageWidth = screenWidth / 3;
            imageHeight = imageWidth * 4 / 3;
        }

        ViewGroup.LayoutParams params = binding.detailsImg.getLayoutParams();
        params.width = imageWidth;
        params.height = imageHeight;
        binding.detailsImg.setLayoutParams(params);
    }
}
