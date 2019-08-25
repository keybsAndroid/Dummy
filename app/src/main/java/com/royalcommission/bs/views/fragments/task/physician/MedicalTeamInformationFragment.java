package com.royalcommission.bs.views.fragments.task.physician;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.royalcommission.bs.R;
import com.royalcommission.bs.models.MedicalTeamList;
import com.royalcommission.bs.views.adapters.MedicalTeamInfoAdapter;
import com.royalcommission.bs.views.fragments.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MedicalTeamInformationFragment extends BaseFragment {


    public static final String TAG = "MedicalTeamInformation";
    private MedicalTeamInfoAdapter adapter;

    public MedicalTeamInformationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MedicalTeamList medicalTeamList = new MedicalTeamList();
        adapter = new MedicalTeamInfoAdapter(getActivity(), medicalTeamList.getMedicalTeamLists());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_medical_team_information, container, false);
        View medicalTeamView = view.findViewById(R.id.medical_team);
        TextView medicalTeamInfoTextView = medicalTeamView.findViewById(R.id.title);
        medicalTeamInfoTextView.setText(getString(R.string.medical_team_information));
        RecyclerView medicalTeamInfoRecyclerView = view.findViewById(R.id.medical_team_info);
        setAdapter(medicalTeamInfoRecyclerView);
        return view;
    }

    private void setAdapter(RecyclerView medicalTeamInfoRecyclerView) {
        medicalTeamInfoRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        medicalTeamInfoRecyclerView.setHasFixedSize(true);
        medicalTeamInfoRecyclerView.setAdapter(adapter);
    }

}
