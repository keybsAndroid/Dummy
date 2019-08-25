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
import com.royalcommission.bs.modules.utils.CommonUtils;
import com.royalcommission.bs.modules.utils.Constants;
import com.royalcommission.bs.modules.utils.GridSpacingItemDecoration;
import com.royalcommission.bs.views.activities.HomeActivity;
import com.royalcommission.bs.views.adapters.MealsSelectionAdapter;
import com.royalcommission.bs.views.fragments.base.BaseFragment;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MealsSelectionFragment extends BaseFragment implements MealsSelectionAdapter.MealsSelectionClickListener {

    public static final String TAG = MealsSelectionFragment.class.getSimpleName();
    private View view;

    public MealsSelectionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_meals_selection, container, false);
            View todayTaskView = view.findViewById(R.id.title_layout);
            if (todayTaskView != null) {
                TextView todayMedicineTextView = todayTaskView.findViewById(R.id.title);
                todayMedicineTextView.setText(getString(R.string.patient_care_giver));
            }
            RecyclerView mealsSelectionRecyclerView = view.findViewById(R.id.today_meals_selection_recycler_view);
            setAdapter(mealsSelectionRecyclerView);
        }
        return view;
    }

    private List<String> getMealsSelection() {
        return Arrays.asList(getResources().getStringArray(R.array.meals_selection_arrays));
    }

    private void setAdapter(RecyclerView recyclerView) {
        int valueInPixels = getResources().getInteger(R.integer.grid_view_spacing_large);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), Constants.NUMBER_OF_MEALS_SELECTION_COLUMNS));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(Constants.NUMBER_OF_MEALS_SELECTION_COLUMNS, CommonUtils.dpToPx(recyclerView.getContext(), valueInPixels), true));
        recyclerView.setNestedScrollingEnabled(false);
        //recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.HORIZONTAL));
        MealsSelectionAdapter mealsAdapter = new MealsSelectionAdapter(getActivity(), getMealsSelection(), this);
        recyclerView.setAdapter(mealsAdapter);
    }

    @Override
    public void onClick(int position) {
        if (getActivity() != null) {
            ((HomeActivity) getActivity()).loadFragments(MealsFragment.getInstance(position), true, MealsFragment.TAG);
        }
    }
}
