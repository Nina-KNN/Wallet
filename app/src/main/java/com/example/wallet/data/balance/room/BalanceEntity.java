package com.example.wallet.data.balance.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class BalanceEntity {
    @PrimaryKey
    @NonNull
    public String id;

    public String title;
    public int operationSum;
    public boolean choiceProfit;
    public long date;
    public String comment;
}
