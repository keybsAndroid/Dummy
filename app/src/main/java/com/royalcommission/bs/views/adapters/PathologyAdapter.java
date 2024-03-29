package com.royalcommission.bs.views.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.royalcommission.bs.R;
import com.royalcommission.bs.modules.api.model.Pathology;
import com.royalcommission.bs.modules.utils.CommonUtils;
import com.royalcommission.bs.modules.utils.DateUtils;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.List;

/**
 * Created by Prashant on 7/8/2018.
 */
public class PathologyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private TestResultListener testResultListener;
    private Context mContext;
    private List<Pathology> mPathologyList;

    public PathologyAdapter(Context context, List<Pathology> objects, TestResultListener resultListener) {
        this.mContext = context;
        this.mPathologyList = objects;
        this.testResultListener = resultListener;
    }


    @NonNull
    @Override
    public PathologyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.pathology_table_item, parent, false);
        return new PathologyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof PathologyHolder) {
            if (mPathologyList != null) {
                Pathology pathology = mPathologyList.get(position);
                if (pathology != null) {

                    if (CommonUtils.isValidString(pathology.getOrderDate()))
                        if (((PathologyHolder) holder).date != null)
                            ((PathologyHolder) holder).date.setText(DateUtils.getDateTime(pathology.getOrderDate(), false));

                    if (CommonUtils.isValidString(pathology.getOrderName()))
                        if (((PathologyHolder) holder).name != null)
                            ((PathologyHolder) holder).name.setText(pathology.getOrderName());

                    if (CommonUtils.isValidString(pathology.getPostOrderName()))
                        if (((PathologyHolder) holder).result != null)
                            ((PathologyHolder) holder).result.setText(pathology.getPostOrderName());

                    ((PathologyHolder) holder).childView.setVisibility(View.GONE);

                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mPathologyList.size();
    }

    public class PathologyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name, date, appointmentID;
        private HtmlTextView result;
        private View childView;
        private View parentView;
        private boolean isExpanded = false;
        private ImageView rightArrow;

        PathologyHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            date = view.findViewById(R.id.date);
            rightArrow = view.findViewById(R.id.right_arrow);
            result = view.findViewById(R.id.result);
            appointmentID = view.findViewById(R.id.app_id);
            childView = view.findViewById(R.id.child);
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


    public interface TestResultListener {
        void onClick();
    }


}
