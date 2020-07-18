package com.example.wallet.feature.list.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallet.R;
import com.example.wallet.data.icons.IconObject;

import java.util.List;

public class IconsListAdapter extends RecyclerView.Adapter<IconsListViewHolder> {

    private List<IconObject> iconsList;
    private ItemListenerForIcons itemListener;

    public IconsListAdapter(List<IconObject> iconsList, ItemListenerForIcons itemListener) {
        this.iconsList = iconsList;
        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public IconsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_of_icons_list, parent, false);

        return new IconsListViewHolder(itemView, itemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull IconsListViewHolder holder, int position) {
        holder.bindTo(iconsList.get(position));
    }

    @Override
    public int getItemCount() {
        return iconsList.size();
    }

    public interface ItemListenerForIcons{
        void onIconClickListener(IconObject icon);
    }
}



class IconsListViewHolder extends RecyclerView.ViewHolder{

    private IconObject icon;
    private IconsListAdapter.ItemListenerForIcons itemListener;

    private ImageView iconImageView;
    private TextView iconNameTextView;

    public IconsListViewHolder(@NonNull View itemView, IconsListAdapter.ItemListenerForIcons itemListener) {
        super(itemView);

        iconImageView = itemView.findViewById(R.id.icon_image_recycler);
        iconNameTextView = itemView.findViewById(R.id.icon_name_recycler);

        itemView.setOnClickListener(clickListener);
        this.itemListener = itemListener;
    }

    public void bindTo(IconObject icon){
        this.icon = icon;

        iconImageView.setImageResource(icon.getIconImage());
        iconNameTextView.setText(icon.getIconName());
    }

    private final View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            itemListener.onIconClickListener(icon);
        }
    };
}
