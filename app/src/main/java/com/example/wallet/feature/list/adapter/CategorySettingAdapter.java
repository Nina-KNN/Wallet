package com.example.wallet.feature.list.adapter;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.wallet.R;
import com.example.wallet.data.icons.IconObject;
import com.example.wallet.data.icons.IconsItemStoreProvider;
import com.example.wallet.feature.details.base.BaseActivity;
import com.example.wallet.feature.list.adapter.baseAdapter.BaseRecyclerAdapter;

import java.util.List;

public class CategorySettingAdapter extends BaseRecyclerAdapter<IconObject> {

    public CategorySettingAdapter(BaseActivity baseActivity, List<IconObject> items) {
        super(baseActivity, items);
    }

    @Override
    protected int getCardLayoutID() {
        return R.layout.item_of_category_setting;
    }

    @Override
    protected BaseItem createViewHolder(View view) {

        return new BaseItem(view) {
            @Override
            public void bind(IconObject item) {
                ImageView categoryImageView =  itemView.findViewById(R.id.icon_image_category_setting);
                TextView categoryNameTextView = itemView.findViewById(R.id.category_name_category_setting);
                ImageButton shownCategoryImageButton = itemView.findViewById(R.id.shown_eye_category_setting);
                ImageButton editImageButton = itemView.findViewById(R.id.edit_category_setting);

                categoryImageView.setImageResource(item.getIconImage());
                categoryNameTextView.setText(item.getIconName());
                shownCategoryVisibility(item.isIconVisibility(), shownCategoryImageButton);

                editImageButton.setOnClickListener(v -> createAlertDialog(v.getContext(), item, categoryNameTextView));
                shownCategoryImageButton.setOnClickListener(v -> changeCategoryVisibility(v.getContext(), item, shownCategoryImageButton));
            }
        };
    }

    private void changeCategoryVisibility(Context context, IconObject icon, ImageButton imageButton) {
        boolean changeVisibility = ! icon.isIconVisibility();
        shownCategoryVisibility(changeVisibility, imageButton);
        icon.setIconVisibility(changeVisibility);
        IconsItemStoreProvider.getInstance(context).update(icon);
    }

    private void shownCategoryVisibility(boolean isShown, ImageButton imageButton) {
        if(isShown) {
            imageButton.setImageResource(R.drawable.ic_eye_on_button);
        } else {
            imageButton.setImageResource(R.drawable.ic_eye_off_button);
        }
    }

    private AlertDialog createAlertDialog(Context context, IconObject icon, TextView categoryName) {
        EditText inputEditTextField = new EditText(context);
        inputEditTextField.setText(icon.getIconName());

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.edit_category_name) + "\"" + icon.getIconName() + "\":")
                .setView(inputEditTextField)
                .setPositiveButton(R.string.save_button, (dialog, which) -> {
                    String editTextInput = inputEditTextField.getText().toString();
                    categoryName.setText(editTextInput);
                    icon.setIconName(editTextInput);
                    IconsItemStoreProvider.getInstance(context).update(icon);
                    dialog.cancel();
                })
                .setNegativeButton(R.string.cancel_button, null);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return alertDialog;
    }
}
