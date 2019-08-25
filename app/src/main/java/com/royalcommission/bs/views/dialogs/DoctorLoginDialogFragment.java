package com.royalcommission.bs.views.dialogs;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.royalcommission.bs.R;
import com.royalcommission.bs.app.AppController;
import com.royalcommission.bs.modules.api.listener.RetrofitListener;
import com.royalcommission.bs.modules.api.model.BaseResponse;
import com.royalcommission.bs.modules.api.model.DoctorLoginResponse;
import com.royalcommission.bs.views.activities.HomeActivity;

import java.util.Objects;

/**
 * Created by Prashant on 7/30/2018.
 */


public class DoctorLoginDialogFragment extends BaseDialogFragment {

    private ProgressDialog dialog;
    private long mLastClickedTime;

    public DoctorLoginDialogFragment() {
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
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.doctor_login_dialog, null, false);
            dialog.setContentView(view);
            View loginFormTitle = view.findViewById(R.id.title_layout);
            TextView title = loginFormTitle.findViewById(R.id.title);
            EditText doctorId = view.findViewById(R.id.doctor_id);
            EditText password = view.findViewById(R.id.password);
            doctorId.setText("elsayedm");
            password.setText("m@303030");
            view.findViewById(R.id.login).setOnClickListener(v -> {
                if (SystemClock.elapsedRealtime() - mLastClickedTime < 1000)
                    return;
                mLastClickedTime = SystemClock.elapsedRealtime();
                validateCredentials(doctorId.getText().toString(), password.getText().toString());
            });
            title.setText(getString(R.string.doctor_login_title));
        }

        return dialog;
    }

    private void validateCredentials(String doctorId, String password) {
        if (isValidString(doctorId) && isValidString(password)) {
            doDoctorLogin(doctorId, password);
        } else {
            if (!isValidString(doctorId)) {
                showMessageAlert(null, getString(R.string.valid_doctor_id));
            } else if (!isValidString(password)) {
                showMessageAlert(null, getString(R.string.valid_doctor_password));
            }
        }
    }

    private void doDoctorLogin(String doctorId, String password) {
        processRequest(AppController.getWebService().doRoundingDoctorLogin(doctorId, password), false, false, null, new RetrofitListener() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    DoctorLoginResponse doctorLoginResponse = (DoctorLoginResponse) object;
                    BaseResponse baseResponse = doctorLoginResponse.getBaseResponse();
                    if (baseResponse != null) {
                        if (baseResponse.getResponseCode() == 1) {
                            HomeActivity.isDoctorLoggedIn = true;
                            HomeActivity.loggedInDoctorName = doctorLoginResponse.getDoctorLoginResponse();
                            closeDialogFragment();
                            ((HomeActivity) Objects.requireNonNull(getActivity())).goToDoctorRoundingPage(doctorLoginResponse.getDoctorLoginResponse());
                        } else {
                            ((HomeActivity) Objects.requireNonNull(getActivity())).logoutDoctorRoundingPage();
                            showMessageAlert(getString(R.string.doctor_login_title), getString(R.string.doctor_login_invalid_credentials));
                        }
                    } else {
                        ((HomeActivity) Objects.requireNonNull(getActivity())).logoutDoctorRoundingPage();
                    }
                }
            }

            @Override
            public void onError(String error) {
                ((HomeActivity) Objects.requireNonNull(getActivity())).logoutDoctorRoundingPage();
                showMessageAlert(null, error);
            }
        }, DoctorLoginResponse.class);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (getDialog() != null) {
            getDialog().cancel();
            getDialog().dismiss();
        }
    }

    private void closeDialogFragment() {
        if (getDialog() != null && getDialog().isShowing()) {
            getDialog().cancel();
            getDialog().dismiss();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //blockBackButtonPressWhenDialogOpen();
    }
}
