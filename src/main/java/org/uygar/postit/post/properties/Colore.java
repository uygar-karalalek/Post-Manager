package org.uygar.postit.post.properties;

import javafx.scene.paint.Color;

public enum Colore {

    ROSSO(1, Color.web("#ba2d16"), "red", Color.web("#dcdee0")),

    ARANCIONE(2, Color.web("#fcba03"), "orange", Color.BLACK),

    GIALLO(3, Color.web("#f6d56a"),"yellow", Color.BLACK),

    VERDE(4, Color.web("#88f58c"),"green", Color.BLACK),

    CYAN(5, Color.web("#00ffe1"), "cyan", Color.BLACK),

    AZZURRO(6, Color.web("#81e5ff"),"lightBlue", Color.BLACK),

    AZZURRO_SCURO(6, Color.web("#21bceb"),"darkCyan", Color.web("#dcdee0")),

    BLU(7, Color.web("#3854e0"),"blue", Color.WHITE),

    VIOLA(8, Color.web("#623be3"), "viola", Color.WHITE);

    public int colorId;
    public Color postItColor;
    public String colorName;
    public Color postItTextColor;

    Colore(int colorId, Color color, String colorName, Color postItTextColor) {
        this.colorId = colorId;
        this.postItColor = color;
        this.colorName = colorName;
        this.postItTextColor = postItTextColor;
    }

}