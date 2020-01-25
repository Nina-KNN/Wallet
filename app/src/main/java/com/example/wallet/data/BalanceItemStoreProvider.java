package com.example.wallet.data;

import android.content.Context;

import com.example.wallet.data.room.RoomBalanceItemStore;

public class BalanceItemStoreProvider {
    //Singleton
    private static BalanceItemStore instance;

    private BalanceItemStoreProvider() {}

    public static BalanceItemStore getInstance(Context context) {
        if(instance == null) {
//            instance = new InMemoryBalanceItemStore();
            instance = new RoomBalanceItemStore(context);
        }

        return instance;
    }
    //End of singleton
}
