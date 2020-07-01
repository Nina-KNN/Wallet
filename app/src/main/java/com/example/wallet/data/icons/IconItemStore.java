package com.example.wallet.data.icons;

import java.util.List;
import java.util.UUID;

public interface IconItemStore {
    // Вывести список категорий в зависимости от того это "Доход" или "Расход"
    List<IconObject> getIconsList(boolean isProfit);

    // Вывести список категорий в зависимости от того это "Доход" или "Расход"
    // Если "isConsiderIconVisibility = true" - отображать список только с "iconVisibility = true"
    List<IconObject> getIconsList(boolean isProfit, boolean isIconVisibility);

    IconObject getIconById(UUID iconId);

    // Добавить новый объект в список объектов типа IconObject
    void addItemInIconObjectList(IconObject icon);

    //Удаление объектов
    void deleteBalanceItem(IconObject icon);

    void update(IconObject icon);

    void addListener(Listener listener);

    void removeListener(Listener listener);

    public interface Listener {
        void onIconListChange();
    }

}
