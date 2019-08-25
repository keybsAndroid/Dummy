package com.royalcommission.bs.views.fragments.doctor;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.royalcommission.bs.R;
import com.royalcommission.bs.modules.utils.CommonUtils;
import com.royalcommission.bs.modules.utils.Constants;
import com.royalcommission.bs.modules.utils.GridSpacingItemDecoration;
import com.royalcommission.bs.views.activities.HomeActivity;
import com.royalcommission.bs.views.adapters.DoctorDashBoardGridViewAdapter;
import com.royalcommission.bs.views.fragments.base.BaseFragment;
import com.royalcommission.bs.views.fragments.dashboard.inpatient.InpatientFragment;
import com.royalcommission.bs.views.fragments.dashboard.testresults.PathologyFragment;
import com.royalcommission.bs.views.fragments.dashboard.testresults.TestResultsFragment;
import com.royalcommission.bs.views.fragments.task.medicine.MedicineFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorDashBoardFragment extends BaseFragment implements DoctorDashBoardGridViewAdapter.ClickListener {


    public static final String TAG = DoctorDashBoardFragment.class.getSimpleName();

    public DoctorDashBoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_dash_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.doctor_dash_board);
        int valueInPixels = getResources().getInteger(R.integer.grid_view_spacing);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), Constants.NUMBER_OF_DOCTOR_DASHBOARD_COLUMNS));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(Constants.NUMBER_OF_DOCTOR_DASHBOARD_COLUMNS, CommonUtils.dpToPx(recyclerView.getContext(), valueInPixels), true));
        recyclerView.setNestedScrollingEnabled(false);
        ArrayList<String> menuList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.doctor_dashboard_arrays)));
        DoctorDashBoardGridViewAdapter doctorDashBoardGridViewAdapter = new DoctorDashBoardGridViewAdapter(getActivity(), menuList, this);
        recyclerView.setAdapter(doctorDashBoardGridViewAdapter);
    }

    @Override
    public void onClick(int position) {
        if (position != -1) {
            switch (position) {
                case 0:
                    loadFragment(InpatientFragment.getInstance(getString(R.string.vital_information)), InpatientFragment.TAG);
                    break;
                case 1:
                    loadFragment(MedicineFragment.getInstance(getString(R.string.prescription_order)), MedicineFragment.TAG);
                    break;
                case 2:
                    loadFragment(new TestResultsFragment(), PathologyFragment.TAG);
                    break;
                default:
                    loadFragment(InpatientFragment.getInstance(getString(R.string.vital_information)), InpatientFragment.TAG);
                    break;

            }
        }
    }


    private void loadFragment(Fragment fragment, String tag) {
        if (getActivity() != null && fragment != null)
            ((HomeActivity) getActivity()).loadFragments(fragment, true, tag);
    }
}
