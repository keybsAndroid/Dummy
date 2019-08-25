package com.royalcommission.bs.views.fragments.task.meals;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.royalcommission.bs.R;
import com.royalcommission.bs.app.AppController;
import com.royalcommission.bs.modules.api.listener.RetrofitListener;
import com.royalcommission.bs.modules.api.model.BaseResponse;
import com.royalcommission.bs.modules.api.model.TodayMeal;
import com.royalcommission.bs.modules.api.model.TodayMealResponse;
import com.royalcommission.bs.modules.utils.CommonUtils;
import com.royalcommission.bs.modules.utils.Constants;
import com.royalcommission.bs.modules.utils.GridSpacingItemDecoration;
import com.royalcommission.bs.modules.utils.SharedPreferenceUtils;
import com.royalcommission.bs.views.activities.HomeActivity;
import com.royalcommission.bs.views.adapters.MealsAdapter;
import com.royalcommission.bs.views.fragments.base.BaseFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MealsFragment extends BaseFragment implements MealsAdapter.MealsClickListener {

    public static final String TAG = MealsFragment.class.getSimpleName();
    private List<TodayMeal> todayMealList = new ArrayList<>();
    private View view;
    private MealsAdapter mealsAdapter;

    public MealsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_meals, container, false);
            View todayTaskView = view.findViewById(R.id.title_layout);
            if (todayTaskView != null) {
                TextView todayMedicineTextView = todayTaskView.findViewById(R.id.title);
                todayMedicineTextView.setText(getString(R.string.today_meals));
            }
            RecyclerView todayMealsRecyclerView = view.findViewById(R.id.today_meals_recycler_view);
            setAdapter(todayMealsRecyclerView);
            getTodayMeals();
        }
        return view;
    }

    private void setAdapter(RecyclerView recyclerView) {
        int valueInPixels = getResources().getInteger(R.integer.grid_view_spacing);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), Constants.NUMBER_OF_COLUMNS));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(Constants.NUMBER_OF_COLUMNS, CommonUtils.dpToPx(recyclerView.getContext(), valueInPixels), true));
        recyclerView.setNestedScrollingEnabled(false);
        mealsAdapter = new MealsAdapter(getActivity(), todayMealList, this);
        recyclerView.setAdapter(mealsAdapter);
    }

    private void getTodayMeals() {
        //Dummy Data
        String patientId = "129666";
        //String patientId = SharedPreferenceUtils.getPatientID(AppController.getInstance());
        processRequest(AppController.getWebService().getTodayMeals(patientId, SharedPreferenceUtils.getHospitalID(AppController.getInstance())), false, true, null, new RetrofitListener() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    TodayMealResponse todayMealResponse = (TodayMealResponse) object;
                    BaseResponse baseResponse = todayMealResponse.getBaseResponse();
                    if (baseResponse != null) {
                        if (baseResponse.getResponseCode() == 1) {
                            if (todayMealList != null) {
                                todayMealList.clear();
                                todayMealList.addAll(todayMealResponse.getTodayMealList());
                            }
                            if (todayMealList != null && !todayMealList.isEmpty()) {
                                todayMealList.add(new TodayMeal());
                                mealsAdapter.notifyDataSetChanged();
                            } else {
                                showMessageAlert(getString(R.string.today_meals), getString(R.string.meals_empty));
                            }
                        } else {
                            showMessageAlert(getString(R.string.today_meals), getString(R.string.server_error));
                        }
                    }
                }
            }

            @Override
            public void onError(String error) {
                showMessageAlert(getString(R.string.today_meals), error);
            }
        }, TodayMealResponse.class);

    }

    @Override
    public void onClick(String title, TodayMeal todayMeal) {
        if (todayMeal != null) {
            if (getActivity() != null) {
                ((HomeActivity) getActivity()).loadFragments(MealsDetailFragment.getInstance(title, todayMeal), true, MealsDetailFragment.TAG);
            }
        }
    }
}
