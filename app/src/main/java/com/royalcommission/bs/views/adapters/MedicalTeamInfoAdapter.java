package com.royalcommission.bs.views.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.royalcommission.bs.R;
import com.royalcommission.bs.models.MedicalTeamList;

import java.util.List;

/**
 * Created by Prashant on 10/21/2018.
 */
public class MedicalTeamInfoAdapter extends RecyclerView.Adapter<MedicalTeamInfoAdapter.MedicalTeamInfoHolder> {

    private List<MedicalTeamList> mMedicalTeamLists;
    private Context mContext;

    public MedicalTeamInfoAdapter(Context context, List<MedicalTeamList> medicalTeamLists) {
        this.mContext = context;
        this.mMedicalTeamLists = medicalTeamLists;
    }

    @NonNull
    @Override
    public MedicalTeamInfoAdapter.MedicalTeamInfoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_item_medical_info, viewGroup, false);
        return new MedicalTeamInfoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicalTeamInfoAdapter.MedicalTeamInfoHolder medicalTeamInfoHolder, int position) {
        if (position != -1) {
            MedicalTeamList medicalTeam = mMedicalTeamLists.get(position);
            medicalTeamInfoHolder.doctorName.setText(medicalTeam.getName());
            medicalTeamInfoHolder.doctorDesignation.setText(medicalTeam.getDesignation());
            medicalTeamInfoHolder.doctorDepartment.setText(medicalTeam.getDepartment());
            medicalTeamInfoHolder.doctorProfile.post(() -> {
                RoundingParams roundingParams = RoundingParams.fromCornersRadius(30f);
                roundingParams.setRoundAsCircle(true);
                medicalTeamInfoHolder.doctorProfile.setHierarchy(new GenericDraweeHierarchyBuilder(mContext.getResources())
                        .setRoundingParams(roundingParams)
                        .build());
                medicalTeamInfoHolder.doctorProfile.setImageURI(medicalTeam.getProfilePicture());
            });
        }
    }

    @Override
    public int getItemCount() {
        return mMedicalTeamLists == null ? 0 : mMedicalTeamLists.size();
    }

    class MedicalTeamInfoHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView doctorProfile;
        TextView doctorName, doctorDesignation, doctorDepartment;
        Button doctorSelect;

        MedicalTeamInfoHolder(@NonNull View itemView) {
            super(itemView);
            doctorProfile = itemView.findViewById(R.id.row_image);
            doctorName = itemView.findViewById(R.id.row_title_one);
            doctorDesignation = itemView.findViewById(R.id.row_title_two);
            doctorDepartment = itemView.findViewById(R.id.row_title_three);
            doctorSelect = itemView.findViewById(R.id.select);
        }
    }
}
