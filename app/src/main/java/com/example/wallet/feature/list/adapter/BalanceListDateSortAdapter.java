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

public class BalanceListDateSortAdapter extends BaseRecyclerAdapter<GregorianCalendar> {
    public static final String ITEMS_ID = "items_id";
    private Context context;

    private List<GregorianCalendar> items;
    private TextView sum;

    private BalanceListDayAdapter adapter;
    private RecyclerView recyclerView;
    private BaseRecyclerAdapter.OnItemClick<GregorianCalendar> outerItemListener;

    public BalanceListDateSortAdapter(BaseActivity baseActivity, List<GregorianCalendar> items, OnItemClick<GregorianCalendar> onItemClick) {
        super(baseActivity, items, onItemClick);
        this.items = items;
        this.outerItemListener = onItemClick;
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

                sum = view.findViewById(R.id.sum_item_balance_date_title);
                TextView date = view.findViewById(R.id.date_item_balance_date_title);
                date.setText(WorkWithDate.showSimpleDateFormat(item));

                List<Balance> dayBalanceList = makeBalanceListForDay(item);
                recyclerView = view.findViewById(R.id.recycler_balance_list_for_day);
                makeRecyclerView(dayBalanceList);
                view.setOnClickListener(makeItemClickListener(item, view.getId()));
            }
        };
    }

    private void makeRecyclerView(List<Balance> balanceList) {
        adapter = new BalanceListDayAdapter((BaseActivity) context, balanceList, innerItemListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }

    private View.OnClickListener makeItemClickListener(GregorianCalendar item, int id) {
        View.OnClickListener clickListener = v -> {
            outerItemListener.onItemClick(item, id);
        };

        return clickListener;
    }

    private List<Balance> makeBalanceListForDay(GregorianCalendar date) {
        long startDateValue = WorkWithDate.makeStartDateValue(date, true);
        long finishDateValue = WorkWithDate.makeStartDateValue(date, false);

        List<Balance> balanceList = BalanceItemStoreProvider.getInstance(context)
                .getBalanceListForIsProfitPeriod(startDateValue, finishDateValue,true);

        int daySum = 0;

        for(Balance balance : balanceList) {
            daySum += balance.getOperationSum();
        }

        sum.setText(String.valueOf(daySum));

        return balanceList;
    }

    // Обработка нажатий на элементы во внутреннем ресайлере
    private BalanceListDayAdapter.OnItemClick<Balance> innerItemListener = new OnItemClick<Balance>() {
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
