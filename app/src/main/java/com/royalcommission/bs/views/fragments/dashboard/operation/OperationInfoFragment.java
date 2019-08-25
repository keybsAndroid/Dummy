package com.royalcommission.bs.views.fragments.dashboard.operation;


import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.royalcommission.bs.R;
import com.royalcommission.bs.app.AppController;
import com.royalcommission.bs.modules.api.listener.RetrofitListener;
import com.royalcommission.bs.modules.api.model.BaseResponse;
import com.royalcommission.bs.modules.api.model.OperationInfo;
import com.royalcommission.bs.modules.api.model.OperationInfoResponse;
import com.royalcommission.bs.modules.utils.CommonUtils;
import com.royalcommission.bs.modules.utils.DateTimePickerUtil;
import com.royalcommission.bs.modules.utils.SharedPreferenceUtils;
import com.royalcommission.bs.views.adapters.OperationInfoAdapter;
import com.royalcommission.bs.views.fragments.base.BaseFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OperationInfoFragment extends BaseFragment {
    private List<OperationInfo> operationInfoList = new ArrayList<>();
    public static final String TAG = OperationInfoFragment.class.getSimpleName();
    private RecyclerView operationInfoRV;
    private TextView fromDateTextView, toDateTextView;
    private ImageView fromDatePicker, toDatePicker;
    private ImageView search;
    private RelativeLayout searchLayout;
    private Button threeMonthButtonIP, sixMonthButtonIP, nineMonthButtonIP, oneYearButtonIP;
    private int[] allButtonID = {R.id.three_month, R.id.six_month, R.id.nine_month, R.id.one_year};
    private boolean isFrom;
    private String startDate;
    private String endDate;
    private long mLastClickTime;
    private long mFromLastClickTime;
    private long mToLastClickTime;
    private int selectedMonthLimit;
    private OperationInfoAdapter operationInfoAdapter;

    public OperationInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_operation_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        operationInfoRV = view.findViewById(R.id.operation_info);
        View listDurationLayout = view.findViewById(R.id.list_duration);
        listDurationLayout.findViewById(R.id.hint).setVisibility(View.GONE);
        fromDateTextView = listDurationLayout.findViewById(R.id.from_date);
        fromDatePicker = listDurationLayout.findViewById(R.id.from);
        toDateTextView = listDurationLayout.findViewById(R.id.to_date);
        toDatePicker = listDurationLayout.findViewById(R.id.to);
        search = listDurationLayout.findViewById(R.id.search);
        searchLayout = listDurationLayout.findViewById(R.id.search_layout);
        DateTimePickerUtil.DateTimeSetListener dateTimeSetListenerIP = new DateTimePickerUtil.DateTimeSetListener() {
            @Override
            public void setDate(int day, int month, int year) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH, day);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.YEAR, year);
                String dateFormat = CommonUtils.getDateFormat(calendar);
                if (isFrom) {
                    startDate = CommonUtils.getDateMonthFormat(calendar);
                    if (fromDateTextView != null)
                        fromDateTextView.setText(dateFormat);
                } else {
                    endDate = CommonUtils.getDateMonthFormat(calendar);
                    if (toDateTextView != null)
                        toDateTextView.setText(dateFormat);
                }
            }

            @Override
            public void setTime(int hour, int minute, Calendar calendar) {

            }
        };

        View.OnClickListener onClickListener = viewIP -> {
            if (viewIP.getId() == R.id.three_month) {
                setEnableAndDisableIP(viewIP.getId());
                startAnimation(viewIP);
            } else if (viewIP.getId() == R.id.six_month) {
                setEnableAndDisableIP(viewIP.getId());
                startAnimation(viewIP);
            } else if (viewIP.getId() == R.id.nine_month) {
                setEnableAndDisableIP(viewIP.getId());
                startAnimation(viewIP);
            } else if (viewIP.getId() == R.id.one_year) {
                setEnableAndDisableIP(viewIP.getId());
                startAnimation(viewIP);
            } else if (viewIP.getId() == search.getId() || viewIP.getId() == searchLayout.getId()) {
                if (isValidString(startDate)
                        && isValidString(endDate)) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                        return;
                    mLastClickTime = SystemClock.elapsedRealtime();
                    getOperationInfo(SharedPreferenceUtils.getPatientID(AppController.getInstance()), SharedPreferenceUtils.getHospitalID(AppController.getInstance()), startDate, endDate);
                } else {
                    showToastMessage(getString(R.string.select_a_period));
                }
            } else if (viewIP.getId() == fromDatePicker.getId() || viewIP.getId() == fromDateTextView.getId()) {
                isFrom = true;
                if (SystemClock.elapsedRealtime() - mFromLastClickTime < 2000)
                    return;
                mFromLastClickTime = SystemClock.elapsedRealtime();
                openDatePicker(dateTimeSetListenerIP, selectedMonthLimit);
            } else if (viewIP.getId() == toDatePicker.getId() || viewIP.getId() == toDateTextView.getId()) {
                isFrom = false;
                if (SystemClock.elapsedRealtime() - mToLastClickTime < 2000)
                    return;
                mToLastClickTime = SystemClock.elapsedRealtime();
                openDatePicker(dateTimeSetListenerIP, selectedMonthLimit);
            }
        };

        search.setOnClickListener(onClickListener);
        searchLayout.setOnClickListener(onClickListener);
        threeMonthButtonIP = listDurationLayout.findViewById(R.id.three_month);
        threeMonthButtonIP.setOnClickListener(onClickListener);
        sixMonthButtonIP = listDurationLayout.findViewById(R.id.six_month);
        sixMonthButtonIP.setOnClickListener(onClickListener);
        nineMonthButtonIP = listDurationLayout.findViewById(R.id.nine_month);
        nineMonthButtonIP.setOnClickListener(onClickListener);
        oneYearButtonIP = listDurationLayout.findViewById(R.id.one_year);
        oneYearButtonIP.setOnClickListener(onClickListener);
        setEnableAndDisableIP(threeMonthButtonIP.getId());
        fromDateTextView.setOnClickListener(onClickListener);
        fromDatePicker.setOnClickListener(onClickListener);
        toDateTextView.setOnClickListener(onClickListener);
        toDatePicker.setOnClickListener(onClickListener);
        setAdapter(operationInfoRV);
    }

    private void setAdapter(RecyclerView recyclerView) {
        operationInfoAdapter = new OperationInfoAdapter(getActivity(), operationInfoList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(operationInfoAdapter);
    }

    private void openDatePicker(DateTimePickerUtil.DateTimeSetListener listener, int selectedMonthLimit) {
        if (getActivity() != null) {
            DateTimePickerUtil dateTimePickerUtil = new DateTimePickerUtil(getActivity(), listener, selectedMonthLimit, false);
            dateTimePickerUtil.showDatePicker(null, null, false, false);
        }
    }

    private void setEnableAndDisableIP(int id) {
        if (getActivity() != null) {
            for (int buttonID : allButtonID) {
                if (buttonID == id) {
                    if (threeMonthButtonIP.getId() == buttonID) {
                        threeMonthButtonIP.setSelected(true);
                        threeMonthButtonIP.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.list_duration_button_selected_bg));
                        selectedMonthLimit = 3;
                        setCalendarSelection(fromDateTextView, toDateTextView, selectedMonthLimit, false);
                    } else if (sixMonthButtonIP.getId() == buttonID) {
                        sixMonthButtonIP.setSelected(true);
                        sixMonthButtonIP.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.list_duration_button_selected_bg));
                        selectedMonthLimit = 6;
                        setCalendarSelection(fromDateTextView, toDateTextView, selectedMonthLimit, false);
                    } else if (nineMonthButtonIP.getId() == buttonID) {
                        nineMonthButtonIP.setSelected(true);
                        nineMonthButtonIP.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.list_duration_button_selected_bg));
                        selectedMonthLimit = 9;
                        setCalendarSelection(fromDateTextView, toDateTextView, selectedMonthLimit, false);
                    } else if (oneYearButtonIP.getId() == buttonID) {
                        oneYearButtonIP.setSelected(true);
                        oneYearButtonIP.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.list_duration_button_selected_bg));
                        selectedMonthLimit = 1;
                        setCalendarSelection(fromDateTextView, toDateTextView, selectedMonthLimit, true);
                    }
                } else {
                    if (threeMonthButtonIP.getId() == buttonID) {
                        threeMonthButtonIP.setSelected(false);
                        threeMonthButtonIP.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.list_duration_button_bg));
                    } else if (sixMonthButtonIP.getId() == buttonID) {
                        sixMonthButtonIP.setSelected(false);
                        sixMonthButtonIP.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.list_duration_button_bg));
                    } else if (nineMonthButtonIP.getId() == buttonID) {
                        nineMonthButtonIP.setSelected(false);
                        nineMonthButtonIP.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.list_duration_button_bg));
                    } else if (oneYearButtonIP.getId() == buttonID) {
                        oneYearButtonIP.setSelected(false);
                        oneYearButtonIP.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.list_duration_button_bg));
                    }
                }
            }
        }
    }

    private void setCalendarSelection(TextView fromDateTextView, TextView toDateTextView, int selectedMonthLimit, boolean isYear) {
        Calendar monthLimit = Calendar.getInstance();
        if (isYear) {
            monthLimit.set(monthLimit.get(Calendar.YEAR) - 1, monthLimit.get(Calendar.MONTH),
                    monthLimit.get(Calendar.DAY_OF_MONTH));
        } else {
            monthLimit.set(monthLimit.get(Calendar.YEAR), monthLimit.get(Calendar.MONTH) - selectedMonthLimit,
                    monthLimit.get(Calendar.DAY_OF_MONTH));
        }
        fromDateTextView.setText(CommonUtils.getDateFormat(monthLimit));
        toDateTextView.setText(CommonUtils.getDateFormat(Calendar.getInstance()));
        startDate = CommonUtils.getDateMonthFormat(monthLimit);
        endDate = CommonUtils.getDateMonthFormat(Calendar.getInstance());
    }

    private void getOperationInfo(String patientId, String hospitalId, String startDate, String endDate) {
        if (isValidString(patientId) && isValidString(hospitalId) && isValidString(startDate) && isValidString(endDate)) {
            //PatientID=815520&HospitalID=00001&FromDate=01012017&Todate=31122017
            patientId = "815520";
            hospitalId = "00001";
            startDate = "01012017";
            endDate = "31122017";
            processRequest(AppController.getWebService().getOperationInfo(patientId, SharedPreferenceUtils.getHospitalID(getActivity()), startDate, endDate), false, true, null, new RetrofitListener() {
                @Override
                public void onSuccess(Object object) {
                    if (object != null) {
                        OperationInfoResponse operationInfoResponse = (OperationInfoResponse) object;
                        BaseResponse baseResponse = operationInfoResponse.getBaseResponse();
                        if (baseResponse != null) {
                            if (baseResponse.getResponseCode() == 1) {
                                operationInfoList.clear();
                                List<OperationInfo> operationInfos = operationInfoResponse.getOperationInfoList();
                                if (operationInfos != null && !operationInfos.isEmpty()) {
                                    operationInfoList.addAll(operationInfos);
                                    operationInfoAdapter.notifyDataSetChanged();
                                } else
                                    showMessageAlert(null, getString(R.string.no_results));
                            } else {
                                showMessageAlert(null, getString(R.string.no_results));
                            }
                        }

                    }
                }

                @Override
                public void onError(String error) {

                }
            }, OperationInfoResponse.class);
        }

    }

}
