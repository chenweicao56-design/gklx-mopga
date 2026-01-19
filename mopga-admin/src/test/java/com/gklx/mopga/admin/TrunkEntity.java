package com.gklx.mopga.admin;

public class TrunkEntity {

    private String text;

    private String fontFamily;

    private String color;

    private String fontSize;

    private String textAlign;

    private String textWeight;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFontSize() {
        return fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public String getTextAlign() {
        return textAlign;
    }

    public void setTextAlign(String textAlign) {
        this.textAlign = textAlign;
    }

    public String getTextWeight() {
        return textWeight;
    }

    public void setTextWeight(String textWeight) {
        this.textWeight = textWeight;
    }

    @Override
    public String toString() {
        return "TrunkEntity{" +
                "text='" + text + '\'' +
                ", fontFamily='" + fontFamily + '\'' +
                ", color='" + color + '\'' +
                ", fontSize='" + fontSize + '\'' +
                ", textAlign='" + textAlign + '\'' +
                ", textWeight='" + textWeight + '\'' +
                '}';
    }
}
