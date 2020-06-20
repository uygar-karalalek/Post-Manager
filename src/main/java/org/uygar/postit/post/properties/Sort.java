package org.uygar.postit.post.properties;

public enum Sort {

    DONE("Task finite"), UNDONE("Task non finite");
    String name;

    Sort(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}