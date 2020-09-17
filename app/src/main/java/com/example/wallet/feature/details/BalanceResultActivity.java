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
import com.example.wallet.feature.list.PrefsUtils;
import com.example.wallet.feature.list.WorkWithDate;

import java.util.GregorianCalendar;
import java.util.List;

import static com.example.wallet.feature.list.baseConst.BaseConst.PROFIT_VALUE;

public class BalanceResultActivity extends BaseActivity implements View.OnClickListener {

    RelativeLayout relativeLayout;

    private TextView dateTextView;
    private TextView profitTextView;
    private TextView expenseTextView;
    private TextView balanceTextView;
    private TextView totalBalanceTextView;

    private List<Balance> balanceList;
    private GregorianCalendar today = new GregorianCalendar();

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
        findViewById(R.id.chart_button_balance_result).setOnClickListener(this);
        findViewById(R.id.settings_button_balance_result).setOnClickListener(this);
        findViewById(R.id.title_profit_result_balance).setOnClickListener(this);
        findViewById(R.id.title_expense_result_balance).setOnClickListener(this);
        findViewById(R.id.add_profit_result_balance).setOnClickListener(this);
        findViewById(R.id.add_expense_result_balance).setOnClickListener(this);

        dateTextView.setText(WorkWithDate.showDateUtilsFormatWithoutDay(today, this));

        showBalance();
        makeTouchListener(BalanceResultActivity.this);
    }

    // отобразить доход, расход и баланс на экране
    private void showBalance() {
        long firstDayInMonth = WorkWithDate.makeMonthPeriod(true, today);
        long lastDayInMonth = WorkWithDate.makeMonthPeriod(false, today);

        balanceList = BalanceItemStoreProvider.getInstance(this).getBalanceList();
        int profit = 0;
        int expense = 0;
        int result = 0;

        long minDay = today.getTimeInMillis();
        long maxDay = today.getTimeInMillis();

        for(Balance balance : balanceList) {
            if(balance.getDate().getTimeInMillis() < minDay) {
                minDay = balance.getDate().getTimeInMillis();
            }
            if(balance.getDate().getTimeInMillis() > maxDay) {
                maxDay = balance.getDate().getTimeInMillis();
            }

            if(balance.isChoiceProfit()) {
                result += Math.abs(balance.getOperationSum());

                if(balance.getDate().getTimeInMillis() >= firstDayInMonth && balance.getDate().getTimeInMillis() <= lastDayInMonth) {
                    profit += Math.abs(balance.getOperationSum());
                }
            } else {
                result -= Math.abs(balance.getOperationSum());

                if(balance.getDate().getTimeInMillis() >= firstDayInMonth && balance.getDate().getTimeInMillis() <= lastDayInMonth) {
                    expense += Math.abs(balance.getOperationSum());
                }
            }
        }

        CheckCorrectDataInPrefsUtils(minDay, true);
        CheckCorrectDataInPrefsUtils(maxDay, false);

        profitTextView.setText(String.valueOf(profit));
        expenseTextView.setText(String.valueOf(expense));
        balanceTextView.setText(String.valueOf(profit - expense));
        totalBalanceTextView.setText(String.valueOf(result));
    }

    // Открыть BalanceListActivity активити и передать значение profit
    private void openBalanceListActivity(Boolean profit) {
        Intent intent = new Intent(this, BalanceListActivity.class);
        intent.putExtra(PROFIT_VALUE, profit);
        startActivity(intent);
        finish();
    }

    // Открыть ItemOperationActivity и передать значение profit
    private void openItemOperationActivity(Boolean profit) {
        Intent intent = new Intent(this, ItemOperationActivity.class);
        intent.putExtra(PROFIT_VALUE, profit);
        startActivity(intent);
    }

//    Проверить правильность сохраненных в prefsUtils первого и последнего дня  и при необходимости их перезаписать
    private void CheckCorrectDataInPrefsUtils(long date, boolean firstDay) {
        PrefsUtils prefsUtils = new PrefsUtils(this);

        if(firstDay) {
            if(prefsUtils.getFirstDayInBalanceList() != date) {
                prefsUtils.saveFirstDateInBalanceList(date);
            }
        } else {
            if(prefsUtils.getLastDayInBalanceList() != date) {
                prefsUtils.saveLastDayInBalanceList(date);
            }
        }
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
            case R.id.title_profit_result_balance:
                openBalanceListActivity(true);
                break;
            case R.id.expense_button_balance_result:
            case R.id.title_expense_result_balance:
                openBalanceListActivity(false);
                break;
            case R.id.chart_button_balance_result:
                showToast("Chart button pressed");
                // Open Char activity
                break;
            case R.id.settings_button_balance_result:
                Intent intent = new Intent(this, CategorySettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.add_profit_result_balance:
                openItemOperationActivity(true);
                break;
            case R.id.add_expense_result_balance:
                openItemOperationActivity(false);
                break;
        }
    }
}
