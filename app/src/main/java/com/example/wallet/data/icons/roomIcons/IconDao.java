package com.example.wallet.data.icons.roomIcons;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface IconDao {
    @Query("SELECT * FROM IconEntity WHERE iconProfit == :iconProfit")
    List<IconEntity> getIconList(boolean iconProfit);

    @Query("SELECT * FROM IconEntity WHERE iconProfit == :iconProfit AND iconVisibility == :iconVisibility")
    List<IconEntity> getIconList(boolean iconProfit, boolean iconVisibility);

    @Query("SELECT * FROM IconEntity WHERE iconId == :iconId")
    IconEntity getIconById(String iconId);

    @Insert
    void add(IconEntity iconEntity);

    @Delete
    void delete(IconEntity iconEntity);

    @Update
    void update(IconEntity iconEntity);

}
