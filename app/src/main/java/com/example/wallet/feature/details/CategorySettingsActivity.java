package com.example.wallet.feature.details;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallet.R;
import com.example.wallet.data.icons.IconObject;
import com.example.wallet.data.icons.IconsItemStoreProvider;
import com.example.wallet.feature.details.base.BaseActivity;
import com.example.wallet.feature.list.OnSwipeTouchListener;
import com.example.wallet.feature.list.adapter.CategorySettingAdapter;

import java.util.List;

public class CategorySettingsActivity extends BaseActivity implements View.OnClickListener{

    RelativeLayout relativeLayout;
    private RecyclerView recyclerView;

    private TextView profitTextView;
    private TextView expenseTextView;
    private View profitLineView;
    private View expenseLineView;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_category_settings;
    }

    @Override
    protected void initView() {
        relativeLayout = findViewById(R.id.content_settings);
        profitTextView = findViewById(R.id.profit_icons_setting);
        expenseTextView = findViewById(R.id.expense_icons_setting);
        profitLineView = findViewById(R.id.profit_line_icons_settings);
        expenseLineView = findViewById(R.id.expense_line_icons_settings);

        findViewById(R.id.button_back_icons_setting).setOnClickListener(this);
        profitTextView.setOnClickListener(this);
        expenseTextView.setOnClickListener(this);

        makeTouchListener(this);
        makeRecyclerView(false);
    }

    private void makeRecyclerView(boolean isProfit) {
        List<IconObject> categoryList = IconsItemStoreProvider.getInstance(this).getIconsList(isProfit);

        recyclerView = findViewById(R.id.recycler_category_setting);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CategorySettingAdapter(categoryList));
    }

    private void profitOrExpenseWasChoice(boolean isProfit) {
        Resources res = getResources();
        int lineColor = res.getColor(R.color.colorBlueDark);
        makeRecyclerView(isProfit);

        if(isProfit) {
            profitTextView.setTextAppearance(getApplicationContext(), R.style.boldText);
            expenseTextView.setTextAppearance(getApplicationContext(), R.style.normalText);
            profitLineView.setBackgroundColor(lineColor);
            expenseLineView.setBackgroundColor(Color.TRANSPARENT);
        } else {
            profitTextView.setTextAppearance(getApplicationContext(), R.style.normalText);
            expenseTextView.setTextAppearance(getApplicationContext(), R.style.boldText);
            profitLineView.setBackgroundColor(Color.TRANSPARENT);
            expenseLineView.setBackgroundColor(lineColor);
        }
    }

    // Действия при свайпах в разные стороны
    private void makeTouchListener(final Context currentActivity) {
        relativeLayout.setOnTouchListener(new OnSwipeTouchListener(currentActivity) {
            public void onSwipeRight() {
                profitOrExpenseWasChoice(true);
            }

            public void onSwipeLeft() {
                profitOrExpenseWasChoice(false);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back_icons_setting:
                onBackPressed();
            case R.id.profit_icons_setting:
                profitOrExpenseWasChoice(true);
                break;
            case R.id.expense_icons_setting:
                profitOrExpenseWasChoice(false);
                break;
        }
    }
}