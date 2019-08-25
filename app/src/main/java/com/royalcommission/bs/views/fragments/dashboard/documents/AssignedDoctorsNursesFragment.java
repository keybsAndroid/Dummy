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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.royalcommission.bs.R;
import com.royalcommission.bs.app.AppController;
import com.royalcommission.bs.modules.api.listener.RetrofitListener;
import com.royalcommission.bs.modules.api.model.AssignedDoctorNurseResponse;
import com.royalcommission.bs.modules.api.model.BaseResponse;
import com.royalcommission.bs.modules.utils.SharedPreferenceUtils;
import com.royalcommission.bs.views.adapters.NurseAdapter;
import com.royalcommission.bs.views.adapters.PhysicianAdapter;
import com.royalcommission.bs.views.dialogs.BaseDialogFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AssignedDoctorsNursesFragment extends BaseDialogFragment implements View.OnClickListener {

    private AlertDialog alertDialog;
    private long lastClickedTime;
    private static List<String> doctorList = new ArrayList<>();
    private static List<String> nurseList = new ArrayList<>();
    private NurseAdapter nurseAdapter;
    private PhysicianAdapter physicianAdapter;
    private LinearLayout doctorLayout, nurseLayout;

    public AssignedDoctorsNursesFragment() {
        // Required empty public constructor
    }

    public static AssignedDoctorsNursesFragment getInstance() {
        return new AssignedDoctorsNursesFragment();
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
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_dialog_physician_nurse_assigned, null, false);
            alertDialog.setContentView(view);

            doctorLayout = view.findViewById(R.id.doctor_info_layout);
            RecyclerView doctorRecyclerView = view.findViewById(R.id.doctor_recycler_view);

            nurseLayout = view.findViewById(R.id.nurse_info_layout);
            RecyclerView nurseRecyclerView = view.findViewById(R.id.nurse_recycler_view);

            setDoctorAdapter(doctorRecyclerView);
            setNurseAdapter(nurseRecyclerView);
            getAssignedDoctorNurseList();
            view.findViewById(R.id.cancel).setOnClickListener(this);
        }
        return alertDialog;
    }

    private void setDoctorAdapter(RecyclerView recyclerView) {
        if (getActivity() != null) {
            physicianAdapter = new PhysicianAdapter(getActivity(), doctorList);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setAdapter(physicianAdapter);
        }
    }

    private void setNurseAdapter(RecyclerView recyclerView) {
        if (getActivity() != null) {
            nurseAdapter = new NurseAdapter(getActivity(), nurseList);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setAdapter(nurseAdapter);
        }
    }


    private void getAssignedDoctorNurseList() {
        //Dummy Data
        String patientId = "80000002";
        //String patientId = SharedPreferenceUtils.getPatientID(AppController.getInstance());
        processRequest(AppController.getWebService().getAssignedDoctorNurseList(patientId, SharedPreferenceUtils.getHospitalID(AppController.getInstance())), false, true, null, new RetrofitListener() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    AssignedDoctorNurseResponse roundingDoctorResponse = (AssignedDoctorNurseResponse) object;
                    BaseResponse baseResponse = roundingDoctorResponse.getBaseResponse();
                    if (baseResponse != null) {
                        if (baseResponse.getResponseCode() == 1) {
                            if (roundingDoctorResponse.getAssignedDoctorList() != null && !roundingDoctorResponse.getAssignedDoctorList().isEmpty()) {
                                if (doctorList != null) {
                                    doctorList.clear();
                                    doctorList.addAll(roundingDoctorResponse.getAssignedDoctorList());
                                    physicianAdapter.notifyDataSetChanged();
                                }
                            } else {
                                doctorLayout.setVisibility(View.GONE);
                            }
                            if (roundingDoctorResponse.getAssignedNurseList() != null && !roundingDoctorResponse.getAssignedNurseList().isEmpty()) {
                                if (nurseList != null) {
                                    nurseList.clear();
                                    nurseList.addAll(roundingDoctorResponse.getAssignedNurseList());
                                    nurseAdapter.notifyDataSetChanged();
                                }
                            } else {
                                nurseLayout.setVisibility(View.GONE);
                            }
                        } else {
                            doctorLayout.setVisibility(View.GONE);
                            nurseLayout.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onError(String error) {
                doctorLayout.setVisibility(View.GONE);
                nurseLayout.setVisibility(View.GONE);
                showMessageAlert(null, error);
            }
        }, AssignedDoctorNurseResponse.class);

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
        blockBackButtonPressWhenDialogOpen();
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
            if (view.getId() == R.id.cancel) {
                dismissAllowingStateLoss();
            }
        }
    }

}
