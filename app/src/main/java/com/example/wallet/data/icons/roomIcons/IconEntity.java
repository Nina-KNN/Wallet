package com.example.wallet.data.icons.roomIcons;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class IconEntity {
    @PrimaryKey
    @NonNull
    public String iconId;

    public int iconImage;
    public String iconName;
    public boolean iconProfit;
    public boolean iconVisibility;
    public int iconPosition;

}
