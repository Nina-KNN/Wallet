package com.example.wallet.data;

import java.util.Date;
import java.util.UUID;

public class Balance {
    private UUID id;
    private String title; // содержит значение "Доход" или "Расход"
    private int operationSum; // сумма операции дохода или расхода
    private boolean choiceProfit; // при true это "profit" - доход, при false это "expense" - расход
    private Date date;
    private String comment;

    public Balance() {
        id = UUID.randomUUID();
        date = new Date();
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getOperationSum() {
        return operationSum;
    }

    public boolean isChoiceProfit() {
        return choiceProfit;
    }

    public Date getDate() {
        return date;
    }

    public String getComment() {
        return comment;
    }



    public void setId(UUID id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOperationSum(int operationSum) {
        this.operationSum = operationSum;
    }

    public void setChoiceProfit(boolean choiceProfit) {
        this.choiceProfit = choiceProfit;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
