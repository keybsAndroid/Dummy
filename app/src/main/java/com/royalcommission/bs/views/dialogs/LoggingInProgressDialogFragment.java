package com.royalcommission.bs.views.dialogs;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.royalcommission.bs.R;
import com.royalcommission.bs.modules.utils.CommonUtils;

/**
 * Created by Prashant on 7/30/2018.
 */


public class LoggingInProgressDialogFragment extends DialogFragment {

    private ProgressDialog dialog;
    private static String mLoaderMessage;

    public static LoggingInProgressDialogFragment newInstance(String loaderMessage) {
        mLoaderMessage = loaderMessage;
        return new LoggingInProgressDialogFragment();
    }

    public LoggingInProgressDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getActivity() != null) {
            dialog = new ProgressDialog(getActivity());
            if (dialog.getWindow() != null)
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("");
            if (!dialog.isShowing())
                dialog.show();
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.logging_in_progress_bar, null, false);
            dialog.setContentView(view);
            if (view.findViewById(R.id.loader_message) != null) {
                TextView loaderMessage = view.findViewById(R.id.loader_message);
                if (CommonUtils.isValidString(mLoaderMessage)) {
                    loaderMessage.setText(mLoaderMessage);
                } else {
                    loaderMessage.setText(getString(R.string.logging_in));
                }
            }
        }

        return dialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (getDialog() != null) {
            getDialog().cancel();
            getDialog().dismiss();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        blockBackButtonPressWhenDialogOpen();
    }

    private void blockBackButtonPressWhenDialogOpen() {
        if (getDialog() != null) {
            getDialog().setOnKeyListener((dialog, keyCode, event) -> (keyCode == android.view.KeyEvent.KEYCODE_BACK));
        }
    }
}
