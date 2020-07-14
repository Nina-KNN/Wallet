package com.example.wallet.data.icons;

import android.content.Context;

import com.example.wallet.data.icons.roomIcons.RoomIconStore;

public class IconsItemStoreProvider {
    // Singleton
    private static IconItemStore instance;

    private IconsItemStoreProvider() {}

    public static IconItemStore getInstance(Context context) {
        if(instance == null) {
            instance = new RoomIconStore(context);
        }

        return instance;
    }
    // End of singleton
}

