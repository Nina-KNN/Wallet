package com.example.wallet.data.icons;

import android.util.Log;

import com.example.wallet.R;

import java.util.ArrayList;
import java.util.List;

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

        iconsList.add(new IconObject(R.drawable.ex_cocktail, "cocktail", "id_cocktail", false));
        iconsList.add(new IconObject(R.drawable.ex_gas_station, "gas_station", "id_gas_station", false));
        iconsList.add(new IconObject(R.drawable.ex_kye, "kye", "id_kye", false));
        iconsList.add(new IconObject(R.drawable.ex_menu, "menu", "id_menu", false));
        iconsList.add(new IconObject(R.drawable.ex_shopping_basket, "shopping_basket", "id_shopping_basket", false));
        iconsList.add(new IconObject(R.drawable.ex_watch, "watch", "id_watch", false));
        iconsList.add(new IconObject(R.drawable.ex_necklace, "necklace", "id_necklace", false));
        iconsList.add(new IconObject(R.drawable.ex_cup, "cup", "cup", false));
        iconsList.add(new IconObject(R.drawable.ex_bus, "bus", "id_bus", false));
        iconsList.add(new IconObject(R.drawable.ex_car, "car", "id_car", false));

        return iconsList;
    }

    private static List<IconObject> createProfitIconList() {
        List<IconObject> iconsList = new ArrayList<>();

        iconsList.add(new IconObject(R.drawable.p_coins_1, "coins_1", "id_prof_coins_1", true));
        iconsList.add(new IconObject(R.drawable.p_calculator, "calculator", "id_calculator", true));
        iconsList.add(new IconObject(R.drawable.p_cash_back, "cash_back", "id_cash_back", true));
        iconsList.add(new IconObject(R.drawable.p_hands_1, "hands_1", "id_hands_1", true));
        iconsList.add(new IconObject(R.drawable.p_safe, "safe", "id_safe", true));
        iconsList.add(new IconObject(R.drawable.p_coins_2, "coins_2", "id_coins_2", true));
        iconsList.add(new IconObject(R.drawable.p_coins_3, "coins_3", "id_coins_3", true));
        iconsList.add(new IconObject(R.drawable.p_growth_chart, "growth_chart", "id_growth_chart", true));
        iconsList.add(new IconObject(R.drawable.p_men, "men", "id_men", true));
        iconsList.add(new IconObject(R.drawable.p_money, "money", "id_money", true));

        return iconsList;
    }
}
