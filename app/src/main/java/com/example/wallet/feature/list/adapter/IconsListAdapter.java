package com.example.wallet.feature.list.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wallet.R;
import com.example.wallet.data.icons.IconObject;
import com.example.wallet.feature.details.base.BaseActivity;
import com.example.wallet.feature.list.adapter.baseAdapter.BaseRecyclerAdapter;

import java.util.List;

public class IconsListAdapter extends BaseRecyclerAdapter<IconObject> {

    public IconsListAdapter(BaseActivity baseActivity, List<IconObject> items, OnItemClick<IconObject> onItemClick) {
        super(baseActivity, items, onItemClick);
    }

    @Override
    public void setOnItemClick(OnItemClick<IconObject> onItemClick) {
        super.setOnItemClick(onItemClick);
    }

    @Override
    protected int getCardLayoutID() {
        return R.layout.item_of_icons_list;
    }

    @Override
    protected BaseItem createViewHolder(View view) {
        return new BaseItem(view) {
            @Override
            public void bind(IconObject item) {
                ImageView iconImageView = itemView.findViewById(R.id.icon_image_recycler);
                TextView iconNameTextView = itemView.findViewById(R.id.icon_name_recycler);

                iconImageView.setImageResource(item.getIconImage());
                iconNameTextView.setText(item.getIconName());
            }
        };
    }
}

