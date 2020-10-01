package org.uygar.postit.post.properties;

import javafx.scene.paint.Color;

public enum Colore {

    BLU("blue", Color.WHITE),
    ROSSO("red", Color.WHITE),
    GIALLO("yellow", Color.BLACK),
    VERDE("green", Color.WHITE),
    ARANCIONE("orange", Color.WHITE);

    public String colorName;
    public Color postItTextColor;

    Colore(String colorName, Color postItTextColor) {
        this.colorName = colorName;
        this.postItTextColor = postItTextColor;
    }

    @Override
    public String toString() {
        return colorName;
    }

}