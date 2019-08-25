package com.royalcommission.bs.modules.utils;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.royalcommission.bs.R;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Prashant on 7/30/2018.
 */
public class DateTimePickerUtil {

    private static Calendar mCalendar;
    private Context mContext;
    private DateTimeSetListener mDateTimeListener;
    private int monthLimit;
    private boolean isMonthLimit;
    private long mLastClickTime;

    public DateTimePickerUtil(Context mContext, DateTimeSetListener mDateTimeListener) {
        this.mContext = mContext;
        this.mDateTimeListener = mDateTimeListener;
    }

    public DateTimePickerUtil(Context mContext, DateTimeSetListener mDateTimeListener, int monthLimit, boolean isMonthLimit) {
        this.mContext = mContext;
        this.mDateTimeListener = mDateTimeListener;
        this.monthLimit = monthLimit;
        this.isMonthLimit = isMonthLimit;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            if (view.isShown()) {
                mCalendar.set(year, monthOfYear, dayOfMonth,
                        mCalendar.get(Calendar.HOUR_OF_DAY),
                        mCalendar.get(Calendar.MINUTE));
                mDateTimeListener.setDate(dayOfMonth, monthOfYear, year);
            }
        }
    };

    public void showDatePicker(String title, Calendar calendar, boolean isSetMinDate, boolean isDateOfBirth) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return;
        }

        mLastClickTime = SystemClock.elapsedRealtime();

        CommonUtils.setCalendarLanguageToByDefaultEnglish();

        if (calendar != null) {
            mCalendar = calendar;
        } else {
            mCalendar = Calendar.getInstance(Locale.ENGLISH);
        }

        int mYear = mCalendar.get(Calendar.YEAR);
        int mMonth = mCalendar.get(Calendar.MONTH);
        int mDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, R.style.DialogTheme, mDateSetListener, mYear, mMonth, mDay);
        if (isMonthLimit) {
            Calendar mCalendarLimit = Calendar.getInstance(Locale.ENGLISH);

            if (monthLimit == 1) {
                mCalendarLimit.set(Calendar.DAY_OF_MONTH, 1);
                mCalendarLimit.add(Calendar.YEAR, -1);
            } else {
                mCalendarLimit.add(Calendar.MONTH, -monthLimit);
                mCalendarLimit.set(Calendar.DAY_OF_MONTH, 1);
            }
            datePickerDialog.getDatePicker().setMinDate(mCalendarLimit.getTimeInMillis());
            datePickerDialog.getDatePicker().setMaxDate(mCalendar.getTimeInMillis());
        } else {
            if (isSetMinDate) {
                datePickerDialog.getDatePicker().setMinDate(mCalendar.getTimeInMillis());
            } else if (isDateOfBirth) {
                Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                cal.set(Calendar.DAY_OF_MONTH, day);
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
            } else {
                datePickerDialog.getDatePicker().setMaxDate(mCalendar.getTimeInMillis());
            }
        }
        if (CommonUtils.isValidString(title)) {
            View customView = LayoutInflater.from(mContext).inflate(R.layout.custom_calendar_title, null, false);
            if (customView != null) {
                TextView customDialogTitle = customView.findViewById(R.id.title);
                if (customDialogTitle != null)
                    customDialogTitle.setText(title);
                datePickerDialog.setCustomTitle(customView);
                //datePickerDialog.setTitle(title);
            }
        }
        datePickerDialog.setCancelable(false);
        datePickerDialog.setCanceledOnTouchOutside(false);
        datePickerDialog.show();
        datePickerDialog.setOnKeyListener((dialog, keyCode, event) -> keyCode == KeyEvent.KEYCODE_BACK);
    }

    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mCalendar.set(mCalendar.get(Calendar.YEAR),
                    mCalendar.get(Calendar.MONTH),
                    mCalendar.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
            mDateTimeListener.setTime(hourOfDay, minute, mCalendar);

        }
    };

    public void showTimePicker() {
        mCalendar = Calendar.getInstance(Locale.ENGLISH);
        int mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int mMinute = mCalendar.get(Calendar.MINUTE);
        CommonUtils.setCalendarLanguageToByDefaultEnglish();
        TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, R.style.DialogTheme, mTimeSetListener, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public interface DateTimeSetListener {
        void setDate(int day, int month, int year);

        void setTime(int hour, int minute, Calendar calendar);
    }

}
