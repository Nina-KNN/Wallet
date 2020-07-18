package com.example.wallet.data.balance;

import java.util.GregorianCalendar;
import java.util.UUID;

public class Balance implements Comparable<Balance>{
    private UUID id;
    private UUID categoryId;
    private int operationSum;
    private boolean choiceProfit;
    private GregorianCalendar date;
    private String comment;

    public Balance() {
        id = UUID.randomUUID();
        date = new GregorianCalendar();
    }

    public UUID getId() {
        return id;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public int getOperationSum() {
        return operationSum;
    }

    public boolean isChoiceProfit() {
        return choiceProfit;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public String getComment() {
        return comment;
    }



    public void setId(UUID id) {
        this.id = id;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public void setOperationSum(int operationSum) {
        this.operationSum = operationSum;
    }

    public void setChoiceProfit(boolean choiceProfit) {
        this.choiceProfit = choiceProfit;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    @Override
    public int compareTo(Balance o) {
        return (int) (o.getDate().getTimeInMillis() - this.getDate().getTimeInMillis());
    }
}
