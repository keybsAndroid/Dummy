package com.royalcommission.bs.views.fragments.task.test;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.royalcommission.bs.R;
import com.royalcommission.bs.views.fragments.base.BaseFragment;
import com.royalcommission.bs.views.fragments.task.meals.MealsFragment;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class MedicalTestFragment extends BaseFragment {

    public static final String TAG = MedicalTestFragment.class.getSimpleName();
    public MedicalTestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_medical_test, container, false);
    }

}
