package com.royalcommission.bs.views.fragments.task.meals;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.royalcommission.bs.R;
import com.royalcommission.bs.modules.api.model.TodayMeal;
import com.royalcommission.bs.modules.utils.DateUtils;
import com.royalcommission.bs.views.fragments.base.BaseFragment;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class MealsDetailFragment extends BaseFragment {

    public static final String TAG = MealsDetailFragment.class.getSimpleName();
    private static TodayMeal mTodayMeal;
    private static String mTitle;
    private static int menuTypeFor;

    public MealsDetailFragment() {
        // Required empty public constructor
    }

    public static MealsDetailFragment getInstance(int menuType, String title, TodayMeal todayMeal) {
        mTodayMeal = todayMeal;
        mTitle = title;
        menuTypeFor = menuType;
        return new MealsDetailFragment();
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meals_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView mealImage = view.findViewById(R.id.meal_image);
        TextView title = view.findViewById(R.id.title);
        TextView mealName = view.findViewById(R.id.meal_name);
        LinearLayout snacksLayout = view.findViewById(R.id.snacks_layout);
        TextView snacksName = view.findViewById(R.id.snacks_name);
        TextView dietName = view.findViewById(R.id.diet_name);
        TextView mealTime = view.findViewById(R.id.meal_time);
        TextView mealDetails = view.findViewById(R.id.meal_details);
        TextView doctorComments = view.findViewById(R.id.doctor_comments);
        setTextViewValues(title, mTitle);
        if (mTodayMeal != null) {
            int code = mTodayMeal.getMealCode();
            if (isValidInteger(code)) {
                switch (code) {
                    case 1:
                        setTextViewValues(mealName, menuTypeFor == 0 ? mTodayMeal.getCareGiverBreakFast() : mTodayMeal.getPatientBreakFast());
                        setTextViewValues(snacksName, menuTypeFor == 0 ? mTodayMeal.getCareGiver10oClockSnack() : mTodayMeal.getPatient10oClockSnack());
                        setTextViewValues(dietName, mTodayMeal.getMealName());
                        setTextViewValues(mealTime, DateUtils.getCurrentDate(mTodayMeal.getMealDate()));
                        setTextViewValues(mealDetails, mTodayMeal.getMealDetail());
                        setTextViewValues(doctorComments, null);
                        snacksLayout.setVisibility(View.VISIBLE);
                        mealImage.setImageResource(R.drawable.ic_break_fast);
                        break;
                    case 2:
                        setTextViewValues(mealName, menuTypeFor == 0 ? mTodayMeal.getCareGiverLunch() : mTodayMeal.getPatientLunch());
                        setTextViewValues(dietName, mTodayMeal.getMealName());
                        setTextViewValues(mealTime, DateUtils.getCurrentDate(mTodayMeal.getMealDate()));
                        setTextViewValues(mealDetails, mTodayMeal.getMealDetail());
                        setTextViewValues(doctorComments, null);
                        snacksLayout.setVisibility(View.GONE);
                        mealImage.setImageResource(R.drawable.ic_lunch);
                        break;
                    case 3:
                        setTextViewValues(mealName, menuTypeFor == 0 ? mTodayMeal.getCareGiverDinner() : mTodayMeal.getPatientDinner());
                        setTextViewValues(snacksName, menuTypeFor == 0 ? mTodayMeal.getCareGiver3oClockSnack() : mTodayMeal.getPatient3oClockSnack());
                        setTextViewValues(dietName, mTodayMeal.getMealName());
                        setTextViewValues(mealTime, DateUtils.getCurrentDate(mTodayMeal.getMealDate()));
                        setTextViewValues(mealDetails, mTodayMeal.getMealDetail());
                        setTextViewValues(doctorComments, null);
                        snacksLayout.setVisibility(View.VISIBLE);
                        mealImage.setImageResource(R.drawable.ic_dinner);
                        break;
                }
            }
        }
    }
}
