package com.royalcommission.bs.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.royalcommission.bs.R;
import com.royalcommission.bs.modules.api.model.Prescription;
import com.royalcommission.bs.modules.utils.CommonUtils;
import com.royalcommission.bs.views.interfaces.MedicineClickListener;

import java.util.List;

/**
 * Created by Prashant on 10/21/2018.
 */
public class TodayMedicinesAdapter extends RecyclerView.Adapter<TodayMedicinesAdapter.TodayMedicinesHolder> {

    private List<Prescription> todayMedicines;
    private Context mContext;
    private MedicineClickListener medicineClickListener;

    public TodayMedicinesAdapter(Context context, List<Prescription> medicalTeamLists, MedicineClickListener medicineClickListener) {
        this.mContext = context;
        this.todayMedicines = medicalTeamLists;
        this.medicineClickListener = medicineClickListener;
    }

    @NonNull
    @Override
    public TodayMedicinesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_item_today_medicines, viewGroup, false);
        return new TodayMedicinesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodayMedicinesHolder todayMedicinesHolder, int position) {
        Prescription todayMedicines = this.todayMedicines.get(position);
        todayMedicinesHolder.medicineName.setText(todayMedicines.getMedicine());
        if (CommonUtils.isValidString(todayMedicines.getTypeOfDose())) {
            String qty = Math.round(Float.parseFloat(String.valueOf(todayMedicines.getQuantityOfDose()))) + " " + todayMedicines.getTypeOfDose();
            todayMedicinesHolder.medicineType.setText(qty);
        }
    }

    @Override
    public int getItemCount() {
        return this.todayMedicines == null ? 0 : this.todayMedicines.size();
    }

    public void swap(List<Prescription> prescriptionList) {
        this.todayMedicines = prescriptionList;
        this.notifyDataSetChanged();
    }

    class TodayMedicinesHolder extends RecyclerView.ViewHolder {
        TextView medicineName;
        TextView medicineType;

        TodayMedicinesHolder(@NonNull View itemView) {
            super(itemView);
            medicineName = itemView.findViewById(R.id.medicine_name);
            medicineType = itemView.findViewById(R.id.medicine_type);
            itemView.setOnClickListener(v -> medicineClickListener.onMedicineClickListener(todayMedicines.get(getAdapterPosition())));
        }
    }
}
