package com.example.wallet.Adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BalanceListAdapter extends RecyclerView.Adapter<BalanceViewHolder> {

    @NonNull
    @Override
    public BalanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BalanceViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}


class BalanceViewHolder extends RecyclerView.ViewHolder {

    public BalanceViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}