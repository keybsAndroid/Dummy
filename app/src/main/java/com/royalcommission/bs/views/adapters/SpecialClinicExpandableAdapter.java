package com.keybs.rc.views.adapters.general;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keybs.rc.R;
import com.keybs.rc.modules.network.retrofit.model.responses.SpecialClinicDetail;
import com.keybs.rc.modules.utils.CommonUtils;
import com.keybs.rc.modules.utils.DateUtils;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.List;

/**
 * Created by Prashant.
 */

public class SpecialClinicExpandableAdapter extends RecyclerView.Adapter<SpecialClinicExpandableAdapter.SpecialClinicAdapterHolder> {

    private Context context;
    private List<SpecialClinicDetail> clinicDetails;

    public SpecialClinicExpandableAdapter(Context context, List<SpecialClinicDetail> clinicDetails) {
        this.context = context;
        this.clinicDetails = clinicDetails;
    }


    @NonNull
    @Override
    public SpecialClinicAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.special_clinic_item_expandable_list, parent, false);
        return new SpecialClinicAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SpecialClinicAdapterHolder holder, int position) {

        SpecialClinicDetail clinicDetail = clinicDetails.get(position);
        if (clinicDetail != null) {
            if (CommonUtils.isValidString(clinicDetail.getName()))
                holder.requestDetailName.setText(clinicDetail.getName());

            /*if (CommonUtils.isValidString(clinicDetail.getOrderId()))
                holder.requestDetailName.setText(clinicDetail.getOrderId());*/

            if (CommonUtils.isValidString(clinicDetail.getResult()))
                holder.result.setHtml(clinicDetail.getResult());
            else
                holder.result.setHtml(context.getString(R.string.hypen));

            if (CommonUtils.isValidString(clinicDetail.getDate())) {
                //holder.date.setText(DateUtils.getDateTime(clinicDetail.getDate()));
                holder.requestDate.setText(DateUtils.getDateTime(clinicDetail.getDate(), false));
            }

        }


    }

    @Override
    public int getItemCount() {
        return clinicDetails.size();
    }


    public class SpecialClinicAdapterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView requestDetailName;
        private TextView requestDate;
        private ImageView rightArrow;
        private View parentView;
        private View childView;
        private TextView date, name;
        private HtmlTextView result;
        private boolean isExpanded = false;

        SpecialClinicAdapterHolder(View view) {
            super(view);
            requestDetailName = view.findViewById(R.id.request_detail_name);
            requestDate = view.findViewById(R.id.request_date);
            rightArrow = view.findViewById(R.id.right_arrow);
            childView = view.findViewById(R.id.child);
            date = childView.findViewById(R.id.date);
            name = childView.findViewById(R.id.name);
            result = childView.findViewById(R.id.result);
            parentView = view.findViewById(R.id.parent);
            parentView.setOnClickListener(this);
            rightArrow.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (view == null)
                return;

            if (view.getId() == parentView.getId() || view.getId() == rightArrow.getId()) {
                if (!isExpanded) {
                    isExpanded = true;
                    rightArrow.setImageResource(R.drawable.ic_chevron_down_grey600_36dp);
                } else {
                    isExpanded = false;
                    rightArrow.setImageResource(R.drawable.ic_chevron_right_grey600_36dp_auto_mirror);
                }
                childView.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            }
        }

    }


}
