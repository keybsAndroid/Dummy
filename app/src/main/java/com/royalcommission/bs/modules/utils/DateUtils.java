package com.royalcommission.bs.modules.utils;

import com.royalcommission.bs.modules.api.model.NotificationMessages;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Prashant on 9/9/2018.
 */
public class DateUtils {

    private static long ONE_HOUR = 60 * 60 * 1000;
    private static int SECOND = 1;
    private static int MINUTE = 60 * SECOND;

    static SimpleDateFormat getDateFormat(DateFormatType dateFormatType) {
        switch (dateFormatType) {
            case CURRENT_DATE:
                return new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            case CURRENT_TIME:
                return new SimpleDateFormat("hh:mm:a", Locale.US);
            case CALENDAR_ADAPTER_DATE:
                return new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            case CALENDAR_TITLE_FORMAT:
                return new SimpleDateFormat("MMMM yyyy", Locale.US);
            case ALARM_TIME:
                return new SimpleDateFormat("dd/MM/yyyy hh:mm:a", Locale.US);
            case OPERATION_TIME:
                return new SimpleDateFormat("dd/MM/yyyy hh:mm:a", Locale.US);
            case PRESCRIPTION_ALARM_FORMAT:
                return new SimpleDateFormat("dd MMM, hh:mm a", Locale.US);
            case FILE_NAME_CREATION:
                return new SimpleDateFormat("ddMMyyyyhhmmss", Locale.US);
            case HOME_PAGE_TIME_FORMAT:
                return new SimpleDateFormat("hh:mm a EEE", Locale.US);
            case HOME_PAGE_DATE_FORMAT:
                return new SimpleDateFormat("MMM d", Locale.US);
            case APPOINTMENT_DATE:
                return new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            case EXPIRY_DATE:
                return new SimpleDateFormat("yyyyMMdd", Locale.US);
            case DUMMY_APPOINTMENT_TIME:
                return new SimpleDateFormat("yyyy-MM-dd'T'", Locale.US);
            case APPOINTMENT_TIME:
                return new SimpleDateFormat("hh:mm:ss", Locale.US);
            case APPOINTMENT_TIME_SERVER:
                return new SimpleDateFormat("HH:mm:ss", Locale.US);
            case EXERCISE_DATE:
                return new SimpleDateFormat("E, dd MMM yyyy hh:mm a", Locale.US);
            case VACCINATION_DATE:
                return new SimpleDateFormat("E, dd MMM yyyy", Locale.US);
            case APPOINTMENT_HISTORY_FORMAT:
                return new SimpleDateFormat("yyyyMMdd", Locale.US);
            case DATE_MONTH_FORMAT:
                return new SimpleDateFormat("ddMMyyyy", Locale.US);
            case DEFAULT:
                return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
            default:
                return new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        }
    }

    public static String getCurrentDate(String dateString) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.US);
            Date date = inputFormat.parse(dateString);
            return getDateFormat(DateFormatType.CURRENT_DATE).format(date);
        } catch (Exception e) {
            return "";
        }
    }


    public static boolean isTodayNotifications(String dateString) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.US);
            Date date = inputFormat.parse(dateString);
            return getDateFormat(DateFormatType.CURRENT_DATE).format(date).equalsIgnoreCase(getDateFormat(DateFormatType.CURRENT_DATE).format(new Date()));
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isTodayMessages(String dateString) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.US);
            Date messageTime = inputFormat.parse(dateString);
            Calendar messageTimeCalendar = Calendar.getInstance();
            messageTimeCalendar.setTime(messageTime);
            Calendar currentTimeCalendar = Calendar.getInstance();
            currentTimeCalendar.setTime(new Date());
            long diff = new Date().getTime() - messageTime.getTime();
            return diff <= ONE_HOUR;
        } catch (Exception e) {
            return false;
        } /*finally {
            if (DateUtils.isTodayNotifications("2019-08-18T11:30:59")) {
                Timber.tag("Today Notification").i("true");
                if (DateUtils.isTodayMessages("2019-08-18T10:59:59")) {
                    Timber.tag("Today Messages").i("true");
                } else {
                    Timber.tag("Today Messages").i("false");
                }
            } else {
                Timber.tag("Today Notification").i("false");
            }
        }*/
    }

    public static List<NotificationMessages> getNotificationsListFilteredByToday(List<NotificationMessages> notificationMessages) {
        List<NotificationMessages> notificationMessagesFiltered = new ArrayList<>();
        for (NotificationMessages notification : notificationMessages) {
            if (DateUtils.isTodayNotifications(notification.getDate())) {
                if (DateUtils.isTodayMessages(notification.getDate())) {
                    notificationMessagesFiltered.add(notification);
                }
            }
        }
        return notificationMessagesFiltered;
    }


    public static String getCurrentTime(String timeString) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.US);
            Date date = inputFormat.parse(timeString);
            return getDateFormat(DateFormatType.CURRENT_TIME).format(date);
        } catch (Exception e) {
            return "";
        }
    }

    public static String getOperationTime(String timeString) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.US);
            Date date = inputFormat.parse(timeString);
            return getDateFormat(DateFormatType.CURRENT_DATE).format(date);
        } catch (Exception e) {
            return "";
        }
    }

    static String getCurrentDate() {
        try {
            return getDateFormat(DateFormatType.CURRENT_DATE).format(new Date().getTime());
        } catch (Exception e) {
            return "";
        }
    }

    public static String getDateTime(String dateTime, boolean isShowTime) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.US);
            Date date = inputFormat.parse(dateTime);
            if (isShowTime)
                return getDateFormat(DateFormatType.NORMAL_DATE_TIME).format(date);
            else
                return getDateFormat(DateFormatType.CURRENT_DATE).format(date);
        } catch (Exception e) {
            return "";
        }
    }

    public static Date getDate(String dateTime) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.US);
            return inputFormat.parse(dateTime);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getAdmissionDate(String dateTime) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.US);
            Date date = inputFormat.parse(dateTime);
            return new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getAppDate(String dateTime) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.US);
            Date date = inputFormat.parse(dateTime);
            return new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getAppTime(String dateTime) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.US);
            Date date = inputFormat.parse(dateTime);
            return new SimpleDateFormat("HH:mm a", Locale.US).format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getPrescriptionDate(String dateTime) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.US);
            Date date = inputFormat.parse(dateTime);
            return new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getAppointmentDate(String dateStr, boolean isUI) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.US);
            Calendar calendar = Calendar.getInstance();
            Date date = inputFormat.parse(dateStr);
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            if (isUI)
                return getDateFormat(DateFormatType.APPOINTMENT_DATE).format(date);
            else
                return getDateFormat(DateFormatType.DUMMY_APPOINTMENT_TIME).format(calendar.getTime()) + "00:00:00";  //yyyy-MM-dd'T'00:00:00
        } catch (Exception e) {
            return "";
        }
    }

    public static String getAppointmentTime(String timeStr, boolean isUI) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.US);
            Date date = inputFormat.parse(timeStr);
            if (isUI)
                return getDateFormat(DateFormatType.APPOINTMENT_TIME).format(date);
            else
                return getDateFormat(DateFormatType.DEFAULT).format(date); //yyyy-MM-dd'T'HH:mm:ss

        } catch (Exception e) {
            return "";
        }
    }

    private static int calculateAge(Date birthDate) {
        long timeBetween = new Date().getTime() - birthDate.getTime();
        double yearsBetween = timeBetween / 3.15576e+10;
        return (int) Math.floor(yearsBetween);
    }

    private static SimpleDateFormat getFileNameDateFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy_HHmmss", Locale.US);
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();
        dateFormat.setTimeZone(tz);
        return dateFormat;
    }
}
