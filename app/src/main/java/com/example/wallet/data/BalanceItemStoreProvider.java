package com.example.wallet.data;

import android.content.Context;

public class BalanceItemStoreProvider {
    //Singleton
    private static BalanceItemStore instance;

    private BalanceItemStoreProvider() {}

    public static BalanceItemStore getInstance() {
        if(instance == null) {
            instance = new InMemoryBalanceItemStore();
        }
        return instance;
    }
    //End of singleton
}
