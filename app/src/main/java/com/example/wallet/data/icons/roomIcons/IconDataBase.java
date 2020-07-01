package com.example.wallet.data.icons.roomIcons;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(
        entities = {IconEntity.class},
        version = 1,
        exportSchema = false
)

public abstract class IconDataBase extends RoomDatabase {
    public abstract IconDao iconDao();
}
