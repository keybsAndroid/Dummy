package com.royalcommission.bs.views.dialogs;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.royalcommission.bs.R;
import com.royalcommission.bs.modules.api.model.NotificationMessages;
import com.royalcommission.bs.views.adapters.NotificationsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsDialogFragment extends BaseDialogFragment implements View.OnClickListener {

    private AlertDialog alertDialog;
    private long lastClickedTime;

    private static List<NotificationMessages> notificationMessages = new ArrayList<>();

    public NotificationsDialogFragment() {
        // Required empty public constructor
    }

    public static NotificationsDialogFragment getInstance(List<NotificationMessages> notificationMessagesList) {
        // Required empty public constructor
        notificationMessages = notificationMessagesList;
        return new NotificationsDialogFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getActivity() != null) {
            alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setCanceledOnTouchOutside(false);
            if (!alertDialog.isShowing())
                alertDialog.show();
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_dialog_notifications, null, false);
            alertDialog.setContentView(view);
            RecyclerView recyclerView = view.findViewById(R.id.notification_recycler_view);
            setAdapter(recyclerView);
            view.findViewById(R.id.cancel).setOnClickListener(this);
        }
        return alertDialog;
    }

    private void setAdapter(RecyclerView recyclerView) {
        if (getActivity() != null) {
            NotificationsAdapter notificationsAdapter = new NotificationsAdapter(getActivity(), notificationMessages);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(notificationsAdapter);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (getDialog().getWindow() != null) {
            WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            getDialog().getWindow().setAttributes(params);
        }
        blockBackButtonPressWhenDialogOpen();
    }

    private void blockBackButtonPressWhenDialogOpen() {
        if (getDialog() != null) {
            getDialog().setOnKeyListener((dialog, keyCode, event) -> (keyCode == android.view.KeyEvent.KEYCODE_BACK));
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            if (SystemClock.elapsedRealtime() - lastClickedTime < 1000)
                return;
            lastClickedTime = SystemClock.elapsedRealtime();
            if (view.getId() == R.id.cancel) {
                dismissAllowingStateLoss();
            }
        }
    }

}
