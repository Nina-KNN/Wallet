package com.example.wallet.data.icons;

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

        iconsList.add(new IconObject(R.drawable.ex_cocktail, "cocktail", false, true));
        iconsList.add(new IconObject(R.drawable.ex_gas_station, "gas_station", false, true));
        iconsList.add(new IconObject(R.drawable.ex_kye, "kye", false, true));
        iconsList.add(new IconObject(R.drawable.ex_menu, "menu", false, true));
        iconsList.add(new IconObject(R.drawable.ex_shopping_basket, "shopping_basket", false, true));
        iconsList.add(new IconObject(R.drawable.ex_watch, "watch", false, true));
        iconsList.add(new IconObject(R.drawable.ex_necklace, "necklace",false, true));
        iconsList.add(new IconObject(R.drawable.ex_cup, "cup", false, true));
        iconsList.add(new IconObject(R.drawable.ex_bus, "bus", false, true));
        iconsList.add(new IconObject(R.drawable.ex_car, "car", false, true));

        return iconsList;
    }

    private static List<IconObject> createProfitIconList() {
        List<IconObject> iconsList = new ArrayList<>();

        iconsList.add(new IconObject(R.drawable.p_coins_1, "coins_1", true, true));
        iconsList.add(new IconObject(R.drawable.p_calculator, "calculator", true, true));
        iconsList.add(new IconObject(R.drawable.p_cash_back, "cash_back", true, true));
        iconsList.add(new IconObject(R.drawable.p_hands_1, "hands_1", true, true));
        iconsList.add(new IconObject(R.drawable.p_safe, "safe", true, true));
        iconsList.add(new IconObject(R.drawable.p_coins_2, "coins_2", true, true));
        iconsList.add(new IconObject(R.drawable.p_coins_3, "coins_3", true, true));
        iconsList.add(new IconObject(R.drawable.p_growth_chart, "growth_chart", true, true));
        iconsList.add(new IconObject(R.drawable.p_men, "men", true, true));
        iconsList.add(new IconObject(R.drawable.p_money, "money",true, true));

        return iconsList;
    }
}


  /*
public class BalanceItemStoreProvider {
    //Singleton
    private static BalanceItemStore instance;

    private BalanceItemStoreProvider() {}

    public static BalanceItemStore getInstance(Context context) {
        if(instance == null) {
            instance = new RoomBalanceItemStore(context);
        }

        return instance;
    }
    //End of singleton
}
     */
