package com.example.wallet.feature.list.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallet.R;
import com.example.wallet.data.Balance;
import com.example.wallet.data.BalanceItemStore;

class BalanceViewHolder extends RecyclerView.ViewHolder {

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

    public BalanceViewHolder(@NonNull View itemView, BalanceListAdapter.ItemListener itemListener) {
        super(itemView);

        titleView = itemView.findViewById(R.id.title);
        dateView = itemView.findViewById(R.id.date);
        operationSumTextView = itemView.findViewById(R.id.sum);
        idTextView = itemView.findViewById(R.id.id);
        commentTextView = itemView.findViewById(R.id.comment);
        itemImageView = itemView.findViewById(R.id.value_image);

        itemView.setOnClickListener(clickListener);
        itemView.setOnLongClickListener(longClickListener);
        this.itemListener = itemListener;
    }

    public void bindTo(Balance balance) {
        this.balance = balance;

        dateView.setText(BalanceItemStore.getInstance().dateFormatNew(balance.getDate()));
        operationSumTextView.setText(String.valueOf(balance.getOperationSum()));
        idTextView.setText(balance.getId().toString());
        commentTextView.setText(balance.getComment());

        if(balance.getTitle().equals(String.valueOf(R.string.title_profit))) {
            titleView.setText(R.string.title_profit);
            itemImageView.setImageResource(R.drawable.profit_image);
        } else {
            titleView.setText(R.string.title_expense);
            itemImageView.setImageResource(R.drawable.expense_image);
        }
    }
}
