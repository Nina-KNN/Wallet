package com.example.wallet.data.icons;

import android.content.Context;

import com.example.wallet.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreateIconsList {

    private static List<IconObject> createIconList() {
        List<IconObject> iconsList = new ArrayList<>();

        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.ex_cocktail, "cocktail", false, true, 1));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.ex_cat, "cat", false, true, 2));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.ex_diploma, "diploma", false, true, 3));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.ex_flower, "flower", false, true, 4));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.ex_house, "house", false, true, 5));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.ex_palm, "rest", false, true, 6));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.ex_plane, "plane",false, true, 7));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.ex_cup, "cup", false, true, 8));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.ex_present, "present", false, true, 9));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.ex_shoes, "shoes", false, true, 10));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.ex_toys, "toys", false, true, 11));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.ex_travel_suitcase, "travel_suitcase", false, true, 12));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.ex_tv, "tv", false, true, 13));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.ex_womens_boots, "womens_boots", false, true, 14));

        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.p_coins_1, "coins_1", true, true, 15));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.p_command_work_1, "command_work", true, true, 16));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.p_cash_back, "cash_back", true, true, 17));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.p_command_work_2, "command_work_2", true, true, 18));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.p_component, "save_money", true, true, 19));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.p_hand_with_money, "hand_with_money", true, true, 20));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.p_money_and_coins, "money_and_coins", true, true, 21));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.p_growth_chart, "growth_chart", true, true, 22));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.p_two_hand_with_money, "two_hand_with_money", true, true, 23));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.p_briefcase, "briefcase",true, true, 24));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.p_wallet, "wallet",true, true, 25));


        return iconsList;
    }

    public static void addIconsToRoom(Context context) {
        List<IconObject> iconsFromRoom = IconsItemStoreProvider.getInstance(context).getAllIconsList();

        if(iconsFromRoom.size() == 0) {
            List<IconObject> iconList = createIconList();

            for (IconObject icon : iconList) {
                IconsItemStoreProvider.getInstance(context).addItemInIconObjectList(icon);
            }
        }
    }
}
