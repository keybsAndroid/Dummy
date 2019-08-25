package com.royalcommission.bs.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.royalcommission.bs.R;
import com.royalcommission.bs.modules.api.model.NotificationMessages;
import com.royalcommission.bs.modules.utils.CommonUtils;
import com.royalcommission.bs.modules.utils.DateUtils;

import java.util.List;

/**
 * Created by Prashant on 10/17/2018.
 */
public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationHolder> {

    private Context mContext;
    private List<NotificationMessages> notificationMessages;

    public NotificationsAdapter(Context context, List<NotificationMessages> notificationMessagesList) {
        this.mContext = context;
        this.notificationMessages = notificationMessagesList;
    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_notification, viewGroup, false);
        return new NotificationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NotificationHolder subMenuHolder, int position) {
        NotificationMessages notificationMessages = this.notificationMessages.get(position);
        if (notificationMessages != null) {
            if (CommonUtils.isValidString(notificationMessages.getSms()))
                subMenuHolder.message.setText(notificationMessages.getSms());

            if (CommonUtils.isValidString(notificationMessages.getDate())) {
                String time = DateUtils.getAppTime(notificationMessages.getDate());
                subMenuHolder.time.setText(time);
            }

        }
    }


    @Override
    public int getItemCount() {
        return notificationMessages == null ? 0 : notificationMessages.size();
    }

    class NotificationHolder extends RecyclerView.ViewHolder {
        private TextView message, time;

        NotificationHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
            time = itemView.findViewById(R.id.time);
        }
    }
}
