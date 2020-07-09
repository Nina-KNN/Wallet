package com.example.wallet.data.icons;

import com.example.wallet.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreateIconsList {

    // Singleton
    private static List<IconObject> instanceIcon;

    public static List<IconObject> getInstanceIcon(boolean profit) {
        if(profit) {
            instanceIcon = createProfitIconList();
        } else {
            instanceIcon = createExpenseIconList();
        }

        return instanceIcon;
    }
    //End of singleton

    private static List<IconObject> createExpenseIconList() {
        List<IconObject> iconsList = new ArrayList<>();

        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.ex_cocktail, "cocktail", false, true, 1));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.ex_gas_station, "gas_station", false, false, 2));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.ex_kye, "kye", false, true, 3));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.ex_menu, "menu", false, true, 4));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.ex_shopping_basket, "shopping_basket", false, true, 5));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.ex_watch, "watch", false, true, 6));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.ex_necklace, "necklace",false, true, 7));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.ex_cup, "cup", false, true, 8));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.ex_bus, "bus", false, true, 9));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.ex_car, "car", false, true, 10));

        return iconsList;
    }

    private static List<IconObject> createProfitIconList() {
        List<IconObject> iconsList = new ArrayList<>();

        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.p_coins_1, "coins_1", true, true, 11));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.p_calculator, "calculator", true, true, 12));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.p_cash_back, "cash_back", true, true, 13));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.p_hands_1, "hands_1", true, true, 14));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.p_safe, "safe", true, true, 15));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.p_coins_2, "coins_2", true, true, 16));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.p_coins_3, "coins_3", true, true, 17));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.p_growth_chart, "growth_chart", true, true, 18));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.p_men, "men", true, true, 19));
        iconsList.add(new IconObject(UUID.randomUUID(), R.drawable.p_money, "money",true, true, 20));

        return iconsList;
    }
}
