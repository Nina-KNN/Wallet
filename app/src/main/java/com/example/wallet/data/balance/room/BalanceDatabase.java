package com.example.wallet.data.balance.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(
        entities = {BalanceEntity.class},
        version = 2,
        exportSchema = false
)
public abstract class BalanceDatabase extends RoomDatabase {

    public abstract BalanceDao balanceDao();
}
