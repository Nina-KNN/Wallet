package com.example.wallet.feature.list.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallet.R;
import com.example.wallet.data.balance.Balance;
import com.example.wallet.databinding.ItemOfBalanceListBinding;

import java.util.List;

public class BalanceListAdapter extends RecyclerView.Adapter<BalanceViewHolder> {

    private List<Balance> balanceList;
    private ItemListener itemListener;
    private Context context;
    private boolean profit;

    public BalanceListAdapter(List<Balance> balanceList, ItemListener itemListener, boolean profit, Context context) {
        this.balanceList = balanceList;
        this.itemListener = itemListener;
        this.profit = profit;
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
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        ItemOfBalanceListBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_of_balance_list, parent, false);
//        return new BalanceViewHolder(binding, itemListener);
        return new BalanceViewHolder(binding, itemListener, context);
    }

    @Override
    public void onBindViewHolder(@NonNull BalanceViewHolder holder, int position) {
        Balance balance = balanceList.get(position);

        holder.itemView.setVisibility(View.VISIBLE);
        holder.itemView.setLayoutParams(
                new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

        // Скрыть элемент
        if(balance.isChoiceProfit() != profit) {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }

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