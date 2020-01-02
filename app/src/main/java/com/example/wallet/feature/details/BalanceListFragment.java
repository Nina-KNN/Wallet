package com.example.wallet.feature.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallet.R;
import com.example.wallet.data.Balance;
import com.example.wallet.data.BalanceItemStore;
import com.example.wallet.feature.list.Adapter.BalanceListAdapter;

import java.util.UUID;

public class BalanceListFragment extends Fragment {

    //View
    private RecyclerView recyclerView;
    private BalanceListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_balance_list, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new BalanceListAdapter(BalanceItemStore.getInstance().getBalanceList(), itemListener);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private final BalanceListAdapter.ItemListener itemListener = new BalanceListAdapter.ItemListener() {
        @Override
        public void onBalanceItemClicked(Balance balance) {
            //добавить транзакцию фрагмента
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, ItemBalanceFragment.makeInstance(balance.getId()))
                    .addToBackStack(null)
                    .commit();
        }
    };

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

    // Обработка нажатия на кнопку меню
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add_item) {
            //добавить транзакцию фрагмента
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, ItemBalanceFragment.makeInstance(UUID.randomUUID()))
                    .addToBackStack(null)
                    .commit();
            adapter.notifyDataSetChanged();

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

}


