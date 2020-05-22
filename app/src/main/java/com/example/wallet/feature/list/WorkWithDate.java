package com.example.wallet.feature.list;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class WorkWithDate {
    public static final  GregorianCalendar TODAY_DATE = new GregorianCalendar();

    public static DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());


    public static long makeMonthPeriod(boolean firstDay, GregorianCalendar currentDate){
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH);
        int day;

        if(firstDay) {
            day = currentDate.getActualMinimum(Calendar.DAY_OF_MONTH);
        } else {
            day = currentDate.getActualMaximum(Calendar.DAY_OF_MONTH);
        }

        GregorianCalendar lastDay = new GregorianCalendar(year, month, day);

        return lastDay.getTimeInMillis();
    }

}
