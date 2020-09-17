package com.example.wallet.feature.details;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallet.R;
import com.example.wallet.data.balance.Balance;
import com.example.wallet.data.balance.BalanceItemStoreProvider;
import com.example.wallet.data.icons.IconObject;
import com.example.wallet.data.icons.IconsItemStoreProvider;
import com.example.wallet.feature.details.base.BaseActivity;
import com.example.wallet.feature.list.CalendarDialog;
import com.example.wallet.feature.list.OnSwipeTouchListener;
import com.example.wallet.feature.list.WorkWithDate;
import com.example.wallet.feature.list.adapter.IconsListAdapter;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import static com.example.wallet.feature.list.baseConst.BaseConst.ITEMS_ID;
import static com.example.wallet.feature.list.baseConst.BaseConst.PROFIT_VALUE;

public class ItemOperationActivity extends BaseActivity implements View.OnClickListener{
    RelativeLayout relativeLayout;

    private TextView profitTextView;
    private TextView expenseTextView;
    private View profitLineView;
    private View expenseLineView;
    private TextView dateTextView;
    private TextView titleTextView;
    private ImageView imageImageView;
    private TextView iconNameTextView;
    private EditText sumEditText;
    private EditText commentEditText;

    private Balance balance;
    private RecyclerView recyclerView;

    private UUID id;
    private UUID categoryId;
    private boolean profit;
    private GregorianCalendar date;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_item_operation;
    }

    @Override
    protected void initView() {
        relativeLayout = findViewById(R.id.content_item_operation);

        profitTextView = findViewById(R.id.profit_item_operation);
        expenseTextView = findViewById(R.id.expense_item_operation);
        profitLineView = findViewById(R.id.profit_line_item_operation);
        expenseLineView = findViewById(R.id.expense_line_item_operation);
        dateTextView = findViewById(R.id.date_item_operation);
        titleTextView = findViewById(R.id.title_item_operation);
        iconNameTextView = findViewById(R.id.icon_name_item_operation);
        sumEditText = findViewById(R.id.enter_item_operation);
        commentEditText = findViewById(R.id.enter_comment_item_operation);

        profitTextView.setOnClickListener(this);
        expenseTextView.setOnClickListener(this);
        imageImageView = findViewById(R.id.image_item_operation);
        imageImageView.setOnClickListener(this);
        findViewById(R.id.save_button_item_operation).setOnClickListener(this);
        findViewById(R.id.button_back_item_operation).setOnClickListener(this);
        findViewById(R.id.calendar_image_button_item_operation).setOnClickListener(this);

        fillAndShowAllData();
        makeTouchListener(this);
    }

    private void fillAndShowAllData() {
        Intent intent = getIntent();
        id = (UUID) intent.getSerializableExtra(ITEMS_ID);

        if(id != null) {
            // Если объект уже существует в списке
            balance = BalanceItemStoreProvider.getInstance(this).getById(id);
            date = balance.getDate();
            profit = balance.isChoiceProfit();

            dateTextView.setText(WorkWithDate.showDateUtilsFormat(date, this));
            sumEditText.setText(String.valueOf(Math.abs(balance.getOperationSum())));
            commentEditText.setText(balance.getComment());

            categoryId = balance.getCategoryId();
            IconObject icon = IconsItemStoreProvider.getInstance(this).getIconById(categoryId);
            imageImageView.setImageResource(icon.getIconImage());
            iconNameTextView.setText(icon.getIconName());

            titleTextView.setText(R.string.edit_note);

        } else {
            // Если новый объект
            profit = (boolean) intent.getSerializableExtra(PROFIT_VALUE);
            balance = new Balance();
            date = new GregorianCalendar();
            dateTextView.setText(WorkWithDate.showDateUtilsFormat(date, this));

            titleTextView.setText(R.string.add_new_note);
            makeRecyclerView(profit);
        }

        profitOrExpenseWasChoice(profit);
        enterOperationSum();
        enterComment();
    }

    private void makeRecyclerView(boolean profit) {
        List<IconObject> iconsList = IconsItemStoreProvider.getInstance(this).getIconsList(profit, true);

        recyclerView = findViewById(R.id.recycler_item_operation);
        recyclerView.setLayoutManager(new GridLayoutManager(this, columnsCount()));
        recyclerView.setAdapter(new IconsListAdapter(this, iconsList, itemListener));
        recyclerView.setVisibility(View.VISIBLE);
    }

    // вычесление количества столбцов для recyclerView
    private int columnsCount(){
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE); //Получаем размер экрана
        Display display = wm.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int screenWidth = point.x; //Ширина экрана
        int photoWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 110, this.getResources().getDisplayMetrics()); //Переводим в точки
        int columnsCount = screenWidth/photoWidth; //Число столбцов

        return columnsCount;
    }

    // Обработка нажатия на элемент списка
    private final IconsListAdapter.OnItemClick<IconObject> itemListener = new IconsListAdapter.OnItemClick<IconObject>() {

        @Override
        public void onItemClick(IconObject item, int position) {
            categoryId = item.getIconId();

            iconNameTextView.setText(item.getIconName());
            imageImageView.setImageResource(item.getIconImage());
            recyclerView.setVisibility(View.INVISIBLE);

            balance.setCategoryId(categoryId);
        }

        @Override
        public void onItemLongClick(IconObject item, int position) {

        }
    };

    private void saveBalanceData() {
        if(sumEditText.getText().length() == 0 || Integer.parseInt(String.valueOf(sumEditText.getText())) == 0) {
            showToast(getString(R.string.message_about_wrong_data));
        } else if(categoryId == null) {
            showToast(getString(R.string.choice_category));
        } else {
            // Сохранить созданный элемент или обновить существующий
            sumEditText.setText(String.valueOf(Math.abs(balance.getOperationSum())));
            balance.setChoiceProfit(profit);
            balance.setDate(date);
            WorkWithDate.checkCorrectDateInPrefsUtils(date.getTimeInMillis(), this);

            if(id != null) {
                BalanceItemStoreProvider.getInstance(this).update(balance);
            } else {
                id = UUID.randomUUID();
                balance.setId(id);
                BalanceItemStoreProvider.getInstance(this).addNewItemInBalanceList(balance);
            }

            Intent intent = new Intent(this, BalanceListActivity.class);
            intent.putExtra(PROFIT_VALUE, profit);
            startActivity(intent);
            finish();
        }
    }

    private void enterOperationSum() {
        sumEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString() != null && ! s.toString().equals("")) {
                    balance.setOperationSum(Integer.parseInt(s.toString())); // заполнение поля в объекте
                } else {
                    balance.setOperationSum(0); // заполнение поля в объекте
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void enterComment() {
        commentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                balance.setComment(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    // Скрыть клавиатуру при нажатии на пустое место на экране
    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                v.clearFocus();
                InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            }
        }

        return super.dispatchTouchEvent(event);
    }

    // В зависимости от выбора "Доход"или "Расход" выделить выбранное
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

    // Изменить значение "Доход"или "Расход" и показать список категорий
    private void changeProfitValue(boolean isProfit) {
        profit = isProfit;
        profitOrExpenseWasChoice(profit);
        imageImageView.setImageResource(R.drawable.ic_touch_app);
        iconNameTextView.setText(R.string.make_choice);
        makeRecyclerView(profit);
    }

    // Действия при свайпах в разные стороны
    private void makeTouchListener(final Context currentActivity) {
        relativeLayout.setOnTouchListener(new OnSwipeTouchListener(currentActivity) {
            public void onSwipeRight() {
                if(profit != true) {
                    changeProfitValue(true);
                }
            }

            public void onSwipeLeft() {
                if(profit != false) {
                    changeProfitValue(false);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profit_item_operation:
                if(profit != true) {
                    changeProfitValue(true);
                }
                break;
            case R.id.expense_item_operation:
                if(profit != false) {
                    changeProfitValue(false);
                }
                break;
            case R.id.image_item_operation:
                makeRecyclerView(profit);
                break;

            case R.id.save_button_item_operation:
                saveBalanceData();
                break;

            case R.id.button_back_item_operation:
                onBackPressed();
                break;

            case R.id.calendar_image_button_item_operation:
                CalendarDialog.setDateForShowCalendarDialog(dateTextView, date, this);
                break;
        }
    }
}
