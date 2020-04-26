package com.example.wallet.data.balance;

import android.content.Context;

import com.example.wallet.data.balance.room.RoomBalanceItemStore;

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
