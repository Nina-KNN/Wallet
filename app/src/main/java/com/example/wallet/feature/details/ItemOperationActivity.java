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
import com.example.wallet.data.balance.Balance;
import com.example.wallet.data.balance.BalanceItemStoreProvider;
import com.example.wallet.data.icons.CreateIconsList;
import com.example.wallet.data.icons.IconObject;
import com.example.wallet.feature.list.CalendarDialog;
import com.example.wallet.feature.list.WorkWithDate;
import com.example.wallet.feature.list.adapter.IconsListAdapter;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import static com.example.wallet.feature.details.BalanceListActivity.ITEMS_ID;
import static com.example.wallet.feature.details.BalanceListActivity.PROFIT_VALUE;

public class ItemOperationActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView dateTextView;
    private TextView titleTextView;
    private CheckBox choiceProfitCheckBox;
    private ImageView imageImageView;
    private TextView iconNameTextView;
    private EditText sumEditText;
    private EditText commentEditText;

    private Balance balance;
    private RecyclerView recyclerView;

    private UUID id;
    private int image;
    private String imageName = "";
    private boolean profit;
    private GregorianCalendar date = new GregorianCalendar();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_operation);

        viewById();
        fillAndShowAllData();
    }


    private void viewById() {
        dateTextView = findViewById(R.id.date_positive_operation);
        titleTextView = findViewById(R.id.title_positive_operation);
        choiceProfitCheckBox = findViewById(R.id.choice_profit_positive_operation);
        iconNameTextView = findViewById(R.id.icon_name_operation);
        sumEditText = findViewById(R.id.enter_positive_operation);
        commentEditText = findViewById(R.id.enter_comment_positive_operation);

        imageImageView = findViewById(R.id.image_value_operation);
        imageImageView.setOnClickListener(this);
        findViewById(R.id.save_button_positive_operation).setOnClickListener(this);
        findViewById(R.id.button_back_item_operation).setOnClickListener(this);
        findViewById(R.id.calendar_image_button_item_operation).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_value_operation:
                makeRecyclerView(profit);
                break;

            case R.id.save_button_positive_operation:
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

    private void fillAndShowAllData() {
        Intent intent = getIntent();
        id = (UUID) intent.getSerializableExtra(ITEMS_ID);

        if(id != null) {
            // Если объект уже существует в списке
            balance = BalanceItemStoreProvider.getInstance(this).getById(id);
            date.setTime(balance.getDate());

            makeChoice(balance.isChoiceProfit());
            dateTextView.setText(WorkWithDate.showDateUtilsFormat(date, this));
            sumEditText.setText(String.valueOf(Math.abs(balance.getOperationSum())));
            commentEditText.setText(balance.getComment());
            imageImageView.setImageResource(Integer.parseInt(balance.getTitle()));

            for(IconObject iconObject : CreateIconsList.getInstanceIcon(balance.isChoiceProfit())){
                if(iconObject.getIconImage() == Integer.parseInt(balance.getTitle())) {
                    imageName = iconObject.getIconName();
                    iconNameTextView.setText(imageName);
                    break;
                }
            }
        } else {
            // Если новый объект
            profit = (boolean) intent.getSerializableExtra(PROFIT_VALUE);
            balance = new Balance();
            makeChoice(profit);
            dateTextView.setText(WorkWithDate.showDateUtilsFormat(date, this));
        }

        enterOperationSum();
        enterComment();
        makeCheckBoxListener();
    }

    private void makeRecyclerView(boolean profit) {
        List<IconObject> iconsList = CreateIconsList.getInstanceIcon(profit);

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
        int photoWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 110, this.getResources().getDisplayMetrics()); //Переводим в точки
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

            balance.setTitle(String.valueOf(image));
        }
    };

    private void saveBalanceData() {
        if(sumEditText.getText().length() == 0 || Integer.parseInt(String.valueOf(sumEditText.getText())) == 0) {
            Toast.makeText(this, R.string.message_about_wrong_data, Toast.LENGTH_SHORT).show();
        } else if(imageName.isEmpty() || imageName.equals(String.valueOf(R.string.make_choice))) {
            Toast.makeText(this, R.string.choice_category, Toast.LENGTH_SHORT).show();
        } else {
            // Сохранить созданный элемент или обновить существующий
            sumEditText.setText(String.valueOf(Math.abs(balance.getOperationSum())));
            balance.setDate(date.getTime());

            if(id != null) {
                BalanceItemStoreProvider.getInstance(this).update(balance);
            } else {
                id = UUID.randomUUID();
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

    private void makeCheckBoxListener() {
        choiceProfitCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                makeChoice(isChecked);
                imageImageView.setImageResource(R.drawable.ic_touch_app);
                imageName = String.valueOf(R.string.make_choice);
                iconNameTextView.setText(R.string.make_choice);
                makeRecyclerView(profit);
            }
        });
    }

    private void makeChoice(boolean choice) {
        profit = choice;
        balance.setChoiceProfit(choice); // изменение поля в объекте
        choiceProfitCheckBox.setChecked(choice);

        if(choice) {
            titleTextView.setText(R.string.title_profit);
        } else {
            titleTextView.setText(R.string.title_expense);
        }
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
