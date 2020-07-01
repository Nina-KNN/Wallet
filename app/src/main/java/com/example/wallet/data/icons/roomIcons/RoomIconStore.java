package com.example.wallet.data.icons.roomIcons;

import android.content.Context;

import androidx.room.Room;

import com.example.wallet.data.icons.BaseIconItemStore;
import com.example.wallet.data.icons.IconObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RoomIconStore extends BaseIconItemStore {
    private IconDao iconDao;

    public RoomIconStore(Context context) {
        iconDao = Room
                .databaseBuilder(
                        context,
                        IconDataBase.class,
                        "icon-database.sqlite"
                ).allowMainThreadQueries()
                .build()
                .iconDao();
    }

    @Override
    public List<IconObject> getIconsList(boolean isProfit) {
        List<IconEntity> iconEntityList = iconDao.getIconList(isProfit);
        List<IconObject> resultList = new ArrayList<>();

        for(IconEntity iconEntity : iconEntityList) {
            resultList.add(IconConverter.iconConverter(iconEntity));
        }

        return resultList;
    }

    @Override
    public List<IconObject> getIconsList(boolean isProfit, boolean isIconVisibility) {
        List<IconEntity> iconEntityList = iconDao.getIconList(isProfit, isIconVisibility);
        List<IconObject> resultList = new ArrayList<>();

        for(IconEntity iconEntity : iconEntityList) {
            resultList.add(IconConverter.iconConverter(iconEntity));
        }

        return resultList;
    }


    @Override
    public IconObject getIconById(UUID iconId) {
        IconEntity iconEntity = iconDao.getIconById(iconId.toString());
        return IconConverter.iconConverter(iconEntity);
    }

    @Override
    public void addItemInIconObjectList(IconObject icon) {
        iconDao.add(IconConverter.iconConverter(icon));
        notifyListeners();
    }

    @Override
    public void deleteBalanceItem(IconObject icon) {
        iconDao.delete(IconConverter.iconConverter(icon));
    }

    @Override
    public void update(IconObject icon) {
        iconDao.update(IconConverter.iconConverter(icon));
    }
}
