package com.example.wallet.feature.details;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallet.R;
import com.example.wallet.data.balance.Balance;
import com.example.wallet.data.balance.BalanceItemStore;
import com.example.wallet.data.balance.BalanceItemStoreProvider;
import com.example.wallet.feature.details.base.BaseActivity;
import com.example.wallet.feature.list.WorkWithDate;
import com.example.wallet.feature.list.adapter.BalanceListAdapter;
import com.example.wallet.feature.list.adapter.BalanceViewHolder;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class BalanceListActivity extends BaseActivity implements View.OnClickListener {

    private static final int REQUEST_ACCESS_TYPE = 1;
    public static final String PROFIT_VALUE = "profit_value";
    public static final String ITEMS_ID = "items_id";

    private TextView titleTextView;
    private ImageButton profitImageButton;

    private RecyclerView recyclerView;
    private BalanceListAdapter adapter;
    private boolean profit;

    private GregorianCalendar currentDate = new GregorianCalendar();

    @Override
    protected int getLayoutID() {
        return R.layout.activity_balance_list;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        profit = (boolean) intent.getSerializableExtra("profit");

        recyclerView = findViewById(R.id.recycler);

        titleTextView = findViewById(R.id.title_positive_list);
        profitImageButton = findViewById(R.id.change_list);

        findViewById(R.id.add_item).setOnClickListener(this);
        findViewById(R.id.next_month).setOnClickListener(this);
        findViewById(R.id.previous_month).setOnClickListener(this);
        findViewById(R.id.balance_list).setOnClickListener(this);
        findViewById(R.id.button_back_balance_list).setOnClickListener(this);
        profitImageButton.setOnClickListener(this);


        makeChangeProfit(profit);
        makeDeleteItemBySwiped();
    }

    private void makeRecyclerView(boolean isProfit) {
        adapter = new BalanceListAdapter(
                BalanceItemStoreProvider.getInstance(this).getBalanceListForPeriod(firstDayInMonth(), lastDayInMonth()),
                itemListener,
                isProfit);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    // Обработка нажатия на элемент списка
    private final BalanceListAdapter.ItemListener itemListener = new BalanceListAdapter.ItemListener() {
        @Override
        public void onBalanceItemClicked(Balance balance) {
            Intent intent = new Intent(BalanceListActivity.this, ItemOperationActivity.class);
            intent.putExtra(ITEMS_ID, balance.getId());
            startActivityForResult(intent, REQUEST_ACCESS_TYPE);
        }

        @Override
        public void onBalanceItemLongClicked(Balance balance) {
            makeDeleteItemByLongPressed(balance);
        }
    };

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
        List<Balance> balanceList = BalanceItemStoreProvider.getInstance(this).
            getBalanceListForPeriod(firstDayInMonth(), lastDayInMonth());

        adapter.submitNewList(balanceList);
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

    // Удаление по свайпу в сторону
    private void makeDeleteItemBySwiped() {
        ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                // используется для перемещения итемов между собой
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                BalanceViewHolder balanceViewHolder = (BalanceViewHolder) viewHolder;

                Balance balanceItem = balanceViewHolder.getBalance();
                deleteItem(balanceItem, viewHolder.getAdapterPosition());
            }
        });

        touchHelper.attachToRecyclerView(recyclerView);
    }

    // Удаление при длительном нажатии
    private void makeDeleteItemByLongPressed(final Balance balance) {
        new AlertDialog.Builder(this)
                .setMessage(R.string.dialog_delete_item_message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BalanceItemStoreProvider.getInstance(BalanceListActivity.this).deleteBalanceItem(balance.getId());
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .create()
                .show();


        // При удалении элемента из списка, отрисовать список заново
        BalanceItemStoreProvider.getInstance(this).addListener(balanceListChangedList);
    }

    // В зависимости от Profit это или Expense отобразить правильные данные
    private void makeChangeProfit(boolean profit) {
        if(profit) {
            profitImageButton.setImageResource(R.drawable.ic_expense_button);
            titleTextView.setText("PROFIT");
        } else {
            profitImageButton.setImageResource(R.drawable.ic_profit_button);
            titleTextView.setText("EXPENSE");
        }

        makeRecyclerView(profit);;
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

            case R.id.next_month:
                currentDate.add(Calendar.MONTH, 1);
                makeRecyclerView(profit);
                showToast("Next_Month button was presses " + WorkWithDate.dateFormat.format(currentDate.getTime()));
                break;

            case R.id.previous_month:
                currentDate.add(Calendar.MONTH, -1);
                makeRecyclerView(profit);
                showToast("Previous_Month button was presses " + WorkWithDate.dateFormat.format(currentDate.getTime()));
                break;

            case R.id.change_list:
                profit = !profit;
                makeChangeProfit(profit);
                break;

            case R.id.balance_list:
                intent = new Intent(this, BalanceResultActivity.class);
                startActivity(intent);
                break;

            case R.id.button_back_balance_list:
                currentDate = new GregorianCalendar();
                makeRecyclerView(profit);
                break;
        }
    }
}
