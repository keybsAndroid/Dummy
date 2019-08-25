package com.royalcommission.bs.views.fragments.task.physician;


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
import com.royalcommission.bs.app.AppController;
import com.royalcommission.bs.modules.api.listener.RetrofitListener;
import com.royalcommission.bs.modules.api.model.BaseResponse;
import com.royalcommission.bs.modules.api.model.RoundingDoctorResponse;
import com.royalcommission.bs.modules.utils.CommonUtils;
import com.royalcommission.bs.modules.utils.Constants;
import com.royalcommission.bs.modules.utils.GridSpacingItemDecoration;
import com.royalcommission.bs.modules.utils.SharedPreferenceUtils;
import com.royalcommission.bs.views.adapters.PhysicianAdapter;
import com.royalcommission.bs.views.fragments.base.BaseFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhysicianRoundingFragment extends BaseFragment {

    public static final String TAG = PhysicianRoundingFragment.class.getSimpleName();
    private List<String> doctorList = new ArrayList<>();
    private PhysicianAdapter physicianAdapter;

    public PhysicianRoundingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_physician_rounding, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.today_physician_rounding_recycler_view);
        setAdapter(recyclerView);
        getDoctorList();
    }

    private void setAdapter(RecyclerView recyclerView) {
        if (getActivity() != null) {
            physicianAdapter = new PhysicianAdapter(getActivity(), doctorList);
            recyclerView.setHasFixedSize(true);
            int valueInPixels = getResources().getInteger(R.integer.grid_view_spacing);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), Constants.NUMBER_OF_COLUMNS));
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(Constants.NUMBER_OF_COLUMNS, CommonUtils.dpToPx(recyclerView.getContext(), valueInPixels), true));
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setAdapter(physicianAdapter);
        }
    }

    private void getDoctorList() {
        //Dummy Data
        String patientId = "80000002";
        //String patientId = SharedPreferenceUtils.getPatientID(AppController.getInstance());
        processRequest(AppController.getWebService().getTodayRoundingDoctor(patientId, SharedPreferenceUtils.getHospitalID(AppController.getInstance())), false, true, null, new RetrofitListener() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    RoundingDoctorResponse roundingDoctorResponse = (RoundingDoctorResponse) object;
                    BaseResponse baseResponse = roundingDoctorResponse.getBaseResponse();
                    if (baseResponse != null) {
                        if (baseResponse.getResponseCode() == 1) {
                            if (doctorList != null) {
                                doctorList.clear();
                                doctorList.addAll(roundingDoctorResponse.getRoundingDoctorList());
                            }
                            if (doctorList != null && !doctorList.isEmpty()) {
                                physicianAdapter.notifyDataSetChanged();
                            } else {
                                showMessageAlert(getString(R.string.physician_round), getString(R.string.doctor_empty));
                            }
                        } else {
                            showMessageAlert(getString(R.string.physician_round), getString(R.string.server_error));
                        }
                    }
                }
            }

            @Override
            public void onError(String error) {
                showMessageAlert(getString(R.string.physician_round), error);
            }
        }, RoundingDoctorResponse.class);

    }

}
