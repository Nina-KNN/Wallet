package com.example.wallet.data.icons.roomIcons;

import com.example.wallet.data.icons.IconObject;

import java.util.UUID;

public class IconConverter {
    // Конвертировать объекты IconEntity в IconObject
    static IconObject iconConverter(IconEntity iconEntity) {
        IconObject icon = new IconObject();

        icon.setIconId(UUID.fromString(iconEntity.iconId));
        icon.setIconImage(iconEntity.iconImage);
        icon.setIconName(iconEntity.iconName);
        icon.setIconProfit(iconEntity.iconProfit);
        icon.setIconVisibility(iconEntity.iconVisibility);
        icon.setIconPosition(iconEntity.iconPosition);

        return icon;
    }


    // Конвертировать объекты IconObject в IconEntity
    static IconEntity iconConverter(IconObject icon) {
        IconEntity iconEntity = new IconEntity();

        iconEntity.iconId = icon.getIconId().toString();
        iconEntity.iconImage = icon.getIconImage();
        iconEntity.iconName = icon.getIconName();
        iconEntity.iconProfit = icon.isIconProfit();
        iconEntity.iconVisibility = icon.isIconVisibility();
        iconEntity.iconPosition = icon.getIconPosition();

        return iconEntity;
    }

}
