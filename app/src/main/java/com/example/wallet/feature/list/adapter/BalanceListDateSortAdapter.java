package com.example.wallet.feature.list.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallet.R;
import com.example.wallet.data.balance.Balance;
import com.example.wallet.data.balance.BalanceItemStoreProvider;
import com.example.wallet.feature.details.ItemOperationActivity;
import com.example.wallet.feature.details.base.BaseActivity;
import com.example.wallet.feature.list.DeleteConfirmationDialog;
import com.example.wallet.feature.list.WorkWithDate;
import com.example.wallet.feature.list.adapter.baseAdapter.BaseRecyclerAdapter;

import java.util.GregorianCalendar;
import java.util.List;

import static com.example.wallet.feature.list.baseConst.BaseConst.ITEMS_ID;

public class BalanceListDateSortAdapter extends BaseRecyclerAdapter<GregorianCalendar> {
    private Context context;

    private List<GregorianCalendar> items;
    private TextView sumTextView;
    private boolean isProfit;

    private BalanceListDateSortInnerAdapter adapter;
    private RecyclerView recyclerView;

    public BalanceListDateSortAdapter(BaseActivity baseActivity, List<GregorianCalendar> items, boolean isProfit) {
        super(baseActivity, items);
        this.items = items;
        this.isProfit = isProfit;

    }

    @Override
    protected int getCardLayoutID() {
        return R.layout.item_of_balance_list_date_title;
    }

    @Override
    protected BaseItem createViewHolder(View view) {
        return new BaseItem(view) {
            @Override
            public void bind(GregorianCalendar item) {
                context = view.getContext();

                sumTextView = view.findViewById(R.id.sum_item_balance_date_title);
                TextView date = view.findViewById(R.id.date_item_balance_date_title);
                date.setText(WorkWithDate.showSimpleDateFormat(item));

                List<Balance> dayBalanceList = makeBalanceListForDay(item);
                recyclerView = view.findViewById(R.id.recycler_balance_list_for_day);
                makeRecyclerView(dayBalanceList);
            }
        };
    }

    private void makeRecyclerView(List<Balance> balanceList) {
        adapter = new BalanceListDateSortInnerAdapter((BaseActivity) context, balanceList, innerItemListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }

    private List<Balance> makeBalanceListForDay(GregorianCalendar date) {
        long startDateValue = WorkWithDate.makeStartDateValue(date, true);
        long finishDateValue = WorkWithDate.makeStartDateValue(date, false);

        List<Balance> balanceList = BalanceItemStoreProvider.getInstance(context)
                .getBalanceListForIsProfitPeriod(startDateValue, finishDateValue, isProfit);

        int daySum = 0;
        for(Balance balance : balanceList) {
            daySum += balance.getOperationSum();
        }
        sumTextView.setText(String.valueOf(daySum));

        return balanceList;
    }

    // Обработка нажатий на элементы во внутреннем ресайлере
    private BalanceListDateSortInnerAdapter.OnItemClick<Balance> innerItemListener = new OnItemClick<Balance>() {
        @Override
        public void onItemClick(Balance item, int position) {
            Intent intent = new Intent(context, ItemOperationActivity.class);
            intent.putExtra(ITEMS_ID, item.getId());
            context.startActivity(intent);
        }

        @Override
        public void onItemLongClick(Balance item, int position) {
            // Удаление при длительном нажатии
            DeleteConfirmationDialog dialogFragment = new DeleteConfirmationDialog();
            dialogFragment.onCreateDialog(item, context).show();
        }
    };

    public void submitNewList(List<GregorianCalendar> newCalendarList) {
        this.items = newCalendarList;
        notifyDataSetChanged();
    }
}
