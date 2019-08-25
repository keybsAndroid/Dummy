package com.royalcommission.bs.views.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.royalcommission.bs.R;
import com.royalcommission.bs.modules.utils.ButtonTag;


/**
 * Created by Prashant on 7/30/2018.
 */
public class MessageDialogFragment extends BaseDialogFragment {

    private AlertDialog alertDialog;
    private static String BUTTON_POSITIVE_TEXT;
    private static ButtonTag TAG;
    private static boolean isSingle;
    private static String BUTTON_NEGATIVE_TEXT;
    private static DialogClickListener clickListener;

    public static MessageDialogFragment newInstance(boolean isSingleButton, ButtonTag tag, String title, String message, String positiveButtonName, String negativeButtonName, DialogClickListener dialogClickListener) {
        MessageDialogFragment fragment = new MessageDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("message", message);
        isSingle = isSingleButton;
        TAG = tag;
        BUTTON_POSITIVE_TEXT = positiveButtonName;
        BUTTON_NEGATIVE_TEXT = negativeButtonName;
        clickListener = dialogClickListener;
        fragment.setArguments(args);
        return fragment;
    }

    public static MessageDialogFragment newInstance(String title, String error) {
        MessageDialogFragment fragment = new MessageDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("message", error);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments() != null && getActivity() != null) {
            String title = getArguments().getString("title");
            String message = getArguments().getString("message");
            alertDialog = new AlertDialog.Builder(getActivity()).create();
            if (isValidString(title))
                alertDialog.setTitle(title);
            if (isValidString(message))
                alertDialog.setMessage(message);
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);

            if (BUTTON_POSITIVE_TEXT == null)
                BUTTON_POSITIVE_TEXT = getString(R.string.ok_button);

            if (BUTTON_NEGATIVE_TEXT == null)
                BUTTON_NEGATIVE_TEXT = getString(R.string.cancel_button);

            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, BUTTON_POSITIVE_TEXT, (dialog, which) -> {
                if (clickListener != null)
                    clickListener.positiveButtonClicked(TAG);
                alertDialog.cancel();
            });

            if (!isSingle) {
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, BUTTON_NEGATIVE_TEXT, (dialog, which) -> {
                    if (clickListener != null)
                        clickListener.negativeButtonClicked(TAG);
                    alertDialog.cancel();
                });
            }

            if (!alertDialog.isShowing())
                alertDialog.show();
        }
        return alertDialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }


    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            FragmentTransaction ft = manager.beginTransaction();
            ft.add(this, tag);
            ft.commitAllowingStateLoss();
        } catch (IllegalStateException e) {
            Log.d("commitAllowingStateLoss", "Exception", e);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        blockBackButtonPressWhenDialogOpen();
    }


}
