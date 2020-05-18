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

    @Query("SELECT * FROM BalanceEntity WHERE date >= :startDatePeriodParam AND date <= :endDatePeriodParam")
    List<BalanceEntity> getBalanceListForPeriod(long startDatePeriodParam, long endDatePeriodParam);

    @Query("SELECT * FROM BalanceEntity WHERE id == :idParam")
    BalanceEntity getBalanceById(String idParam);

    @Insert
    void add(BalanceEntity balanceEntity);

    @Delete
    void delete(BalanceEntity balanceEntity);

    @Update
    void update(BalanceEntity balanceEntity);
}
