
package com.baikaleg.v3.popularmovies.ui;

public interface BasePresenter<T> {

    void takeView(T view);

    void dropView();

}
