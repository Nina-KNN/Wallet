package com.example.wallet.feature.list.adapter;

import android.view.View;
import android.widget.TextView;

import com.example.wallet.R;
import com.example.wallet.data.balance.Balance;
import com.example.wallet.feature.details.base.BaseActivity;
import com.example.wallet.feature.list.WorkWithDate;
import com.example.wallet.feature.list.adapter.baseAdapter.BaseRecyclerAdapter;

import java.util.List;

public class BalanceListCategorySortInnerAdapter extends BaseRecyclerAdapter<Balance> {

    private BaseRecyclerAdapter.OnItemClick<Balance> itemListener;

    protected BalanceListCategorySortInnerAdapter(BaseActivity baseActivity, List<Balance> items, OnItemClick<Balance> onItemClick) {
        super(baseActivity, items);
        this.itemListener = onItemClick;
    }

    @Override
    protected int getCardLayoutID() {
        return R.layout.item_of_balance_list_category;
    }

    @Override
    protected BaseItem createViewHolder(View view) {
        return new BaseItem(view) {
            @Override
            public void bind(Balance item) {
                TextView dateTextView = view.findViewById(R.id.date_recycler_one_category);
                TextView sumTextView = view.findViewById(R.id.sum_recycler_one_category);
                TextView commentTextView = view.findViewById(R.id.comment_recycler_one_category);

                dateTextView.setText(WorkWithDate.showSimpleDateFormat(item.getDate()));
                sumTextView.setText(String.valueOf(item.getOperationSum()));
                commentTextView.setText(item.getComment());

                view.setOnClickListener(makeItemClickListener(item, view.getId()));
                view.setOnLongClickListener(makeItemLongClickListener(item, view.getId()));
            }
        };
    }

    private View.OnClickListener makeItemClickListener(Balance item, int id) {
        View.OnClickListener clickListener = v -> itemListener.onItemClick(item, id);

        return clickListener;
    }

    private View.OnLongClickListener makeItemLongClickListener(Balance item, int id) {
        View.OnLongClickListener longClickListener = v -> {
            itemListener.onItemLongClick(item, id);
            return true;
        };

        return longClickListener;
    }

}
