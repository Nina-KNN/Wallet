package com.example.wallet.feature.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallet.R;
import com.example.wallet.data.Balance;
import com.example.wallet.feature.list.Adapter.BalanceListAdapter;

import java.util.ArrayList;
import java.util.List;

public class BalanseListFragment extends Fragment {

    //Model
    private List<Balance> balances = generateDemoBalance();

    //View
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_balance_list, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new BalanceListAdapter(balances));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    // Заполнить лист crimes
    private static List<Balance> generateDemoBalance() {
        List<Balance> result = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Balance balance = new Balance();
            balance.setOperationSum(i);
            balance.setTitle("Balance #" + i);

            result.add(balance);
        }
        return result;
    }
}
