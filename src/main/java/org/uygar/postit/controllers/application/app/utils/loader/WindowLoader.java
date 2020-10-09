package org.uygar.postit.controllers.application.app.utils.loader;

import org.uygar.postit.controllers.application.app.AppController;

public abstract class WindowLoader {

    AppController appController;

    public WindowLoader(AppController controller) {
        this.appController = controller;
    }

    public abstract void load();

}