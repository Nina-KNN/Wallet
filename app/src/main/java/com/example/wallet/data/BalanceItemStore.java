package com.example.wallet.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BalanceItemStore {
    //Singleton
    private static BalanceItemStore instance;

    private BalanceItemStore() {}

    public static BalanceItemStore getInstance() {
        if(instance == null) {
            instance = new BalanceItemStore();
        }
        return instance;
    }
    //End of singleton

    private List<Balance> balanceList = generateDemoBalance();

    public List<Balance> getBalanceList() {
        return balanceList;
    }

    public Balance getById(UUID id) {
        for(Balance balanceItem : balanceList) {
            if(balanceItem.getId().equals(id)) {
                return balanceItem;
            }
        }
        return null;
    }

    // Заполнить лист crimes
    private static List<Balance> generateDemoBalance() {
        List<Balance> result = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            Balance balance = new Balance();
            balance.setOperationSum(i);
            balance.setTitle("Balance #" + i);
            balance.setId(UUID.randomUUID());
            balance.setComment("com " + i);

            result.add(balance);
        }
        return result;
    }
}
