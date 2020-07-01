package com.example.wallet.data.icons;

import java.util.HashSet;
import java.util.Set;

public abstract class BaseIconItemStore implements IconItemStore{

    private final Set<IconItemStore.Listener> listenersSet = new HashSet<>();

    // Метод для уведомления слушателей об изменениях
    protected final void notifyListeners() {
        for(IconItemStore.Listener listener : listenersSet) {
            listener.onIconListChange();
        }
    }

    @Override
    public final void addListener(IconItemStore.Listener listener) {
        listenersSet.add(listener);
    }

    @Override
    public final void removeListener(IconItemStore.Listener listener) {
        listenersSet.remove(listener);
    }

}
