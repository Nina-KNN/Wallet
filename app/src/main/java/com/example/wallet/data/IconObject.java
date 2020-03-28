package com.example.wallet.data;

public class IconObject {
    private int iconImage;
    private String iconName;

    public IconObject(int iconImage, String iconName) {
        this.iconImage = iconImage;
        this.iconName = iconName;
    }

    public int getIconImage() {
        return iconImage;
    }

    public void setIconImage(int iconImage) {
        this.iconImage = iconImage;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }
}
