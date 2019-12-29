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
            boolean prof = false;
            if (i % 2 == 0) { prof = true;}

            Balance balance = new Balance();
            balance.setId(UUID.randomUUID());
            balance.setComment("com " + i);
            balance.setChoiceProfit(prof);

            if(prof) {
                balance.setTitle("Profit");
//                balance.setTitle(String.valueOf(R.string.title_profit));
                balance.setOperationSum(i);
            } else {
//                balance.setTitle(String.valueOf(R.string.title_expense));
                balance.setTitle("Expense");
                balance.setOperationSum(-i);
            }

            result.add(balance);
        }
        return result;
    }
}
