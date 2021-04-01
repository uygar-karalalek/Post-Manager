package org.uygar.postit.post.properties;

import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

public enum Colore {

    ROSSO(1, Color.web("#fc4e4e"), "red", Color.web("#dcdee0")),

    ARANCIONE(2, Color.web("#fcba03"), "orange", Color.web("#34373b")),

    GIALLO(3, Color.web("#f6d56a"),"yellow", Color.web("#34373b")),

    VERDE(4, Color.web("#88f58c"),"green", Color.web("#34373b")),

    CYAN(5, Color.web("#00ffe1"), "cyan", Color.web("#34373b")),

    AZZURRO(6, Color.web("#81e5ff"),"lightBlue", Color.web("#34373b")),

    AZZURRO_SCURO(6, Color.web("#21bceb"),"darkCyan", Color.web("#34373b")),

    BLU(7, Color.web("#3854e0"),"blue", Color.WHITE),

    VIOLA(8, Color.web("#623be3"), "purple", Color.WHITE);

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

    public String getPostItTextColorWebFormat() {
        return getWebFormatColor(postItTextColor);
    }

    public String getPostItColorWebFormat() {
        return getWebFormatColor(postItColor);
    }

    @NotNull
    private String getWebFormatColor(Color color) {
        int redInt = (int) (color.getRed() * 255);
        String red = getHexFromCode(redInt);

        int greenInt = (int) (color.getGreen() * 255);
        String green = getHexFromCode(greenInt);

        int blueInt = (int) (color.getBlue() * 255);
        String blue = getHexFromCode(blueInt);

        return "#" + red + green + blue;
    }

    private String getHexFromCode(int code) {
        String hex = Integer.toHexString(code);
        return hex.length() == 1 ? "0"+hex : hex;
    }

    public String getPostItColorRGBWebFormat() {
        return getRGBWebFormat(postItColor);
    }

    private String getRGBWebFormat(Color color) {
        return "rgb(" + (color.getRed() * 255)
                + ", " +
                (color.getGreen() * 255)
                + ", " +
                (color.getBlue() * 255) + ");";
    }

}