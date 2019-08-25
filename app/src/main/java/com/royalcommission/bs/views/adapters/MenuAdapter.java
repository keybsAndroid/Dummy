package com.royalcommission.bs.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.royalcommission.bs.R;
import com.royalcommission.bs.models.Menu;
import com.royalcommission.bs.views.activities.HomeActivity;
import com.royalcommission.bs.views.interfaces.MenuAdapterClickListener;

import java.util.List;

/**
 * Created by Prashant on 10/17/2018.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuHolder> {

    private Context mContext;
    private List<Menu> mMenuList;
    private MenuAdapterClickListener mMenuAdapterClickListener;
    private int clickedPosition = -1;

    public MenuAdapter(Context context, List<Menu> menuList, MenuAdapterClickListener menuAdapterClickListener) {
        this.mContext = context;
        this.mMenuList = menuList;
        this.mMenuAdapterClickListener = menuAdapterClickListener;
    }

    @NonNull
    @Override
    public MenuAdapter.MenuHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_row_menu, viewGroup, false);
        return new MenuHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MenuAdapter.MenuHolder menuHolder, int position) {
        Menu menu = mMenuList.get(position);
        menuHolder.menuImage.setImageResource(menu.getImageId());

        if (position != mMenuList.size() - 1) {
            menuHolder.divider.setVisibility(View.VISIBLE);
        } else {
            menuHolder.divider.setVisibility(View.GONE);
        }

        menuHolder.menuName.setText(menu.getName());
        menuHolder.itemView.setOnClickListener(v -> {
            if (mContext != null) {
                if (!((HomeActivity) mContext).isDoctorLoggedIn()) {
                    clickedPosition = menuHolder.getAdapterPosition();
                    mMenuAdapterClickListener.onMenuClickListener(menuHolder.getAdapterPosition());
                    notifyDataSetChanged();
                }
            }
        });
        changeBackGround(menuHolder, clickedPosition);
    }

    private void changeBackGround(MenuHolder menuHolder, int clickedPosition) {
        if (menuHolder != null && clickedPosition != -1) {
            if (clickedPosition == menuHolder.getAdapterPosition()) {
                if (clickedPosition == 0) {
                    menuHolder.itemView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.background_selected_top_gradient_light_corner));
                } else if (clickedPosition == mMenuList.size() - 1) {
                    menuHolder.itemView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.background_selected_bottom_gradient_light_corner));
                } else {
                    menuHolder.itemView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.background_selected_gradient_light_corner));
                }
            } else {
                menuHolder.itemView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.background_gradient_dark_corner));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mMenuList.size();
    }

    class MenuHolder extends RecyclerView.ViewHolder {
        private TextView menuName;
        private ImageView menuImage;
        private View divider;

        MenuHolder(@NonNull View itemView) {
            super(itemView);
            menuName = itemView.findViewById(R.id.menu_name);
            menuImage = itemView.findViewById(R.id.menu_image);
            divider = itemView.findViewById(R.id.divider);
        }
    }
}
