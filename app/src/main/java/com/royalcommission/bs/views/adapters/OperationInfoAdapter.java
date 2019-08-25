package com.royalcommission.bs.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.royalcommission.bs.R;
import com.royalcommission.bs.modules.api.model.OperationInfo;
import com.royalcommission.bs.modules.utils.CommonUtils;
import com.royalcommission.bs.modules.utils.DateUtils;

import java.util.List;

/**
 * Created by Prashant on 10/17/2018.
 */
public class OperationInfoAdapter extends RecyclerView.Adapter<OperationInfoAdapter.OperationHolder> {

    private Context mContext;
    private List<OperationInfo> operationInfoList;

    public OperationInfoAdapter(Context context, List<OperationInfo> operationInfos) {
        this.mContext = context;
        this.operationInfoList = operationInfos;
    }

    @NonNull
    @Override
    public OperationHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_op_info, viewGroup, false);
        return new OperationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OperationHolder operationHolder, int position) {
        OperationInfo operationInfo = operationInfoList.get(position);
        if (operationInfo != null) {
            if (CommonUtils.isValidString(operationInfo.getName()))
                operationHolder.name.setText(operationInfo.getName());
            if (CommonUtils.isValidString(operationInfo.getDate())) {
                String operationTime = DateUtils.getOperationTime(operationInfo.getDate());
                operationHolder.date.setText(operationTime);
            }
            if (CommonUtils.isValidString(operationInfo.getRoom()))
                operationHolder.room.setText(operationInfo.getRoom());
            if (CommonUtils.isValidString(operationInfo.getDiagnosis()))
                operationHolder.diagnosis.setText(operationInfo.getDiagnosis());
        }
    }

    @Override
    public int getItemCount() {
        return operationInfoList.size();
    }

    class OperationHolder extends RecyclerView.ViewHolder {
        private TextView name, date, room, diagnosis;

        OperationHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            room = itemView.findViewById(R.id.room);
            diagnosis = itemView.findViewById(R.id.diagnosis);
        }
    }
}
