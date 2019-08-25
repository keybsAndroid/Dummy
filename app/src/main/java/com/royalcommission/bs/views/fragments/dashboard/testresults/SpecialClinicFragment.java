package com.royalcommission.bs.views.fragments.testresults;


import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.royalcommission.bs.R;
import com.royalcommission.bs.app.AppController;
import com.royalcommission.bs.modules.api.listener.RetrofitListener;
import com.royalcommission.bs.modules.api.model.BaseResponse;
import com.royalcommission.bs.modules.api.model.SpecialClinicDetail;
import com.royalcommission.bs.modules.api.model.SpecialClinicResponse;
import com.royalcommission.bs.modules.api.parser.RetrofitResponseParser;
import com.royalcommission.bs.modules.utils.CommonUtils;
import com.royalcommission.bs.modules.utils.DateTimePickerUtil;
import com.royalcommission.bs.modules.utils.SharedPreferenceUtils;
import com.royalcommission.bs.views.adapters.SpecialClinicExpandableAdapter;
import com.royalcommission.bs.views.fragments.base.BaseFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SpecialClinicFragment extends BaseFragment {


    private static String mAppointmentId;
    private List<SpecialClinicDetail> specialClinicDetailList = new ArrayList<>();
    private SpecialClinicExpandableAdapter specialClinicExpandableAdapter;
    private TextView fromDateTextView, toDateTextView;
    private ImageView fromDatePicker, toDatePicker;
    private ImageView search;
    private RelativeLayout searchLayout;
    private Button threeMonthButtonIP, sixMonthButtonIP, nineMonthButtonIP, oneYearButtonIP;
    private int[] allButtonID = {R.id.three_month, R.id.six_month, R.id.nine_month, R.id.one_year};
    private boolean isFromIP;
    private String startDate;
    private String endDate;
    private long mLastClickTime;
    private long mFromLastClickTime;
    private long mToLastClickTime;
    private int selectedMonthLimit;
    private static boolean mIsCalendar;

    public SpecialClinicFragment() {
        // Required empty public constructor
    }

    public static SpecialClinicFragment getInstance(String appointmentID, boolean isCalendar) {
        mAppointmentId = appointmentID;
        mIsCalendar = isCalendar;
        return new SpecialClinicFragment();
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_special_clinic, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        if (view != null) {

            View listDurationLayout = view.findViewById(R.id.list_duration);
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
                    if (isFromIP) {
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
                        getSpecialClinicDetails(SharedPreferenceUtils.getPatientID(AppController.getInstance()), SharedPreferenceUtils.getHospitalID(AppController.getInstance()), startDate, endDate);
                    } else {
                        showToastMessage(getString(R.string.select_a_period));
                    }

                } else if (viewIP.getId() == fromDatePicker.getId() || viewIP.getId() == fromDateTextView.getId()) {
                    isFromIP = true;
                    if (SystemClock.elapsedRealtime() - mFromLastClickTime < 2000)
                        return;
                    mFromLastClickTime = SystemClock.elapsedRealtime();
                    openDatePicker(dateTimeSetListenerIP, selectedMonthLimit);
                } else if (viewIP.getId() == toDatePicker.getId() || viewIP.getId() == toDateTextView.getId()) {
                    isFromIP = false;
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
            RecyclerView recyclerView = view.findViewById(R.id.expandable_list_view);
            setupSpecialClinicDetailsAdapter(recyclerView);
            listDurationLayout.setVisibility(View.VISIBLE);
        }
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


    private void setupSpecialClinicDetailsAdapter(RecyclerView recyclerView) {
        if (recyclerView != null) {
            specialClinicExpandableAdapter = new SpecialClinicExpandableAdapter(getActivity(), specialClinicDetailList);
            RecyclerView.LayoutManager mLaboratoryAdapterLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLaboratoryAdapterLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(specialClinicExpandableAdapter);
        }
    }

    private void getSpecialClinicDetails(String patientID, String hospitalID, String startDate, String endDate) {


        processRequest(AppController.getWebService().getSpecialClinicDetails(patientID, hospitalID, startDate, endDate), false, true, null, new RetrofitListener() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    SpecialClinicResponse specialClinicResponse = RetrofitResponseParser.convertInstanceOfObject(object, SpecialClinicResponse.class);
                    if (specialClinicResponse != null) {
                        BaseResponse baseResponse = specialClinicResponse.getBaseResponse();
                        if (baseResponse != null) {
                            if (baseResponse.getResponseCode() == 1) {
                                List<SpecialClinicDetail> clinicDetails = specialClinicResponse.getSpecialClinicDetails();
                                if (clinicDetails != null) {
                                    specialClinicDetailList.clear();
                                    specialClinicDetailList.addAll(clinicDetails);
                                    if (specialClinicExpandableAdapter != null)
                                        specialClinicExpandableAdapter.notifyDataSetChanged();
                                    if (specialClinicDetailList.isEmpty())
                                        showMessageAlert(null, getString(R.string.no_results));
                                } else {
                                    showServerError(null);
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

            }
        }, SpecialClinicResponse.class);
    }

}
