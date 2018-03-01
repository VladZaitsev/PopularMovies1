package com.baikaleg.v3.popularmovies;

import com.baikaleg.v3.popularmovies.dagger.AppComponent;
import com.baikaleg.v3.popularmovies.dagger.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class App extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        AppComponent appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
        return appComponent;
    }
}