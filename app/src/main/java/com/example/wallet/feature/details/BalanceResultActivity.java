package com.example.wallet.feature.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wallet.R;
import com.example.wallet.feature.list.OnSwipeTouchListener;

public class BalanceResultActivity extends AppCompatActivity {

    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_result);
        int profit = 150000;
        int expense = 10000;
        int balance = profit - expense;

        relativeLayout = findViewById(R.id.content_main);
        TextView profitTextView = findViewById(R.id.profit_result_balance);
        TextView expenseTextView = findViewById(R.id.expense_result_balance);
        TextView balanceTextView = findViewById(R.id.balance_result_balance);

        profitTextView.setText(String.valueOf(profit));
        expenseTextView.setText(String.valueOf(expense));
        balanceTextView.setText(String.valueOf(balance));

        makeTouchListener(BalanceResultActivity.this);


    }

    private void makeTouchListener(final Context currentActivity) {
        relativeLayout.setOnTouchListener(new OnSwipeTouchListener(currentActivity) {
            Intent intent;

            public void onSwipeTop() {
                Toast.makeText(currentActivity, "top", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeRight() {
                // Переход на активити со списком Доходов
                intent = new Intent(currentActivity, BalanceListActivity.class);
                intent.putExtra("profit", true);
                startActivity(intent);
            }

            public void onSwipeLeft() {
                // Переход на активити со списком Расходов
                intent = new Intent(currentActivity, BalanceListActivity.class);
                intent.putExtra("profit", false);
                startActivity(intent);
            }

            public void onSwipeBottom() {
                Toast.makeText(currentActivity, "bottom", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
