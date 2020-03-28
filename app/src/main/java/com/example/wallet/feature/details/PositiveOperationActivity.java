package com.example.wallet.feature.details;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallet.R;
import com.example.wallet.data.Balance;
import com.example.wallet.data.IconObject;
import com.example.wallet.feature.list.adapter.IconsListAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class PositiveOperationActivity extends AppCompatActivity implements View.OnClickListener{

    private Balance balance;
    private RecyclerView recyclerView;
    private List<IconObject> iconsList = createIconList();

    private TextView dateTextView;
    private TextView idTextView;
    private TextView titleTextView;
    private CheckBox choiceProfitCheckBox;
    private ImageView imageImageView;
    private TextView iconNameTextView;
    private EditText sumEditText;
    private EditText commentEditText;
    private Button saveButton;

    private int image;
    private String imageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_positive_operation);

        viewById();

        balance = new Balance();
        fillAndShowAllData();
    }


    private void viewById() {
        dateTextView = findViewById(R.id.date_positive_operation);
        idTextView = findViewById(R.id.id_positive_operation);
        titleTextView = findViewById(R.id.title_positive_operation);
        choiceProfitCheckBox = findViewById(R.id.choice_profit_positive_operation);
        imageImageView = findViewById(R.id.image_value_positive_operation);
        iconNameTextView = findViewById(R.id.icon_name_positive_operation);
        sumEditText = findViewById(R.id.enter_positive_operation);
        commentEditText = findViewById(R.id.enter_comment_positive_operation);
        saveButton = findViewById(R.id.save_button_positive_operation);
    }

    private void fillAndShowAllData() {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(balance.getDate());

        dateTextView.setText(currentDate);
        idTextView.setText(UUID.randomUUID().toString());
        titleTextView.setText(R.string.title_profit);
        choiceProfitCheckBox.setVisibility(View.GONE);

        imageImageView.setOnClickListener(this);
        saveButton.setOnClickListener(this);
    }

    private void makeRecyclerView() {
        recyclerView = findViewById(R.id.recycler_positive_operation);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(new IconsListAdapter(iconsList, itemListener));
    }

    // Обработка нажатия на элемент списка
    private final IconsListAdapter.ItemListenerForIcons itemListener = new IconsListAdapter.ItemListenerForIcons() {
        @Override
        public void onIconClickListener(IconObject icon) {
            image = icon.getIconImage();
            imageName = icon.getIconName();

            iconNameTextView.setText(imageName);
            imageImageView.setImageResource(image);
        }
    };

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_value_positive_operation:
                makeRecyclerView();
                Toast.makeText(this, "image was pressed", Toast.LENGTH_SHORT).show();
                break;
            case R.id.save_button_positive_operation:
                Toast.makeText(this, "save was pressed", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
