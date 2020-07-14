package com.example.wallet.data.balance.room;

import com.example.wallet.data.balance.Balance;

import java.util.Date;
import java.util.UUID;

public class Converter {

    //Конвертация объектов BalanceEntity в объекты Balance
    static Balance convert(BalanceEntity entity) {
        Balance balance = new Balance();

        balance.setId(UUID.fromString(entity.id));
        balance.setCategoryId(UUID.fromString(entity.categoryId));
        balance.setOperationSum(entity.operationSum);
        balance.setChoiceProfit(entity.choiceProfit);
        balance.setDate(new Date(entity.date));
        balance.setComment(entity.comment);

        return balance;
    }



    //Конвертация объектов Balance в объекты BalanceEntity
    static BalanceEntity convert(Balance balance) {
        BalanceEntity balanceEntity = new BalanceEntity();

        balanceEntity.id = balance.getId().toString();
        balanceEntity.categoryId = balance.getCategoryId().toString();
        balanceEntity.operationSum = balance.getOperationSum();
        balanceEntity.choiceProfit = balance.isChoiceProfit();
        balanceEntity.date = balance.getDate().getTime();
        balanceEntity.comment = balance.getComment();

        return balanceEntity;
    }
}
