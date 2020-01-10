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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.wallet.R;
import com.example.wallet.data.Balance;
import com.example.wallet.data.BalanceItemStoreProvider;
import com.example.wallet.databinding.FragmentOperationBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class ItemBalanceFragment extends Fragment {

    private static String KEY_ITEM_ID = "key_item_id";

    //Model
    private Balance balance;
    UUID id;
    FragmentOperationBinding binding;

    //View
    private TextView titleTextView;
    private TextView dateTextView;
    private EditText enterProfitEditText;
    private EditText commentEditText;
    private Button saveButton;
    private ImageView itemImageView;
    private TextView idTextView;
    private CheckBox isProfitCheckBox;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = (UUID) getArguments().getSerializable(KEY_ITEM_ID);

        if(id == null) {
            balance = new Balance();
        } else {
            balance = BalanceItemStoreProvider.getInstance(getContext()).getById(id);
        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_operation, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titleTextView = binding.title;
        dateTextView = binding.date;
        enterProfitEditText = binding.enterOperationSum;
        commentEditText = binding.enterComment;
        saveButton = binding.saveButton;
        itemImageView = binding.imageValue;
        idTextView = binding.id;
        isProfitCheckBox = binding.choiceProfit;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

        if(id == null) {
            // Если новый объект
            Date date = new Date();
            balance.setDate(date);

            dateTextView.setText(dateFormat.format(date));
            isProfitCheckBox.setChecked(false);
            makeChoice(false);
        } else {
            // Если объект уже существует в списке
            idTextView.setText(balance.getId().toString());
            commentEditText.setText(balance.getComment());
            enterProfitEditText.setText(String.valueOf(Math.abs(balance.getOperationSum())));
            makeChoice(balance.isChoiceProfit());
            dateTextView.setText(dateFormat.format(balance.getDate()));
        }

        isProfitCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                makeChoice(isChecked);
            }
        });

        enterProfitEditText.addTextChangedListener(new TextWatcher() {
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

        commentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                balance.setComment(s.toString()); // заполнение поля в объекте
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Сохранить изменения при нажатии кнопки
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(enterProfitEditText.getText().length() == 0 || Integer.parseInt(String.valueOf(enterProfitEditText.getText())) == 0) {
                    Toast.makeText(
                            getContext(),
                            R.string.message_about_wrong_data,
                            Toast.LENGTH_SHORT)
                            .show();
                } else {

                    // Проверить в каком состоянии сейчас "choiceProfit", и при необходимости изменить знак "operationSum()"
                    if(isProfitCheckBox.isChecked() && balance.getOperationSum() < 0) {
                        enterProfitEditText.setText(String.valueOf(balance.getOperationSum() * (-1)));
                    } else if (! isProfitCheckBox.isChecked() && balance.getOperationSum() > 0) {
                        enterProfitEditText.setText(String.valueOf(balance.getOperationSum() * (-1)));
                    } else {
                        enterProfitEditText.setText(String.valueOf(balance.getOperationSum()));
                    }

                    // Сохранить созданный элемент или обновить существующий
                    if (id == null) {
                        balance.setId(UUID.randomUUID());
                        BalanceItemStoreProvider.getInstance(getContext()).addNewItemInBalanceList(balance);
                    } else {
                        // обновить уже существующий элемент
                        saveBalanceItemChange();
                    }

                    // симулировать нажатие на back
                    getActivity().onBackPressed();
                }

            }
        });

    }



    private void makeChoice(boolean choice) {
        balance.setChoiceProfit(choice); // изменение поля в объекте
        isProfitCheckBox.setChecked(choice);

        if(choice) {
            balance.setTitle(String.valueOf(R.string.title_profit)); // изменение поля в объекте

            titleTextView.setText(R.string.title_profit);
            itemImageView.setImageResource(R.drawable.image_profit);
        } else {
            balance.setTitle(String.valueOf(R.string.title_expense)); // изменение поля в объекте

            titleTextView.setText(R.string.title_expense);
            itemImageView.setImageResource(R.drawable.image_expense);
        }
    }

    // Сохранить изменения в существующем объекте
    private void saveBalanceItemChange() {
        BalanceItemStoreProvider.getInstance(getContext()).update(balance);
    }


    public static ItemBalanceFragment makeInstance(UUID id) {
        ItemBalanceFragment fragment = new ItemBalanceFragment();
        Bundle args = new Bundle();

        args.putSerializable(KEY_ITEM_ID, id);
        fragment.setArguments(args);

        return fragment;
    }
}
