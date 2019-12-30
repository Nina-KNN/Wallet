package com.example.wallet.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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



    private List<Balance> balanceList = new ArrayList<>();

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


    // Создать рандомный элемент
    public void generateNewRandomItem() {
        Random random = new Random();
        Balance balanceItem = new Balance();

        boolean profit = random.nextBoolean();
//        int sum = generateRandomEvenNumber();
        int sum = random.nextInt();
        if(sum < 0) { sum *= (-1); }

        balanceItem.setId(UUID.randomUUID());
        balanceItem.setComment("New item");
        balanceItem.setChoiceProfit(profit);

        if(profit) {
//                balanceItem.setTitle(String.valueOf(R.string.title_profit));
            balanceItem.setTitle("Profit");
            balanceItem.setOperationSum(sum);
        } else {
//                balanceItem.setTitle(String.valueOf(R.string.title_expense));
            balanceItem.setTitle("Expense");
            balanceItem.setOperationSum(-sum);
        }

        balanceList.add(balanceItem);
    }
}
