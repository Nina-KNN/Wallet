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

    private List<Balance> balanceList;
    private ItemListener itemListener;

    public BalanceListAdapter(List<Balance> balanceList, ItemListener itemListener) {
        this.balanceList = balanceList;
        this.itemListener = itemListener;

        setHasStableIds(true);
    }

    public void submitNewList(List<Balance> newBalanceList) {
        this.balanceList = newBalanceList;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return balanceList.get(position).hashCode();
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
        Balance balance = balanceList.get(position);

        holder.bindTo(balance);
    }

    @Override
    public int getItemCount() {
        return balanceList.size();
    }

    public interface ItemListener {
        void onBalanceItemClicked(Balance balance);
        void onBalanceItemLongClicked(Balance balance);
    }
}