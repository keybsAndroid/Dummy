package com.royalcommission.bs.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.royalcommission.bs.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by Prashant on 7/3/2018.
 */
public class MealsSelectionAdapter extends RecyclerView.Adapter<MealsSelectionAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<String> todayMealList;
    private int[] imageResources = {R.drawable.ic_care_giver_svg, R.drawable.ic_patient_meal_svg};
    private Context mContext;
    private MealsSelectionClickListener mMealsClickListener;
    private String mealName = "";

    public MealsSelectionAdapter(Context context, List<String> menuList, MealsSelectionClickListener mealsClickListener) {
        todayMealList = menuList;
        inflater = (LayoutInflater.from(context));
        mContext = context;
        mMealsClickListener = mealsClickListener;
    }

    @NonNull
    @Override
    public MealsSelectionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_grid_meal_selection, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull MealsSelectionAdapter.ViewHolder holder, int position) {
        String mealFor = todayMealList.get(position);
        holder.textView.setText(mealFor);
        holder.imageView.setImageResource(imageResources[position]);
        if (position == 0) {
            holder.select.setBackground(ContextCompat.getDrawable(holder.select.getContext(), R.drawable.button_primary_corner_small));
           // holder.border.setVisibility(View.INVISIBLE);
        } else if (position == 1) {
            holder.border.setVisibility(View.INVISIBLE);
            //holder.select.setBackground(ContextCompat.getDrawable(holder.select.getContext(), R.drawable.button_secondary_corner_small));
        }
        holder.select.setOnClickListener(v -> {
            if (mMealsClickListener != null)
                mMealsClickListener.onClick(position);
        });
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return todayMealList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        private Button select;
        private View border;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.grid_image);
            textView = view.findViewById(R.id.grid_title);
            select = view.findViewById(R.id.select);
            border = view.findViewById(R.id.border);
        }
    }

    public interface MealsSelectionClickListener {
        void onClick(int position);
    }


}
