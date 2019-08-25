package com.royalcommission.bs.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.royalcommission.bs.R;
import com.royalcommission.bs.modules.utils.CommonUtils;

import java.util.List;

/**
 * Created by Prashant on 10/17/2018.
 */
public class DischargeMedicineInfoAdapter extends RecyclerView.Adapter<DischargeMedicineInfoAdapter.MedicineHolder> {

    private Context mContext;
    private List<String> dischargeMedicineList;

    public DischargeMedicineInfoAdapter(Context context, List<String> medicines) {
        this.mContext = context;
        this.dischargeMedicineList = medicines;
    }

    @NonNull
    @Override
    public MedicineHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_discharge_medicine_info, viewGroup, false);
        return new MedicineHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MedicineHolder medicineHolder, int position) {
        String medicine = dischargeMedicineList.get(position);
        if (CommonUtils.isValidString(medicine)) {
            if (medicine.contains("[")) {
                String splittingChar = mContext.getString(R.string.square_bracket);
                int end = medicine.indexOf(splittingChar);
                String name = medicine.substring(0, end);
                String detail = medicine.substring(end);
                if (CommonUtils.isValidString(name))
                    medicineHolder.name.setText(name);
                if (CommonUtils.isValidString(detail))
                    medicineHolder.details.setText(detail);
            }
        }
    }

    @Override
    public int getItemCount() {
        return dischargeMedicineList.size();
    }

    class MedicineHolder extends RecyclerView.ViewHolder {
        private TextView name, details;

        MedicineHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            details = itemView.findViewById(R.id.details);
        }
    }
}
