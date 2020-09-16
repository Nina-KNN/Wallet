package com.example.wallet.feature.details;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

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
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

public class BalanceListActivity extends BaseActivity implements View.OnClickListener {

    private static final int REQUEST_ACCESS_TYPE = 1;
    public static final String PROFIT_VALUE = "profit_value";
    public static final String ITEMS_ID = "items_id";

    private TextView titleTextView;
    private ImageButton profitImageButton;
    private TextView dateTextView;

    private RecyclerView recyclerView;
    private BalanceListDateSortAdapter adapterDateSort;
    private BalanceListCategorySortAdapter adapterCategorySort;
    private int viewTypeRecycler = 1;
    private boolean profit;

    private GregorianCalendar currentDate;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_balance_list;
    }

    @Override
    protected void initView() {
        currentDate = new GregorianCalendar();
        Intent intent = getIntent();
        profit = (boolean) intent.getSerializableExtra("profit");

        recyclerView = findViewById(R.id.recycler);

        titleTextView = findViewById(R.id.title_positive_list);
        profitImageButton = findViewById(R.id.change_list);
        dateTextView = findViewById(R.id.month_and_year_balance_list);
        dateTextView.setText(WorkWithDate.showDateUtilsFormatWithoutDay(currentDate, this));


        findViewById(R.id.add_item).setOnClickListener(this);
        findViewById(R.id.next_month_balance_list).setOnClickListener(this);
        findViewById(R.id.previous_month_balance_list).setOnClickListener(this);
        findViewById(R.id.change_view_list).setOnClickListener(this);
        findViewById(R.id.balance_list).setOnClickListener(this);
        dateTextView.setOnClickListener(this);
        profitImageButton.setOnClickListener(this);

        makeChangeProfit(profit);
    }

    private void makeRecyclerView() {
        if(viewTypeRecycler == 1) {
            List<GregorianCalendar> calendarList = makeCalendarList();
            adapterDateSort = new BalanceListDateSortAdapter(this, calendarList, profit);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapterDateSort);
        } else {
            List<IconObject> categoryList = makeCategoryList();
            adapterCategorySort = new BalanceListCategorySortAdapter(this, categoryList, profit, firstDayInMonth(), lastDayInMonth());
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapterCategorySort);
        }
    }

    private List<Balance> makeBalanceListForMonth(boolean isProfit) {
        List<Balance> balanceList = BalanceItemStoreProvider.getInstance(this).
                getBalanceListForIsProfitPeriod(firstDayInMonth(), lastDayInMonth(), isProfit);
        return balanceList;
    }

    private List<GregorianCalendar> makeCalendarList() {
        List<Balance> balanceList = makeBalanceListForMonth(profit);
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
        List<Balance> balanceList = makeBalanceListForMonth(profit);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        profit = (boolean) data.getSerializableExtra("profit");
        makeChangeProfit(profit);
    }

    private final BalanceItemStore.Listener balanceListChangedList = new BalanceItemStore.Listener() {
        @Override
        public void onBalanceListChange() {
            updateList();
        }
    };

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

    // при удалении элемента по свайпу вывести сообщение и при необходимости пользователь
    // может востановить удаленный итем
    private void deleteItem(final Balance balance, final int position) {
        BalanceItemStoreProvider.getInstance(this).deleteBalanceItem(balance);
        // При удалении элемента из списка, отрисовать список заново
        updateList();

        Snackbar.make(recyclerView, R.string.snackbar_message, Snackbar.LENGTH_LONG)
                .setAction(R.string.snackbar_action, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BalanceItemStoreProvider.getInstance(BalanceListActivity.this).resurrectBalanceItem(balance, position);
                        updateList();
                    }
                })
                .show();
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

    // В зависимости от Profit это или Expense отобразить правильные данные
    private void makeChangeProfit(boolean profit) {
        if(profit) {
            profitImageButton.setImageResource(R.drawable.ic_expense_button);
            titleTextView.setText(R.string.title_profit);
        } else {
            profitImageButton.setImageResource(R.drawable.ic_profit_button);
            titleTextView.setText(R.string.title_expense);
        }

        makeRecyclerView();;
    }

    // Вычислить начало периода
    private long firstDayInMonth() {
        return WorkWithDate.makeMonthPeriod(true, currentDate);
    }

    // Вычислить конец периода
    private long lastDayInMonth(){
        return WorkWithDate.makeMonthPeriod(false, currentDate);
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.add_item:
                intent = new Intent(this, ItemOperationActivity.class);
                intent.putExtra(PROFIT_VALUE, profit);
                startActivityForResult(intent, REQUEST_ACCESS_TYPE);
                break;

            case R.id.next_month_balance_list:
                if(WorkWithDate.isMonthInBalanceList(this, lastDayInMonth(), false)) {
                    currentDate.add(Calendar.MONTH, 1);
                    dateTextView.setText(WorkWithDate.showDateUtilsFormatWithoutDay(currentDate, this));
                    makeRecyclerView();
                } else {
                    showToast(getString(R.string.exist_next_month));
                }
                break;

            case R.id.previous_month_balance_list:
                if(WorkWithDate.isMonthInBalanceList(this, firstDayInMonth(), true)) {
                    currentDate.add(Calendar.MONTH, -1);
                    dateTextView.setText(WorkWithDate.showDateUtilsFormatWithoutDay(currentDate, this));
                    makeRecyclerView();
                } else {
                    showToast(getString(R.string.exist_prev_month));
                }
                break;

            case R.id.change_list:
                profit = !profit;
                makeChangeProfit(profit);
                break;

            case R.id.change_view_list:
                // переключение между сортировкой по дате и по категориям списка Balance
                changeViewTypeRecycler();
                break;

            case R.id.balance_list:
                intent = new Intent(this, BalanceResultActivity.class);
                startActivity(intent);
                break;

            case R.id.month_and_year_balance_list:
                currentDate = new GregorianCalendar();
                dateTextView.setText(WorkWithDate.showDateUtilsFormatWithoutDay(currentDate, this));
                makeRecyclerView();
                break;
        }
    }
}
