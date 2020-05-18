package com.example.wallet.data.balance;

import java.util.List;
import java.util.UUID;

public interface BalanceItemStore {
    List<Balance> getBalanceList();

    List<Balance> getBalanceListForPeriod(long startDatePeriod, long endDatePeriod);

    Balance getById(UUID id);

    // Добавить новый объект в список объектов типа Balance
    void addNewItemInBalanceList(Balance balance);

    //Удаление объектов
    void deleteBalanceItem(Balance balance);

    //Удаление объектов по id
    void deleteBalanceItem(UUID id);

    // Востановить удаленный элемент
    void resurrectBalanceItem(Balance balanceItem, int position);

    void update(Balance balanceItem);

    void addListener(Listener listener);

    void removeListener(Listener listener);

    public interface Listener {
        void onBalanceListChange();
    }
}
