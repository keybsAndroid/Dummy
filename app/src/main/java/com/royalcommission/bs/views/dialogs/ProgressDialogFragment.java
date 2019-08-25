package com.royalcommission.bs.views.dialogs;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.royalcommission.bs.R;

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
            final String message = getArguments().getString("msg");
            dialog = new ProgressDialog(getActivity());
            if (dialog.getWindow() != null)
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            if (isValidString(message))
                dialog.setMessage(message);
            dialog.setCanceledOnTouchOutside(false);
            if (!dialog.isShowing())
                dialog.show();
            dialog.setContentView(R.layout.custom_progress_bar);
            final TextView messageTextView = dialog.findViewById(R.id.message);
            if (messageTextView != null && isValidString(message)) {
                messageTextView.post(() -> messageTextView.setText(message));
            }
        }
        return dialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onResume() {
        super.onResume();
        blockBackButtonPressWhenDialogOpen();
    }

}
