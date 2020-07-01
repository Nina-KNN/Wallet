package com.example.wallet.data.icons;

import java.util.UUID;

public class IconObject {

    private UUID iconId;
    private int iconImage;
    private String iconName;
    private boolean iconProfit;
    private boolean iconVisibility;
    private int iconPosition;

    public IconObject() {}

    public IconObject(int iconImage, String iconName, boolean iconProfit, boolean iconVisibility) {
        this.iconImage = iconImage;
        this.iconName = iconName;
        this.iconProfit = iconProfit;
        this.iconVisibility = iconVisibility;
    }

    public IconObject(UUID iconId, int iconImage, String iconName, boolean iconProfit, boolean iconVisibility, int position) {
        this.iconId = iconId;
        this.iconImage = iconImage;
        this.iconName = iconName;
        this.iconProfit = iconProfit;
        this.iconVisibility = iconVisibility;
        this.iconPosition = position;
    }

    public UUID getIconId() {
        return iconId;
    }

    public void setIconId(UUID iconId) {
        this.iconId = iconId;
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

    public boolean isIconProfit() {
        return iconProfit;
    }

    public void setIconProfit(boolean iconProfit) {
        this.iconProfit = iconProfit;
    }

    public boolean isIconVisibility() {
        return iconVisibility;
    }

    public void setIconVisibility(boolean iconVisibility) {
        this.iconVisibility = iconVisibility;
    }

    public int getIconPosition() {
        return iconPosition;
    }

    public void setIconPosition(int iconPosition) {
        this.iconPosition = iconPosition;
    }
}
