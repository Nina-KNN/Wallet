package com.example.wallet.feature.list;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;

import com.example.wallet.data.balance.Balance;
import com.example.wallet.data.balance.BalanceItemStoreProvider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
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

    private static GregorianCalendar showFirstOrLastMonthInBalanceList(Context context, boolean isStartDate) {
        List<Balance> balanceList = BalanceItemStoreProvider.getInstance(context).getBalanceList();
        GregorianCalendar startDayInBalanceList = new GregorianCalendar();

        GregorianCalendar todayDate = new GregorianCalendar();
        long dateOfNote = todayDate.getTimeInMillis();

        for(Balance balance : balanceList) {
            if (isStartDate) {
                if (balance.getDate().getTime() < dateOfNote) {
                    dateOfNote = balance.getDate().getTime();
                    startDayInBalanceList.setTimeInMillis(dateOfNote);
                    Log.d("12345", showDateUtilsFormat(startDayInBalanceList, context) + " : " + showDateUtilsFormat(todayDate, context));
                }
            } else {
                if (balance.getDate().getTime() > dateOfNote) {
                    dateOfNote = balance.getDate().getTime();
                    startDayInBalanceList.setTimeInMillis(dateOfNote);
                    Log.d("12345", showDateUtilsFormat(startDayInBalanceList, context) + " : " + showDateUtilsFormat(todayDate, context));
                }
            }
        }

        return startDayInBalanceList;
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
