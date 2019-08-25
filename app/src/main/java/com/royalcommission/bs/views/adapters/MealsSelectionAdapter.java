package com.royalcommission.bs.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.royalcommission.bs.R;
import com.royalcommission.bs.modules.api.model.TodayMeal;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by Prashant on 7/3/2018.
 */
public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<TodayMeal> todayMealList;
    private int[] imageResources = {R.drawable.ic_break_fast, R.drawable.ic_lunch,
            R.drawable.ic_dinner, R.drawable.ic_snacks};
    private Context mContext;
    private MealsClickListener mMealsClickListener;
    private String mealName = "";

    public MealsAdapter(Context context, List<TodayMeal> menuList, MealsClickListener mealsClickListener) {
        todayMealList = menuList;
        inflater = (LayoutInflater.from(context));
        mContext = context;
        mMealsClickListener = mealsClickListener;
    }

    @NonNull
    @Override
    public MealsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_grid_meal, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull MealsAdapter.ViewHolder holder, int position) {
        TodayMeal todayMeal = todayMealList.get(position);
        if (position == 0) {
            mealName = mContext.getString(R.string.breakfast);
        } else if (position == 1) {
            mealName = mContext.getString(R.string.lunch);
        } else if (position == 2) {
            mealName = mContext.getString(R.string.dinner);
        } else if (position == 3) {
            mealName = mContext.getString(R.string.snacks);
        }
        holder.textView.setText(mealName);
        holder.imageView.setImageResource(imageResources[position]);
        holder.select.setOnClickListener(v -> {
            if (mMealsClickListener != null) {
                mMealsClickListener.onClick(mealName, todayMealList.get(holder.getAdapterPosition()));
            }
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

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.grid_image);
            textView = view.findViewById(R.id.grid_title);
            select = view.findViewById(R.id.select);
        }
    }

    public interface MealsClickListener {
        void onClick(String title, TodayMeal todayMeal);
    }


}
