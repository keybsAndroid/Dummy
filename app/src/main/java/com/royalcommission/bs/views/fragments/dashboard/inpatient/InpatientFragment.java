package com.royalcommission.bs.views.fragments.dashboard.inpatient;


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
import com.royalcommission.bs.modules.utils.DateUtils;
import com.royalcommission.bs.modules.utils.SharedPreferenceUtils;
import com.royalcommission.bs.views.fragments.base.BaseFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class InpatientFragment extends BaseFragment {


    public static final String TAG = InpatientFragment.class.getSimpleName();
    private static String mTitle;
    private LinearLayout nameLayout, bedLayout, ageLayout, genderLayout, deptLayout, diagnosisLayout, dateLayout, allergyLayout;
    private TextView title, name, bedNumber, age, gender, department, diagnosis, date, allergy;

    public InpatientFragment() {
        // Required empty public constructor
    }

    public static InpatientFragment getInstance(String titleName) {
        // Required empty public constructor
        mTitle = titleName;
        return new InpatientFragment();
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inpatient, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nameLayout = view.findViewById(R.id.name_layout);
        bedLayout = view.findViewById(R.id.bed_layout);
        ageLayout = view.findViewById(R.id.age_layout);
        genderLayout = view.findViewById(R.id.gender_layout);
        deptLayout = view.findViewById(R.id.dept_layout);
        diagnosisLayout = view.findViewById(R.id.diagnosis_layout);
        dateLayout = view.findViewById(R.id.date_layout);
        allergyLayout = view.findViewById(R.id.allergy_layout);

        title = view.findViewById(R.id.title);
        name = view.findViewById(R.id.name);
        bedNumber = view.findViewById(R.id.bed_number);
        age = view.findViewById(R.id.age);
        gender = view.findViewById(R.id.gender);
        department = view.findViewById(R.id.department);
        diagnosis = view.findViewById(R.id.diagnosis);
        date = view.findViewById(R.id.date);
        allergy = view.findViewById(R.id.allergy);

        if (title != null) {
            if (isValidString(mTitle)) {
                title.setText(mTitle);
            }
        }

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
            setTextValues(genderLayout, gender, inpatientInfo.getGender());
            setTextValues(deptLayout, department, inpatientInfo.getDept());
            setTextValues(diagnosisLayout, diagnosis, inpatientInfo.getDiagnosis());
            if (isValidString(inpatientInfo.getDate())) {
                String admissionDate = DateUtils.getAdmissionDate(inpatientInfo.getDate());
                setTextValues(dateLayout, date, admissionDate);
            }
            setTextValues(allergyLayout, allergy, inpatientInfo.getAllergy());
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
