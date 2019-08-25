package com.royalcommission.bs.views.fragments.dashboard.documents;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.royalcommission.bs.R;
import com.royalcommission.bs.views.dialogs.BaseDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MedicalDocumentFragment extends BaseDialogFragment implements View.OnClickListener {


    public static final String TAG = MedicalDocumentFragment.class.getSimpleName();
    private AlertDialog alertDialog;
    private long lastClickedTime;

    public MedicalDocumentFragment() {
        // Required empty public constructor
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
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_medical_document, null, false);
            alertDialog.setContentView(view);
            LinearLayout documentRequest = view.findViewById(R.id.document_request);
            LinearLayout documentStatus = view.findViewById(R.id.document_status);
            if (documentRequest != null) documentRequest.setOnClickListener(this);
            if (documentStatus != null) documentStatus.setOnClickListener(this);
        }
        return alertDialog;
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onResume() {
        super.onResume();
        //blockBackButtonPressWhenDialogOpen();
        if (getDialog().getWindow() != null) {
            WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            getDialog().getWindow().setAttributes(params);
        }

    }

    @Override
    public void onClick(View view) {
        if (view == null)
            return;
        if (SystemClock.elapsedRealtime() - lastClickedTime < 1000)
            return;
        lastClickedTime = SystemClock.elapsedRealtime();
        dismissAllowingStateLoss();
        if (view.getId() == R.id.document_request) {
            showMedicalDocumentRequestDialog();
        } else if (view.getId() == R.id.document_status) {
            showMedicalDocumentStatusDialog();
        }
    }

    private void showMedicalDocumentRequestDialog() {
        DocumentRequestFragment fragment = new DocumentRequestFragment();
        if (getActivity() != null) {
            fragment.show(getActivity().getSupportFragmentManager(), "document request");
        }
    }

    private void showMedicalDocumentStatusDialog() {
        DocumentStatusFragment fragment = new DocumentStatusFragment();
        if (getActivity() != null) {
            fragment.show(getActivity().getSupportFragmentManager(), "document status");
        }
    }
}
