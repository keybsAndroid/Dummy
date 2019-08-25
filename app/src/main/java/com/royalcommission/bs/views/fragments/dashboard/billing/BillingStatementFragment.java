package com.royalcommission.bs.views.fragments.dashboard.billing;


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
import com.royalcommission.bs.modules.api.model.CalculateCharge;
import com.royalcommission.bs.modules.api.model.CalculateChargeResponse;
import com.royalcommission.bs.modules.api.model.InpatientInfo;
import com.royalcommission.bs.modules.api.model.InpatientResponse;
import com.royalcommission.bs.modules.utils.SharedPreferenceUtils;
import com.royalcommission.bs.views.fragments.base.BaseFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BillingStatementFragment extends BaseFragment {

    public static InpatientInfo mInpatientInfo;
    public static final String TAG = BillingStatementFragment.class.getSimpleName();
    private LinearLayout nameLayout, bedLayout, paidLayout, amountLayout;
    private TextView appName, name, bedNumber, paid, amount;

    public BillingStatementFragment() {
        // Required empty public constructor
    }

    public static BillingStatementFragment getInstance(InpatientInfo inpatientInfo) {
        mInpatientInfo = inpatientInfo;
        return new BillingStatementFragment();
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_billing_staement, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nameLayout = view.findViewById(R.id.name_layout);
        bedLayout = view.findViewById(R.id.bed_layout);
        paidLayout = view.findViewById(R.id.paid_layout);
        amountLayout = view.findViewById(R.id.amount_layout);

        name = view.findViewById(R.id.name);
        bedNumber = view.findViewById(R.id.bed_number);
        paid = view.findViewById(R.id.paid_status);
        amount = view.findViewById(R.id.amount_to_pay);


        if (mInpatientInfo == null) {
            getInpatientInfo();
        } else {
            setTextValues(nameLayout, name, mInpatientInfo.getPatientName());
            setTextValues(bedLayout, bedNumber, mInpatientInfo.getBedNumber());
            getBillingStatement();
        }
    }

    private void getBillingStatement() {
        if (isValidString(mInpatientInfo.getAppointmentId())) {
            processRequest(AppController.getWebService().calculateCharge(SharedPreferenceUtils.getPatientID(AppController.getInstance()), mInpatientInfo.getAppointmentId()), false, true, null, new RetrofitListener() {
                @Override
                public void onSuccess(Object object) {
                    if (object != null) {
                        CalculateChargeResponse calculateChargeResponse = (CalculateChargeResponse) object;
                        BaseResponse baseResponse = calculateChargeResponse.getBaseResponse();
                        if (baseResponse != null) {
                            if (baseResponse.getResponseCode() == 1) {
                                CalculateCharge calculateCharge = calculateChargeResponse.getCalculateCharge();
                                if (calculateCharge != null) {
                                    parseResponse(calculateCharge);
                                }
                            }
                        }
                    }
                }

                @Override
                public void onError(String error) {

                }
            }, CalculateChargeResponse.class);
        }
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
            mInpatientInfo = inpatientInfo;
            getBillingStatement();
        }
    }

    private void parseResponse(CalculateCharge calculateCharge) {
        if (calculateCharge != null) {
            setTextValues(paidLayout, paid, calculateCharge.isStatus() ? getString(R.string.yes) : getString(R.string.no));
            setTextValues(amountLayout, amount, calculateCharge.getTotalAmount() + getString(R.string.currency_code));
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
