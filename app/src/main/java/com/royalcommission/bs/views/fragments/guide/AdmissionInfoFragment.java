package com.royalcommission.bs.views.fragments.guide;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.royalcommission.bs.R;
import com.royalcommission.bs.app.AppController;
import com.royalcommission.bs.modules.api.listener.RetrofitListener;
import com.royalcommission.bs.modules.api.model.BaseResponse;
import com.royalcommission.bs.modules.api.model.InpatientInfo;
import com.royalcommission.bs.modules.api.model.InpatientResponse;
import com.royalcommission.bs.modules.utils.SharedPreferenceUtils;
import com.royalcommission.bs.views.fragments.base.BaseFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdmissionInfoFragment extends BaseFragment {


    private LinearLayout nameLayout, bedLayout, ageLayout;
    private TextView name, bedNumber, age;

    public static final String TAG = AdmissionInfoFragment.class.getSimpleName();

    public AdmissionInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admission_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nameLayout = view.findViewById(R.id.name_layout);
        bedLayout = view.findViewById(R.id.bed_layout);
        ageLayout = view.findViewById(R.id.age_layout);

        name = view.findViewById(R.id.name);
        bedNumber = view.findViewById(R.id.bed_number);
        age = view.findViewById(R.id.age);

        getInpatientInfo();
    }

    private void getInpatientInfo() {

        processRequest(AppController.getWebService().getInpatientInfo(SharedPreferenceUtils.getPatientID(AppController.getInstance()), SharedPreferenceUtils.getHospitalID(AppController.getInstance())), false, true, null, new RetrofitListener() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    InpatientResponse inpatientResponse = (InpatientResponse) object;
                    BaseResponse baseResponse = inpatientResponse.getBaseResponse();
                    if (baseResponse != null) {
                        if (baseResponse.getResponseCode() == 1) {
                            List<InpatientInfo> infoList = inpatientResponse.getInpatientInfoList();
                            if (infoList != null && !infoList.isEmpty()) {
                                parseResponse(infoList.get(0));
                            }
                        } else {
                            showMessageAlert(null, getString(R.string.no_results));
                        }
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        }, InpatientResponse.class);

    }

    private void parseResponse(InpatientInfo inpatientInfo) {
        if (inpatientInfo != null) {
            setTextValues(nameLayout, name, inpatientInfo.getPatientName());
            setTextValues(bedLayout, bedNumber, inpatientInfo.getBedNumber());
            setTextValues(ageLayout, age, inpatientInfo.getAge());
        }
    }

    private void setTextValues(LinearLayout layout, TextView textView, String values) {
        if (isValidString(values)) {
            if (textView != null)
                textView.setText(values);
        } else {
            if (layout != null)
                layout.setVisibility(View.GONE);
        }
    }
}
