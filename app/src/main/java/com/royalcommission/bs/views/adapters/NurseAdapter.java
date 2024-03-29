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
public class NurseAdapter extends RecyclerView.Adapter<NurseAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<String> nurseList;

    public NurseAdapter(Context context, List<String> menuList) {
        nurseList = menuList;
        inflater = (LayoutInflater.from(context));
    }

    @NonNull
    @Override
    public NurseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_grid_nurse, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull NurseAdapter.ViewHolder holder, int position) {
        holder.textView.setText(nurseList.get(holder.getAdapterPosition()));
        holder.imageView.setImageResource(R.drawable.ic_nurse_svg);
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
