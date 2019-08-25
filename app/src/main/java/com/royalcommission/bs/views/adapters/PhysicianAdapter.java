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
public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<String> nurseList;

    public DoctorAdapter(Context context, List<String> menuList) {
        nurseList = menuList;
        inflater = (LayoutInflater.from(context));
    }

    @NonNull
    @Override
    public DoctorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_grid_doctor, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull DoctorAdapter.ViewHolder holder, int position) {
        holder.textView.setText(nurseList.get(holder.getAdapterPosition()));
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return nurseList == null ? 0 : nurseList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.grid_image);
            textView = view.findViewById(R.id.grid_title);
        }
    }


}
