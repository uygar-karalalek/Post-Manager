package org.uygar.postit.controllers.application.app_controller.app_controller_managers;

import org.uygar.postit.controllers.application.app_controller.AppController;

public class AppManager {

    protected AppController appController;

    public AppManager(AppController controller) {
        this.appController = controller;
    }

}