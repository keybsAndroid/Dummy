package com.royalcommission.bs.views.fragments.guide;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.royalcommission.bs.R;
import com.royalcommission.bs.views.adapters.ViewPagerAdapter;
import com.royalcommission.bs.views.fragments.base.BaseFragment;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class SurveysAndSuggestionsFragment extends BaseFragment {

    private ViewPagerAdapter adapter;
    public static final String TAG = SurveysAndSuggestionsFragment.class.getSimpleName();

    public SurveysAndSuggestionsFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null)
            adapter = new ViewPagerAdapter(getChildFragmentManager());
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_survey_suggestion, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(0);
        setupViewPager(viewPager);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        if (getActivity() != null && viewPager != null) {
            if (adapter != null) {
                viewPager.setAdapter(null);
                adapter.clearList();
                adapter.addFragment(new SurveyFragment(), getString(R.string.survey));
                adapter.addFragment(new SuggestionFragment(), getString(R.string.suggestion));
                viewPager.setAdapter(adapter);
            }
        }
    }
}
