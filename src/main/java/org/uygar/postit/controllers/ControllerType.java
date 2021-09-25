package org.uygar.postit.controllers;

public enum ControllerType {

    APPLICATION("application"),
    ADD("add"),
    FILTER("filter"),
    STATISTICA("statistica"),
    POST("post"),
    IMPORT("import"),
    POSTIT("postIt");

    public String controllerName;

    ControllerType(String value) {
        this.controllerName = value;
    }

}