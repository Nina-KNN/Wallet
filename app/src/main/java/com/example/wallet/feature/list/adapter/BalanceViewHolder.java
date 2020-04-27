package com.example.wallet.feature.list.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.wallet.data.balance.Balance;
import com.example.wallet.data.icons.CreateIconsList;
import com.example.wallet.data.icons.IconObject;
import com.example.wallet.databinding.ItemOfBalanceListBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class BalanceViewHolder extends RecyclerView.ViewHolder {

    private Balance balance;
    private BalanceListAdapter.ItemListener itemListener;

    private TextView titleView;
    private TextView dateView;
    private TextView operationSumTextView;
    private TextView idTextView;
    private TextView commentTextView;
    private ImageView itemImageView;

    private final View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            itemListener.onBalanceItemClicked(balance);
        }
    };

    private final View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            itemListener.onBalanceItemLongClicked(balance);

            return true;
        }
    };


    public BalanceViewHolder(ItemOfBalanceListBinding binding, BalanceListAdapter.ItemListener itemListener) {
        super(binding.getRoot());

        titleView = binding.title;
        dateView = binding.date;
        operationSumTextView = binding.sum;
        idTextView = binding.id;
        commentTextView = binding.comment;
        itemImageView = binding.imageValue;

        itemView.setOnClickListener(clickListener);
        itemView.setOnLongClickListener(longClickListener);
        this.itemListener = itemListener;
    }


    public void bindTo(Balance balance) {
        this.balance = balance;

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(balance.getDate());

        dateView.setText(currentDate);
        operationSumTextView.setText(String.valueOf(balance.getOperationSum()));
        idTextView.setText(balance.getId().toString());
        commentTextView.setText(balance.getComment());

        itemImageView.setImageResource(Integer.parseInt(balance.getTitle()));
        for(IconObject iconObject : CreateIconsList.getInstanceIcon(balance.isChoiceProfit())){
            if(iconObject.getIconImage() == Integer.parseInt(balance.getTitle())) {
                titleView.setText(iconObject.getIconName());
                break;
            }
        }
    }

    public Balance getBalance() {
        return balance;
    }
}
