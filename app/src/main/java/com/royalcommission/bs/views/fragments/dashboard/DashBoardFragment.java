package com.royalcommission.bs.views.fragments.dashboard;


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
import com.royalcommission.bs.views.adapters.DashBoardGridViewAdapter;
import com.royalcommission.bs.views.fragments.base.BaseFragment;
import com.royalcommission.bs.views.fragments.dashboard.billing.BillingStatementFragment;
import com.royalcommission.bs.views.fragments.dashboard.discharge.DischargeMedicineFragment;
import com.royalcommission.bs.views.fragments.dashboard.documents.MedicalDocumentFragment;
import com.royalcommission.bs.views.fragments.dashboard.inpatient.InpatientFragment;
import com.royalcommission.bs.views.fragments.dashboard.operation.OperationInfoFragment;
import com.royalcommission.bs.views.fragments.dashboard.outpatient.OutPatientFragment;
import com.royalcommission.bs.views.fragments.dashboard.testresults.TestResultsFragment;
import com.royalcommission.bs.views.fragments.task.meals.MealsSelectionFragment;
import com.royalcommission.bs.views.fragments.task.medicine.MedicineFragment;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashBoardFragment extends BaseFragment implements DashBoardGridViewAdapter.ClickListener {


    public static final String TAG = DashBoardFragment.class.getSimpleName();

    public DashBoardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dash_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.dash_board);
        int valueInPixels = getResources().getInteger(R.integer.grid_view_spacing);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), Constants.NUMBER_OF_COLUMNS));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(Constants.NUMBER_OF_COLUMNS, CommonUtils.dpToPx(recyclerView.getContext(), valueInPixels), true));
        recyclerView.setNestedScrollingEnabled(false);
        ArrayList<String> menuList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.menu_arrays)));
        DashBoardGridViewAdapter dashBoardGridViewAdapter = new DashBoardGridViewAdapter(getActivity(), menuList, this);
        recyclerView.setAdapter(dashBoardGridViewAdapter);
    }

    private void selectMenuFragment(int position) {
        switch (position) {
            case 0:
                loadFragments(new MealsSelectionFragment(), MedicineFragment.TAG);
                break;
            case 1:
                showMedicalDocumentDialog();
                break;
            /*case 2:
                loadFragments(new ConvenienceServiceRequestsFragment(), ConvenienceServiceRequestsFragment.TAG);
                break;*/
            case 2:
                loadFragments(new TestResultsFragment(), TestResultsFragment.TAG);
                break;
            case 3:
                loadFragments(new OperationInfoFragment(), OperationInfoFragment.TAG);
                break;
            case 4:
                loadFragments(BillingStatementFragment.getInstance(HomeActivity.mInpatientInfo), BillingStatementFragment.TAG);
                break;
            case 5:
                loadFragments(new OutPatientFragment(), OutPatientFragment.TAG);
                break;
            case 6:
                loadFragments(InpatientFragment.getInstance(getString(R.string.inpatient_information)), InpatientFragment.TAG);
                break;
            case 7:
                loadFragments(new DischargeMedicineFragment(), DischargeMedicineFragment.TAG);
                break;
        }
    }

    private void loadFragments(Fragment fragment, String tag) {
        if (getActivity() != null) {
            ((HomeActivity) getActivity()).loadFragments(fragment, true, tag);
        }
    }


    private void showMedicalDocumentDialog() {
        MedicalDocumentFragment fragment = new MedicalDocumentFragment();
        if (getActivity() != null) {
            fragment.show(getActivity().getSupportFragmentManager(), "medical documents");
        }
    }

    @Override
    public void onClick(int position) {
        selectMenuFragment(position);
    }
}
