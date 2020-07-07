package com.example.wallet;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wallet.feature.details.base.BaseActivity;
import com.example.wallet.feature.list.OnSwipeTouchListener;

public class IconsSettingsActivity extends BaseActivity implements View.OnClickListener{

    RelativeLayout relativeLayout;

    private TextView profitTextView;
    private TextView expenseTextView;
    private View profitLineView;
    private View expenseLineView;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_icons_settings;
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
    }


    private void profitOrExpenseWasChoice(boolean isProfit) {
        Resources res = getResources();
        int lineColor = res.getColor(R.color.colorBlueDark);

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