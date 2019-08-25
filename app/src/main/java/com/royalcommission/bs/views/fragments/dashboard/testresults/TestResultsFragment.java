package com.royalcommission.bs.views.fragments.dashboard.testresults;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.royalcommission.bs.R;
import com.royalcommission.bs.views.adapters.ViewPagerAdapter;
import com.royalcommission.bs.views.fragments.base.BaseFragment;


public class TestResultsFragment extends BaseFragment {

    public static final String TAG = TestResultsFragment.class.getSimpleName();
    private ViewPagerAdapter adapter;


    public TestResultsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null)
            adapter = new ViewPagerAdapter(getChildFragmentManager());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_test_results, container, false);
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }


    private void setupViewPager(ViewPager viewPager) {
        if (getActivity() != null && viewPager != null) {
            if (adapter != null) {
                viewPager.setAdapter(null);
                adapter.clearList();
                adapter.addFragment(LaboratoryFragment.getInstance(null, true), getString(R.string.laboratory));
                adapter.addFragment(RadiologyFragment.getInstance(true), getString(R.string.radiology));
                adapter.addFragment(SpecialClinicFragment.getInstance(null, true), getString(R.string.special_clinic));
                adapter.addFragment(PathologyFragment.getInstance(null, true), getString(R.string.pathology));
                viewPager.setAdapter(adapter);
            }
        }
    }

}
