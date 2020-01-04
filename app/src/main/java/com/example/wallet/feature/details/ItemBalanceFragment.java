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
import androidx.fragment.app.Fragment;

import com.example.wallet.R;
import com.example.wallet.data.Balance;
import com.example.wallet.data.BalanceItemStoreProvider;

import java.util.Date;
import java.util.UUID;

public class ItemBalanceFragment extends Fragment {

    private static String KEY_ITEM_ID = "key_item_id";

    //Model
    private Balance balance;
    UUID id;

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
        if(BalanceItemStoreProvider.getInstance().getBalanceList().contains(BalanceItemStoreProvider.getInstance().getById(id))) {
            balance = BalanceItemStoreProvider.getInstance().getById(id);
        } else {
            balance = new Balance();
        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_operation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titleTextView = view.findViewById(R.id.title);
        dateTextView = view.findViewById(R.id.date);
        enterProfitEditText = view.findViewById(R.id.enter_operationSum);
        commentEditText = view.findViewById(R.id.enter_comment);
        saveButton = view.findViewById(R.id.save_button);
        itemImageView = view.findViewById(R.id.value_image);
        idTextView = view.findViewById(R.id.id);
        isProfitCheckBox = view.findViewById(R.id.choiceProfit);
    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(BalanceItemStoreProvider.getInstance().getBalanceList()
                .contains(BalanceItemStoreProvider.getInstance().getById(id))) {
            // Если объект уже существует в списке
            idTextView.setText(balance.getId().toString());
            commentEditText.setText(balance.getComment());
            enterProfitEditText.setText(String.valueOf(Math.abs(balance.getOperationSum())));
            makeChoice(balance.isChoiceProfit());
            dateTextView.setText(BalanceItemStoreProvider.getInstance().dateFormatNew(balance.getDate()));
        } else {
            // Если новый объект
            Date date = new Date();

            idTextView.setText(id.toString());
            dateTextView.setText(BalanceItemStoreProvider.getInstance().dateFormatNew(date));
            isProfitCheckBox.setChecked(false);
            makeChoice(false);

            balance.setId(id);
            balance.setDate(date);
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
                String inputString = s.toString();

                if (s.toString() != null && ! s.toString().equals("")) {
                    balance.setOperationSum(Integer.parseInt(s.toString())); // заполнение поля в объекте
                } else {
                    balance.setOperationSum(Integer.parseInt("0")); // заполнение поля в объекте
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

                   if (!BalanceItemStoreProvider.getInstance().getBalanceList()
                           .contains(BalanceItemStoreProvider.getInstance().getById(id))) {
                       BalanceItemStoreProvider.getInstance().addNewItemInBalanceList(balance);
                   }

                   getActivity().onBackPressed(); // симулировать нажатие на back
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

            itemImageView.setImageResource(R.drawable.profit_image);
        } else {
            balance.setTitle(String.valueOf(R.string.title_expense)); // изменение поля в объекте
            titleTextView.setText(R.string.title_expense);

            itemImageView.setImageResource(R.drawable.expense_image);
        }
    }




    public static ItemBalanceFragment makeInstance(UUID id) {
        ItemBalanceFragment fragment = new ItemBalanceFragment();
        Bundle args = new Bundle();

        args.putSerializable(KEY_ITEM_ID, id);
        fragment.setArguments(args);

        return fragment;
    }
}
