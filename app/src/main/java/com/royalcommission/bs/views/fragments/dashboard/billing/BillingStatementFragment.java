package com.royalcommission.bs.views.fragments.dashboard.billing;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.royalcommission.bs.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BillingStaementFragment extends Fragment {


    public BillingStaementFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_billing_staement, container, false);
    }

}