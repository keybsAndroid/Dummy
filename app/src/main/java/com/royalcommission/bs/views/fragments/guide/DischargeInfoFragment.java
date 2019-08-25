package com.royalcommission.bs.views.fragments.guide;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.royalcommission.bs.R;
import com.royalcommission.bs.app.AppController;
import com.royalcommission.bs.modules.api.listener.RetrofitListener;
import com.royalcommission.bs.modules.api.model.BaseResponse;
import com.royalcommission.bs.modules.api.model.DischargeInfo;
import com.royalcommission.bs.modules.api.model.DischargeInfoResponse;
import com.royalcommission.bs.modules.api.model.InpatientResponse;
import com.royalcommission.bs.modules.utils.SharedPreferenceUtils;
import com.royalcommission.bs.views.fragments.base.BaseFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DischargeInfoFragment extends BaseFragment {


    public static final String TAG = DischargeInfoFragment.class.getSimpleName();
    private LinearLayout patientIdLayout, appointmentIdLayout, orderNameLayout, deptNameLayout, serviceNameLayout, staffLayout;
    private TextView patientId, appointmentId, orderName, deptName, serviceName, staffName;

    public DischargeInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discharge_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        patientIdLayout = view.findViewById(R.id.patient_id_layout);
        appointmentIdLayout = view.findViewById(R.id.appointment_id_layout);
        orderNameLayout = view.findViewById(R.id.order_name_layout);
        deptNameLayout = view.findViewById(R.id.dept_name_layout);
        serviceNameLayout = view.findViewById(R.id.service_name_layout);
        staffLayout = view.findViewById(R.id.staff_layout);

        patientId = view.findViewById(R.id.patient_id);
        appointmentId = view.findViewById(R.id.appointment_id);
        orderName = view.findViewById(R.id.order_name);
        deptName = view.findViewById(R.id.dept_name);
        serviceName = view.findViewById(R.id.service_name);
        staffName = view.findViewById(R.id.staff_name);

        String patientID = "101102";
        //String patientID = SharedPreferenceUtils.getPatientID(AppController.getInstance());

        getDischargeInfo(patientID);
    }

    private void getDischargeInfo(String patientID) {
        processRequest(AppController.getWebService().getDischargeInfo(patientID), false, true, null, new RetrofitListener() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    DischargeInfoResponse dischargeInfoResponse = (DischargeInfoResponse) object;
                    BaseResponse baseResponse = dischargeInfoResponse.getBaseResponse();
                    if (baseResponse != null) {
                        if (baseResponse.getResponseCode() == 1) {
                            List<DischargeInfo> dischargeInfoList = dischargeInfoResponse.getDischargeInfoList();
                            if (dischargeInfoList != null && !dischargeInfoList.isEmpty()) {
                                parseResponse(dischargeInfoList.get(0));
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
        }, DischargeInfoResponse.class);

    }

    private void parseResponse(DischargeInfo dischargeInfo) {
        if (dischargeInfo != null) {
            setTextValues(patientIdLayout, patientId, dischargeInfo.getPatientID());
            setTextValues(appointmentIdLayout, appointmentId, dischargeInfo.getAppointmentID());
            setTextValues(orderNameLayout, orderName, dischargeInfo.getOrderName());
            setTextValues(deptNameLayout, deptName, dischargeInfo.getDeptName());
            setTextValues(serviceNameLayout, serviceName, dischargeInfo.getServiceName());
            setTextValues(staffLayout, staffName, dischargeInfo.getStaffName());
        }
    }

    private void setTextValues(LinearLayout layout, TextView textView, String values) {
        if (isValidString(values)) {
            if (textView != null)
                textView.setText(values);
        } else {
            if (layout != null)
                layout.setVisibility(View.GONE);
        }
    }

}
