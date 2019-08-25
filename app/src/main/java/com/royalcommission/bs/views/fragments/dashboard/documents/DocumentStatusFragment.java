package com.royalcommission.bs.views.fragments.dashboard.documents;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.royalcommission.bs.R;
import com.royalcommission.bs.app.AppController;
import com.royalcommission.bs.modules.api.listener.RetrofitListener;
import com.royalcommission.bs.modules.api.model.BaseResponse;
import com.royalcommission.bs.modules.api.model.Document;
import com.royalcommission.bs.modules.api.model.DocumentResponse;
import com.royalcommission.bs.modules.api.model.RequestedDocumentStatus;
import com.royalcommission.bs.modules.api.model.RequestedDocumentStatusResponse;
import com.royalcommission.bs.modules.utils.SharedPreferenceUtils;
import com.royalcommission.bs.views.adapters.CompletedDocumentsAdapter;
import com.royalcommission.bs.views.adapters.DocumentsStatusAdapter;
import com.royalcommission.bs.views.dialogs.BaseDialogFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DocumentStatusFragment extends BaseDialogFragment implements CompletedDocumentsAdapter.CompletedDocumentsClickListener, DocumentsStatusAdapter.DocumentsStatusClickListener {

    private AlertDialog alertDialog;
    private String patientID;
    private List<Document> completedDocumentList = new ArrayList<>();
    private List<RequestedDocumentStatus> requestedDocumentList = new ArrayList<>();
    private CompletedDocumentsAdapter completedDocumentsAdapter;
    private DocumentsStatusAdapter documentsStatusAdapter;
    private TextView documentProcessingEmpty, completedDocumentsEmpty;


    public DocumentStatusFragment() {
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
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_document_status, null, false);
            RecyclerView documentProcessing = view.findViewById(R.id.document_processing);
            RecyclerView completedDocuments = view.findViewById(R.id.completed_documents);
            documentProcessingEmpty = view.findViewById(R.id.document_processing_empty);
            completedDocumentsEmpty = view.findViewById(R.id.completed_documents_empty);
            alertDialog.setContentView(view);
            patientID = SharedPreferenceUtils.getPatientID(getActivity());
            completedDocumentsAdapter = new CompletedDocumentsAdapter(getActivity(), completedDocumentList, this);
            completedDocuments.setLayoutManager(new LinearLayoutManager(getActivity()));
            completedDocuments.setHasFixedSize(true);
            completedDocuments.setAdapter(completedDocumentsAdapter);

            documentsStatusAdapter = new DocumentsStatusAdapter(getActivity(), requestedDocumentList, this);
            documentProcessing.setLayoutManager(new LinearLayoutManager(getActivity()));
            documentProcessing.setHasFixedSize(true);
            documentProcessing.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
            documentProcessing.setAdapter(documentsStatusAdapter);
            requestedDocumentStatus();
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


    private void requestedDocumentStatus() {
        if (isValidString(patientID))
            processRequest(AppController.getWebService().getRequestedMedicalDocumentStatus(patientID), false, true, null, new RetrofitListener() {
                @Override
                public void onSuccess(Object object) {
                    if (object != null) {
                        RequestedDocumentStatusResponse documentStatusResponse = (RequestedDocumentStatusResponse) object;
                        BaseResponse baseResponse = documentStatusResponse.getBaseResponse();
                        if (baseResponse != null) {
                            if (baseResponse.getResponseCode() == 1) {
                                requestedDocumentList.clear();
                                if (documentStatusResponse.getRequestedDocumentStatusList() != null && !documentStatusResponse.getRequestedDocumentStatusList().isEmpty()) {
                                    requestedDocumentList.addAll(documentStatusResponse.getRequestedDocumentStatusList());
                                    documentProcessingEmpty.setVisibility(View.GONE);
                                } else {
                                    documentProcessingEmpty.setVisibility(View.VISIBLE);
                                    showServerError(getString(R.string.no_results));
                                }
                                documentsStatusAdapter.notifyDataSetChanged();
                            } else {
                                documentProcessingEmpty.setVisibility(View.VISIBLE);
                            }
                        }

                        completedDocuments();
                    }
                }

                @Override
                public void onError(String error) {
                    documentProcessingEmpty.setVisibility(View.VISIBLE);
                    completedDocuments();
                }
            }, RequestedDocumentStatusResponse.class);
    }

    private void completedDocuments() {
        if (isValidString(patientID))
            processRequest(AppController.getWebService().getCompletedMedicalDocumentList(patientID), false, true, null, new RetrofitListener() {
                @Override
                public void onSuccess(Object object) {
                    if (object != null) {
                        DocumentResponse documentResponse = (DocumentResponse) object;
                        BaseResponse baseResponse = documentResponse.getBaseResponse();
                        if (baseResponse != null) {
                            if (baseResponse.getResponseCode() == 1) {
                                completedDocumentList.clear();
                                if (documentResponse.getDocumentList() != null && !documentResponse.getDocumentList().isEmpty()) {
                                    completedDocumentList.addAll(documentResponse.getDocumentList());
                                    completedDocumentsEmpty.setVisibility(View.GONE);
                                } else {
                                    completedDocumentsEmpty.setVisibility(View.VISIBLE);
                                }
                                completedDocumentsAdapter.notifyDataSetChanged();
                            } else {
                                completedDocumentsEmpty.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }

                @Override
                public void onError(String error) {
                    completedDocumentsEmpty.setVisibility(View.VISIBLE);
                    //showServerError(error);
                }
            }, DocumentResponse.class);
    }

    @Override
    public void onClick(Document document) {
        if (document != null) {
            showMedicalDocuments(document);
        }
    }

    private void showMedicalDocuments(Document document) {
        if (document != null) {
            DocumentViewFragment fragment = DocumentViewFragment.getInstance(document);
            if (getActivity() != null) {
                fragment.show(getActivity().getSupportFragmentManager(), "document view");
            }
        }
    }

    @Override
    public void onClick(RequestedDocumentStatus requestedDocumentStatus) {
        if (requestedDocumentStatus != null) {

        }
    }
}
