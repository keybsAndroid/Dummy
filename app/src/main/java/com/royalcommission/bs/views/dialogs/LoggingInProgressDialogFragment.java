package com.keybs.rc.views.fragments.dialog;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.keybs.rc.R;

/**
 * Created by Prashant on 7/30/2018.
 */


public class LoggingInProgressDialogFragment extends DialogFragment {

    private ProgressDialog dialog;

    public static LoggingInProgressDialogFragment newInstance() {
        return new LoggingInProgressDialogFragment();
    }

    public LoggingInProgressDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getActivity() != null) {
            //getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
            dialog = new ProgressDialog(getActivity());
            if (dialog.getWindow() != null)
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("");
            if (!dialog.isShowing())
                dialog.show();
            dialog.setContentView(R.layout.logging_in_progress_bar);
        }

        return dialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        /*if (getActivity() != null) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        }*/

    }

    /*@Override
    public void dismiss() {
        if (getFragmentManager() != null) super.dismiss();
    }*/

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
