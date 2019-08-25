package com.royalcommission.bs.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.royalcommission.bs.R;
import com.royalcommission.bs.modules.api.model.Appointment;
import com.royalcommission.bs.modules.utils.CommonUtils;
import com.royalcommission.bs.modules.utils.DateUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by Prashant on 7/3/2018.
 */
public class AppointmentGridViewAdapter extends RecyclerView.Adapter<AppointmentGridViewAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Appointment> appointmentList;
    private ClickListener mClickListener;


    public AppointmentGridViewAdapter(Context context, List<Appointment> menuList, ClickListener clickListener) {
        appointmentList = menuList;
        inflater = (LayoutInflater.from(context));
        mClickListener = clickListener;
    }

    @NonNull
    @Override
    public AppointmentGridViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_grid_appointment, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull AppointmentGridViewAdapter.ViewHolder holder, int position) {
        Appointment appointment = appointmentList.get(holder.getAdapterPosition());
        holder.appName.setText("Appointment " + position);
        if (CommonUtils.isValidString(appointment.getAppointmentDate()))
            holder.date.setText(DateUtils.getAdmissionDate(appointment.getAppointmentDate()));
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return appointmentList == null ? 0 : appointmentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView appName, date;

        public ViewHolder(View view) {
            super(view);
            appName = view.findViewById(R.id.app_name);
            date = view.findViewById(R.id.date);
            view.setOnClickListener(v -> {
                if (mClickListener != null) {
                    mClickListener.onClick(getAdapterPosition());
                }
            });
        }
    }

    public interface ClickListener {
        void onClick(int position);
    }

}
