package com.example.wallet.data.icons;

public class IconObject {

    private int iconImage;
    private String iconName;
    private boolean profit;
    private boolean iconVisibility;

    public IconObject(int iconImage, String iconName, boolean profit, boolean iconVisibility) {
        this.iconImage = iconImage;
        this.iconName = iconName;
        this.profit = profit;
        this.iconVisibility = iconVisibility;
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

    public boolean isProfit() {
        return profit;
    }

    public void setProfit(boolean profit) {
        this.profit = profit;
    }

    public boolean isIconVisibility() {
        return iconVisibility;
    }

    public void setIconVisibility(boolean iconVisibility) {
        this.iconVisibility = iconVisibility;
    }
}
