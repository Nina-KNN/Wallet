package com.example.wallet.feature.details;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wallet.R;
import com.example.wallet.data.balance.Balance;
import com.example.wallet.data.balance.BalanceItemStoreProvider;
import com.example.wallet.feature.details.base.BaseActivity;
import com.example.wallet.feature.list.OnSwipeTouchListener;
import com.example.wallet.feature.list.WorkWithDate;

import java.util.GregorianCalendar;
import java.util.List;

public class BalanceResultActivity extends BaseActivity implements View.OnClickListener {

    RelativeLayout relativeLayout;

    private TextView dateTextView;
    private TextView profitTextView;
    private TextView expenseTextView;
    private TextView balanceTextView;
    private TextView totalBalanceTextView;

    private List<Balance> balanceList;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_balance_result;
    }

    @Override
    protected void initView() {
        relativeLayout = findViewById(R.id.content_main);
        dateTextView = findViewById(R.id.date_profit);
        profitTextView = findViewById(R.id.profit_result_balance);
        expenseTextView = findViewById(R.id.expense_result_balance);
        balanceTextView = findViewById(R.id.balance_result_balance);
        totalBalanceTextView = findViewById(R.id.total_balance_result_balance);

        findViewById(R.id.profit_button_balance_result).setOnClickListener(this);
        findViewById(R.id.expense_button_balance_result).setOnClickListener(this);

        dateTextView.setText(WorkWithDate.showDateUtilsFormatWithoutDay(new GregorianCalendar(), this));

        showBalance();
        makeTouchListener(BalanceResultActivity.this);
    }

    // отобразить доход, расход и баланс на экране
    private void showBalance() {
        GregorianCalendar todayDate = new GregorianCalendar();
        long firstDayInMonth = WorkWithDate.makeMonthPeriod(true, todayDate);
        long lastDayInMonth = WorkWithDate.makeMonthPeriod(false, todayDate);

        balanceList = BalanceItemStoreProvider.getInstance(this).getBalanceList();
        int profit = 0;
        int expense = 0;
        int result = 0;

        for(Balance balance : balanceList) {
            if(balance.isChoiceProfit()) {
                result += Math.abs(balance.getOperationSum());

                if(balance.getDate().getTime() >= firstDayInMonth && balance.getDate().getTime() <= lastDayInMonth) {
                    profit += Math.abs(balance.getOperationSum());
                }
            } else {
                result -= Math.abs(balance.getOperationSum());

                if(balance.getDate().getTime() >= firstDayInMonth && balance.getDate().getTime() <= lastDayInMonth) {
                    expense += Math.abs(balance.getOperationSum());
                }
            }
        }

        profitTextView.setText(String.valueOf(profit));
        expenseTextView.setText(String.valueOf(expense));
        balanceTextView.setText(String.valueOf(profit - expense));
        totalBalanceTextView.setText(String.valueOf(result));
    }

    // Открыть новую активити и передать значение profit
    private void openBalanceListActivity(Boolean profit) {
        Intent intent = new Intent(this, BalanceListActivity.class);
        intent.putExtra("profit", profit);
        startActivity(intent);
        finish();
    }

    // Действия при свайпах в разные стороны
    private void makeTouchListener(final Context currentActivity) {
        relativeLayout.setOnTouchListener(new OnSwipeTouchListener(currentActivity) {
            public void onSwipeTop() {
                showToast("top");
            }

            public void onSwipeRight() {
                // Переход на активити со списком Доходов
                openBalanceListActivity(true);
            }

            public void onSwipeLeft() {
                // Переход на активити со списком Расходов
                openBalanceListActivity(false);
            }

            public void onSwipeBottom() {
                showToast("bottom");
            }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.profit_button_balance_result:
                openBalanceListActivity(true);
                break;
            case R.id.expense_button_balance_result:
                openBalanceListActivity(false);
                break;
        }
    }
}
