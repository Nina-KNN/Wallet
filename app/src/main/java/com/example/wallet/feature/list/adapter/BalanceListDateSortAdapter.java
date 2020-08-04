package com.example.wallet.feature.list.adapter;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallet.R;
import com.example.wallet.data.balance.Balance;
import com.example.wallet.data.balance.BalanceItemStoreProvider;
import com.example.wallet.feature.details.base.BaseActivity;
import com.example.wallet.feature.list.WorkWithDate;
import com.example.wallet.feature.list.adapter.baseAdapter.BaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BalanceListDateSortAdapter extends BaseRecyclerAdapter<Balance> {
    public BalanceListDateSortAdapter(BaseActivity baseActivity, List<Balance> items, OnItemClick<Balance> onItemClick) {
        super(baseActivity, items, onItemClick);
    }

    @Override
    protected int getCardLayoutID() {
        return R.layout.item_of_balance_list_date_title;
    }

    @Override
    protected BaseItem createViewHolder(View view) {
        return new BaseItem(view) {
            @Override
            public void bind(Balance item) {
                List<Balance> balanceList = BalanceItemStoreProvider.getInstance(view.getContext()).getBalanceList();
                List<Balance> dayBalanceList = new ArrayList<>();
                int daySum = 0;

                for(Balance balance : balanceList) {
                    if(balance.getDate().get(Calendar.DATE) == item.getDate().get(Calendar.DATE)
                            && balance.isChoiceProfit() == item.isChoiceProfit()) {
                        dayBalanceList.add(balance);
                        daySum += balance.getOperationSum();
                    }
                }
                makeRecyclerView(view, dayBalanceList);

                TextView date = view.findViewById(R.id.date_item_balance_date_title);
                TextView sum = view.findViewById(R.id.sum_item_balance_date_title);

                date.setText(WorkWithDate.showSimpleDateFormat(item.getDate()));
                sum.setText(String.valueOf(daySum));
            }
        };
    }

    private void makeRecyclerView(View view, List<Balance> balanceList) {
        RecyclerView recyclerView;
        recyclerView = view.findViewById(R.id.recycler_balance_list_for_day);

        BalanceListDayAdapter adapter = new BalanceListDayAdapter((BaseActivity) view.getContext(), balanceList, itemListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
    }

    private BalanceListDayAdapter.OnItemClick<Balance> itemListener = new OnItemClick<Balance>() {
        @Override
        public void onItemClick(Balance item, int position) {
            Log.d("12345", "One item " + item.getOperationSum());
        }

        @Override
        public void onItemLongClick(Balance item, int position) {
            Log.d("12345", "One item long " + item.getOperationSum());
        }
    };
}
