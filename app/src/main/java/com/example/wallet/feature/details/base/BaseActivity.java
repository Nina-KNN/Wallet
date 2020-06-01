package com.example.wallet.feature.details.base;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        initView();
    }

    protected abstract int getLayoutID();

    protected abstract void initView();

    protected void showToast(String text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }
}
