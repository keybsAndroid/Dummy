package com.keybs.rc.views.fragments.verification;


import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.keybs.rc.R;
import com.keybs.rc.app.AppController;
import com.keybs.rc.modules.events.AccountEvent;
import com.keybs.rc.modules.network.listener.RetrofitListener;
import com.keybs.rc.modules.network.retrofit.model.responses.BaseResponse;
import com.keybs.rc.modules.network.retrofit.model.responses.Login;
import com.keybs.rc.modules.network.retrofit.model.responses.LoginResponse;
import com.keybs.rc.modules.network.retrofit.model.responses.Patient;
import com.keybs.rc.modules.network.retrofit.model.responses.UpdateResponse;
import com.keybs.rc.modules.network.retrofit.model.responses.VerifyOTPResponse;
import com.keybs.rc.modules.network.retrofit.parser.RetrofitResponseParser;
import com.keybs.rc.modules.preference.SharedPreferenceUtils;
import com.keybs.rc.modules.utils.CommonUtils;
import com.keybs.rc.modules.utils.Constants;
import com.keybs.rc.views.activities.home.HomeActivity;
import com.keybs.rc.views.activities.login.LoginActivity;
import com.keybs.rc.views.fragments.base.BaseDialogFragment;
import com.keybs.rc.views.fragments.home.HomeFragment;
import com.keybs.rc.views.fragments.login.LoginFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * A simple {@link Fragment} subclass.
 */
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
    private final String KEY_PROGRESSBAR_RANGE = "progressRange";
    private final String KEY_OTP = "otp";
    private static String NATIONAL_ID;
    private static String MOBILE_NUMBER;
    private static String REGION;
    private static String OTP;
    private static String HOSPITAL_ID;
    private static String PATIENT_ID;
    //private ProgressBar timerProgressBar;
    //private int progressRange = 0;

    public LoginFragment loginFragment;
    public HomeFragment homeFragment;
    private static String SELECTED_LOGIN_TYPE;
    private Button resend;

    public VerificationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*setStyle(DialogFragment.STYLE_NORMAL,
                android.R.style.Theme_Black_NoTitleBar_Fullscreen);*/
    }

    public static VerificationFragment newInstance(Bundle bundle) {
        // Required empty public constructor
        if (bundle != null) {
            NATIONAL_ID = bundle.getString(VERIFICATION_FRAGMENT_BUNDLE_KEY_NATIONAL_ID);
            MOBILE_NUMBER = bundle.getString(VERIFICATION_FRAGMENT_BUNDLE_KEY_MOBILE_NUMBER);
            REGION = bundle.getString(VERIFICATION_FRAGMENT_BUNDLE_KEY_REGION);
            OTP = bundle.getString(VERIFICATION_FRAGMENT_BUNDLE_KEY_OTP);
            SELECTED_LOGIN_TYPE = bundle.getString(VERIFICATION_FRAGMENT_BUNDLE_KEY_LOGIN_TYPE);
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
        //outState.putInt(KEY_PROGRESSBAR_RANGE, progressRange);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            remainingTotalTime = savedInstanceState.getLong(KEY_REMAINING_TOTAL_TIME);
            timer.setText(savedInstanceState.getString(KEY_TEXT_VIEW_COUNT_DOWN));
            // progressRange = savedInstanceState.getInt(KEY_PROGRESSBAR_RANGE, 0);
            //timerProgressBar.setProgress(progressRange);
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
        initializeFragment();
        return view;
    }

    private void initializeFragment() {
        if (homeFragment == null)
            homeFragment = new HomeFragment();

        if (loginFragment == null)
            loginFragment = new LoginFragment();
    }

    private void initView(View view) {
        if (view != null) {
            timer = view.findViewById(R.id.timer);
            //timerProgressBar = view.findViewById(R.id.progress_timer);
            otpCode = view.findViewById(R.id.otp_code);
            if (OTP != null) {
                otpCode.setText(OTP);
            }

            resend = view.findViewById(R.id.resend);
            resend.setOnClickListener(this);

            /*if (getPageFrom() != null) {
                if (getPageFrom().equalsIgnoreCase(Constants.LOGIN)) {
                    resend.setVisibility(View.VISIBLE);
                } else {
                    resend.setVisibility(View.GONE);
                }
            }*/

            View bannerLayout = view.findViewById(R.id.banner);
            ImageView appLogo = bannerLayout.findViewById(R.id.app_logo);
            // final Button cancel = view.findViewById(R.id.cancel);
            Button confirm = view.findViewById(R.id.find);

            confirm.setOnClickListener(this);
            clearTimer();
            if (!isCountDownTimerExpired)
                setCountDownTimer(remainingTotalTime, Constants.COUNT_DOWN_TIMER_INTERVAL);
        }
    }

    private void setCountDownTimer(final long remainingTime, long interval) {
        //timerProgressBar.setMax((int) (remainingTime / 1000));
        countDownTimer = new CountDownTimer(remainingTime, interval) { // adjust the milli seconds here
            public void onTick(long millisUntilFinished) {
                isCountDownTimerExpired = false;
                remainingTotalTime = millisUntilFinished;

                if (getActivity() != null && isAdded() && isAttached) {
                    String time = String.format(Locale.ENGLISH, getString(R.string.timer_count),
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                    //  progressRange = (int) (millisUntilFinished / 1000);
                    // timerProgressBar.setProgress(progressRange);
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
                    //setProgressBarValues();
                }
            }
        }.start();
    }

    private void setProgressBarValues() {
        // progressRange = 0;
        // timerProgressBar.setMax((int) remainingTotalTime / 1000);
        // timerProgressBar.setProgress((int) remainingTotalTime / 1000);
    }

    private void setTime(String time) {
        if (timer != null && time != null)
            timer.setText(time);
    }

    public void performVerification() {
        if (getPageFrom() != null)
            showToastMessage(getPageFrom());
    }

    private void clearTimer() {
        if (getActivity() != null && isAdded() && isAttached) {
            if (countDownTimer != null) {
                countDownTimer.cancel();
                setTime("");
                //   progressRange = 0;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideKeyboard();
        changeToolbarTitle();
    }

    private void changeToolbarTitle() {
        if (getActivity() != null) {
            if (getActivity() instanceof LoginActivity) {
                ((LoginActivity) getActivity()).showToolbarTitle(false);
            }
            if (getPageFrom() != null) {
                if (getPageFrom().equalsIgnoreCase(Constants.LOGIN) && getActivity() instanceof LoginActivity)
                    ((LoginActivity) getActivity()).setToolbarTitleTextView(getString(R.string.login));
                else if (getPageFrom().equalsIgnoreCase(Constants.OPEN_NEW_FILE) && getActivity() instanceof LoginActivity)
                    ((LoginActivity) getActivity()).setToolbarTitleTextView(getString(R.string.open_new_file_title));
                else if (getPageFrom().equalsIgnoreCase(Constants.RESET_PASSWORD) && getActivity() instanceof LoginActivity)
                    ((LoginActivity) getActivity()).setToolbarTitleTextView(getString(R.string.forgot_password_title));
                else if (getPageFrom().equalsIgnoreCase(Constants.VERIFY_PHONE_NUMBER) && getActivity() instanceof HomeActivity)
                    ((HomeActivity) getActivity()).showNormalToolbar(false);
                else if (getPageFrom().equalsIgnoreCase(Constants.FIND_YOUR_ID) && getActivity() instanceof LoginActivity)
                    ((LoginActivity) getActivity()).setToolbarTitleTextView(getString(R.string.login));
            }
        }
    }

    public static String getPageFrom() {
        return pageFrom;
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
            Observable<Object> objectObservable = null;
            if (pageFrom != null) {
                if (pageFrom.equalsIgnoreCase(Constants.LOGIN)) {
                    objectObservable = AppController.getWebService().getVerifyLoginOTP(SharedPreferenceUtils.getPatientID(AppController.getInstance()), otp);
                } else if (pageFrom.equalsIgnoreCase(Constants.OPEN_NEW_FILE)) {
                    objectObservable = AppController.getWebService().getVerifyLoginOTP(SharedPreferenceUtils.getPatientID(AppController.getInstance()), otp);
                } else if (pageFrom.equalsIgnoreCase(Constants.VERIFY_PHONE_NUMBER)) {
                    objectObservable = AppController.getWebService().updatePatientMobileWithOTP(SharedPreferenceUtils.getPatientID(AppController.getInstance()), SharedPreferenceUtils.getHospitalID(AppController.getInstance()), MOBILE_NUMBER, otp);
                }
                if (objectObservable != null) {
                    processGETRequest(false, null, true, objectObservable, new RetrofitListener() {
                        @Override
                        public void onSuccess(Object object) {
                            if (object != null) {

                                if (CommonUtils.isValidString(getPageFrom())) {
                                    if (getPageFrom().equalsIgnoreCase(Constants.VERIFY_PHONE_NUMBER)) {
                                        UpdateResponse updateResponse = RetrofitResponseParser.convertInstanceOfObject(object, UpdateResponse.class);
                                        if (updateResponse != null) {
                                            BaseResponse baseResponse = updateResponse.getBaseResponse();
                                            if (baseResponse != null) {
                                                if (baseResponse.getResponseCode() == 1) {
                                                    if (isValidString(updateResponse.getUpdateResponse())) {
                                                        SharedPreferenceUtils.setPatientMobile(AppController.getInstance(), MOBILE_NUMBER);
                                                        screenMoveTo();
                                                        showToastMessage(updateResponse.getUpdateResponse());
                                                    } else {
                                                        showMessageAlert(getString(R.string.phone), getString(R.string.update_success));
                                                    }
                                                } else {
                                                    showMessageAlert(getString(R.string.phone), updateResponse.getUpdateResponse());
                                                }
                                            }
                                        }

                                    } else {
                                        VerifyOTPResponse verifyOTPResponse = RetrofitResponseParser.convertInstanceOfObject(object, VerifyOTPResponse.class);
                                        if (verifyOTPResponse != null) {
                                            BaseResponse baseResponse = verifyOTPResponse.getBaseResponse();
                                            if (baseResponse != null) {
                                                if (baseResponse.getResponseCode() == 1) {
                                                    Patient patientResponse = verifyOTPResponse.getPatient();
                                                    if (patientResponse != null) {
                                                        SharedPreferenceUtils.savePatientResponse(AppController.getInstance(), patientResponse, baseResponse);
                                                        screenMove();
                                                    }
                                                } else {
                                                    showMessageAlert(getString(R.string.otp_title), getString(R.string.otp_error));
                                                }
                                            } else {
                                                showAPIError();
                                            }
                                        } else {
                                            showAPIError();
                                        }
                                    }
                                } else {
                                    closeDialogFragment();
                                    clearTimer();
                                }
                            }
                        }

                        @Override
                        public void onSuccess(List<Object> object) {

                        }

                        @Override
                        public void onError(String error) {
                            Log.e("verifyOTP", "onError: " + error);
                        }
                    });
                }
            }

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

    private Observable<Object> getLoginType(String medicalID) {
        Observable<Object> objectObservable = null;
        List<String> asList = Arrays.asList(getResources().getStringArray(R.array.login_type));
        if (SELECTED_LOGIN_TYPE != null && asList.contains(SELECTED_LOGIN_TYPE)) {
            switch (getLoginTypeIndex(SELECTED_LOGIN_TYPE)) {
                case 1:
                    objectObservable = AppController.getWebService().getLoginByPatientID(medicalID);
                    break;
                case 2:
                    objectObservable = AppController.getWebService().getLoginByFamilyID(medicalID);
                    break;
                case 3:
                    objectObservable = AppController.getWebService().getLoginByPassportNumber(medicalID);
                    break;
                case 4:
                    objectObservable = AppController.getWebService().getLoginByIqamaID(medicalID);
                    break;
                case 5:
                    objectObservable = AppController.getWebService().getLoginByNationalID(medicalID);
                    break;
            }
            return objectObservable;
        } else {
            return null;
        }
    }

    private int getLoginTypeIndex(String selectedLoginType) {
        List<String> places = Arrays.asList(getResources().getStringArray(R.array.login_type));
        return places.indexOf(selectedLoginType);
    }


    private void resendOTP() {
        String medicalRecordNumber = SharedPreferenceUtils.getPatientID(AppController.getInstance());
        if (isValidString(medicalRecordNumber) && isValidString(SELECTED_LOGIN_TYPE)) {
            if (isValidateCredentialsByIdType(medicalRecordNumber, SELECTED_LOGIN_TYPE)) {
                doLoginByType(medicalRecordNumber);
            } else {
                goBackToLogin();
            }
        } else {
            goBackToLogin();
        }
    }

    private void goBackToLogin() {
        if (getActivity() != null && getActivity() instanceof LoginActivity) {
            getActivity().onBackPressed();
        }
    }

    private boolean isValidateCredentialsByIdType(String mrnNumber, String selectedLoginType) {
        if (isValidString(selectedLoginType)) {
            return isValidString(mrnNumber);
        } else {
            return false;
        }
    }


    public void doLoginByType(String medicalID) {
        if (getLoginType(medicalID) != null) {
            processGETRequest(false, null, true, getLoginType(medicalID), new RetrofitListener() {
                @Override
                public void onSuccess(Object object) {
                    if (object != null) {
                        LoginResponse loginResponse = RetrofitResponseParser.convertInstanceOfObject(object, LoginResponse.class);
                        if (loginResponse != null) {
                            BaseResponse baseResponse = loginResponse.getBaseResponse();
                            if (baseResponse != null && isValidString(baseResponse.toString())) {
                                if (baseResponse.getResponseCode() == 1) {
                                    Login login = loginResponse.getLogin();
                                    if (login != null) {
                                        if (isValidString(login.getMsg())) {
                                            String otp = CommonUtils.getNumberFromString(login.getMsg());
                                            if (isValidString(otp)) {
                                                if (otpCode != null)
                                                    otpCode.setText(otp);
                                            }
                                        }
                                    }
                                } else {
                                    showMessageAlert(getString(R.string.login_title), getString(R.string.login_id_error));
                                }
                            }
                        }
                    }
                }

                @Override
                public void onSuccess(List<Object> object) {
                    Log.d("Login", "Success: " + object);
                }

                @Override
                public void onError(String error) {
                    Log.d("Login", "onError: " + error);
                }
            });
        }
    }

    private void screenMoveTo() {
        if (pageFrom != null) {
            if (pageFrom.equalsIgnoreCase(Constants.OPEN_NEW_FILE)) {
                showToastMessage(getString(R.string.verification_confirm_message));
                if (getActivity() != null && getActivity() instanceof LoginActivity)
                    ((LoginActivity) getActivity()).loadLoginFragment();
            } else if (pageFrom.equalsIgnoreCase(Constants.RESET_PASSWORD)) {
                showToastMessage(getString(R.string.password_confirm_message));
                if (getActivity() != null && getActivity() instanceof LoginActivity) {
                    ((LoginActivity) getActivity()).loadLoginFragment();
                } else if (getActivity() != null && getActivity() instanceof HomeActivity) {
                    ((HomeActivity) getActivity()).clearBackStack();
                    ((HomeActivity) getActivity()).loadHomeFragment();
                }
            } else if (pageFrom.equalsIgnoreCase(Constants.LOGIN)) {
                showToastMessage(getString(R.string.login_succes));
                if (getActivity() != null)
                    ((LoginActivity) getActivity()).goToHomePage();
            } else if (pageFrom.equalsIgnoreCase(Constants.VERIFY_PHONE_NUMBER)) {
                if (getActivity() != null && getActivity() instanceof HomeActivity) {
                    closeDialogFragment();
                    EventBus.getDefault().post(new AccountEvent(MOBILE_NUMBER));
                }
            }
        }
    }

    public HomeFragment getHomeFragment() {
        return homeFragment == null ? new HomeFragment() : homeFragment;
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
        resetValues();
    }

    private void resetValues() {
        NATIONAL_ID = "";
        MOBILE_NUMBER = "";
        REGION = "";
    }
}
