package com.royalcommission.bs.views.fragments.dashboard.documents;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.royalcommission.bs.R;
import com.royalcommission.bs.app.AppController;
import com.royalcommission.bs.modules.api.listener.RetrofitListener;
import com.royalcommission.bs.modules.api.model.BaseResponse;
import com.royalcommission.bs.modules.api.model.LocalDocumentResponse;
import com.royalcommission.bs.modules.api.model.LocalDocuments;
import com.royalcommission.bs.modules.utils.CommonUtils;
import com.royalcommission.bs.modules.utils.Constants;
import com.royalcommission.bs.modules.utils.GridSpacingItemDecoration;
import com.royalcommission.bs.modules.utils.SharedPreferenceUtils;
import com.royalcommission.bs.views.adapters.CheckBoxAdapter;
import com.royalcommission.bs.views.dialogs.BaseDialogFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DocumentRequestFragment extends BaseDialogFragment implements View.OnClickListener, CheckBox.OnCheckedChangeListener {

    private AlertDialog alertDialog;
    private String selectedPurpose = null;
    private CheckBox patientSitter, admissionNotification, deliveryLeave, dischargeSummary, sickLeave;
    private int[] checkBoxes = {R.id.patient_sitter, R.id.admission_notification, R.id.delivery_leave, R.id.discharge_summary, R.id.sick_leave};
    private List<CheckBox> checkBoxList = new ArrayList<>();
    private List<LocalDocuments> localDocumentsList = new ArrayList<>();
    private String selectedDocName = null;
    private String selectedDocId = null;
    private CheckBoxAdapter checkBoxAdapter;
    private long lastClickedTime;
    private RecyclerView recyclerView;

    public DocumentRequestFragment() {
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
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_document_request, null, false);
            alertDialog.setContentView(view);
            recyclerView = view.findViewById(R.id.menu_recycler_view);
            patientSitter = view.findViewById(R.id.patient_sitter);
            admissionNotification = view.findViewById(R.id.admission_notification);
            deliveryLeave = view.findViewById(R.id.delivery_leave);
            dischargeSummary = view.findViewById(R.id.discharge_summary);
            sickLeave = view.findViewById(R.id.sick_leave);
            view.findViewById(R.id.request_document).setOnClickListener(this);

            /*checkBoxList.clear();
            checkBoxList.add(patientSitter);
            checkBoxList.add(admissionNotification);
            checkBoxList.add(deliveryLeave);
            checkBoxList.add(dischargeSummary);
            checkBoxList.add(sickLeave);

            patientSitter.setOnCheckedChangeListener(this);
            admissionNotification.setOnCheckedChangeListener(this);
            deliveryLeave.setOnCheckedChangeListener(this);
            dischargeSummary.setOnCheckedChangeListener(this);
            sickLeave.setOnCheckedChangeListener(this);*/

            RadioGroup groupButton = view.findViewById(R.id.group_button);
            groupButton.setOnCheckedChangeListener((group, checkedId) -> {
                switch (checkedId) {
                    case R.id.personal_use:
                        selectedPurpose = getSelectedOption(group);
                        break;
                    case R.id.insurance_use:
                        selectedPurpose = getSelectedOption(group);
                        break;
                }
            });
            setAdapter(recyclerView);
            getMedicalDocList();
        }
        return alertDialog;
    }

    private void setAdapter(RecyclerView recyclerView) {
        int valueInPixels = getResources().getInteger(R.integer.grid_view_spacing);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), Constants.NUMBER_OF_CHECKBOX_COLUMNS));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(Constants.NUMBER_OF_CHECKBOX_COLUMNS, CommonUtils.dpToPx(recyclerView.getContext(), valueInPixels), true));
        recyclerView.setNestedScrollingEnabled(false);
        checkBoxAdapter = new CheckBoxAdapter(getActivity(), localDocumentsList, position -> {
            selectedDocName = localDocumentsList.get(position).getDocumentName();
            selectedDocId = localDocumentsList.get(position).getDocumentId();
            showToastMessage(selectedDocName + " - " + selectedDocId);
        });
        recyclerView.setAdapter(checkBoxAdapter);
    }

    private void updateAdapter() {
        if (recyclerView != null) {
            recyclerView.post(() -> checkBoxAdapter.notifyDataSetChanged());
        }
    }

    private String getSelectedOption(RadioGroup group) {
        RadioButton radioButtonAns = group.findViewById(group.getCheckedRadioButtonId());
        if (radioButtonAns != null) {
            return radioButtonAns.getText().toString();
        } else {
            return null;
        }
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

    private void blockBackButtonPressWhenDialogOpen() {
        if (getDialog() != null) {
            getDialog().setOnKeyListener((dialog, keyCode, event) -> (keyCode == android.view.KeyEvent.KEYCODE_BACK));
        }
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            if (SystemClock.elapsedRealtime() - lastClickedTime < 1000)
                return;
            lastClickedTime = SystemClock.elapsedRealtime();
            if (view.getId() == R.id.request_document) {
                if (isValidString(selectedPurpose) && isValidString(selectedDocName)) {
                    requestDocument();
                } else {
                    if (!isValidString(selectedPurpose)) {
                        showMessageAlert(getString(R.string.document_request), "Please Select The Document Purpose");
                    } else if (!isValidString(selectedDocName)) {
                        showMessageAlert(getString(R.string.document_request), "Please Select The Document You Want To Request");
                    }
                }
            }
        }
    }

    private void getMedicalDocList() {
        processRequest(AppController.getWebService().getAvailableMedicalDocumentList(), false, true, null, new RetrofitListener() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    LocalDocumentResponse localDocumentResponse = (LocalDocumentResponse) object;
                    BaseResponse baseResponse = localDocumentResponse.getBaseResponse();
                    if (baseResponse != null) {
                        if (baseResponse.getResponseCode() == 1) {
                            List<LocalDocuments> documents = localDocumentResponse.getDocuments();
                            if (documents != null && !documents.isEmpty()) {
                                localDocumentsList.clear();
                                localDocumentsList.addAll(documents);
                                updateAdapter();
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
        }, LocalDocumentResponse.class);
    }

    private void requestDocument() {
        processRequest(AppController.getWebService().requestMedicalDocumentList(SharedPreferenceUtils.getPatientID(AppController.getInstance()), selectedDocName, selectedDocId, selectedPurpose), false, true, null, new RetrofitListener() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    BaseResponse baseResponse = (BaseResponse) object;
                    if (baseResponse.getResponseCode() == 1) {
                        dismissAllowingStateLoss();
                        showToastMessage("Your Document Has Been Requested");
                    } else {
                        //dismissAllowingStateLoss();
                        showServerError("Failed To Request");
                    }
                }
            }

            @Override
            public void onError(String error) {
                dismissAllowingStateLoss();
                showServerError(error);
            }
        }, BaseResponse.class);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        /*switch (buttonView.getId()) {
            case R.id.patient_sitter:
                selectedCheckBox = null;
                if (isChecked) {
                    selectedCheckBox = buttonView.getText().toString();
                    unCheckOthers(((CheckBox) buttonView));
                }
                break;
            case R.id.admission_notification:
                selectedCheckBox = null;
                if (isChecked) {
                    selectedCheckBox = buttonView.getText().toString();
                    unCheckOthers(((CheckBox) buttonView));
                }
                break;

            case R.id.delivery_leave:
                selectedCheckBox = null;
                if (isChecked) {
                    selectedCheckBox = buttonView.getText().toString();
                    unCheckOthers(((CheckBox) buttonView));
                }
                break;

            case R.id.discharge_summary:
                selectedCheckBox = null;
                if (isChecked) {
                    selectedCheckBox = buttonView.getText().toString();
                    unCheckOthers(((CheckBox) buttonView));
                }
                break;
            case R.id.sick_leave:
                selectedCheckBox = null;
                if (isChecked) {
                    selectedCheckBox = buttonView.getText().toString();
                    unCheckOthers(((CheckBox) buttonView));
                }
                break;
        }*/
    }

    private void unCheckOthers(CheckBox buttonView) {
        for (CheckBox checkBox : checkBoxList) {
            if (checkBox != buttonView) {
                checkBox.setChecked(false);
            }
        }
    }

}
