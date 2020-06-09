package com.example.wallet.data.balance.room;

import android.content.Context;

import androidx.room.Room;

import com.example.wallet.data.balance.Balance;
import com.example.wallet.data.balance.BaseBalanceItemStore;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class RoomBalanceItemStore extends BaseBalanceItemStore {

    private BalanceDao balanceDao;

    public RoomBalanceItemStore(Context context) {
        balanceDao = Room
                .databaseBuilder(
                        context,
                        BalanceDatabase.class,
                        "Balance-database.sqlite"
                )
                .allowMainThreadQueries()
                .build()
                .balanceDao();
    }

    @Override
    public List<Balance> getBalanceList() {
        List<BalanceEntity> balanceEntityList = balanceDao.getBalanceList();
        List<Balance> resultList = new ArrayList<>();

        for(BalanceEntity balanceEntity : balanceEntityList) {
            resultList.add(Converter.convert(balanceEntity));
        }

        return resultList;
    }

    @Override
    public List<Balance> getBalanceListForPeriod(long startDatePeriod, long endDatePeriod){
        List<BalanceEntity> balanceEntityList = balanceDao.getBalanceListForPeriod(startDatePeriod, endDatePeriod);
        List<Balance> resultList = new ArrayList<>();

        for(BalanceEntity balanceEntity : balanceEntityList) {
            resultList.add(Converter.convert(balanceEntity));
        }

        Collections.sort(resultList);
        return resultList;
    }

    @Override
    public Balance getById(UUID id) {
        BalanceEntity balanceEntity = balanceDao.getBalanceById(id.toString());

        return Converter.convert(balanceEntity);
    }

    @Override
    public void addNewItemInBalanceList(Balance balance) {
        balanceDao.add(Converter.convert(balance));
        notifyListeners();
    }

    @Override
    public void deleteBalanceItem(Balance balance) {
        balanceDao.delete(Converter.convert(balance));
        notifyListeners();
    }

    @Override
    public void deleteBalanceItem(UUID id) {
        BalanceEntity entityToDelete = new BalanceEntity();

        entityToDelete.id = id.toString();
        balanceDao.delete(entityToDelete);
        notifyListeners();
    }

    @Override
    public void resurrectBalanceItem(Balance balanceItem, int position) {
        balanceDao.add(Converter.convert(balanceItem));
        notifyListeners();
    }

    @Override
    public void update(Balance balanceItem) {
        balanceDao.update(Converter.convert(balanceItem));
    }
}
