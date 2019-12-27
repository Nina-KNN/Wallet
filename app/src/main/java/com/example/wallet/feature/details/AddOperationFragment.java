package com.example.wallet.feature.details;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wallet.R;
import com.example.wallet.data.Balance;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Random;

public class AddOperationFragment extends Fragment {

    //Model
    private Balance balance;

    //View
    private TextView titleTextView;
    private TextView dateTextView;
    private EditText enterProfitEditText;
    private EditText commentEditText;
    private Button saveButton;
    private ImageView profitImageView;
    private TextView idTextView;
    private CheckBox isProfit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        balance = new Balance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_operation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titleTextView = view.findViewById(R.id.title);
        dateTextView = view.findViewById(R.id.date);
        enterProfitEditText = view.findViewById(R.id.enter_operationSum);
        commentEditText = view.findViewById(R.id.comment);
        saveButton = view.findViewById(R.id.save_button);
        profitImageView = view.findViewById(R.id.value_image);
        idTextView = view.findViewById(R.id.id);
        isProfit = view.findViewById(R.id.choiceProfit);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Random random = new Random();
        idTextView.setText(String.valueOf(random.nextInt()));

        boolean check = random.nextBoolean();
        balance.setChoiceProfit(check);
        isProfit.setChecked(check);
        isProfit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                balance.setChoiceProfit(isChecked);
                isProfit.setChecked(isChecked);

                makeSetTitle(isChecked);
            }
        });

        makeSetTitle(check);

        // Форматирование времени как "день.месяц.год"
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String dateText = dateFormat.format(balance.getDate());
        dateTextView.setText(dateText);

        enterProfitEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                balance.setOperationSum(Integer.parseInt(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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

        //saveButton;
    }


    private void makeSetTitle(boolean choice) {

        if(choice == true) {
            balance.setTitle(String.valueOf(R.string.title_profit));
            titleTextView.setText(R.string.title_profit);

            profitImageView.setImageResource(R.drawable.profit_image);
        } else {
            balance.setTitle(String.valueOf(R.string.title_expense));
            titleTextView.setText(R.string.title_expense);

            profitImageView.setImageResource(R.drawable.expense_image);
        }
    }
}
