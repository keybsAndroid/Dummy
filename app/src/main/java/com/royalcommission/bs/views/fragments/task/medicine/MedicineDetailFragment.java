package com.royalcommission.bs.views.fragments.task.medicine;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.royalcommission.bs.R;
import com.royalcommission.bs.modules.api.model.Prescription;
import com.royalcommission.bs.modules.utils.DateUtils;
import com.royalcommission.bs.views.fragments.base.BaseFragment;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class MedicineDetailFragment extends BaseFragment {

    public static final String TAG = MedicineDetailFragment.class.getSimpleName();
    private static Prescription prescription;

    public MedicineDetailFragment() {
        // Required empty public constructor
    }

    public static MedicineDetailFragment getInstance(Prescription todayMedicine) {
        // Required empty public constructor
        prescription = todayMedicine;
        return new MedicineDetailFragment();
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_medicine_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (prescription != null) {
            TextView medicineName = view.findViewById(R.id.medicine_name);
            TextView startDate = view.findViewById(R.id.start_date);
            TextView endDate = view.findViewById(R.id.end_date);
            TextView quantity = view.findViewById(R.id.quantity);
            TextView duration = view.findViewById(R.id.duration);

            if (isValidString(prescription.getMedicine()))
                medicineName.setText(prescription.getMedicine());

            if (isValidString(prescription.getStartOfDose()))
                startDate.setText(DateUtils.getPrescriptionDate(prescription.getStartOfDose()));

            if (isValidString(prescription.getEndOfDose()))
                endDate.setText(DateUtils.getPrescriptionDate(prescription.getEndOfDose()));

            if (isValidString(prescription.getTypeOfDose())) {
                String qty = Math.round(Float.parseFloat(String.valueOf(prescription.getQuantityOfDose()))) + " " + prescription.getTypeOfDose();
                quantity.setText(qty);
            }

            if (isValidInteger(prescription.getDurationOfDoseInDays())) {
                float days = Float.parseFloat(String.valueOf(prescription.getDurationOfDoseInDays()));
                int intervalDays = Math.round(days);
                String dur = intervalDays > 1 ? intervalDays + " " + view.getContext().getString(R.string.days) : intervalDays + " " + view.getContext().getString(R.string.day);
                duration.setText(dur);
            }
        }
    }
}
