package com.example.wallet.feature.details;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallet.R;
import com.example.wallet.data.balance.Balance;
import com.example.wallet.data.balance.BalanceItemStore;
import com.example.wallet.data.balance.BalanceItemStoreProvider;
import com.example.wallet.data.icons.IconObject;
import com.example.wallet.data.icons.IconsItemStoreProvider;
import com.example.wallet.feature.details.base.BaseActivity;
import com.example.wallet.feature.list.WorkWithDate;
import com.example.wallet.feature.list.adapter.BalanceListCategorySortAdapter;
import com.example.wallet.feature.list.adapter.BalanceListDateSortAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

public class BalanceListDateSortActivity extends BaseActivity implements View.OnClickListener{

    private RecyclerView recyclerView;
    private GregorianCalendar currentDate;
    private BalanceListDateSortAdapter adapterDateSort;
    private BalanceListCategorySortAdapter adapterCategorySort;
    private int viewTypeRecycler = 1;
    boolean isProfit = false;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_balance_list_date;
    }

    @Override
    protected void initView() {
        currentDate = new GregorianCalendar();
        findViewById(R.id.title_balance_list_date).setOnClickListener(this);
        findViewById(R.id.change_view_list_date_button).setOnClickListener(this);

        recyclerView = findViewById(R.id.recycler_balance_list_date_sort);
        makeRecyclerView();
    }

    private void makeRecyclerView() {
        if(viewTypeRecycler == 1) {
            List<GregorianCalendar> calendarList = makeCalendarList();
            adapterDateSort = new BalanceListDateSortAdapter(this, calendarList, isProfit);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapterDateSort);
        } else {
            List<IconObject> categoryList = makeCategoryList();
            adapterCategorySort = new BalanceListCategorySortAdapter(this, categoryList, isProfit, firstDayInMonth(), lastDayInMonth());
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapterCategorySort);
        }

    }

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

    private List<GregorianCalendar> makeCalendarList() {
        List<Balance> balanceList = makeBalanceListForMonth(isProfit);
        List<GregorianCalendar> calendarList = new ArrayList<>();
        List<Integer> dateList = new ArrayList<>();

        for(Balance bal : balanceList) {
            if(calendarList.isEmpty()) {
                dateList.add(bal.getDate().get(Calendar.DATE));
                calendarList.add(bal.getDate());
            } else {
                if(!dateList.contains(bal.getDate().get(Calendar.DATE))) {
                    dateList.add(bal.getDate().get(Calendar.DATE));
                    calendarList.add(bal.getDate());
                }
            }
        }

        return calendarList;
    }

    private List<IconObject> makeCategoryList() {
        List<Balance> balanceList = makeBalanceListForMonth(isProfit);
        List<IconObject> categoryList = new ArrayList<>();
        List<UUID> iconIdList = new ArrayList<>();

        for(Balance bal : balanceList) {
            UUID id = bal.getCategoryId();
            if(iconIdList.isEmpty()) {
                categoryList.add(IconsItemStoreProvider.getInstance(this).getIconById(id));
                iconIdList.add(bal.getCategoryId());
            } else {
                if(!iconIdList.contains(bal.getCategoryId())) {
                    iconIdList.add(bal.getCategoryId());
                    categoryList.add(IconsItemStoreProvider.getInstance(this).getIconById(id));
                }
            }
        }

        return categoryList;
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
        if (viewTypeRecycler == 1) {
            List<GregorianCalendar> calendarList = makeCalendarList();
            adapterDateSort.submitNewList(calendarList);
        } else {
            List<IconObject> categoryList = makeCategoryList();
            adapterCategorySort.submitNewList(categoryList);
        }
    }

    // переключение между сортировкой по дате и по категориям списка Balance
    private void changeViewTypeRecycler() {
        if (viewTypeRecycler == 1) {
            viewTypeRecycler = 2;
        } else {
            viewTypeRecycler = 1;
        }
        makeRecyclerView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_balance_list_date:
                Intent intent = new Intent(this, BalanceResultActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.change_view_list_date_button:
                // переключение между сортировкой по дате и по категориям списка Balance
                changeViewTypeRecycler();
                break;
        }
    }
}
