package com.example.wallet.feature.list.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.wallet.data.balance.Balance;
import com.example.wallet.data.icons.IconObject;
import com.example.wallet.data.icons.IconsItemStoreProvider;
import com.example.wallet.databinding.ItemOfBalanceListBinding;
import com.example.wallet.feature.list.WorkWithDate;

public class BalanceViewHolder extends RecyclerView.ViewHolder {

    private Balance balance;
    private BalanceListAdapter.ItemListener itemListener;
    private Context context;

    private TextView titleView;
    private TextView dateView;
    private TextView operationSumTextView;
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


    public BalanceViewHolder(ItemOfBalanceListBinding binding, BalanceListAdapter.ItemListener itemListener, Context context) {
        super(binding.getRoot());

        titleView = binding.title;
        dateView = binding.date;
        operationSumTextView = binding.sum;
        commentTextView = binding.comment;
        itemImageView = binding.imageValue;

        itemView.setOnClickListener(clickListener);
        itemView.setOnLongClickListener(longClickListener);
        this.itemListener = itemListener;
        this.context = context;
    }


    public void bindTo(Balance balance) {
        this.balance = balance;

        String currentDate = WorkWithDate.dateFormat.format(balance.getDate());

        dateView.setText(currentDate);
        operationSumTextView.setText(String.valueOf(balance.getOperationSum()));
        commentTextView.setText(balance.getComment());

        IconObject icon = IconsItemStoreProvider.getInstance(context).getIconById(balance.getCategoryId());
        itemImageView.setImageResource(icon.getIconImage());
        titleView.setText(icon.getIconName());
    }

    public Balance getBalance() {
        return balance;
    }
}
