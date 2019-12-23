package com.example.wallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.wallet.Adapter.BalanceListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Model
    private List<Balance> balances = generateDemoBalance();

    //View
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_list);

        recyclerView = findViewById(R.id.recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new BalanceListAdapter(balances));
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
