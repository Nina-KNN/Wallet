package com.example.wallet.feature.list.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallet.data.Balance;
import com.example.wallet.R;

import java.util.List;

public class BalanceListAdapter extends RecyclerView.Adapter<BalanceViewHolder> {

    private List<Balance> balances;
    private ItemListener itemListener;

    public BalanceListAdapter(List<Balance> balances, ItemListener itemListener) {
        this.balances = balances;
        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public BalanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View itemView = inflater.inflate(R.layout.item_of_balance_list, parent, false);

        return new BalanceViewHolder(itemView, itemListener);
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

    public interface ItemListener {
        void onBalanceItemClicked(Balance balance);
    }
}