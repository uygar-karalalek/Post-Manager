package org.uygar.postit.post.properties;

import javafx.scene.paint.Color;

public enum Colore {

    VERDE(Color.web("#88f58c"),"green", Color.BLACK),
    ROSSO(Color.web("#b51947"), "red", Color.web("#dcdee0")),
    ARANCIONE(Color.web("#fcba03"), "orange", Color.BLACK),
    GIALLO(Color.web("#f6d56a"),"yellow", Color.BLACK),
    CYAN(Color.web("#00ffe1"), "cyan", Color.BLACK),
    AZZURRO(Color.web("#81e5ff"),"lightBlue", Color.BLACK),
    BLU(Color.web("#3854e0"),"blue", Color.WHITE),
    VIOLA(Color.web("#623be3"), "viola", Color.WHITE);

    public Color postItColor;
    public String colorName;
    public Color postItTextColor;

    Colore(Color color, String colorName, Color postItTextColor) {
        this.postItColor = color;
        this.colorName = colorName;
        this.postItTextColor = postItTextColor;
    }

}