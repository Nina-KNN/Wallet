package com.example.wallet.feature.list;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.wallet.R;

public class PrefsUtils {
    private SharedPreferences sharedPreferences;
    private final String FIRST_DAY = "first_day";
    private final String LAST_DAY = "last_day";

    private String appName = String.valueOf(R.string.app_name);

    public PrefsUtils(Context context) {
        sharedPreferences = context.getSharedPreferences(
                appName,
                Context.MODE_PRIVATE
        );
    }

    public void saveFirstDateInBalanceList(long firstDay) {
        sharedPreferences.edit().putLong(FIRST_DAY, firstDay).apply();
    }

    public void saveLastDayInBalanceList(long lastDay) {
        sharedPreferences.edit().putLong(LAST_DAY, lastDay).apply();
    }

    public long getFirstDayInBalanceList() {
        return sharedPreferences.getLong(FIRST_DAY, 0);
    }

    public long getLastDayInBalanceList() {
        return sharedPreferences.getLong(LAST_DAY, 0);
    }
}
