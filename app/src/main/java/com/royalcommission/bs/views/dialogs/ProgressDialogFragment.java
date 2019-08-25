package com.keybs.rc.views.fragments.dialog;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.keybs.rc.R;
import com.keybs.rc.modules.utils.CommonUtils;
import com.keybs.rc.views.customviews.TextViewSanFransiscoMedium;
import com.keybs.rc.views.fragments.base.BaseDialogFragment;

/**
 * Created by Prashant on 7/30/2018.
 */

public class ProgressDialogFragment extends BaseDialogFragment {

    private ProgressDialog dialog;

    public static ProgressDialogFragment newInstance(String message) {
        ProgressDialogFragment fragment = new ProgressDialogFragment();
        Bundle args = new Bundle();
        args.putString("msg", message);
        fragment.setArguments(args);
        return fragment;
    }

    public ProgressDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments() != null && getActivity() != null) {
            //getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
            final String message = getArguments().getString("msg");
            dialog = new ProgressDialog(getActivity());
            if (dialog.getWindow() != null)
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            if (CommonUtils.isValidString(message))
                dialog.setMessage(message);
            dialog.setCanceledOnTouchOutside(false);
            if (!dialog.isShowing())
                dialog.show();
            dialog.setContentView(R.layout.custom_progress_bar);
            final TextViewSanFransiscoMedium messageTextView = dialog.findViewById(R.id.message);
            if (messageTextView != null && isValidString(message)) {
                messageTextView.post(() -> messageTextView.setText(message));
            }
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
