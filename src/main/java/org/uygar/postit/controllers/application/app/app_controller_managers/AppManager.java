package org.uygar.postit.controllers.application.app.app_controller_managers;

import org.uygar.postit.controllers.application.app.AppController;

public class AppManager {

    protected AppController appController;

    public AppManager(AppController controller) {
        this.appController = controller;
    }

}