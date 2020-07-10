package com.example.wallet.feature.list.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallet.R;
import com.example.wallet.data.icons.IconObject;

import java.util.List;

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
    private ImageButton shownCategoryImageButton;
    private ImageButton editImageButton;

    public CategorySettingViewHolder(@NonNull View itemView) {
        super(itemView);

        categoryImageView = itemView.findViewById(R.id.icon_image_category_setting);
        categoryNameTextView = itemView.findViewById(R.id.category_name_category_setting);
        shownCategoryImageButton = itemView.findViewById(R.id.shown_eye_category_setting);
        editImageButton = itemView.findViewById(R.id.edit_category_setting);
    }

    public void bindTo(IconObject icon) {
        this.icon = icon;

        categoryImageView.setImageResource(icon.getIconImage());
        categoryNameTextView.setText(icon.getIconName());
        if(icon.isIconVisibility()) {
            shownCategoryImageButton.setImageResource(R.drawable.ic_eye_on_button);
        } else {
            shownCategoryImageButton.setImageResource(R.drawable.ic_eye_off_button);
        }

        editImageButton.setOnClickListener(this);
        shownCategoryImageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shown_eye_category_setting:
                changeCategoryVisibility(v.getContext(), icon);
                break;
            case R.id.edit_category_setting:
                createAlertDialog(v.getContext(), icon);
                break;
        }
    }

    private void changeCategoryVisibility(Context context, IconObject icon) {
        if(icon.isIconVisibility()) {
            shownCategoryImageButton.setImageResource(R.drawable.ic_eye_off_button);
        } else {
            shownCategoryImageButton.setImageResource(R.drawable.ic_eye_on_button);
        }
    }

    private AlertDialog createAlertDialog(Context context, IconObject icon) {
        EditText inputEditTextField = new EditText(context);
        inputEditTextField.setText(icon.getIconName());

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit category " + "\"" + icon.getIconName() + "\" to:")
                .setView(inputEditTextField)
                .setPositiveButton("Save", (dialog, which) -> {
                    String editTextInput = inputEditTextField.getText().toString();
                    categoryNameTextView.setText(editTextInput);
                    dialog.cancel();
                })
                .setNegativeButton("Cancel", null);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return alertDialog;
    }
}
