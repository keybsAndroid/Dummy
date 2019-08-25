package com.royalcommission.bs.views.fragments.dashboard.outpatient;


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
import com.royalcommission.bs.modules.api.model.Appointment;
import com.royalcommission.bs.modules.api.model.AppointmentResponse;
import com.royalcommission.bs.modules.api.model.BaseResponse;
import com.royalcommission.bs.modules.utils.CommonUtils;
import com.royalcommission.bs.modules.utils.Constants;
import com.royalcommission.bs.modules.utils.GridSpacingItemDecoration;
import com.royalcommission.bs.modules.utils.SharedPreferenceUtils;
import com.royalcommission.bs.views.activities.HomeActivity;
import com.royalcommission.bs.views.adapters.AppointmentGridViewAdapter;
import com.royalcommission.bs.views.fragments.base.BaseFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OutPatientFragment extends BaseFragment implements AppointmentGridViewAdapter.ClickListener {


    public static final String TAG = OutPatientFragment.class.getSimpleName();
    private List<Appointment> appointmentList = new ArrayList<>();
    private AppointmentGridViewAdapter appointmentGridViewAdapter;

    public OutPatientFragment() {
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_out_patient, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.out_patient);
        int valueInPixels = getResources().getInteger(R.integer.grid_view_spacing);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), Constants.NUMBER_OF_APPOINTMENT_COLUMNS));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(Constants.NUMBER_OF_APPOINTMENT_COLUMNS, CommonUtils.dpToPx(recyclerView.getContext(), valueInPixels), true));
        recyclerView.setNestedScrollingEnabled(false);
        appointmentGridViewAdapter = new AppointmentGridViewAdapter(getActivity(), appointmentList, this);
        recyclerView.setAdapter(appointmentGridViewAdapter);
        getOutPatientInfo();
    }

    private void getOutPatientInfo() {

        processRequest(AppController.getWebService().getOutPatientInfo(SharedPreferenceUtils.getPatientID(AppController.getInstance())), false, true, null, new RetrofitListener() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    AppointmentResponse appointmentResponse = (AppointmentResponse) object;
                    BaseResponse baseResponse = appointmentResponse.getBaseResponse();
                    if (baseResponse != null) {
                        if (baseResponse.getResponseCode() == 1) {
                            appointmentList.clear();
                            if (appointmentResponse.getAppointmentList() != null)
                                appointmentList.addAll(appointmentResponse.getAppointmentList());
                            if (appointmentList != null && !appointmentList.isEmpty()) {
                                appointmentGridViewAdapter.notifyDataSetChanged();
                            } else {
                                showMessageAlert(null, getString(R.string.no_results));
                            }
                        } else {
                            showMessageAlert(null, getString(R.string.no_results));
                        }
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        }, AppointmentResponse.class);

    }

    @Override
    public void onClick(int position) {
        if (getActivity() != null && position != -1) {
            ((HomeActivity) getActivity()).loadFragments(AppointmentDetailFragment.getInstance(appointmentList.get(position)), true, AppointmentDetailFragment.TAG);
        }
    }
}
