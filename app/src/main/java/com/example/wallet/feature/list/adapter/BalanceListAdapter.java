package com.example.wallet.feature.list.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallet.R;
import com.example.wallet.data.Balance;
import com.example.wallet.databinding.ItemOfBalanceListBinding;

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

        ItemOfBalanceListBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_of_balance_list, parent, false);
        return new BalanceViewHolder(binding, itemListener);
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