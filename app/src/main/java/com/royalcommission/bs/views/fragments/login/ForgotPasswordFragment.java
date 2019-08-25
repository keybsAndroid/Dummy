package com.royalcommission.bs.views.fragments.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.royalcommission.bs.R;
import com.royalcommission.bs.app.AppController;
import com.royalcommission.bs.modules.api.listener.RetrofitListener;
import com.royalcommission.bs.modules.api.model.BaseResponse;
import com.royalcommission.bs.modules.api.model.LocalAccount;
import com.royalcommission.bs.modules.api.model.LocalAccountResponse;
import com.royalcommission.bs.modules.api.parser.RetrofitResponseParser;
import com.royalcommission.bs.modules.utils.CommonUtils;
import com.royalcommission.bs.views.dialogs.BaseDialogFragment;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Prashant on 7/30/2018.
 */
public class ForgotPasswordFragment extends BaseDialogFragment {

    private EditText enterUserMedicalNumber;

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        processRequest(AppController.getWebService().getPassword(patientID), false, true, null, new RetrofitListener() {
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
                                } else {
                                    showMessageAlert(getString(R.string.find_your_password), getString(R.string.email_is_not_registered));
                                }
                            } else {
                                closeDialogFragment();
                                showMessageAlert(getString(R.string.find_your_password), getString(R.string.email_is_not_registered));
                            }
                        } else {
                            closeDialogFragment();
                            showServerError(null);
                        }
                    } else {
                        showServerError(null);
                    }
                }
            }

            @Override
            public void onError(String error) {
                showServerError(null);
            }
        }, LocalAccountResponse.class);
    }
}
