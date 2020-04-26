package com.example.wallet.feature.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wallet.R;
import com.example.wallet.data.balance.Balance;
import com.example.wallet.data.balance.BalanceItemStoreProvider;
import com.example.wallet.feature.list.OnSwipeTouchListener;

import java.util.List;

public class BalanceResultActivity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout relativeLayout;

    private TextView profitTextView;
    private TextView expenseTextView;
    private TextView balanceTextView;

    private List<Balance> balanceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_result);

        relativeLayout = findViewById(R.id.content_main);
        profitTextView = findViewById(R.id.profit_result_balance);
        expenseTextView = findViewById(R.id.expense_result_balance);
        balanceTextView = findViewById(R.id.balance_result_balance);

        findViewById(R.id.profit_button_balance_result).setOnClickListener(this);
        findViewById(R.id.expense_button_balance_result).setOnClickListener(this);

        showBalance();
        makeTouchListener(BalanceResultActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.profit_button_balance_result:
                openNewActivity(true);
                break;
            case R.id.expense_button_balance_result:
                openNewActivity(false);
                break;
        }
    }

    // отобразить доход, расход и баланс на экране
    private void showBalance() {
        balanceList = BalanceItemStoreProvider.getInstance(this).getBalanceList();
        int profit = 0;
        int expense = 0;

        for(Balance balance : balanceList) {
            if(balance.isChoiceProfit()) {
                profit += balance.getOperationSum();
            } else {
                expense += Math.abs(balance.getOperationSum());
            }
        }

        profitTextView.setText(String.valueOf(profit));
        expenseTextView.setText(String.valueOf(expense));
        balanceTextView.setText(String.valueOf(profit - expense));
    }

    // Действия при свайпах в разные стороны
    private void makeTouchListener(final Context currentActivity) {
        relativeLayout.setOnTouchListener(new OnSwipeTouchListener(currentActivity) {
            public void onSwipeTop() {
                Toast.makeText(currentActivity, "top", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeRight() {
                // Переход на активити со списком Доходов
                openNewActivity(true);
            }

            public void onSwipeLeft() {
                // Переход на активити со списком Расходов
                openNewActivity(false);
            }

            public void onSwipeBottom() {
                Toast.makeText(currentActivity, "bottom", Toast.LENGTH_SHORT).show();
            }

        });
    }

    // Открыть новую активити и передать значение profit
    private void openNewActivity(Boolean profit) {
        Intent intent = new Intent(this, BalanceListActivity.class);
        intent.putExtra("profit", profit);
        startActivity(intent);
        finish();
    }
}
