package com.royalcommission.bs.modules.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.royalcommission.bs.app.AppController;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import timber.log.Timber;

/**
 * Created by Prashant on 7/8/2018.
 */
public class CommonUtils {

    private static SharedPreferences getPreferences(Context context, String preferenceRegion) {
        return context.getSharedPreferences(preferenceRegion, Context.MODE_PRIVATE);
    }

    public static String getPreferenceString(Context context, String preferenceRegion, String preferenceName) {
        try {
            final SharedPreferences prefs = getPreferences(context, preferenceRegion);
            return prefs.getString(preferenceName, "");
        } catch (Exception e) {
            Timber.tag("PreferenceHelperError").e("getPref: %s", String.valueOf(e));
        }
        return null;
    }

    public static void setPreferenceString(Context context, String preferenceRegion, String preferenceName, String preferenceValue) {
        final SharedPreferences prefs = getPreferences(context, preferenceRegion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String parsingInteger(String convertToInteger) {
        int intValue;
        try {
            intValue = Integer.parseInt(convertToInteger);
        } catch (NumberFormatException ex) {
            intValue = 0;
        }
        return String.valueOf(intValue);
    }

    public static boolean isValidString(String stringToBeValid) {
        return stringToBeValid != null && !stringToBeValid.equalsIgnoreCase("");
    }

    public static String parsingLong(String convertToLong) {
        long longValue;
        try {
            longValue = Long.parseLong(convertToLong);
        } catch (NumberFormatException ex) {
            longValue = 0;
        }
        return String.valueOf(longValue);
    }

    public static void roundOff(double roundOffValue) {
        DecimalFormat df = new DecimalFormat("#.##");
        double step = 0.05;
        double finalValue = step * (Math.ceil(roundOffValue / step));
        double result = Double.valueOf(df.format(finalValue));
        Timber.d("result: %s", result);
    }

    public static String getCalendarDate(Calendar calendar) {
        SimpleDateFormat formatter = DateUtils.getDateFormat(DateFormatType.CURRENT_DATE);
        String formattedTime = formatter.format(calendar.getTime());
        Calendar currentCalendar = Calendar.getInstance();
        String formattedCurrentTime = formatter.format(currentCalendar.getTime());
        return formattedCurrentTime + " - " + formattedTime;
    }

    public static String getCalendarDateBefore(Calendar calendar) {
        CommonUtils.setCalendarLanguageToByDefaultEnglish();
        String formattedTime = DateUtils.getDateFormat(DateFormatType.CURRENT_DATE).format(calendar.getTime());
        Calendar currentCalendar = Calendar.getInstance();
        String formattedCurrentTime = DateUtils.getDateFormat(DateFormatType.CURRENT_DATE).format(currentCalendar.getTime());
        return formattedTime + " - " + formattedCurrentTime;
    }

    public static String getDateFormat(Calendar calendar) {
        CommonUtils.setCalendarLanguageToByDefaultEnglish();
        return DateUtils.getDateFormat(DateFormatType.CURRENT_DATE).format(calendar.getTime());
    }

    public static String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        CommonUtils.setCalendarLanguageToByDefaultEnglish();
        return DateUtils.getDateFormat(DateFormatType.CURRENT_TIME).format(calendar.getTime());
    }

    public static String getHomePageTime() {
        Calendar calendar = Calendar.getInstance();
        CommonUtils.setCalendarLanguageToByDefaultEnglish();
        return DateUtils.getDateFormat(DateFormatType.HOME_PAGE_TIME_FORMAT).format(calendar.getTime());
    }

    public static String getHomePageDate() {
        Calendar calendar = Calendar.getInstance();
        CommonUtils.setCalendarLanguageToByDefaultEnglish();
        return DateUtils.getDateFormat(DateFormatType.HOME_PAGE_DATE_FORMAT).format(calendar.getTime());
    }

    public static String getAlarmTime(Calendar calendar) {
        CommonUtils.setCalendarLanguageToByDefaultEnglish();
        return DateUtils.getDateFormat(DateFormatType.ALARM_TIME).format(calendar.getTime());
    }

    public static String getCalendarTitleFormat(Calendar calendar) {
        return DateUtils.getDateFormat(DateFormatType.CALENDAR_TITLE_FORMAT).format(calendar.getTime());
    }

    public static String getFileNameCreationFormat(long time) {
        if (time < 1000000000000L) {
            time *= 1000;
        }
        return DateUtils.getDateFormat(DateFormatType.FILE_NAME_CREATION).format(time);
    }

    public static int getDaysDifference(Calendar fromCalendar, Calendar toCalendar) {
        return daysBetween(fromCalendar.getTime(), toCalendar.getTime());
    }

    private static int daysBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    public static void showToast(Context context, String message) {
        if (context != null)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9+._%\\-+]{1,256}" +
                "@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+";
        return email.matches(emailPattern);
    }

    public static String setEnglishTextFormat(String stringToBeInEnglish) {
        return String.format(Locale.US, null, stringToBeInEnglish);
    }

    public void setGridViewHeightBasedOnChildren(GridView gridView, int columns) {
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int items = listAdapter.getCount();
        int rows = 0;

        View listItem = listAdapter.getView(0, null, gridView);
        listItem.measure(0, 0);
        totalHeight = listItem.getMeasuredHeight();

        float x = 1;
        if (items > columns) {
            x = items / columns;
            rows = (int) (x + 1);
            totalHeight *= rows;
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        gridView.setLayoutParams(params);

    }

    public static String getAppointmentHistoryFormat(Calendar calendar) {
        CommonUtils.setCalendarLanguageToByDefaultEnglish();
        return DateUtils.getDateFormat(DateFormatType.APPOINTMENT_HISTORY_FORMAT).format(calendar.getTime());
    }

    public static String getDateMonthFormat(Calendar calendar) {
        CommonUtils.setCalendarLanguageToByDefaultEnglish();
        return DateUtils.getDateFormat(DateFormatType.DATE_MONTH_FORMAT).format(calendar.getTime());
    }

    public static int dpToPx(Context context, int dp) {
        Resources r = context.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public static void setCalendarLanguageToByDefaultEnglish() {
        Locale locale = new Locale(AppController.LANGUAGE_ENGLISH);
        Locale.setDefault(locale);
    }

    public static int generateRandomID() {
        Date now = new Date();
        int intValue;
        try {
            intValue = Integer.parseInt(new SimpleDateFormat("HHmmssSSS", Locale.US).format(now));
        } catch (NumberFormatException ex) {
            try {
                intValue = Integer.parseInt(new SimpleDateFormat("HHmmss", Locale.US).format(now));
            } catch (NumberFormatException exp) {
                try {
                    intValue = Integer.parseInt(new SimpleDateFormat("HHmmSSS", Locale.US).format(now));
                } catch (Exception exception) {
                    intValue = 0;
                }
            }
        }
        Timber.d(String.valueOf(intValue));
        return intValue;
    }

    public boolean serviceIsRunningInForeground(Context context) {
        if (context != null) {
            ActivityManager manager = (ActivityManager) context.getSystemService(
                    Context.ACTIVITY_SERVICE);
            if (manager != null) {
                for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(
                        Integer.MAX_VALUE)) {
                    if (getClass().getName().equals(service.service.getClassName())) {
                        if (service.foreground) {
                            return true;
                        }
                    }
                }
            } else {
                return true;
            }
        }
        return false;
    }

    public static void openPDF(Context context, String fileName) {
        if (context != null) {
            File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(), fileName);
            Uri uri = Uri.fromFile(outputFile);
            if (uri != null) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setDataAndType(uri, "application/pdf");
                browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Intent chooser = Intent.createChooser(browserIntent, "Open PDF with ");
                if (browserIntent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(chooser);
                }
            }
        }
    }

    public static String getNumberFromString(String inputString) {
        if (isValidString(inputString)) {
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(inputString);
            while (m.find()) {
                return m.group();
            }
            return "";
        } else {
            return "";
        }
    }

    public static boolean isTVInstalled(String packageName, PackageManager packageManager) {

        boolean found = true;

        try {

            packageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {

            found = false;
        }

        return found;
    }


}
