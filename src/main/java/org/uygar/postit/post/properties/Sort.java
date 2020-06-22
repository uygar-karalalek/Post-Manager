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

    public static Sort getSortFromName(String name) {
        for (Sort element : values())
            if (element.name.equals(name))
                return element;
        return null;
    }

}