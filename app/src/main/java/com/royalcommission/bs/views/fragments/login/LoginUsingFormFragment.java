package com.royalcommission.bs.views.fragments.login;


import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.royalcommission.bs.R;
import com.royalcommission.bs.app.AppController;
import com.royalcommission.bs.modules.api.listener.RetrofitListener;
import com.royalcommission.bs.modules.api.model.BaseResponse;
import com.royalcommission.bs.modules.api.model.Login;
import com.royalcommission.bs.modules.api.model.LoginResponse;
import com.royalcommission.bs.modules.api.model.Patient;
import com.royalcommission.bs.modules.api.model.VerifyOTPResponse;
import com.royalcommission.bs.modules.utils.CommonUtils;
import com.royalcommission.bs.modules.utils.Constants;
import com.royalcommission.bs.modules.utils.SharedPreferenceUtils;
import com.royalcommission.bs.views.fragments.base.BaseFragment;

import static com.royalcommission.bs.views.dialogs.BaseDialogFragment.VERIFICATION_FRAGMENT_BUNDLE_KEY_OTP;
import static com.royalcommission.bs.views.dialogs.BaseDialogFragment.VERIFICATION_FRAGMENT_BUNDLE_KEY_PATIENT_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginUsingFormFragment extends BaseFragment implements View.OnClickListener {

    public static String TAG = "LoginForm";
    private long mForgotPasswordLCT = 0;
    private long mForgotIdLCT = 0;
    private EditText username, password, otp;
    private View loginLayout;
    private TextView title;

    public LoginUsingFormFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_using_form, container, false);
        View loginFormTitle = view.findViewById(R.id.title_layout);
        loginLayout = view.findViewById(R.id.login_layout);
        title = loginFormTitle.findViewById(R.id.title);
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        username.setText("80000001");
        password.setText("Aa12@@");
        otp = view.findViewById(R.id.otp);
        view.findViewById(R.id.forgot_password).setOnClickListener(this);
        view.findViewById(R.id.forgot_patient_id).setOnClickListener(this);
        view.findViewById(R.id.login).setOnClickListener(this);
        view.findViewById(R.id.resend_otp).setOnClickListener(this);
        view.findViewById(R.id.confirm).setOnClickListener(this);
        title.setText(getString(R.string.patient_login));
        return view;
    }

    @Override
    public void onClick(View view) {

        if (view == null)
            return;

        if (view.getId() == R.id.forgot_password) {
            forgotPassword();
        } else if (view.getId() == R.id.forgot_patient_id) {
            forgotID();
        } else if (view.getId() == R.id.resend_otp) {

        } else if (view.getId() == R.id.confirm) {
            String otpEntered = otp.getText().toString();
            String user = username.getText().toString();
            if (isValidString(user) && isValidString(otpEntered)) {
                validateOTP(user, otpEntered);
            } else {
                showToastMessage(getString(R.string.otp_empty));
            }
        } else if (view.getId() == R.id.login) {
            String user = username.getText().toString();
            String pass = password.getText().toString();
            if (isValidString(user) && isValidString(pass)) {
                doLogin(user, pass);
            } else {
                if (!isValidString(user)) {
                    showToastMessage(getString(R.string.fields_empty));
                } else if (!isValidString(pass)) {
                    showToastMessage(getString(R.string.fields_empty));
                }
            }
        }
    }

    private void forgotID() {
        if (SystemClock.elapsedRealtime() - mForgotIdLCT < 1000)
            return;
        mForgotIdLCT = SystemClock.elapsedRealtime();
        if (getActivity() != null) {
            FindYourIDFragment findYourIDFragment = new FindYourIDFragment();
            if (findYourIDFragment.getDialog() != null)
                findYourIDFragment.getDialog().setCancelable(false);
            findYourIDFragment.show(getActivity().getSupportFragmentManager(), "");
        }
    }

    private void forgotPassword() {
        if (SystemClock.elapsedRealtime() - mForgotPasswordLCT < 1000)
            return;
        mForgotPasswordLCT = SystemClock.elapsedRealtime();
        if (getActivity() != null) {
            ForgotPasswordFragment forgotPasswordFragment = new ForgotPasswordFragment();
            if (forgotPasswordFragment.getDialog() != null)
                forgotPasswordFragment.getDialog().setCancelable(false);
            forgotPasswordFragment.show(getActivity().getSupportFragmentManager(), "");
        }
    }

    private void validateOTP(String user, String otpEntered) {
        processRequest(AppController.getWebService().getVerifyLoginOTP(user, otpEntered), false, true, getString(R.string.validating_otp), new RetrofitListener() {
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
                                screenMoveToHome();
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
    }

    private void doLogin(String user, String pass) {
        processRequest(AppController.getWebService().getLoginByPassword(user, pass), false, false, null, new RetrofitListener() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    LoginResponse loginResponse = (LoginResponse) object;
                    BaseResponse baseResponse = loginResponse.getBaseResponse();
                    if (baseResponse != null && isValidString(baseResponse.toString())) {
                        if (baseResponse.getResponseCode() == 1) {
                            Login login = loginResponse.getLogin();
                            if (login != null) {
                                String code = login.getMsg();
                                if (isValidString(code)) {
                                    //showOTPScreen(CommonUtils.getNumberFromString(code));
                                    screenMoveToOTPScreen(CommonUtils.getNumberFromString(code));
                                } else {
                                    showMessageAlert(getString(R.string.login_title), getString(R.string.login_id_error));
                                }
                            } else {
                                showMessageAlert(getString(R.string.login_title), getString(R.string.login_id_error));
                            }
                        } else {
                            showMessageAlert(getString(R.string.login_title), getString(R.string.login_id_error));
                        }
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        }, LoginResponse.class);
    }

    private void screenMoveToOTPScreen(String otp) {
        Bundle bundle = new Bundle();
        String user = username.getText().toString();
        bundle.putString(VERIFICATION_FRAGMENT_BUNDLE_KEY_OTP, otp);
        bundle.putString(VERIFICATION_FRAGMENT_BUNDLE_KEY_PATIENT_ID, user);
        VerificationFragment verificationFragment = VerificationFragment.newInstance(bundle);
        verificationFragment.setPageFrom(Constants.LOGIN);
        if (getActivity() != null) {
            verificationFragment.show(getActivity().getSupportFragmentManager(), "");
            getActivity().getSupportFragmentManager().executePendingTransactions();
            if (verificationFragment.getDialog() != null) {
                verificationFragment.getDialog().setOnDismissListener(dialog -> onResume());
            }

        }
    }

    private void screenMoveToHome() {
        if (getActivity() != null) {
        }
    }
}
