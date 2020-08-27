package com.example.wallet.feature.details;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallet.R;
import com.example.wallet.data.balance.Balance;
import com.example.wallet.data.balance.BalanceItemStore;
import com.example.wallet.data.balance.BalanceItemStoreProvider;
import com.example.wallet.feature.details.base.BaseActivity;
import com.example.wallet.feature.list.WorkWithDate;
import com.example.wallet.feature.list.adapter.BalanceListDateSortAdapter;
import com.example.wallet.feature.list.adapter.baseAdapter.BaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class BalanceListDateSortActivity extends BaseActivity implements View.OnClickListener{

    private RecyclerView recyclerView;
    private GregorianCalendar currentDate;
    private BalanceListDateSortAdapter adapter;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_balance_list_date;
    }

    @Override
    protected void initView() {
        currentDate = new GregorianCalendar();
        findViewById(R.id.title_balance_list_date).setOnClickListener(this);

        recyclerView = findViewById(R.id.recycler_balance_list_date_sort);
        makeRecyclerView(true);
    }

    private void makeRecyclerView(boolean isProfit) {
        List<Balance> balanceList = makeBalanceListWithoutRepeatingDate(true);
        adapter = new BalanceListDateSortAdapter(this, balanceList, itemListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    // Обработка нажатия на элемент списка
    BalanceListDateSortAdapter.OnItemClick<Balance> itemListener = new BaseRecyclerAdapter.OnItemClick<Balance>() {
        @Override
        public void onItemClick(Balance item, int position) {

        }

        @Override
        public void onItemLongClick(Balance item, int position) {

        }
    };

    // Вычислить начало периода
    private long firstDayInMonth() {
        return WorkWithDate.makeMonthPeriod(true, currentDate);
    }

    // Вычислить конец периода
    private long lastDayInMonth(){
        return WorkWithDate.makeMonthPeriod(false, currentDate);
    }

    private List<Balance> makeBalanceListForMonth(boolean isProfit) {
        List<Balance> balanceList = BalanceItemStoreProvider.getInstance(this).
                getBalanceListForIsProfitPeriod(firstDayInMonth(), lastDayInMonth(), isProfit);
        return balanceList;
    }

    private List<Balance> makeBalanceListWithoutRepeatingDate(boolean isProfit) {
        List<Balance> balanceList = makeBalanceListForMonth(isProfit);
        List<Balance> balanceListWithoutRepeatingDate = new ArrayList<>();

        for(Balance balance: balanceList) {
            if(balanceListWithoutRepeatingDate.size() < 1) {
                balanceListWithoutRepeatingDate.add(balance);
            }

            GregorianCalendar calendar = balance.getDate();
            int newItem = 0;

            for(Balance bal : balanceListWithoutRepeatingDate) {
                GregorianCalendar cal = bal.getDate();

                if(calendar.get(Calendar.DATE) != cal.get(Calendar.DATE)) {
                    newItem++;
                } else {
                    newItem = 0;
                    break;
                }
            }

            if (newItem > 0) {
                balanceListWithoutRepeatingDate.add(balance);
            }
        }

        return balanceListWithoutRepeatingDate;
    }

    private final BalanceItemStore.Listener balanceListChangedList = () -> updateList();

    @Override
    protected void onPause() {
        updateList();
        BalanceItemStoreProvider.getInstance(this).removeListener(balanceListChangedList);
        super.onPause();
    }

    @Override
    protected void onResume() {
        updateList();
        super.onResume();
    }

    // Обновить список итемов
    private void updateList() {
        List<Balance> balanceList = BalanceItemStoreProvider.getInstance(this).
                getBalanceListForIsProfitPeriod(firstDayInMonth(), lastDayInMonth(), true);

        adapter.submitNewList(balanceList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_balance_list_date:
                Intent intent = new Intent(this, BalanceResultActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}