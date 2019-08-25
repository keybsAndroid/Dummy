package com.royalcommission.bs.views.fragments.dashboard.outpatient;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.royalcommission.bs.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppointmentDetailFragment extends Fragment {


    public AppointmentDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_appointment_detail, container, false);
    }

}
