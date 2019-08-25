package com.royalcommission.bs.views.fragments.dashboard.documents;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.royalcommission.bs.R;
import com.royalcommission.bs.views.dialogs.BaseDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class DocumentSuccessFragment extends BaseDialogFragment implements View.OnClickListener {

    private AlertDialog alertDialog;
    private long lastClickedTime;
    private static String mMessage;
    private ImageView cancel;

    public DocumentSuccessFragment() {
        // Required empty public constructor
    }

    public static DocumentSuccessFragment getInstance(String message) {
        // Required empty public constructor
        mMessage = message;
        return new DocumentSuccessFragment();
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
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_document_success, null, false);
            alertDialog.setContentView(view);
            TextView message = view.findViewById(R.id.message);
            if (isValidString(mMessage))
                message.setText(mMessage);
            cancel = view.findViewById(R.id.cancel);
            cancel.setOnClickListener(this);
        }
        return alertDialog;
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
            if (view.getId() == cancel.getId()) {
                dismissAllowingStateLoss();
            }
        }
    }

}
