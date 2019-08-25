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
            R.drawable.ic_dinner};
    private Context mContext;
    private MealsClickListener mMealsClickListener;

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

        holder.textView.setText(getMealName(mContext, position));
        /*if (position == todayMealList.size() - 1) {
            holder.border.setVisibility(View.GONE);
        } else {
            holder.border.setVisibility(View.VISIBLE);
        }*/
        holder.imageView.setImageResource(imageResources[position]);
        holder.select.setOnClickListener(v -> {
            if (mMealsClickListener != null) {
                mMealsClickListener.onClick(getMealName(mContext, holder.getAdapterPosition()), todayMealList.get(holder.getAdapterPosition()));
            }
        });
    }

    private String getMealName(Context mContext, int position) {
        TodayMeal todayMeal = todayMealList.get(position);
        String mealName = "";
        if (todayMeal.getMealCode() == 1) {
            mealName = mContext.getString(R.string.breakfast);
        } else if (todayMeal.getMealCode() == 2) {
            mealName = mContext.getString(R.string.lunch);
        } else if (todayMeal.getMealCode() == 3) {
            mealName = mContext.getString(R.string.dinner);
        }
        return mealName;
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

    public interface MealsClickListener {
        void onClick(String title, TodayMeal todayMeal);
    }


}
