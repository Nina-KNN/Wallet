package com.example.wallet.feature.details;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallet.R;
import com.example.wallet.data.Balance;
import com.example.wallet.data.BalanceItemStore;
import com.example.wallet.data.BalanceItemStoreProvider;
import com.example.wallet.feature.list.DeleteConfirmationDialogFragment;
import com.example.wallet.feature.list.adapter.BalanceListAdapter;
import com.example.wallet.feature.list.adapter.BalanceViewHolder;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class BalanceListFragment extends Fragment {

    //View
    private RecyclerView recyclerView;
    private BalanceListAdapter adapter;
    Button newButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_balance_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler);
        newButton = view.findViewById(R.id.new_button);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PositiveOperationActivity.class);
                startActivity(intent);
            }
        });

        adapter = new BalanceListAdapter(BalanceItemStoreProvider.getInstance(getContext()).getBalanceList(), itemListener);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Удаление по свайпу в сторону
        ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                // используется для перемещения итемов между собой
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                BalanceViewHolder balanceViewHolder = (BalanceViewHolder) viewHolder;

                Balance balanceItem = balanceViewHolder.getBalance();
                deleteItem(balanceItem, viewHolder.getAdapterPosition());
            }
        });

        touchHelper.attachToRecyclerView(recyclerView);
    }

    // при удалении элемента по свайпу вывести сообщение и при необходимости пользователь
    // может востановить удаленный итем
    private void deleteItem(final Balance balance, final int position) {
        BalanceItemStoreProvider.getInstance(getContext()).deleteBalanceItem(balance);
        // При удалении элемента из списка, отрисовать список заново
        updateList();

        Snackbar.make(recyclerView, R.string.snackbar_message, Snackbar.LENGTH_LONG)
                .setAction(R.string.snackbar_action, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BalanceItemStoreProvider.getInstance(getContext()).resurrectBalanceItem(balance, position);
                        updateList();
                    }
                })
                .show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Сообщить менеджеру FragmentManager, что фрагмент должен получить вызов onCreateOptionsMenu()
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.create_new_item, menu);
    }

    // Обработка нажатия на кнопку меню "+"
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add_item) {
            //добавить транзакцию фрагмента
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, ItemBalanceFragment.makeInstance(null))
                    .addToBackStack(null)
                    .commit();
            adapter.notifyDataSetChanged();

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        BalanceItemStoreProvider.getInstance(getContext()).removeListener(balanceListChangedList);
        super.onPause();
    }

    private final BalanceItemStore.Listener balanceListChangedList = new BalanceItemStore.Listener() {
        @Override
        public void onBalanceListChange() {
            updateList();
        }
    };

    // Обновить список итемов
    private void updateList() {
        List<Balance> balanceList = BalanceItemStoreProvider.getInstance(getContext()).getBalanceList();
        adapter.submitNewList(balanceList);
    }

    // Обработка нажатия на элемент списка
    private final BalanceListAdapter.ItemListener itemListener = new BalanceListAdapter.ItemListener() {
        @Override
        public void onBalanceItemClicked(Balance balance) {
            //добавить транзакцию фрагмента
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, ItemBalanceFragment.makeInstance(balance.getId()))
                    .addToBackStack(null)
                    .commit();
        }

        // Удаление при длительном нажатии
        @Override
        public void onBalanceItemLongClicked(Balance balance) {
            DeleteConfirmationDialogFragment dialogFragment = DeleteConfirmationDialogFragment
                    .makeInctance(balance.getId());

            dialogFragment.show(getFragmentManager(), null);

            // При удалении элемента из списка, отрисовать список заново
            BalanceItemStoreProvider.getInstance(getContext()).addListener(balanceListChangedList);
        }
    };
}


