package org.uygar.postit.controllers.application.import_controller.import_controller_managers;

import org.uygar.postit.controllers.application.import_controller.ImportController;

public class ImportManager {

    protected ImportController importController;

    public ImportManager(ImportController importController) {
        this.importController = importController;
    }

}