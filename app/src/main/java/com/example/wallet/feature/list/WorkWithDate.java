package com.example.wallet.feature.list;

import android.content.Context;
import android.text.format.DateUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class WorkWithDate {
    public static DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

    public static String showSimpleDateFormat(GregorianCalendar date) {
        return dateFormat.format(date.getTime());
    }

    public static String showDateUtilsFormat(GregorianCalendar date, Context context) {
        return DateUtils.formatDateTime(context,
                date.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR);
    }

    public static String showDateUtilsFormatWithoutDay(GregorianCalendar date, Context context) {
        return DateUtils.formatDateTime(context,
                date.getTimeInMillis(),
                DateUtils.FORMAT_NO_MONTH_DAY | DateUtils.FORMAT_SHOW_YEAR);
    }


    public static long makeMonthPeriod(boolean firstDay, GregorianCalendar currentDate){
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH);
        int day;

        if(firstDay) {
            day = currentDate.getActualMinimum(Calendar.DAY_OF_MONTH);
        } else {
            day = currentDate.getActualMaximum(Calendar.DAY_OF_MONTH) + 1;
        }

        GregorianCalendar lastDay = new GregorianCalendar(year, month, day);

        return lastDay.getTimeInMillis();
    }

}
