package com.example.wallet.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

public class BalanceItemStore {
    //Singleton
    private static BalanceItemStore instance;

    private BalanceItemStore() {}

    public static BalanceItemStore getInstance() {
        if(instance == null) {
            instance = new BalanceItemStore();
        }
        return instance;
    }
    //End of singleton



    private List<Balance> balanceList = new ArrayList<>();

    public List<Balance> getBalanceList() {
        return balanceList;
    }

    public Balance getById(UUID id) {
        for(Balance balanceItem : balanceList) {
            if(balanceItem.getId().equals(id)) {
                return balanceItem;
            }
        }
        return null;
    }


    // Добавить новый объект в список объектов типа Balance
    public void addNewItemInBalanceList(Balance balance) {
        balanceList.add(balance);
    }


    // Форматирование времени как "день.месяц.год"
    public String dateFormatNew (Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(date);

        return currentDate;
    }

    //Удаление объектов
    public void deleteBalanceItem(Balance balance) {
        balanceList.remove(balance);
        notifyListeners();
    }

    //Удаление объектов по id
    public void deleteBalanceItem(UUID id) {
        for(Balance balanceItem : balanceList) {
            if(balanceItem.getId() == id) {
                balanceList.remove(balanceItem);
                notifyListeners();
                break;
            }
        }
    }

    // Востановить удаленный элемент
    public void resurrectBalanceItem(Balance balanceItem, int position) {
        balanceList.add(position, balanceItem);
        notifyListeners();
    }

    // Метод для уведомления слушателей об изменениях
    private void notifyListeners() {
        for(Listener listener : listenersSet) {
            listener.onCrimesListChange();
        }
    }

    private final Set<Listener> listenersSet = new HashSet<>();

    public void addListener(Listener listener) {
        listenersSet.add(listener);
    }

    public void removeListener(Listener listener) {
        listenersSet.remove(listener);
    }

    public interface Listener {
        void onCrimesListChange();
    }
}
