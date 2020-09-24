package org.uygar.postit.post.properties;

public enum Colore {

    BLU("blue"),
    ROSSO("red"),
    GIALLO("yellow"),
    VERDE("green"),
    ARANCIONE("orange");

    String colorName;

    Colore(String colorName) {
        this.colorName = colorName;
    }

    @Override
    public String toString() {
        return colorName;
    }

}