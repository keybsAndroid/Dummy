package com.royalcommission.bs.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.royalcommission.bs.R;
import com.royalcommission.bs.views.interfaces.SubMenuAdapterClickListener;

import java.util.List;

/**
 * Created by Prashant on 10/17/2018.
 */
public class CompletedDocumentsAdapter extends RecyclerView.Adapter<CompletedDocumentsAdapter.DocumentHolder> {

    private Context mContext;
    private List<String> mSubMenuList;
    private SubMenuAdapterClickListener mMenuItemClickListener;

    public CompletedDocumentsAdapter(Context context, List<String> subMenuList, SubMenuAdapterClickListener menuItemClickListener) {
        this.mContext = context;
        this.mSubMenuList = subMenuList;
        this.mMenuItemClickListener = menuItemClickListener;
    }

    @NonNull
    @Override
    public CompletedDocumentsAdapter.DocumentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_completed_documents, viewGroup, false);
        return new DocumentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CompletedDocumentsAdapter.DocumentHolder subMenuHolder, int position) {
        String name = mSubMenuList.get(position);
        subMenuHolder.subMenuName.setText(name);
        subMenuHolder.subMenuArrow.setOnClickListener(v -> mMenuItemClickListener.onSubMenuItemClickListener(subMenuHolder.getAdapterPosition()));
        subMenuHolder.itemView.setOnClickListener(v -> mMenuItemClickListener.onSubMenuItemClickListener(subMenuHolder.getAdapterPosition()));

        if (position == mSubMenuList.size() - 1) {
            subMenuHolder.lineView.setVisibility(View.GONE);
        } else {
            subMenuHolder.lineView.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return mSubMenuList.size();
    }

    class DocumentHolder extends RecyclerView.ViewHolder {
        private TextView subMenuName;
        private ImageView subMenuArrow;
        private View lineView;


        DocumentHolder(@NonNull View itemView) {
            super(itemView);
            subMenuName = itemView.findViewById(R.id.submenu);
            subMenuArrow = itemView.findViewById(R.id.zoom);
            lineView = itemView.findViewById(R.id.line_view);
        }
    }
}
