package com.example.wallet.feature.details;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.wallet.R;

public class BalanceActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_list);

        FragmentManager fragmentManager = getSupportFragmentManager();

        if(fragmentManager.findFragmentById(R.id.fragment_container) == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, new BalanceListFragment())
//                    .add(R.id.fragment_container, new AddOperationFragment())
                    .commit();
        }
    }
}
