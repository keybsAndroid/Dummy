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

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by Prashant on 7/3/2018.
 */
public class DoctorDashBoardGridViewAdapter extends RecyclerView.Adapter<DoctorDashBoardGridViewAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<String> mMenuList;
    private ClickListener mClickListener;

    private int[] imageResources = {R.drawable.ic_vital_information, R.drawable.ic_prescription_order,
            R.drawable.ic_clinical_pathology, R.drawable.ic_function_examination,
    };


    public DoctorDashBoardGridViewAdapter(Context context, List<String> menuList, ClickListener clickListener) {
        mMenuList = menuList;
        inflater = (LayoutInflater.from(context));
        mClickListener = clickListener;
    }

    @NonNull
    @Override
    public DoctorDashBoardGridViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_doctor_dash_board, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull DoctorDashBoardGridViewAdapter.ViewHolder holder, int position) {
        holder.textView.setText(mMenuList.get(holder.getAdapterPosition()));
        if (holder.getAdapterPosition() < imageResources.length)
            holder.imageView.setImageResource(imageResources[holder.getAdapterPosition()]);
        else
            holder.imageView.setImageResource(android.R.color.transparent);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mMenuList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.image);
            textView = view.findViewById(R.id.title);
            view.setOnClickListener(v -> {
                if (mClickListener != null) {
                    mClickListener.onClick(getAdapterPosition());
                }
            });
        }
    }

    public interface ClickListener {
        void onClick(int position);
    }


}
