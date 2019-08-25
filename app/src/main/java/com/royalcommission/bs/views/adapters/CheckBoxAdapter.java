package com.royalcommission.bs.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.royalcommission.bs.R;
import com.royalcommission.bs.modules.api.model.LocalDocuments;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by Prashant on 7/3/2018.
 */
public class CheckBoxAdapter extends RecyclerView.Adapter<CheckBoxAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<LocalDocuments> mDocumentList;
    private ClickListener mClickListener;
    private int clickedPosition = -1;


    public CheckBoxAdapter(Context context, List<LocalDocuments> menuList, ClickListener clickListener) {
        mDocumentList = menuList;
        inflater = (LayoutInflater.from(context));
        mClickListener = clickListener;
    }

    @NonNull
    @Override
    public CheckBoxAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_check_box, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull CheckBoxAdapter.ViewHolder holder, int position) {
        LocalDocuments localDocuments = mDocumentList.get(holder.getAdapterPosition());
        holder.checkBox.setText(localDocuments.getDocumentName());
        holder.checkBox.setOnClickListener(v -> {
            clickedPosition = position;
            mClickListener.onClick(position);
            notifyDataSetChanged();
        });
        updateCheckedStatus(holder, clickedPosition);
    }

    private void updateCheckedStatus(CheckBoxAdapter.ViewHolder holder, int clickedPosition) {
        if (holder != null && clickedPosition != -1) {
            holder.checkBox.post(() -> {
                if (holder.getAdapterPosition() == clickedPosition) {
                    holder.checkBox.toggle();
                    holder.checkBox.jumpDrawablesToCurrentState();
                } else {
                    holder.checkBox.setChecked(false);
                    holder.checkBox.jumpDrawablesToCurrentState();
                }
            });
        }
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mDocumentList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;

        public ViewHolder(View view) {
            super(view);
            checkBox = view.findViewById(R.id.check_box);
        }
    }


    public interface ClickListener {
        void onClick(int position);
    }


}
