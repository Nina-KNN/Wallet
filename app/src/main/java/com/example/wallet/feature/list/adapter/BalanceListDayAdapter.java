package com.example.wallet.feature.list.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wallet.R;
import com.example.wallet.data.balance.Balance;
import com.example.wallet.data.icons.IconObject;
import com.example.wallet.data.icons.IconsItemStoreProvider;
import com.example.wallet.feature.details.base.BaseActivity;
import com.example.wallet.feature.list.adapter.baseAdapter.BaseRecyclerAdapter;

import java.util.List;

public class BalanceListDayAdapter extends BaseRecyclerAdapter<Balance> {
    protected BalanceListDayAdapter(BaseActivity baseActivity, List<Balance> items, OnItemClick<Balance> onItemClick) {
        super(baseActivity, items, onItemClick);
    }

    @Override
    protected int getCardLayoutID() {
        return R.layout.item_of_balance_list_date;
    }

    @Override
    protected BaseItem createViewHolder(View view) {
        return new BaseItem(view) {
            @Override
            public void bind(Balance item) {
                ImageView categoryImageView = view.findViewById(R.id.image_value_item_balance_for_day);
                TextView categoryNameTextView = view.findViewById(R.id.title_item_balance_for_day);
                TextView sumTextView = view.findViewById(R.id.sum_item_balance_for_day);
                TextView commentTextView = view.findViewById(R.id.comment_item_balance_for_day);

                IconObject icon = IconsItemStoreProvider.getInstance(view.getContext()).getIconById(item.getCategoryId());
                categoryImageView.setImageResource(icon.getIconImage());
                categoryNameTextView.setText(icon.getIconName());
                sumTextView.setText(String.valueOf(item.getOperationSum()));
                commentTextView.setText(item.getComment());
            }
        };
    }
}
