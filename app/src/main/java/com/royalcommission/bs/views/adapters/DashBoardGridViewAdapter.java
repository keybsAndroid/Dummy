package com.keybs.rc.views.adapters.general;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keybs.rc.R;

import java.util.List;

/**
 * Created by Prashant on 7/3/2018.
 */
public class HomeGridViewAdapter extends RecyclerView.Adapter<HomeGridViewAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<String> mMenuList;

    private int[] imageResources = {R.drawable.ic_appointment_svg, R.drawable.ic_list_task_svg,
            R.drawable.ic_action_pay_for_selected_task_appointment_svg, R.drawable.ic_display_ticket_task_svg,
            R.drawable.ic_route_guidance_inside_hospital_svg, R.drawable.ic_surveys_suggestions_complain_svg,
            R.drawable.ic_medical_document_request_svg,
            R.drawable.ic_visit_history_svg, R.drawable.ic_test_results_svg,
            R.drawable.ic_prescriptions_svg,
            R.drawable.ic_hospital_info_svg, R.drawable.ic_group_management_svg,
            R.drawable.ic_education_svg, R.drawable.ic_eligibility_svg_,
            R.drawable.ic_vaccination_svg, R.drawable.ic_emr_svg};


    public HomeGridViewAdapter(Context context, List<String> menuList) {
        mMenuList = menuList;
        inflater = (LayoutInflater.from(context));
    }

    @NonNull
    @Override
    public HomeGridViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_grid, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeGridViewAdapter.ViewHolder holder, int position) {
        holder.textView.setText(mMenuList.get(holder.getAdapterPosition()));
        if (holder.getAdapterPosition() < imageResources.length)
            holder.imageView.setImageResource(imageResources[holder.getAdapterPosition()]);
        else
            holder.imageView.setImageResource(android.R.color.transparent);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mMenuList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.grid_image);
            textView = view.findViewById(R.id.grid_title);
        }
    }


}
