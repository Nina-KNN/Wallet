package com.example.wallet.feature.list.Adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallet.data.Balance;
import com.example.wallet.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

class BalanceViewHolder extends RecyclerView.ViewHolder {

    private Balance balance;

    private TextView titleView;
    private TextView dateView;
    private TextView operationSumView;

    public BalanceViewHolder(@NonNull View itemView) {
        super(itemView);

        titleView = itemView.findViewById(R.id.title);
        dateView = itemView.findViewById(R.id.date);
        operationSumView = itemView.findViewById(R.id.sum);
    }

    public void bindTo(Balance balance) {
        this.balance = balance;

        // Форматирование времени как "день.месяц.год"
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String dateText = dateFormat.format(balance.getDate());

        titleView.setText(balance.getTitle());
        dateView.setText(dateText);
        operationSumView.setText(String.valueOf(balance.getOperationSum()));
    }
}
