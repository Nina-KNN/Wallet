package com.example.wallet.data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface BalanceItemStore {
    List<Balance> getBalanceList();

    Balance getById(UUID id);

    // Добавить новый объект в список объектов типа Balance
    void addNewItemInBalanceList(Balance balance);

    // Форматирование времени как "день.месяц.год"
    String dateFormatNew (Date date);

    //Удаление объектов
    void deleteBalanceItem(Balance balance);

    //Удаление объектов по id
    void deleteBalanceItem(UUID id);

    // Востановить удаленный элемент
    void resurrectBalanceItem(Balance balanceItem, int position);

    void addListener(Listener listener);

    void removeListener(Listener listener);

    public interface Listener {
        void onCrimesListChange();
    }
}
