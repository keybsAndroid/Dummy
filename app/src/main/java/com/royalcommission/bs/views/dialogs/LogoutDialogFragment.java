package com.royalcommission.bs.views.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;

import com.royalcommission.bs.R;


/**
 * Created by Prashant on 7/30/2018.
 */
public class LogoutDialogFragment extends DialogFragment {

    private AlertDialog alertDialog;
    private static LogoutDialogFragment.LogoutDialogClickListener logoutDialogClickListener;
    private static String logoutTitle;
    private static String logoutMessage;
    private static boolean isNormalLogout;

    public static LogoutDialogFragment newInstance(String title, String message, LogoutDialogFragment.LogoutDialogClickListener listener, boolean isNormal) {
        logoutTitle = title;
        logoutMessage = message;
        logoutDialogClickListener = listener;
        isNormalLogout = isNormal;
        return new LogoutDialogFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getActivity() != null) {
            alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setTitle(logoutTitle);
            alertDialog.setMessage(logoutMessage);
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            if (isNormalLogout) {
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.no), (dialog, which) -> closeDialog(alertDialog));

                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.yes), (dialog, which) -> {
                });
            } else {
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.keep_me_signed_in), (dialog, which) -> {
                });

                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.logout_caps), (dialog, which) -> {
                });
            }

            if (!alertDialog.isShowing())
                alertDialog.show();
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view -> {
                closeDialog(alertDialog);
                logoutDialogClickListener.onLogout();
            });
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(v -> {
                closeDialog(alertDialog);
                if (!isNormalLogout)
                    logoutDialogClickListener.onKeepMeSignedIn();
            });
        }
        return alertDialog;
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

    private void closeDialog(AlertDialog alertDialog) {
        if (alertDialog != null) {
            alertDialog.cancel();
        }
    }

    public interface LogoutDialogClickListener {
        void onLogout();

        void onKeepMeSignedIn();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }
}
