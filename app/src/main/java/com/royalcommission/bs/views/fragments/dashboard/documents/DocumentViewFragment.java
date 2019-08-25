package com.royalcommission.bs.views.fragments.dashboard.documents;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.royalcommission.bs.R;
import com.royalcommission.bs.app.AppController;
import com.royalcommission.bs.modules.api.listener.RetrofitListener;
import com.royalcommission.bs.modules.api.model.BaseResponse;
import com.royalcommission.bs.modules.api.model.Document;
import com.royalcommission.bs.modules.api.model.Form;
import com.royalcommission.bs.modules.api.model.FormResponse;
import com.royalcommission.bs.modules.utils.SharedPreferenceUtils;
import com.royalcommission.bs.views.dialogs.BaseDialogFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class DocumentViewFragment extends BaseDialogFragment implements View.OnClickListener {

    private AlertDialog alertDialog;
    private long lastClickedTime;
    private static Document mDocument;
    private List<Form> formList = new ArrayList<>();
    private WebView webView;
    private String designXML = "";
    private String formName = "";
    private Button sendEmail;
    private TextView docName;

    public DocumentViewFragment() {
        // Required empty public constructor
    }

    public static DocumentViewFragment getInstance(Document document) {
        // Required empty public constructor
        mDocument = document;
        return new DocumentViewFragment();
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
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_document_view, null, false);
            alertDialog.setContentView(view);
            webView = view.findViewById(R.id.web_view);
            sendEmail = view.findViewById(R.id.send_mail);
            sendEmail.setOnClickListener(this);
            docName = view.findViewById(R.id.doc_name);
            getMedicalDocList();
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

            if (view.getId() == sendEmail.getId()) {
                sendEmailDialog();
            }

        }
    }

    private void sendEmailDialog() {
        if (mDocument != null) {
            EmailDocumentFragment fragment = EmailDocumentFragment.getInstance(mDocument);
            if (getActivity() != null) {
                fragment.show(getActivity().getSupportFragmentManager(), "document email");
            }
        }
    }

    private void getMedicalDocList() {
        if (mDocument != null) {
            String appointmentID = mDocument.getAppointmentId();
            String formID = String.valueOf(mDocument.getFormId());
            if (isValidString(appointmentID) && isValidString(formID)) {
                processRequest(AppController.getWebService().geFormData(formID, SharedPreferenceUtils.getPatientID(getActivity()), appointmentID), false, true, null, new RetrofitListener() {
                    @Override
                    public void onSuccess(Object object) {
                        if (object != null) {
                            FormResponse formResponse = (FormResponse) object;
                            BaseResponse baseResponse = formResponse.getBaseResponse();
                            if (baseResponse != null) {
                                if (baseResponse.getResponseCode() == 1) {
                                    formList.clear();
                                    if (formResponse.getFormList() != null && !formResponse.getFormList().isEmpty()) {
                                        formList.addAll(formResponse.getFormList());
                                        parseFormData(formList.get(0));
                                    } else {
                                        showServerError(getString(R.string.no_results));
                                    }
                                } else {
                                    showServerError(null);
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(String error) {
                        showServerError(error);
                    }
                }, FormResponse.class);
            }
        }

    }


    @SuppressLint("SetJavaScriptEnabled")
    private void parseFormData(Form form) {
        if (form != null) {
            designXML = form.getDesignXML();
            formName = form.getFormName();
            if (isValidString(formName))
                docName.setText(formName);
            if (isValidString(designXML)) {
                Pattern htmlPattern = Pattern.compile(".*\\<[^>]+>.*", Pattern.DOTALL);
                boolean isHTML = htmlPattern.matcher(designXML).matches();
                if (isHTML) {
                    runOnUIThread(() -> {
                        webView.loadData(designXML, "text/html; charset=utf-8", "UTF-8");
                        webView.setInitialScale(1);
                        WebSettings webSettings = webView.getSettings();
                        webSettings.setBuiltInZoomControls(true);
                        webSettings.setDisplayZoomControls(false);
                        webSettings.setLoadWithOverviewMode(true);
                        webSettings.setUseWideViewPort(true);
                        webSettings.setJavaScriptEnabled(true);
                        webView.setWebViewClient(new WebViewClient() {
                            public void onPageFinished(WebView view, String url) {
                                sendEmail.setVisibility(View.VISIBLE);
                            }
                        });
                    });
                } else {
                    showMessageAlert(null, "Can't parse the data..");
                }
            }
        }
    }

}
