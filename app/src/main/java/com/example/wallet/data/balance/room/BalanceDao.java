package com.example.wallet.data.balance.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BalanceDao {

    @Query("SELECT * FROM BalanceEntity")
    List<BalanceEntity> getBalanceList();

    @Query("SELECT * FROM BalanceEntity WHERE " +
            "date >= :startDatePeriodParam AND date <= :endDatePeriodParam " +
            "AND choiceProfit == :isProfit")
    List<BalanceEntity> getBalanceListForIsProfitPeriod(
            long startDatePeriodParam,
            long endDatePeriodParam,
            boolean isProfit
    );

    @Query("SELECT * FROM BalanceEntity WHERE " +
            "date >= :startDatePeriodParam AND date <= :endDatePeriodParam " +
            "AND choiceProfit == :isProfit AND categoryId == :categoryId")
    List<BalanceEntity> getBalanceListForIsProfitPeriod(
            long startDatePeriodParam,
            long endDatePeriodParam,
            boolean isProfit,
            String categoryId
    );

    @Query("SELECT * FROM BalanceEntity WHERE id == :idParam")
    BalanceEntity getBalanceById(String idParam);

    @Insert
    void add(BalanceEntity balanceEntity);

    @Delete
    void delete(BalanceEntity balanceEntity);

    @Update
    void update(BalanceEntity balanceEntity);
}
