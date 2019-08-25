package com.royalcommission.bs.views.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.royalcommission.bs.R;
import com.royalcommission.bs.models.TodayTaskList;
import com.royalcommission.bs.views.interfaces.TodayTasksAdapterClickListener;

import java.util.List;

/**
 * Created by Prashant on 10/21/2018.
 */
public class TodayTasksAdapter extends RecyclerView.Adapter<TodayTasksAdapter.TodayTasksHolder> {

    private List<TodayTaskList> mTodayTaskLists;
    private Context mContext;
    private TodayTasksAdapterClickListener tasksAdapterClickListener;

    public TodayTasksAdapter(Context context, List<TodayTaskList> medicalTeamLists, TodayTasksAdapterClickListener clickListener) {
        this.mContext = context;
        this.mTodayTaskLists = medicalTeamLists;
        this.tasksAdapterClickListener = clickListener;
    }

    @NonNull
    @Override
    public TodayTasksHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_item_today_tasks, viewGroup, false);
        return new TodayTasksHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodayTasksHolder todayTasksHolder, int position) {
        if (position != -1) {
            TodayTaskList todayTask = mTodayTaskLists.get(position);
            todayTasksHolder.taskName.setText(todayTask.getName());
            todayTasksHolder.taskImage.post(() -> {
                RoundingParams roundingParams = RoundingParams.fromCornersRadius(30f);
                roundingParams.setRoundAsCircle(true);
                todayTasksHolder.taskImage.setHierarchy(new GenericDraweeHierarchyBuilder(mContext.getResources())
                        .setRoundingParams(roundingParams)
                        .build());
                todayTasksHolder.taskImage.setImageURI(todayTask.getProfilePicture());
            });
        }
    }

    @Override
    public int getItemCount() {
        return mTodayTaskLists == null ? 0 : mTodayTaskLists.size();
    }

    class TodayTasksHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView taskImage;
        TextView taskName;
        Button taskSelect;

        TodayTasksHolder(@NonNull View itemView) {
            super(itemView);
            taskImage = itemView.findViewById(R.id.row_image);
            taskName = itemView.findViewById(R.id.row_title_one);
            taskSelect = itemView.findViewById(R.id.select);
            taskSelect.setOnClickListener(v -> tasksAdapterClickListener.onTaskClickListener(getAdapterPosition()));
        }
    }

}
