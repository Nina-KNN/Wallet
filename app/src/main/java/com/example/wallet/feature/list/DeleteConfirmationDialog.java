package com.example.wallet.feature.list;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;

import com.example.wallet.R;
import com.example.wallet.data.balance.Balance;
import com.example.wallet.data.balance.BalanceItemStoreProvider;
import com.example.wallet.feature.details.BalanceListDateSortActivity;

public class DeleteConfirmationDialog {

    public Dialog onCreateDialog(Balance balance, Context context) {
        return new AlertDialog.Builder(context)
                .setMessage(R.string.dialog_delete_item_message)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> actionPressedPositiveButton(balance, context))
                .setNegativeButton(android.R.string.no, null)
                .create();
    }

    private final void actionPressedPositiveButton(Balance balance, Context context) {
        BalanceItemStoreProvider.getInstance(context).deleteBalanceItem(balance.getId());
        Intent intent = new Intent(context, BalanceListDateSortActivity.class);
        context.startActivity(intent);
    }

}
