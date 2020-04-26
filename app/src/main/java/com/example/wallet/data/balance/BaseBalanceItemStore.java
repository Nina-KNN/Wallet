package com.example.wallet.data.balance;

import java.util.HashSet;
import java.util.Set;

public abstract class BaseBalanceItemStore implements BalanceItemStore{

    private final Set<BalanceItemStore.Listener> listenersSet = new HashSet<>();

    // Метод для уведомления слушателей об изменениях
    protected final void  notifyListeners() {
        for(BalanceItemStore.Listener listener : listenersSet) {
            listener.onBalanceListChange();
        }
    }

    @Override
    public final void addListener(BalanceItemStore.Listener listener) {
        listenersSet.add(listener);
    }

    @Override
    public final void removeListener(BalanceItemStore.Listener listener) {
        listenersSet.remove(listener);
    }
}
