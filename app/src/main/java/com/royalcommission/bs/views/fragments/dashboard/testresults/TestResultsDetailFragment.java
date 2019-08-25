package com.royalcommission.bs.views.fragments.testresults;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.royalcommission.bs.R;
import com.royalcommission.bs.app.AppController;
import com.royalcommission.bs.modules.api.listener.RetrofitListener;
import com.royalcommission.bs.modules.api.model.BaseResponse;
import com.royalcommission.bs.modules.api.model.TestResult;
import com.royalcommission.bs.modules.api.model.TestResultsByIDResponse;
import com.royalcommission.bs.modules.api.parser.RetrofitResponseParser;
import com.royalcommission.bs.modules.utils.CommonUtils;
import com.royalcommission.bs.modules.utils.DateTimePickerUtil;
import com.royalcommission.bs.modules.utils.SharedPreferenceUtils;
import com.royalcommission.bs.views.adapters.TestResultListAdapter;
import com.royalcommission.bs.views.fragments.base.BaseFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestResultsDetailFragment extends BaseFragment {

    private List<TestResult> testResultList = new ArrayList<>();
    private TestResultListAdapter testResultListAdapter;
    private String startDate;
    private String endDate;
    private long mLastClickTime;
    private TextView fromDateTextView, toDateTextView;
    private ImageView search;
    private Button threeMonthButton, sixMonthButton, nineMonthButton, oneYearButton;
    private int[] allButtonID = {R.id.three_month, R.id.six_month, R.id.nine_month, R.id.one_year};
    private ImageView fromDatePicker, toDatePicker;
    private RelativeLayout searchLayout;
    private boolean isFrom;
    private static boolean isLab;
    private int selectedMonthLimit;
    private static String orderID;
    private static String mStartDate, mEndDate;

    public TestResultsDetailFragment() {
        // Required empty public constructor
    }

    public static TestResultsDetailFragment newInstance(boolean isLaboratory, String orderId, String startDate, String endDate) {
        isLab = isLaboratory;
        orderID = orderId;
        mStartDate = startDate;
        mEndDate = endDate;
        return new TestResultsDetailFragment();
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_test_results_detail, container, false);
        View listDurationLayout = view.findViewById(R.id.list_duration);
        listDurationLayout.setVisibility(View.GONE);
        RecyclerView recyclerView = view.findViewById(R.id.test_results);
        fromDateTextView = listDurationLayout.findViewById(R.id.from_date);
        fromDatePicker = listDurationLayout.findViewById(R.id.from);
        toDateTextView = listDurationLayout.findViewById(R.id.to_date);
        toDatePicker = listDurationLayout.findViewById(R.id.to);
        search = listDurationLayout.findViewById(R.id.search);
        searchLayout = listDurationLayout.findViewById(R.id.search_layout);
        setupAdapter(recyclerView);
        startDate = mStartDate;
        endDate = mEndDate;

        DateTimePickerUtil.DateTimeSetListener dateTimeSetListenerIP = new DateTimePickerUtil.DateTimeSetListener() {
            @Override
            public void setDate(int day, int month, int year) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH, day);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.YEAR, year);
                String dateFormat = CommonUtils.getDateFormat(calendar);
                if (isFrom) {
                    startDate = CommonUtils.getAppointmentHistoryFormat(calendar);
                    if (fromDateTextView != null)
                        fromDateTextView.setText(dateFormat);
                } else {
                    endDate = CommonUtils.getAppointmentHistoryFormat(calendar);
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
                setEnableAndDisable(viewIP.getId());
                startAnimation(viewIP);
            } else if (viewIP.getId() == R.id.six_month) {
                setEnableAndDisable(viewIP.getId());
                startAnimation(viewIP);
            } else if (viewIP.getId() == R.id.nine_month) {
                setEnableAndDisable(viewIP.getId());
                startAnimation(viewIP);
            } else if (viewIP.getId() == R.id.one_year) {
                setEnableAndDisable(viewIP.getId());
                startAnimation(viewIP);
            } else if (viewIP.getId() == search.getId() || viewIP.getId() == searchLayout.getId()) {

            } else if (viewIP.getId() == fromDatePicker.getId() || viewIP.getId() == fromDateTextView.getId()) {
                isFrom = true;
                openDatePicker(dateTimeSetListenerIP, selectedMonthLimit);
            } else if (viewIP.getId() == toDatePicker.getId() || viewIP.getId() == toDateTextView.getId()) {
                isFrom = false;
                openDatePicker(dateTimeSetListenerIP, selectedMonthLimit);
            }
        };

        search.setOnClickListener(onClickListener);
        searchLayout.setOnClickListener(onClickListener);
        threeMonthButton = listDurationLayout.findViewById(R.id.three_month);
        threeMonthButton.setOnClickListener(onClickListener);
        sixMonthButton = listDurationLayout.findViewById(R.id.six_month);
        sixMonthButton.setOnClickListener(onClickListener);
        nineMonthButton = listDurationLayout.findViewById(R.id.nine_month);
        nineMonthButton.setOnClickListener(onClickListener);
        oneYearButton = listDurationLayout.findViewById(R.id.one_year);
        oneYearButton.setOnClickListener(onClickListener);
        setEnableAndDisable(threeMonthButton.getId());
        fromDateTextView.setOnClickListener(onClickListener);
        fromDatePicker.setOnClickListener(onClickListener);
        toDateTextView.setOnClickListener(onClickListener);
        toDatePicker.setOnClickListener(onClickListener);
        getTestResultsListByOrderID();
        return view;
    }


    private void setupAdapter(RecyclerView recyclerView) {
        if (recyclerView != null) {
            testResultListAdapter = new TestResultListAdapter(getActivity(), testResultList);
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(testResultListAdapter);
            recyclerView.setNestedScrollingEnabled(false);
        }
    }

    private void openDatePicker(DateTimePickerUtil.DateTimeSetListener listener, int selectedMonthLimit) {
        if (getActivity() != null) {
            DateTimePickerUtil dateTimePickerUtil = new DateTimePickerUtil(getActivity(), listener, selectedMonthLimit, false);
            dateTimePickerUtil.showDatePicker(null, null, false, false);
        }
    }

    private void setEnableAndDisable(int id) {
        if (getActivity() != null) {
            for (int buttonID : allButtonID) {
                if (buttonID == id) {
                    if (threeMonthButton.getId() == buttonID) {
                        threeMonthButton.setSelected(true);
                        threeMonthButton.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.list_duration_button_selected_bg));
                        selectedMonthLimit = 3;
                        setCalendarSelection(fromDateTextView, toDateTextView, selectedMonthLimit, false);
                    } else if (sixMonthButton.getId() == buttonID) {
                        sixMonthButton.setSelected(true);
                        sixMonthButton.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.list_duration_button_selected_bg));
                        selectedMonthLimit = 6;
                        setCalendarSelection(fromDateTextView, toDateTextView, selectedMonthLimit, false);
                    } else if (nineMonthButton.getId() == buttonID) {
                        nineMonthButton.setSelected(true);
                        nineMonthButton.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.list_duration_button_selected_bg));
                        selectedMonthLimit = 9;
                        setCalendarSelection(fromDateTextView, toDateTextView, selectedMonthLimit, false);
                    } else if (oneYearButton.getId() == buttonID) {
                        oneYearButton.setSelected(true);
                        oneYearButton.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.list_duration_button_selected_bg));
                        selectedMonthLimit = 1;
                        setCalendarSelection(fromDateTextView, toDateTextView, selectedMonthLimit, true);
                    }
                } else {
                    if (threeMonthButton.getId() == buttonID) {
                        threeMonthButton.setSelected(false);
                        threeMonthButton.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.list_duration_button_bg));
                    } else if (sixMonthButton.getId() == buttonID) {
                        sixMonthButton.setSelected(false);
                        sixMonthButton.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.list_duration_button_bg));
                    } else if (nineMonthButton.getId() == buttonID) {
                        nineMonthButton.setSelected(false);
                        nineMonthButton.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.list_duration_button_bg));
                    } else if (oneYearButton.getId() == buttonID) {
                        oneYearButton.setSelected(false);
                        oneYearButton.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.list_duration_button_bg));
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
        startDate = CommonUtils.getAppointmentHistoryFormat(monthLimit);
        endDate = CommonUtils.getAppointmentHistoryFormat(Calendar.getInstance());
    }


    private void getTestResultsListByOrderID() {
        processRequest(AppController.getWebService().getTestResultsByID(orderID, SharedPreferenceUtils.getHospitalID(getContext())), false, true, null, new RetrofitListener() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    TestResultsByIDResponse testResultsResponse = RetrofitResponseParser.convertInstanceOfObject(object, TestResultsByIDResponse.class);
                    if (testResultsResponse != null) {
                        BaseResponse baseResponse = testResultsResponse.getBaseResponse();
                        if (baseResponse != null) {
                            if (baseResponse.getResponseCode() == 1) {
                                List<TestResult> resultList = testResultsResponse.getTestResultList();
                                if (resultList != null) {
                                    testResultList.clear();
                                    testResultList.addAll(resultList);
                                    if (testResultListAdapter != null)
                                        testResultListAdapter.notifyDataSetChanged();
                                    if (testResultList.isEmpty())
                                        showMessageAlert(null, getString(R.string.no_results));
                                }
                            } else {
                                showServerError(null);
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(String error) {
                showServerError(error);
            }
        }, TestResultsByIDResponse.class);
    }


}
