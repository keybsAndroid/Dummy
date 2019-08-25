package com.royalcommission.bs.views.dialogs;


import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.royalcommission.bs.R;
import com.royalcommission.bs.views.activities.HomeActivity;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class AskDoctorLoginFragment extends BaseDialogFragment implements View.OnClickListener {


    private long mLastClicked;

    public AskDoctorLoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button yes = view.findViewById(R.id.yes);
        Button no = view.findViewById(R.id.no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == null)
            return;

        if (SystemClock.elapsedRealtime() - mLastClicked < 1000)
            return;
        mLastClicked = SystemClock.elapsedRealtime();

        if (v.getId() == R.id.yes) {
            showDoctorRoundingLoggingIn();
        } else if (v.getId() == R.id.no) {
            closeDialogFragment();
        }
    }

    private void showDoctorRoundingLoggingIn() {
        if (isNetworkConnected()) {
            showLoggingInProgressBar(getString(R.string.doctor_logging_in));
            runOnUIThread(this::goToDoctorLogin, 1500);
        }
    }

    private void goToDoctorLogin() {
        hideLoggingInProgressBar();
        closeDialogFragment();
        ((HomeActivity) Objects.requireNonNull(getActivity())).showDoctorLoginDialog();
    }

    @Override
    public void onResume() {
        super.onResume();
        blockBackButtonPressWhenDialogOpen();
        avoidCancellingDialogOnTouchOutside();
    }

    private void closeDialogFragment() {
        if (getDialog() != null && getDialog().isShowing()) {
            getDialog().cancel();
            getDialog().dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
