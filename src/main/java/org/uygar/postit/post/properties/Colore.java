package org.uygar.postit.post.properties;

import javafx.scene.paint.Color;

public enum Colore {

    BLU(Color.web("#81e5ff"),"blue", Color.BLACK),
    ROSSO(Color.web("#b51947"), "red", Color.web("#dcdee0")),
    GIALLO(Color.web("#f6d56a"),"yellow", Color.BLACK),
    VERDE(Color.web("#88f58c"),"green", Color.BLACK),
    ARANCIONE(Color.web("#fcba03"), "orange", Color.BLACK),
    VIOLA(Color.web("#623be3"), "viola", Color.WHITE),
    CYAN(Color.web("#00ffe1"), "cyan", Color.BLACK);

    public Color postItColor;
    public String colorName;
    public Color postItTextColor;

    Colore(Color color, String colorName, Color postItTextColor) {
        this.postItColor = color;
        this.colorName = colorName;
        this.postItTextColor = postItTextColor;
    }

}