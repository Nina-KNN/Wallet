package com.example.wallet.feature.details;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallet.R;
import com.example.wallet.data.Balance;
import com.example.wallet.data.BalanceItemStoreProvider;
import com.example.wallet.data.IconObject;
import com.example.wallet.feature.list.adapter.IconsListAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static com.example.wallet.feature.details.BalanceListActivity.ITEMS_ID;
import static com.example.wallet.feature.details.BalanceListActivity.PROFIT_VALUE;

public class ItemOperationActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView dateTextView;
    private TextView idTextView;
    private TextView titleTextView;
    private CheckBox choiceProfitCheckBox;
    private ImageView imageImageView;
    private TextView iconNameTextView;
    private EditText sumEditText;
    private EditText commentEditText;

    private Balance balance;
    private RecyclerView recyclerView;
    private List<IconObject> iconsList = createIconList();

    private UUID id;
    private int image;
    private String imageName = "";
    private boolean profit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_operation);

        viewById();
        fillAndShowAllData();
    }


    private void viewById() {
        dateTextView = findViewById(R.id.date_positive_operation);
        idTextView = findViewById(R.id.id_positive_operation);
        titleTextView = findViewById(R.id.title_positive_operation);
        choiceProfitCheckBox = findViewById(R.id.choice_profit_positive_operation);
        iconNameTextView = findViewById(R.id.icon_name_positive_operation);
        sumEditText = findViewById(R.id.enter_positive_operation);
        commentEditText = findViewById(R.id.enter_comment_positive_operation);

        imageImageView = findViewById(R.id.image_value_positive_operation);
        imageImageView.setOnClickListener(this);
        findViewById(R.id.save_button_positive_operation).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_value_positive_operation:
                makeRecyclerView();
                break;

            case R.id.save_button_positive_operation:
                saveBalanceData();
                break;
        }
    }

    private void fillAndShowAllData() {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

        Intent intent = getIntent();
        id = (UUID) intent.getSerializableExtra(ITEMS_ID);

        if(id != null) {
            // Если объект уже существует в списке
            balance = BalanceItemStoreProvider.getInstance(this).getById(id);

            makeChoice(balance.isChoiceProfit());
            dateTextView.setText(dateFormat.format(balance.getDate()));
            idTextView.setText(id.toString());
            sumEditText.setText(String.valueOf(Math.abs(balance.getOperationSum())));
            commentEditText.setText(balance.getComment());
        } else {
            // Если новый объект
            profit = (boolean) intent.getSerializableExtra(PROFIT_VALUE);
            balance = new Balance();
            makeChoice(profit);
        }

        enterOperationSum();
        enterComment();
        makeCheckBoxListener();
    }

    private void makeRecyclerView() {
        recyclerView = findViewById(R.id.recycler_positive_operation);
        recyclerView.setLayoutManager(new GridLayoutManager(this, columnsCount()));
        recyclerView.setAdapter(new IconsListAdapter(iconsList, itemListener));
        recyclerView.setVisibility(View.VISIBLE);
    }

    // вычесление количества столбцов для recyclerView
    private int columnsCount(){
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE); //Получаем размер экрана
        Display display = wm.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int screenWidth = point.x; //Ширина экрана
        int photoWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 85, this.getResources().getDisplayMetrics()); //Переводим в точки
        int columnsCount = screenWidth/photoWidth; //Число столбцов

        return columnsCount;
    }

    // Обработка нажатия на элемент списка
    private final IconsListAdapter.ItemListenerForIcons itemListener = new IconsListAdapter.ItemListenerForIcons() {
        @Override
        public void onIconClickListener(IconObject icon) {
            image = icon.getIconImage();
            imageName = icon.getIconName();

            iconNameTextView.setText(imageName);
            imageImageView.setImageResource(image);
            recyclerView.setVisibility(View.INVISIBLE);
        }
    };

    private void saveBalanceData() {
        // Проверить в каком состоянии сейчас "choiceProfit", и при необходимости изменить знак "operationSum()"
        if(choiceProfitCheckBox.isChecked() && balance.getOperationSum() < 0) {
            sumEditText.setText(String.valueOf(balance.getOperationSum() * (-1)));
        } else if (! choiceProfitCheckBox.isChecked() && balance.getOperationSum() > 0) {
            sumEditText.setText(String.valueOf(balance.getOperationSum() * (-1)));
        } else {
            sumEditText.setText(String.valueOf(balance.getOperationSum()));
        }


        if(sumEditText.getText().length() == 0 || Integer.parseInt(String.valueOf(sumEditText.getText())) == 0) {
            Toast.makeText(this, R.string.message_about_wrong_data, Toast.LENGTH_SHORT).show();
        } else if(imageName.isEmpty()) {
            Toast.makeText(this, R.string.choice_category, Toast.LENGTH_SHORT).show();
        } else {
            // Сохранить созданный элемент или обновить существующий
            if(id != null) {
                BalanceItemStoreProvider.getInstance(this).update(balance);
            } else {
                id = UUID.randomUUID();

                balance.setDate(new Date());
                balance.setId(id);
                BalanceItemStoreProvider.getInstance(this).addNewItemInBalanceList(balance);
            }

            Intent intent = new Intent();
            intent.putExtra("profit", profit);
            setResult(RESULT_OK, intent);
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
                    balance.setOperationSum(10); // заполнение поля в объекте
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

    private void makeCheckBoxListener() {
        choiceProfitCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                makeChoice(isChecked);
            }
        });
    }

    private void makeChoice(boolean choice) {
        profit = choice;
        balance.setChoiceProfit(choice); // изменение поля в объекте
        choiceProfitCheckBox.setChecked(choice);

        if(choice) {
            balance.setTitle(String.valueOf(R.string.title_profit)); // изменение поля в объекте
            titleTextView.setText(R.string.title_profit);
        } else {
            balance.setTitle(String.valueOf(R.string.title_expense)); // изменение поля в объекте
            titleTextView.setText(R.string.title_expense);
        }
    }

    private List<IconObject> createIconList() {
        List<IconObject> iconsList = new ArrayList<>();

        iconsList.add(new IconObject(R.drawable.briefcase, "briefcase"));
        iconsList.add(new IconObject(R.drawable.cards, "cards"));
        iconsList.add(new IconObject(R.drawable.cash_back, "cash_back"));
        iconsList.add(new IconObject(R.drawable.coins_1, "coins_1"));
        iconsList.add(new IconObject(R.drawable.coins_2, "coins_2"));
        iconsList.add(new IconObject(R.drawable.coins_3, "coins_3"));
        iconsList.add(new IconObject(R.drawable.book, "book"));

        return iconsList;
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
}
