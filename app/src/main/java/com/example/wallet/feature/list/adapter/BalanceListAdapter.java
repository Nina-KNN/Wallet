package com.example.wallet.feature.list.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallet.R;
import com.example.wallet.data.balance.Balance;

import java.util.List;

public class BalanceListAdapter extends RecyclerView.Adapter<BalanceViewHolder>{

    private List<Balance> balanceList;
    private ItemListener itemListener;
    private Context context;

    public BalanceListAdapter(List<Balance> balanceList, ItemListener itemListener, Context context) {
        this.balanceList = balanceList;
        this.itemListener = itemListener;
        this.context = context;

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
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_of_balance_list, parent, false);

        return new BalanceViewHolder(itemView, itemListener, context);
    }

    @Override
    public void onBindViewHolder(@NonNull BalanceViewHolder holder, int position) {
        Balance balance = balanceList.get(position);

        holder.itemView.setVisibility(View.VISIBLE);
        holder.itemView.setLayoutParams(
                new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

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
