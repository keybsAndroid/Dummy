package com.royalcommission.bs.views.fragments.dashboard.outpatient;


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
import com.royalcommission.bs.modules.api.model.Appointment;
import com.royalcommission.bs.modules.utils.CommonUtils;
import com.royalcommission.bs.modules.utils.DateUtils;
import com.royalcommission.bs.views.fragments.base.BaseFragment;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppointmentDetailFragment extends BaseFragment {

    private LinearLayout appDateLayout, appTimeLayout, doctorInfoLayout, deptLayout, locationLayout;
    private TextView appName, appDate, appTime, doctorInfo, dept, location;
    public static final String TAG = AppointmentDetailFragment.class.getSimpleName();
    private static Appointment mAppointment;

    public AppointmentDetailFragment() {
        // Required empty public constructor
    }


    public static AppointmentDetailFragment getInstance(Appointment appointment) {
        mAppointment = appointment;
        return new AppointmentDetailFragment();
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_appointment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        appName = view.findViewById(R.id.app_name);

        appDateLayout = view.findViewById(R.id.appointment_date_layout);
        appDate = view.findViewById(R.id.appointment_date);

        appTimeLayout = view.findViewById(R.id.appointment_time_layout);
        appTime = view.findViewById(R.id.appointment_time);

        doctorInfoLayout = view.findViewById(R.id.doctor_info_layout);
        doctorInfo = view.findViewById(R.id.doctor_information);

        deptLayout = view.findViewById(R.id.dept_layout);
        dept = view.findViewById(R.id.department);

        locationLayout = view.findViewById(R.id.location_layout);
        location = view.findViewById(R.id.location);

        if (mAppointment != null) {
            if (CommonUtils.isValidString(mAppointment.getDepartmentName())) {
                appName.setText(mAppointment.getDepartmentName());
            }
            String date = "-";
            String time = "-";
            if (CommonUtils.isValidString(mAppointment.getAppointmentDate())) {
                date = DateUtils.getAppDate(mAppointment.getAppointmentDate());
                time = DateUtils.getAppTime(mAppointment.getAppointmentDate());
            }
            setTextValues(appDateLayout, appDate, date);
            setTextValues(appTimeLayout, appTime, time);
            setTextValues(doctorInfoLayout, doctorInfo, mAppointment.getDoctorName());
            setTextValues(deptLayout, dept, mAppointment.getDepartmentName());
            setTextValues(locationLayout, location, mAppointment.getHospitalName());
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
