package com.royalcommission.bs.views.fragments.task.medicine;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.royalcommission.bs.R;
import com.royalcommission.bs.app.AppController;
import com.royalcommission.bs.modules.api.listener.RetrofitListener;
import com.royalcommission.bs.modules.api.model.BaseResponse;
import com.royalcommission.bs.modules.api.model.Prescription;
import com.royalcommission.bs.modules.api.model.PrescriptionResponse;
import com.royalcommission.bs.modules.utils.CommonUtils;
import com.royalcommission.bs.modules.utils.Constants;
import com.royalcommission.bs.modules.utils.GridSpacingItemDecoration;
import com.royalcommission.bs.modules.utils.SharedPreferenceUtils;
import com.royalcommission.bs.views.activities.HomeActivity;
import com.royalcommission.bs.views.adapters.TodayMedicinesAdapter;
import com.royalcommission.bs.views.fragments.base.BaseFragment;
import com.royalcommission.bs.views.interfaces.MedicineClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MedicineFragment extends BaseFragment implements MedicineClickListener {

    public static final String TAG = MedicineFragment.class.getSimpleName();
    private TodayMedicinesAdapter adapter;
    private List<Prescription> prescriptionList = new ArrayList<>();
    private View view;
    private static String mTitle;

    public MedicineFragment() {
        // Required empty public constructor
    }

    public static MedicineFragment getInstance(String title) {
        // Required empty public constructor
        mTitle = title;
        return new MedicineFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_today_medicine, container, false);
            View todayTaskView = view.findViewById(R.id.title_layout);
            if (todayTaskView != null) {
                TextView todayMedicineTextView = todayTaskView.findViewById(R.id.title);
                if (isValidString(mTitle))
                    todayMedicineTextView.setText(mTitle);
            }
            RecyclerView todayMedicinesRecyclerView = view.findViewById(R.id.today_medicine_recycler_view);
            setAdapter(todayMedicinesRecyclerView);
            getTodayMedicines();
        }
        return view;
    }

    private void setAdapter(RecyclerView recyclerView) {
        int valueInPixels = getResources().getInteger(R.integer.grid_view_spacing);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), Constants.NUMBER_OF_TODAY_MEDICINE_COLUMNS));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(Constants.NUMBER_OF_TODAY_MEDICINE_COLUMNS, CommonUtils.dpToPx(recyclerView.getContext(), valueInPixels), true));
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new TodayMedicinesAdapter(getActivity(), prescriptionList, this);
        recyclerView.setAdapter(adapter);
    }

    private void getTodayMedicines() {
        processRequest(AppController.getWebService().getTodayMedicines(SharedPreferenceUtils.getPatientID(AppController.getInstance()), Constants.IN_PATIENT_CATEGORY, SharedPreferenceUtils.getHospitalID(AppController.getInstance())), false, true, null, new RetrofitListener() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    PrescriptionResponse prescriptionResponse = (PrescriptionResponse) object;
                    BaseResponse baseResponse = prescriptionResponse.getBaseResponse();
                    if (baseResponse != null) {
                        if (baseResponse.getResponseCode() == 1) {
                            if (prescriptionList != null) {
                                prescriptionList.clear();
                                prescriptionList.addAll(prescriptionResponse.getPrescriptionList());
                            }
                            if (prescriptionList != null && !prescriptionList.isEmpty()) {
                                adapter.notifyDataSetChanged();
                            } else {
                                showMessageAlert(mTitle, getString(R.string.medicine_empty));
                            }
                        } else {
                            showMessageAlert(mTitle, getString(R.string.server_error));
                        }
                    }
                }
            }

            @Override
            public void onError(String error) {
                showMessageAlert(mTitle, error);
            }
        }, PrescriptionResponse.class);

    }

    private void resetAdapter(List<Prescription> prescriptionList) {
        runOnUIThread(() -> adapter.swap(prescriptionList));
    }

    @Override
    public void onMedicineClickListener(Prescription todayMedicine) {
        if (todayMedicine != null) {
            if (getActivity() != null) {
                ((HomeActivity) getActivity()).loadFragments(MedicineDetailFragment.getInstance(todayMedicine), true, MedicineDetailFragment.TAG);
            }
        }
    }
}
