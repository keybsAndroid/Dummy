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
import com.royalcommission.bs.views.activities.HomeActivity;
import com.royalcommission.bs.views.interfaces.SubMenuAdapterClickListener;

import java.util.List;

/**
 * Created by Prashant on 10/17/2018.
 */
public class SubMenuAdapter extends RecyclerView.Adapter<SubMenuAdapter.SubMenuHolder> {

    private int clickedPosition = -1;
    private Context mContext;
    private List<String> mSubMenuList;
    private SubMenuAdapterClickListener mMenuItemClickListener;

    public SubMenuAdapter(Context context, List<String> subMenuList, SubMenuAdapterClickListener menuItemClickListener) {
        this.mContext = context;
        this.mSubMenuList = subMenuList;
        this.mMenuItemClickListener = menuItemClickListener;
    }

    @NonNull
    @Override
    public SubMenuAdapter.SubMenuHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_row_sub_menu, viewGroup, false);
        return new SubMenuHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SubMenuAdapter.SubMenuHolder subMenuHolder, int position) {
        String name = mSubMenuList.get(position);
        subMenuHolder.subMenuName.setText(name);
        subMenuHolder.itemView.setOnClickListener(v -> {
            if (mContext != null) {
                if (!((HomeActivity) mContext).isDoctorLoggedIn()) {
                    mMenuItemClickListener.onSubMenuItemClickListener(subMenuHolder.getAdapterPosition());
                    clickedPosition = subMenuHolder.getAdapterPosition();
                    notifyDataSetChanged();
                }
            }

        });

        if (position == mSubMenuList.size() - 1) {
            subMenuHolder.lineView.setVisibility(View.GONE);
        } else {
            subMenuHolder.lineView.setVisibility(View.VISIBLE);
        }

        //changeBackGround(subMenuHolder, clickedPosition);
    }

    private void changeBackGround(SubMenuHolder holder, int clickedPosition) {
        if (holder != null && clickedPosition != -1) {
            if (clickedPosition == holder.getAdapterPosition()) {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.shadowColor));
            } else {
                holder.itemView.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.color.white));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mSubMenuList.size();
    }

    class SubMenuHolder extends RecyclerView.ViewHolder {
        private TextView subMenuName;
        private ImageView subMenuArrow;
        private View lineView;


        SubMenuHolder(@NonNull View itemView) {
            super(itemView);
            subMenuName = itemView.findViewById(R.id.submenu);
            subMenuArrow = itemView.findViewById(R.id.arrow);
            lineView = itemView.findViewById(R.id.line_view);
        }
    }
}
