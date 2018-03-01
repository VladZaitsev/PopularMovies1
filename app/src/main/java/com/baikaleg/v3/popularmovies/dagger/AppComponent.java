package com.baikaleg.v3.popularmovies.dagger;

import android.app.Application;

import com.baikaleg.v3.popularmovies.data.source.Repository;
import com.baikaleg.v3.popularmovies.network.MovieApi;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent extends AndroidInjector<DaggerApplication> {

    @Override
    void inject(DaggerApplication instance);

    void inject(MovieApi movieApi);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }

    Repository getRepository();
} 