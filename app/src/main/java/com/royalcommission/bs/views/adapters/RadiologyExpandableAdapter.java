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
import com.royalcommission.bs.modules.api.model.ScanResult;
import com.royalcommission.bs.modules.utils.CommonUtils;
import com.royalcommission.bs.modules.utils.DateUtils;
import com.royalcommission.bs.views.interfaces.TestResultListener;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.List;

public class RadiologyExpandableAdapter extends RecyclerView.Adapter<RadiologyExpandableAdapter.RadiologyAdapterHolder> {
    private Context context;
    private List<ScanResult> scanResultList;
    private TestResultListener clickListener;

    public RadiologyExpandableAdapter(Context context, List<ScanResult> scanResultList, TestResultListener clickListener) {
        this.context = context;
        this.scanResultList = scanResultList;
        this.clickListener = clickListener;
    }


    @NonNull
    @Override
    public RadiologyAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.radiology_item_expandable_list, parent, false);
        return new RadiologyAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RadiologyAdapterHolder holder, int position) {

        ScanResult scanResult = scanResultList.get(position);
        if (scanResult != null) {
            if (CommonUtils.isValidString(scanResult.getScanName()))
                holder.name.setText(scanResult.getScanName());

            if (CommonUtils.isValidString(scanResult.getScanName()))
                holder.requestDetailName.setText(scanResult.getScanName());
            else
                hideView(holder.requestDetailName);

            if (CommonUtils.isValidString(scanResult.getStatus()))
                holder.requestStatus.setText(scanResult.getStatus());
            else
                hideView(holder.requestStatus);

            if (CommonUtils.isValidString(scanResult.getScanDetails()))
                holder.result.setHtml(scanResult.getScanDetails());

            if (CommonUtils.isValidString(scanResult.getDate())) {
                holder.date.setText(DateUtils.getDateTime(scanResult.getDate(), false));

                holder.requestDate.setText(DateUtils.getDateTime(scanResult.getDate(), false));
            }

        }


    }

    private void hideView(TextView textView) {
        if (textView != null)
            textView.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return scanResultList.size();
    }


    public class RadiologyAdapterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView requestDetailName;
        private TextView requestStatus;
        private TextView requestDate;
        private ImageView rightArrow;
        private View parentView;
        private View childView;
        private TextView date, name;
        private HtmlTextView result;
        private boolean isExpanded = false;

        RadiologyAdapterHolder(View view) {
            super(view);
            requestDetailName = view.findViewById(R.id.request_detail_name);
            requestStatus = view.findViewById(R.id.request_status);
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
                    rightArrow.setImageResource(R.drawable.ic_chevron_down_black_24dp);
                } else {
                    isExpanded = false;
                    rightArrow.setImageResource(R.drawable.arrow_right_24_auto_mirror);
                }
                childView.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            }
        }

    }
}
