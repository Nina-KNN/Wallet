package com.example.wallet;

import java.util.Date;
import java.util.UUID;

public class Balance {
    private UUID id;
    private String title; // содержит значение "Доход" или "Расход"
    private double operationSum; // сумма операции дохода или расхода
    private boolean profit; // при true это "profit" - доход, при false это "expense" - расход
    private Date date;

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

    public double getOperationSum() {
        return operationSum;
    }

    public boolean isProfit() {
        return profit;
    }

    public Date getDate() {
        return date;
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

    public void setProfit(boolean profit) {
        this.profit = profit;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
