package com.royalcommission.bs.views.fragments.dashboard.documents;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.royalcommission.bs.R;
import com.royalcommission.bs.app.AppController;
import com.royalcommission.bs.modules.api.listener.RetrofitListener;
import com.royalcommission.bs.modules.api.model.BaseResponse;
import com.royalcommission.bs.modules.api.model.Document;
import com.royalcommission.bs.modules.api.model.EmailSentResponse;
import com.royalcommission.bs.modules.utils.CommonUtils;
import com.royalcommission.bs.modules.utils.SharedPreferenceUtils;
import com.royalcommission.bs.views.dialogs.BaseDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmailDocumentFragment extends BaseDialogFragment implements View.OnClickListener {

    private AlertDialog alertDialog;
    private long lastClickedTime;
    private static Document mDocument;
    private EditText editTextEmail;
    private Button submit;

    public EmailDocumentFragment() {
        // Required empty public constructor
    }

    public static EmailDocumentFragment getInstance(Document document) {
        // Required empty public constructor
        mDocument = document;
        return new EmailDocumentFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getActivity() != null) {
            alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setCanceledOnTouchOutside(false);
            if (!alertDialog.isShowing())
                alertDialog.show();
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_document_email, null, false);
            alertDialog.setContentView(view);
            editTextEmail = view.findViewById(R.id.email);
            submit = view.findViewById(R.id.submit);
            submit.setOnClickListener(this);
        }
        return alertDialog;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (getDialog().getWindow() != null) {
            WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            getDialog().getWindow().setAttributes(params);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            if (SystemClock.elapsedRealtime() - lastClickedTime < 1000)
                return;
            lastClickedTime = SystemClock.elapsedRealtime();
            if (view.getId() == submit.getId()) {
                String email = editTextEmail.getText().toString();
                if (isValidString(email) && CommonUtils.isValidEmail(email)) {
                    sendMedicalDoc(email);
                } else {
                    if (isValidString(email))
                        showMessageAlert(null, getString(R.string.enter_your_email));
                    else if (CommonUtils.isValidEmail(email))
                        showMessageAlert(null, getString(R.string.enter_valid_email));
                }
            }
        }
    }

    private void sendMedicalDoc(String email) {
        if (mDocument != null) {
            String appointmentID = mDocument.getAppointmentId();
            String formID = String.valueOf(mDocument.getFormId());
            if (isValidString(appointmentID) && isValidString(formID)) {
                processRequest(AppController.getWebService().sendDocumentEmail(formID, SharedPreferenceUtils.getPatientID(getActivity()), appointmentID, email), false, true, null, new RetrofitListener() {
                    @Override
                    public void onSuccess(Object object) {
                        if (object != null) {
                            EmailSentResponse sentResponse = (EmailSentResponse) object;
                            BaseResponse baseResponse = sentResponse.getBaseResponse();
                            if (baseResponse != null) {
                                if (baseResponse.getResponseCode() == 1) {
                                    showToastMessage(getString(R.string.email_success) + email);
                                } else {
                                    showToastMessage(getString(R.string.email_fail));
                                }
                            }
                            dismiss();
                        }
                    }

                    @Override
                    public void onError(String error) {
                        showToastMessage(error);
                        dismiss();
                    }
                }, EmailSentResponse.class);
            }
        }

    }

}
