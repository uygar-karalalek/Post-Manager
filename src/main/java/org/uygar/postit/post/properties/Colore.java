package org.uygar.postit.post.properties;

import javafx.scene.paint.Color;

public enum Colore {

    BLU(Color.web("#81e5ff"),"blue", Color.BLACK),
    ROSSO(Color.RED, "red", Color.web("#dcdee0")),
    GIALLO(Color.YELLOW,"yellow", Color.BLACK),
    VERDE(Color.GREEN,"green", Color.WHITE),
    ARANCIONE(Color.ORANGE, "orange", Color.WHITE);

    public Color postItColor;
    public String colorName;
    public Color postItTextColor;

    Colore(Color color, String colorName, Color postItTextColor) {
        this.postItColor = color;
        this.colorName = colorName;
        this.postItTextColor = postItTextColor;
    }

    @Override
    public String toString() {
        return colorName;
    }

}