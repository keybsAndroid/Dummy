package com.royalcommission.bs.views.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.royalcommission.bs.R;
import com.royalcommission.bs.modules.api.model.SpecialClinicDetail;
import com.royalcommission.bs.modules.utils.CommonUtils;
import com.royalcommission.bs.modules.utils.DateUtils;

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

            if (CommonUtils.isValidString(clinicDetail.getResult()))
                holder.result.setHtml(clinicDetail.getResult());
            else
                holder.result.setHtml(context.getString(R.string.hyphen));

            if (CommonUtils.isValidString(clinicDetail.getDate())) {
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
                    rightArrow.setImageResource(R.drawable.outline_keyboard_arrow_down_black_24);
                } else {
                    isExpanded = false;
                    rightArrow.setImageResource(R.drawable.arrow_right_24_auto_mirror);
                }
                childView.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            }
        }

    }


}
