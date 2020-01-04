package com.example.wallet.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class InMemoryBalanceItemStore extends BaseBalanceItemStore {
    private List<Balance> balanceList = new ArrayList<>();

    @Override
    public List<Balance> getBalanceList() {
        return balanceList;
    }

    @Override
    public Balance getById(UUID id) {
        for(Balance balanceItem : balanceList) {
            if(balanceItem.getId().equals(id)) {
                return balanceItem;
            }
        }
        return null;
    }


    // Добавить новый объект в список объектов типа Balance
    @Override
    public void addNewItemInBalanceList(Balance balance) {
        balanceList.add(balance);
    }


    // Форматирование времени как "день.месяц.год"
    @Override
    public String dateFormatNew(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(date);

        return currentDate;
    }

    //Удаление объектов
    @Override
    public void deleteBalanceItem(Balance balance) {
        balanceList.remove(balance);
        notifyListeners();
    }

    //Удаление объектов по id
    @Override
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
    @Override
    public void resurrectBalanceItem(Balance balanceItem, int position) {
        balanceList.add(position, balanceItem);
        notifyListeners();
    }

}
