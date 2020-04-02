package com.example.wallet.feature.details;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallet.R;
import com.example.wallet.data.Balance;
import com.example.wallet.data.BalanceItemStoreProvider;
import com.example.wallet.feature.list.adapter.BalanceListAdapter;

public class BalancePositiveListActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView title;
    private RecyclerView recyclerView;
    private BalanceListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_positive_list);

        viewById();

        adapter = new BalanceListAdapter(BalanceItemStoreProvider.getInstance(this).getBalanceList(), itemListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void viewById() {
        recyclerView = findViewById(R.id.recycler);

        findViewById(R.id.add_positive_item).setOnClickListener(this);
        findViewById(R.id.next_month).setOnClickListener(this);
        findViewById(R.id.previous_month).setOnClickListener(this);
        findViewById(R.id.go_to_negative_list).setOnClickListener(this);

        title = findViewById(R.id.title_positive_list);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_positive_item:
                Intent intent = new Intent(this, PositiveOperationActivity.class);
                startActivity(intent);
                break;

            case R.id.next_month:
                Toast.makeText(this, "Next_Month button was presses", Toast.LENGTH_SHORT).show();
                break;

            case R.id.previous_month:
                Toast.makeText(this, "Previous_Month button was presses", Toast.LENGTH_SHORT).show();
                break;

            case R.id.go_to_negative_list:
                Toast.makeText(this, "Negative_List button was presses", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    // Обработка нажатия на элемент списка
    private final BalanceListAdapter.ItemListener itemListener = new BalanceListAdapter.ItemListener() {
        @Override
        public void onBalanceItemClicked(Balance balance) {
            //добавить переход в активити для редактирования итема
            Toast.makeText(BalancePositiveListActivity.this, "Item was clicked", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBalanceItemLongClicked(Balance balance) {

        }
    };

}
