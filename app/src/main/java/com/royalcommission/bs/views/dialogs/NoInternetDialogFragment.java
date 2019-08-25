package com.royalcommission.bs.views.dialogs;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.royalcommission.bs.R;

/**
 * Created by Prashant on 7/30/2018.
 */


public class NoInternetDialogFragment extends DialogFragment {

    private AlertDialog alertDialog;

    public static NoInternetDialogFragment newInstance() {
        return new NoInternetDialogFragment();
    }

    public NoInternetDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getActivity() != null) {
            //getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
            alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.no_internet_dialog, null);
            alertDialog.setView(view);
            ImageView gifImage = view.findViewById(R.id.no_internet_error);
            Glide.with(getActivity()).asGif().load(R.raw.no_internet_gif).into(gifImage);
            Button okButton = view.findViewById(R.id.ok);
            okButton.setOnClickListener(v -> {
                if (alertDialog != null) {
                    alertDialog.dismiss();
                    alertDialog.cancel();
                }
            });
            if (alertDialog.getWindow() != null)
                alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationSlideUpDown;
            if (!alertDialog.isShowing())
                alertDialog.show();
        }

        return alertDialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        /*if (getActivity() != null) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        }*/

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
