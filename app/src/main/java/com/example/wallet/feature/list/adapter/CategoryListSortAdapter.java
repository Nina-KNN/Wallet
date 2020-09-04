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
import com.example.wallet.feature.list.WorkWithDate;
import com.example.wallet.feature.list.adapter.baseAdapter.BaseRecyclerAdapter;

import java.util.GregorianCalendar;
import java.util.List;

public class CategoryListSortAdapter extends BaseRecyclerAdapter<IconObject> {
    public static final String ITEMS_ID = "items_id";
    private Context context;
    private BalanceListCategorySortAdapter adapter;
    private RecyclerView recyclerView;

    protected CategoryListSortAdapter(BaseActivity baseActivity, List<IconObject> items) {
        super(baseActivity, items);
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
                ImageView imageView = view.findViewById(R.id.image_recycler_category_title);
                TextView categoryNameTextView = view.findViewById(R.id.category_recycler_category_title);
                TextView sumTextView = view.findViewById(R.id.sum_recycler_category_title);
                recyclerView = view.findViewById(R.id.recycler_for_one_category);

                context = view.getContext();
                imageView.setImageResource(item.getIconImage());
                categoryNameTextView.setText(item.getIconName());
                sumTextView.setText("15");  // ИЗМЕНИТЬ

                makeRecyclerView(BalanceListForOneCategory(item));
            }
        };
    }

    private void makeRecyclerView(List<Balance> balanceList) {
        adapter = new BalanceListCategorySortAdapter((BaseActivity) context, balanceList, innerItemListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }

    private List<Balance> BalanceListForOneCategory(IconObject category) {
        List<Balance> balanceList = BalanceItemStoreProvider.getInstance(context)
                .getBalanceListForIsProfitPeriodAndCategory(
                        firstDay(new GregorianCalendar()), // ИЗМЕНИТЬ
                        lastDay(new GregorianCalendar()), // ИЗМЕНИТЬ
                        true,
                        String.valueOf(category.getIconId())
                );

        return balanceList;
    }

    private long firstDay(GregorianCalendar date) {
        return WorkWithDate.makeMonthPeriod(true, date);
    }

    private long lastDay(GregorianCalendar date) {
        return WorkWithDate.makeMonthPeriod(false, date);
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
}
