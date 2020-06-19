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

//    проверить текущая дата это начало или конец периода сохраненных данных, при необходимости пересохранить даты
    public static void checkCorrectDateInPrefsUtils(long currentDate, Context context) {
        PrefsUtils prefsUtils = new PrefsUtils(context);
        long firstDayInBalanceList = prefsUtils.getFirstDayInBalanceList();
        long lastDayInBalanceList = prefsUtils.getLastDayInBalanceList();

        if(currentDate < firstDayInBalanceList) {
            firstDayInBalanceList = currentDate;
            prefsUtils.saveFirstDateInBalanceList(firstDayInBalanceList);
        }
        if(currentDate > lastDayInBalanceList) {
            lastDayInBalanceList = currentDate;
            prefsUtils.saveLastDayInBalanceList(lastDayInBalanceList);
        }
    }

    private static GregorianCalendar showFirstOrLastMonthInBalanceList(Context context, boolean isStartPeriod) {
        PrefsUtils prefsUtils = new PrefsUtils(context);
        GregorianCalendar dayInBalanceList = new GregorianCalendar();

        if(isStartPeriod) {
            long firstDay = prefsUtils.getFirstDayInBalanceList();
            dayInBalanceList.setTimeInMillis(firstDay);
        } else {
            long lastDay = prefsUtils.getLastDayInBalanceList();
            dayInBalanceList.setTimeInMillis(lastDay);
        }
        return dayInBalanceList;
    }

    public static boolean isMonthInBalanceList(Context context, long dayInMonthPeriod, boolean isStartPeriod) {
        GregorianCalendar startDateInList = showFirstOrLastMonthInBalanceList(context, isStartPeriod);
        int startMonth = startDateInList.get(Calendar.MONTH);
        int startYear = startDateInList.get(Calendar.YEAR);

        GregorianCalendar dateInBalanceMonthList = new GregorianCalendar();
        dateInBalanceMonthList.setTimeInMillis(dayInMonthPeriod);
        int currentMonth = dateInBalanceMonthList.get(Calendar.MONTH);
        int currentYear = dateInBalanceMonthList.get(Calendar.YEAR);

        if(isStartPeriod) {
            if (currentYear >= startYear && currentMonth > startMonth) {
                return true;
            }
        } else {
            if (currentYear <= startYear && currentMonth <= startMonth) {
                return true;
            }
        }
        return false;
    }
}
