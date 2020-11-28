package org.uygar.postit.controllers.loader;

public abstract class WindowLoader<T> implements Loader<T> {

    protected T controller;

    public WindowLoader(T controller) {
        this.controller = controller;
    }

}