package com.example.wallet.data;

public class IconObject {
    private int iconImage;
    private String iconName;
    private String id; // изменить на  UUID
    private boolean profit;

    public IconObject(int iconImage, String iconName, String id, boolean profit) {
        this.iconImage = iconImage;
        this.iconName = iconName;
        this.id = id;
        this.profit = profit;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isProfit() {
        return profit;
    }

    public void setProfit(boolean profit) {
        this.profit = profit;
    }
}
