package com.example.wallet.feature.list.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallet.R;
import com.example.wallet.data.balance.Balance;
import com.example.wallet.data.balance.BalanceItemStoreProvider;
import com.example.wallet.data.icons.IconObject;
import com.example.wallet.feature.details.ItemOperationActivity;
import com.example.wallet.feature.details.base.BaseActivity;
import com.example.wallet.feature.list.DeleteConfirmationDialog;
import com.example.wallet.feature.list.adapter.baseAdapter.BaseRecyclerAdapter;

import java.util.List;

import static com.example.wallet.feature.list.baseConst.BaseConst.ITEMS_ID;

public class BalanceListCategorySortAdapter extends BaseRecyclerAdapter<IconObject> {
    private Context context;
    private BalanceListCategorySortInnerAdapter adapter;
    private RecyclerView recyclerView;
    private List<Balance> balanceList;
    private List<IconObject> items;
    private boolean isProfit;
    private long firstDay;
    private long lastDay;

    public BalanceListCategorySortAdapter(BaseActivity baseActivity, List<IconObject> items, boolean isProfit, long firstDay, long lastDay) {
        super(baseActivity, items);
        this.items = items;
        this.isProfit = isProfit;
        this.firstDay = firstDay;
        this.lastDay = lastDay;
    }

    @Override
    protected int getCardLayoutID() {
        return R.layout.item_of_balance_list_category_title;
    }

    @Override
    protected BaseItem createViewHolder(View view) {
        return new BaseItem(view) {
            @Override
            public void bind(IconObject item) {
                balanceList = BalanceListForOneCategory(item);

                ImageView imageView = view.findViewById(R.id.image_recycler_category_title);
                TextView categoryNameTextView = view.findViewById(R.id.category_recycler_category_title);
                TextView sumTextView = view.findViewById(R.id.sum_recycler_category_title);
                recyclerView = view.findViewById(R.id.recycler_for_one_category);

                context = view.getContext();
                imageView.setImageResource(item.getIconImage());
                categoryNameTextView.setText(item.getIconName());
                sumTextView.setText(balanceCategorySum(balanceList));

                makeRecyclerView(balanceList);
            }
        };
    }

    private void makeRecyclerView(List<Balance> balanceList) {
        adapter = new BalanceListCategorySortInnerAdapter((BaseActivity) context, balanceList, innerItemListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }

    private List<Balance> BalanceListForOneCategory(IconObject category) {
        List<Balance> balanceList = BalanceItemStoreProvider.getInstance(context)
                .getBalanceListForIsProfitPeriodAndCategory(firstDay, lastDay, isProfit, String.valueOf(category.getIconId()));

        return balanceList;
    }

    private String balanceCategorySum(List<Balance> balanceList) {
        int sum = 0;
        for(Balance bal : balanceList) {
            sum += bal.getOperationSum();
        }
        return String.valueOf(sum);
    }

    // Обработка нажатий на элементы во внутреннем ресайлере
    private BalanceListCategorySortInnerAdapter.OnItemClick<Balance> innerItemListener = new OnItemClick<Balance>() {
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

    public void submitNewList(List<IconObject> iconObjectList) {
        this.items = iconObjectList;
        notifyDataSetChanged();
    }
}
