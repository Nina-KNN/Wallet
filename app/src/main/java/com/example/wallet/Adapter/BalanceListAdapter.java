package com.example.wallet.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallet.Balance;
import com.example.wallet.R;

import java.util.List;

public class BalanceListAdapter extends RecyclerView.Adapter<BalanceViewHolder> {

    private List<Balance> balances;

    public BalanceListAdapter(List<Balance> balances) {
        this.balances = balances;
    }

    @NonNull
    @Override
    public BalanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View itemView = inflater.inflate(R.layout.item_of_balance, parent, false);

        return new BalanceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BalanceViewHolder holder, int position) {
        Balance balance = balances.get(position);

        holder.bindTo(balance);
    }

    @Override
    public int getItemCount() {
        return balances.size();
    }
}




class BalanceViewHolder extends RecyclerView.ViewHolder {

    private Balance balance;

    private TextView titleView;
    private TextView dateView;
    private TextView operationSumView;

    public BalanceViewHolder(@NonNull View itemView) {
        super(itemView);

        titleView = itemView.findViewById(R.id.title);
        dateView = itemView.findViewById(R.id.date);
        operationSumView = itemView.findViewById(R.id.sum);
    }

    public void bindTo(Balance balance) {
        this.balance = balance;

//        SimpleDateFormat dayFormat = new SimpleDateFormat("EEE, d MMMM", Locale.getDefault());
//        String myString = dayFormat.format(balance.getDate().getTime());
//        dateView.setText(myString);

        titleView.setText(balance.getTitle());
        dateView.setText(balance.getDate().toString());
        operationSumView.setText(String.valueOf(balance.getOperationSum()));
    }
}