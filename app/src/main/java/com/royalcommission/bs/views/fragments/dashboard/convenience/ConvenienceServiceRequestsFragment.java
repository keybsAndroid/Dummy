package com.royalcommission.bs.views.fragments.dashboard.convenience;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.royalcommission.bs.R;
import com.royalcommission.bs.views.fragments.base.BaseFragment;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConvenienceServiceRequestsFragment extends BaseFragment {


    public static final String TAG = ConvenienceServiceRequestsFragment.class.getSimpleName();

    public ConvenienceServiceRequestsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_convenience_service_requests, container, false);
    }

}
