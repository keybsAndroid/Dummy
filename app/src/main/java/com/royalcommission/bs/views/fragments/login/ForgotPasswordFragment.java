package com.keybs.rc.views.fragments.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.keybs.rc.R;
import com.keybs.rc.app.AppController;
import com.keybs.rc.modules.network.listener.RetrofitListener;
import com.keybs.rc.modules.network.retrofit.model.responses.BaseResponse;
import com.keybs.rc.modules.network.retrofit.model.responses.LocalAccount;
import com.keybs.rc.modules.network.retrofit.model.responses.LocalAccountResponse;
import com.keybs.rc.modules.network.retrofit.parser.RetrofitResponseParser;
import com.keybs.rc.modules.utils.CommonUtils;
import com.keybs.rc.views.fragments.base.BaseDialogFragment;

import java.util.List;

/**
 * Created by Prashant on 7/30/2018.
 */
public class ForgotPasswordFragment extends BaseDialogFragment {

    private EditText enterUserMedicalNumber;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.forgot_password_dialog, null);
        enterUserMedicalNumber = view.findViewById(R.id.enter_user_med_num);
        Button confirm = view.findViewById(R.id.find);

        if (getDialog() != null)
            getDialog().setCanceledOnTouchOutside(false);

        confirm.setOnClickListener(v -> {
            if (enterUserMedicalNumber != null && isValidString(enterUserMedicalNumber.getText().toString())) {
                getPassword(enterUserMedicalNumber.getText().toString());
            } else {
                showToastMessage(getString(R.string.fields_empty));
            }
        });
        return view;
    }


    private void closeDialogFragment() {
        if (getDialog() != null) {
            getDialog().cancel();
            getDialog().dismiss();
        }
    }

    private void getPassword(String patientID) {
        processGETRequest(false, null, true, AppController.getWebService().getPassword(patientID), new RetrofitListener() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    LocalAccountResponse localAccountResponse = RetrofitResponseParser.convertInstanceOfObject(object, LocalAccountResponse.class);
                    if (localAccountResponse != null) {
                        BaseResponse baseResponse = localAccountResponse.getBaseResponse();
                        if (baseResponse != null && isValidString(baseResponse.toString())) {
                            if (baseResponse.getResponseCode() == 1) {
                                closeDialogFragment();
                                LocalAccount localAccount = localAccountResponse.getLocalAccount();
                                if (localAccount != null) {
                                    if (isValidString(localAccount.getEmail())) {
                                        if (CommonUtils.isValidEmail(localAccount.getEmail())) {
                                            showMessageAlert(getString(R.string.find_your_password), getString(R.string.mail_shortly));
                                        } else {
                                            showMessageAlert(getString(R.string.find_your_password), getString(R.string.email_is_not_registered));
                                        }
                                    } else {
                                        showMessageAlert(getString(R.string.find_your_password), getString(R.string.email_is_not_registered));
                                    }
                                }
                            } else {
                                closeDialogFragment();
                                showMessageAlert(getString(R.string.find_your_password), getString(R.string.email_is_not_registered));
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
                Log.d("Forgot Password", "Success: " + object);
            }

            @Override
            public void onError(String error) {
                closeDialogFragment();
                Log.d("Forgot Password", "onError: " + error);
            }
        });
    }
}
