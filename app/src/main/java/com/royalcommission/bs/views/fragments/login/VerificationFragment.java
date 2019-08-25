package com.royalcommission.bs.views.fragments.login;


import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.royalcommission.bs.R;
import com.royalcommission.bs.app.AppController;
import com.royalcommission.bs.modules.api.listener.RetrofitListener;
import com.royalcommission.bs.modules.api.model.BaseResponse;
import com.royalcommission.bs.modules.api.model.Patient;
import com.royalcommission.bs.modules.api.model.VerifyOTPResponse;
import com.royalcommission.bs.modules.utils.Constants;
import com.royalcommission.bs.modules.utils.SharedPreferenceUtils;
import com.royalcommission.bs.views.activities.LoginActivity;
import com.royalcommission.bs.views.dialogs.BaseDialogFragment;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class VerificationFragment extends BaseDialogFragment implements View.OnClickListener {

    private boolean isAttached;
    private static String pageFrom;
    private CountDownTimer countDownTimer;
    private TextView timer;
    private EditText otpCode;
    private long remainingTotalTime = Constants.OTP_COUNT_DOWN_TIMER_DEFAULT_VALUE;
    private boolean isCountDownTimerExpired = false;
    private final String KEY_REMAINING_TOTAL_TIME = "remainingTotalTime";
    private final String KEY_TEXT_VIEW_COUNT_DOWN = "textViewCountDown";
    private final String KEY_VERIFICATION_CODE = "textViewCountDown";
    private static String OTP;
    private static String PATIENT_ID;

    public VerificationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static VerificationFragment newInstance(Bundle bundle) {
        // Required empty public constructor
        if (bundle != null) {
            OTP = bundle.getString(VERIFICATION_FRAGMENT_BUNDLE_KEY_OTP);
            PATIENT_ID = bundle.getString(VERIFICATION_FRAGMENT_BUNDLE_KEY_PATIENT_ID);
        }
        return new VerificationFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        isAttached = true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        isAttached = false;
        clearTimer();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_REMAINING_TOTAL_TIME, remainingTotalTime);
        outState.putString(KEY_TEXT_VIEW_COUNT_DOWN, timer.getText().toString());
        outState.putString(KEY_VERIFICATION_CODE, otpCode.getText().toString());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            remainingTotalTime = savedInstanceState.getLong(KEY_REMAINING_TOTAL_TIME);
            timer.setText(savedInstanceState.getString(KEY_TEXT_VIEW_COUNT_DOWN));
            String verificationCode = savedInstanceState.getString(KEY_VERIFICATION_CODE);
            if (verificationCode != null) {
                otpCode.setText(verificationCode);
                if (verificationCode.length() > 1)
                    otpCode.setSelection(verificationCode.length() - 1);
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in_verfication, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        if (view != null) {
            timer = view.findViewById(R.id.timer);
            otpCode = view.findViewById(R.id.otp_code);
            if (OTP != null) {
                otpCode.setText(OTP);
            }
            Button resend = view.findViewById(R.id.resend);
            resend.setOnClickListener(this);
            Button confirm = view.findViewById(R.id.find);
            confirm.setOnClickListener(this);
            clearTimer();
            if (!isCountDownTimerExpired)
                setCountDownTimer(remainingTotalTime, Constants.COUNT_DOWN_TIMER_INTERVAL);
        }
    }

    private void setCountDownTimer(final long remainingTime, long interval) {
        countDownTimer = new CountDownTimer(remainingTime, interval) { // adjust the milli seconds here
            public void onTick(long millisUntilFinished) {
                isCountDownTimerExpired = false;
                remainingTotalTime = millisUntilFinished;

                if (getActivity() != null && isAdded() && isAttached) {
                    String time = String.format(Locale.ENGLISH, getString(R.string.timer_count),
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                    setTime(time);
                }

            }

            public void onFinish() {
                if (getActivity() != null && isAdded() && isAttached) {
                    clearTimer();
                    setTime(getString(R.string.timer_expired));
                    isCountDownTimerExpired = true;
                    remainingTotalTime = Constants.OTP_COUNT_DOWN_TIMER_DEFAULT_VALUE;
                    closeDialogFragment();
                }
            }
        }.start();
    }

    private void setTime(String time) {
        if (timer != null && time != null)
            timer.setText(time);
    }

    private void clearTimer() {
        if (getActivity() != null && isAdded() && isAttached) {
            if (countDownTimer != null) {
                countDownTimer.cancel();
                setTime("");
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (timer != null)
            hideKeyboard(timer);
    }

    public void setPageFrom(String pageFrom) {
        VerificationFragment.pageFrom = pageFrom;
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            hideKeyboard(view);
            if (view.getId() == R.id.find) {
                verifyOTP(otpCode.getText().toString().trim());
            } else if (view.getId() == R.id.resend) {
                clearTimer();
                isCountDownTimerExpired = true;
                remainingTotalTime = Constants.OTP_COUNT_DOWN_TIMER_DEFAULT_VALUE;
                setCountDownTimer(remainingTotalTime, Constants.COUNT_DOWN_TIMER_INTERVAL);
                resendOTP();
            }
        }
    }

    private void verifyOTP(String otp) {
        if (isValidString(otp)) {
            processRequest(AppController.getWebService().getVerifyLoginOTP(PATIENT_ID, otp), false, true, getString(R.string.validating_otp), new RetrofitListener() {
                @Override
                public void onSuccess(Object object) {
                    if (object != null) {
                        VerifyOTPResponse patientResponse = (VerifyOTPResponse) object;
                        BaseResponse baseResponse = patientResponse.getBaseResponse();
                        if (baseResponse != null && isValidString(baseResponse.toString())) {
                            if (baseResponse.getResponseCode() == 1) {
                                Patient patient = patientResponse.getPatient();
                                if (patient != null) {
                                    SharedPreferenceUtils.savePatientResponse(AppController.getInstance(), patient, baseResponse);
                                    screenMove();
                                }
                            } else {
                                showMessageAlert(getString(R.string.validating_otp), getString(R.string.invalid_otp));
                            }
                        }
                    }
                }

                @Override
                public void onError(String error) {

                }
            }, VerifyOTPResponse.class);
        } else {
            showToastMessage(getString(R.string.otp_empty));
        }
    }

    private void screenMove() {
        if (pageFrom != null) {
            closeDialogFragment();
            clearTimer();
            screenMoveTo();
        }
    }

    private void resendOTP() {
        String medicalRecordNumber = SharedPreferenceUtils.getPatientID(AppController.getInstance());
    }

    private void screenMoveTo() {
        if (getActivity() != null) {
            ((LoginActivity) getActivity()).clearBackStack();
            ((LoginActivity) getActivity()).goToHome();
        }
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
