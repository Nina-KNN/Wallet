package com.example.wallet.feature.list;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.format.DateUtils;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarDialog {
    // отображаем диалоговое окно для выбора даты
    public static void setDateForShowCalendarDialog(TextView v, GregorianCalendar date, Context context) {
        new DatePickerDialog(
                context,
                makeDatePickerDialog(v, date, context),
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // установка начальной даты
    private static void setInitialDateTime(TextView v, GregorianCalendar date, Context context) {
        v.setText(DateUtils.formatDateTime(context,
                date.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    // установка обработчика выбора даты
    private static DatePickerDialog.OnDateSetListener makeDatePickerDialog(final TextView v, final GregorianCalendar date, final Context context) {
        DatePickerDialog.OnDateSetListener datePickerDialog = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(Calendar.YEAR, year);
                date.set(Calendar.MONTH, monthOfYear);
                date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                setInitialDateTime(v, date, context);
            }
        };

        return datePickerDialog;
    }
}
