package com.example.wallet.feature.list.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallet.R;
import com.example.wallet.data.icons.IconObject;

import java.util.List;
import java.util.UUID;

public class CategorySettingAdapter extends RecyclerView.Adapter<CategorySettingViewHolder>{

    private List<IconObject> iconsList;

    public CategorySettingAdapter(List<IconObject> iconsList) {
        this.iconsList = iconsList;
    }

    @NonNull
    @Override
    public CategorySettingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_of_category_setting, parent, false);

        return new CategorySettingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategorySettingViewHolder holder, int position) {
        holder.bindTo(iconsList.get(position));
    }

    @Override
    public int getItemCount() {
        return iconsList.size();
    }

}



class CategorySettingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private IconObject icon;

    private ImageView categoryImageView;
    private TextView categoryNameTextView;
    private ImageView shownCategoryImageView;
    private ImageButton editImageButton;

    public CategorySettingViewHolder(@NonNull View itemView) {
        super(itemView);

        categoryImageView = itemView.findViewById(R.id.icon_image_category_setting);
        categoryNameTextView = itemView.findViewById(R.id.category_name_category_setting);
        shownCategoryImageView = itemView.findViewById(R.id.shown_eye_category_setting);
        editImageButton = itemView.findViewById(R.id.edit_category_setting);
    }

    public void bindTo(IconObject icon) {
        this.icon = icon;

        UUID iconId = icon.getIconId();
        categoryImageView.setImageResource(icon.getIconImage());
        categoryNameTextView.setText(icon.getIconName());
        if(icon.isIconVisibility()) {
            shownCategoryImageView.setImageResource(R.drawable.ic_eye_on_button);
        } else {
            shownCategoryImageView.setImageResource(R.drawable.ic_eye_off_button);
        }

        editImageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.d("12345", "Button pressed" + " " + v.getId() + " " + icon.getIconId());

    }
}
