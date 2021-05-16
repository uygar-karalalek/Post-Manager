package org.uygar.postit.controllers.application.app;

public class AppManager {

    protected AppController appController;

    public AppManager(AppController controller) {
        this.appController = controller;
    }

}