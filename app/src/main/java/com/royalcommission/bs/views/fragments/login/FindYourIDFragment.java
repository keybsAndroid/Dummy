package com.keybs.rc.views.fragments.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.keybs.rc.R;
import com.keybs.rc.app.AppController;
import com.keybs.rc.modules.network.listener.RetrofitListener;
import com.keybs.rc.modules.network.retrofit.model.responses.BaseResponse;
import com.keybs.rc.modules.network.retrofit.model.responses.LoginResponse;
import com.keybs.rc.modules.network.retrofit.parser.RetrofitResponseParser;
import com.keybs.rc.modules.utils.CommonUtils;
import com.keybs.rc.modules.utils.Constants;
import com.keybs.rc.modules.utils.SpinnerHintAdapter;
import com.keybs.rc.views.customviews.SpinnerHint;
import com.keybs.rc.views.fragments.base.BaseDialogFragment;
import com.keybs.rc.views.fragments.verification.VerificationFragment;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Prashant on 7/30/2018.
 */
public class FindYourIDFragment extends BaseDialogFragment {

    private Spinner findIDTypeSpinner;
    private List<String> findIDTypes;
    private EditText emailPhone;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.find_your_id_dialog, null);
        emailPhone = view.findViewById(R.id.email_phone);
        findIDTypeSpinner = view.findViewById(R.id.finding_type_spin);
        Button confirm = view.findViewById(R.id.find);

        if (getDialog() != null)
            getDialog().setCanceledOnTouchOutside(false);


        runOnUIThread(this::setHintAdapterForLoginType);


        confirm.setOnClickListener(v -> {
            if (isValid(findIDTypeSpinner, findIDTypes.size())) {
                if (emailPhone != null && isValidString(emailPhone.getText().toString())) {
                    if (findIDTypeSpinner.getSelectedItemPosition() == 0) {
                        if (CommonUtils.isValidEmail(emailPhone.getText().toString())) {
                            getPatientIDFromEmailPhone(AppController.getWebService().getPatientIdByEmail(emailPhone.getText().toString().trim()), 0);
                        } else {
                            showToastMessage(getString(R.string.valid_email));
                        }
                    } else if (findIDTypeSpinner.getSelectedItemPosition() == 1) {
                        getPatientIDFromEmailPhone(AppController.getWebService().getPatientIdByPhone(emailPhone.getText().toString().trim()), 1);
                    }
                } else {
                    if (findIDTypeSpinner.getSelectedItemPosition() == 0) {
                        showToastMessage(getString(R.string.valid_email));
                    } else if (findIDTypeSpinner.getSelectedItemPosition() == 1) {
                        showToastMessage(getString(R.string.valid_mobile));
                    }
                }
            } else {
                showToastMessage(getString(R.string.pls_select_type));
            }
        });
        return view;
    }

    private void setHintAdapterForLoginType() {
        findIDTypes = Arrays.asList(getResources().getStringArray(R.array.find_account_type));
        SpinnerHintAdapter<String> spinnerHintAdapter = new SpinnerHintAdapter<String>(getActivity(), R.layout.spinner_item, R.string.select_type, findIDTypes) {
            @Override
            protected View getCustomView(int position, View convertView, ViewGroup parent) {
                String userName = getItem(position);
                View view = inflateLayout(parent);
                ((TextView) view.findViewById(R.id.text)).setText(userName);
                return view;
            }
        };
        SpinnerHint<String> spinnerHint = new SpinnerHint<>(findIDTypeSpinner,
                spinnerHintAdapter,
                (position, itemAtPosition) -> {
                    if (position == 0) {
                        emailPhone.setFilters(new InputFilter[]{});
                        emailPhone.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                        emailPhone.setHint(getString(R.string.enter_your_email));
                    } else if (position == 1) {
                        emailPhone.setInputType(InputType.TYPE_CLASS_PHONE);
                        emailPhone.setHint(getString(R.string.mobile_number_hint));
                        emailPhone.setKeyListener(DigitsKeyListener.getInstance("1234567890"));
                        emailPhone.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Constants.PHONE_NUMBER_LIMIT)});
                    }
                });
        spinnerHint.init();
    }

    private boolean isValid(Spinner spinner, int listSize) {
        if (spinner != null)
            return spinner.getSelectedItemPosition() <= listSize;
        else
            return false;
    }

    private void closeDialogFragment() {
        if (getDialog() != null) {
            getDialog().cancel();
            getDialog().dismiss();
        }
    }

    private void getPatientIDFromEmailPhone(Observable<Object> observable, int idType) {
        processGETRequest(false, null, true, observable, new RetrofitListener() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    LoginResponse loginResponse = RetrofitResponseParser.convertInstanceOfObject(object, LoginResponse.class);
                    if (loginResponse != null) {
                        BaseResponse baseResponse = loginResponse.getBaseResponse();
                        if (baseResponse != null && isValidString(baseResponse.toString())) {
                            if (baseResponse.getResponseCode() == 1) {
                                closeDialogFragment();
                                if (idType == 0) {
                                    showMessageAlert(getString(R.string.find_your_account), getString(R.string.mail_shortly));
                                } else if (idType == 1) {
                                    showMessageAlert(getString(R.string.find_your_account), getString(R.string.sms_shortly));
                                }
                            } else {
                                closeDialogFragment();
                                if (idType == 0) {
                                    showMessageAlert(getString(R.string.find_your_account), getString(R.string.not_found_email));
                                } else if (idType == 1) {
                                    showMessageAlert(getString(R.string.find_your_account), getString(R.string.not_found_phone));
                                }
                            }
                        } else {
                            closeDialogFragment();
                            showAPIError();
                        }
                    } else {
                        showAPIError();
                    }
                }
            }

            @Override
            public void onSuccess(List<Object> object) {
                Log.d("Login", "Success: " + object);
            }

            @Override
            public void onError(String error) {
                closeDialogFragment();
                Log.d("Login", "onError: " + error);
            }
        });
    }

    private void screenMoveToOTPScreen(String numberFromString) {
        Bundle bundle = new Bundle();
        bundle.putString(VERIFICATION_FRAGMENT_BUNDLE_KEY_OTP, numberFromString);
        VerificationFragment verificationFragment = VerificationFragment.newInstance(bundle);
        verificationFragment.setPageFrom(Constants.FIND_YOUR_ID);
        if (getActivity() != null)
            verificationFragment.show(getActivity().getSupportFragmentManager(), "");
    }
}
