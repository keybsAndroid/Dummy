package com.royalcommission.bs.modules.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.royalcommission.bs.app.AppController;
import com.royalcommission.bs.modules.api.model.BaseResponse;
import com.royalcommission.bs.modules.api.model.Patient;

import timber.log.Timber;


/**
 * Created by Prashant on 8/13/2018.
 */
public class SharedPreferenceUtils {

    private static final String REGION_PREFERENCES = "rc-bs";
    private static final String PREF_LOGIN_STATUS = "login";
    private static final String PREF_GROUP_ADMIN = "groupAdmin";
    private static final String PREF_GRANT_ACCESS_ADMIN = "grandAdminByAdmin";
    private static final String PREF_LOCAL_ACCOUNT = "localAccount";
    private static final String PREF_ALARM_UNIQUE_ID = "uniqueId";
    private static final String PREF_PATIENT_ID = "patientID";
    private static final String PREF_PATIENT_MOBILE = "patientMobile";
    private static final String PREF_PARENT_ID = "parentID";
    private static final String PREF_SESSION_ID = "sessionID";
    private static final String PREF_HOSPITAL_ID = "hospitalID";
    private static final String PREF_HOSPITAL_LIST = "hospitalList";
    private static final String PREF_NOTIFICATION_LIST = "notificationList";
    private static final String PREF_SMS_LIST = "smsList";
    private static final String PREF_COUNTRY_LIST = "countryList";
    private static final String PREF_MARITAL_LIST = "maritalStatusList";
    private static final String LOGGED_IN_STATUS = "1";
    private static final String IS_GROUP_ADMIN = "true";
    private static final String HAS_LOCAL_ACCOUNT = "true";
    private static final String PREF_PATIENT_PSE = "pse";
    private static final String PREF_PATIENT_PME = "pme";
    private static final String HAS_ELIGIBILITY = "eligibility";
    private static final String PREF_PATIENT_FULL_NAME = "full_name";
    private static final String PREF_PATIENT_DOB = "dob";
    private static final String PREF_PATIENT_GENDER = "gender";
    private static final String PREF_PATIENT_BLOOD_GROUP = "bloodGroup";
    private static final String PREF_PATIENT_PROFILE_IMAGE = "profile_image";
    private static final String PREF_PATIENT_PROFILE_HAS_PASSWORD = "password";
    private static final String PREF_PATIENT_PROFILE_PICTURE = "profilePicture";
    private static final String PATIENT_CATEGORY = "category";
    private static final String PREF_TODAY_NOTIFICATION = "todayNotification";
    private static final String PREF_TODAY_VR = "todayVR";
    private static final String PREF_CHECKED_INSIDE_HOSPITAL = "insideHospitalNavigation";
    private static final String PREF_SCHEDULE_JOB = "jobSchedule";
    private static final String PREF_APP_STATUS = "appStatus";


    public static void clearPreferenceUtils(Context context) {
        SharedPreferences settings = context.getSharedPreferences(REGION_PREFERENCES, Context.MODE_PRIVATE);
        settings.edit().clear().apply();
    }

    public static void saveLoginStatus(Context context, String loginVal) {
        if (context != null)
            CommonUtils.setPreferenceString(context, REGION_PREFERENCES, PREF_LOGIN_STATUS, loginVal);
    }

    private static String getLoginStatus(Context context) {
        if (context != null) {
            return CommonUtils.getPreferenceString(context, REGION_PREFERENCES, PREF_LOGIN_STATUS);
        }
        return null;
    }

    public static void saveCheckInStatus(Context context, String checkedIn) {
        if (context != null)
            CommonUtils.setPreferenceString(context, REGION_PREFERENCES, PREF_CHECKED_INSIDE_HOSPITAL, checkedIn);
    }

    private static String getCheckInStatus(Context context) {
        if (context != null) {
            return CommonUtils.getPreferenceString(context, REGION_PREFERENCES, PREF_CHECKED_INSIDE_HOSPITAL);
        }
        return null;
    }

    public static boolean isCheckedInsideHospital(Context context) {
        if (isValidString(getCheckInStatus(context))) {
            return getCheckInStatus(context).equalsIgnoreCase("1");
        } else {
            return false;
        }
    }

    public static void saveBloodGroup(Context context, String bloodGroup) {
        if (context != null)
            CommonUtils.setPreferenceString(context, REGION_PREFERENCES, PREF_PATIENT_BLOOD_GROUP, bloodGroup);
    }

    public static String getBloodGroup(Context context) {
        if (context != null) {
            return CommonUtils.getPreferenceString(context, REGION_PREFERENCES, PREF_PATIENT_BLOOD_GROUP);
        }
        return null;
    }

    public static boolean isAlreadyLoggedIn(Context context) {
        return getLoginStatus(context).equalsIgnoreCase(LOGGED_IN_STATUS);
    }


    public static void setAlarmUniqueId(Context context, String uniqueId) {
        if (context != null)
            CommonUtils.setPreferenceString(context, REGION_PREFERENCES, PREF_ALARM_UNIQUE_ID, uniqueId);
    }

    public static String getAlarmUniqueId(Context context) {
        if (context != null) {
            return CommonUtils.getPreferenceString(context, REGION_PREFERENCES, PREF_ALARM_UNIQUE_ID);
        }
        return null;
    }

    public static boolean isAlreadyShown(Context context) {
        return getTodayNotification(context).equalsIgnoreCase(DateUtils.getCurrentDate());
    }


    public static void setTodayNotification(Context context, String today) {
        if (context != null)
            CommonUtils.setPreferenceString(context, REGION_PREFERENCES, PREF_TODAY_NOTIFICATION, today);
    }

    private static String getTodayNotification(Context context) {
        if (context != null) {
            return CommonUtils.getPreferenceString(context, REGION_PREFERENCES, PREF_TODAY_NOTIFICATION);
        }
        return "";
    }

    public static boolean isVRAlreadyShown(Context context) {
        String today = SharedPreferenceUtils.getPatientID(AppController.getInstance()) + DateUtils.getCurrentDate();
        return getTodayVR(context).equalsIgnoreCase(today);
    }


    public static void setTodayVR(Context context, String today) {
        if (context != null)
            CommonUtils.setPreferenceString(context, REGION_PREFERENCES, PREF_TODAY_VR, today);
    }

    private static String getTodayVR(Context context) {
        if (context != null) {
            return CommonUtils.getPreferenceString(context, REGION_PREFERENCES, PREF_TODAY_VR);
        }
        return "";
    }

    public static void setPatientID(Context context, String patientID) {
        if (context != null)
            CommonUtils.setPreferenceString(context, REGION_PREFERENCES, PREF_PATIENT_ID, patientID);
    }

    public static String getPatientID(Context context) {
        if (context != null) {
            return CommonUtils.getPreferenceString(context, REGION_PREFERENCES, PREF_PATIENT_ID);
        }
        return null;
    }

    public static void setPatientMobile(Context context, String mobile) {
        if (context != null)
            CommonUtils.setPreferenceString(context, REGION_PREFERENCES, PREF_PATIENT_MOBILE, mobile);
    }

    public static String getPatientMobile(Context context) {
        if (context != null) {
            return CommonUtils.getPreferenceString(context, REGION_PREFERENCES, PREF_PATIENT_MOBILE);
        }
        return null;
    }

    public static void setParentID(Context context, String parentID) {
        if (context != null)
            CommonUtils.setPreferenceString(context, REGION_PREFERENCES, PREF_PARENT_ID, parentID);
    }

    public static String getParentID(Context context) {
        if (context != null) {
            return CommonUtils.getPreferenceString(context, REGION_PREFERENCES, PREF_PARENT_ID);
        }
        return null;
    }

    public static void savePatientProfilePicture(Context context, String profilePicture) {
        if (context != null)
            CommonUtils.setPreferenceString(context, REGION_PREFERENCES, PREF_PATIENT_PROFILE_PICTURE, profilePicture);
    }

    public static String getPatientProfilePicture(Context context) {
        if (context != null) {
            return CommonUtils.getPreferenceString(context, REGION_PREFERENCES, PREF_PATIENT_PROFILE_PICTURE);
        }
        return null;
    }

    public static void setPatientPSE(Context context, String pse) {
        if (context != null)
            CommonUtils.setPreferenceString(context, REGION_PREFERENCES, PREF_PATIENT_PSE, pse);
    }

    public static String getPatientPSE(Context context) {
        if (context != null) {
            return CommonUtils.getPreferenceString(context, REGION_PREFERENCES, PREF_PATIENT_PSE);
        }
        return null;
    }

    public static void setPatientPME(Context context, String pme) {
        if (context != null)
            CommonUtils.setPreferenceString(context, REGION_PREFERENCES, PREF_PATIENT_PME, pme);
    }

    public static String getPatientPME(Context context) {
        if (context != null) {
            return CommonUtils.getPreferenceString(context, REGION_PREFERENCES, PREF_PATIENT_PME);
        }
        return null;
    }


    public static void saveGroupAdmin(Context context, String isGroupAdmin) {
        if (context != null)
            CommonUtils.setPreferenceString(context, REGION_PREFERENCES, PREF_GROUP_ADMIN, isGroupAdmin);
    }

    private static String getGroupAdmin(Context context) {
        if (context != null) {
            return CommonUtils.getPreferenceString(context, REGION_PREFERENCES, PREF_GROUP_ADMIN);
        }
        return null;
    }

    public static boolean isGroupAdmin(Context context) {
        return getGroupAdmin(context).equalsIgnoreCase(IS_GROUP_ADMIN);
    }

    public static void saveGrantAccessByAdmin(Context context, String isGrantAccessByAdmin) {
        if (context != null)
            CommonUtils.setPreferenceString(context, REGION_PREFERENCES, PREF_GRANT_ACCESS_ADMIN, isGrantAccessByAdmin);
    }

    private static String getGrantAccessByAdmin(Context context) {
        if (context != null) {
            return CommonUtils.getPreferenceString(context, REGION_PREFERENCES, PREF_GRANT_ACCESS_ADMIN);
        }
        return null;
    }

    public static boolean isGrantAccessByAdmin(Context context) {
        return getGrantAccessByAdmin(context).equalsIgnoreCase(IS_GROUP_ADMIN);
    }


    public static void setEligibility(Context context, String eligibility) {
        if (context != null)
            CommonUtils.setPreferenceString(context, REGION_PREFERENCES, HAS_ELIGIBILITY, eligibility);
    }

    private static String getEligibility(Context context) {
        if (context != null) {
            return CommonUtils.getPreferenceString(context, REGION_PREFERENCES, HAS_ELIGIBILITY);
        }
        return null;
    }

    public static boolean hasEligibility(Context context) {
        return getEligibility(context).equalsIgnoreCase("1");
    }


    public static void setPatientCategory(Context context, String category) {
        if (context != null)
            CommonUtils.setPreferenceString(context, REGION_PREFERENCES, PATIENT_CATEGORY, category);
    }

    public static String getPatientCategory(Context context) {
        if (context != null) {
            return CommonUtils.getPreferenceString(context, REGION_PREFERENCES, PATIENT_CATEGORY);
        }
        return null;
    }


    public static void setSessionID(Context context, String sessionID) {
        if (context != null)
            CommonUtils.setPreferenceString(context, REGION_PREFERENCES, PREF_SESSION_ID, sessionID);
    }

    public static String getSessionID(Context context) {
        if (context != null) {
            return CommonUtils.getPreferenceString(context, REGION_PREFERENCES, PREF_SESSION_ID);
        }
        return null;
    }


    public static boolean isValidPatientID(Context context) {
        return isValidString(getPatientID(context));
    }

    public static boolean isValidString(String stringToBeValid) {
        return stringToBeValid != null && !stringToBeValid.trim().equalsIgnoreCase("");
    }


    public static void setHospitalID(Context context, String hospitalID) {
        if (context != null)
            CommonUtils.setPreferenceString(context, REGION_PREFERENCES, PREF_HOSPITAL_ID, hospitalID);
    }

    public static String getHospitalID(Context context) {
        if (context != null) {
            return CommonUtils.getPreferenceString(context, REGION_PREFERENCES, PREF_HOSPITAL_ID);
        }
        return null;
    }

    public static void saveHospitalList(Context context, String hospitalList) {
        if (context != null)
            CommonUtils.setPreferenceString(context, REGION_PREFERENCES, PREF_HOSPITAL_LIST, hospitalList);
    }

    public static String getHospitalList(Context context) {
        if (context != null) {
            return CommonUtils.getPreferenceString(context, REGION_PREFERENCES, PREF_HOSPITAL_LIST);
        }
        return null;
    }

    public static void saveNotificationList(Context context, String notificationList) {
        if (context != null)
            CommonUtils.setPreferenceString(context, REGION_PREFERENCES, PREF_NOTIFICATION_LIST, notificationList);
    }

    public static String getNotificationList(Context context) {
        if (context != null) {
            return CommonUtils.getPreferenceString(context, REGION_PREFERENCES, PREF_NOTIFICATION_LIST);
        }
        return null;
    }

    public static void sendSMSList(Context context, String smsList) {
        if (context != null)
            CommonUtils.setPreferenceString(context, REGION_PREFERENCES, PREF_SMS_LIST, smsList);
    }

    public static String getSMSList(Context context) {
        if (context != null) {
            return CommonUtils.getPreferenceString(context, REGION_PREFERENCES, PREF_SMS_LIST);
        }
        return null;
    }


    public static void saveCountryList(Context context, String countryList) {
        if (context != null)
            CommonUtils.setPreferenceString(context, REGION_PREFERENCES, PREF_COUNTRY_LIST, countryList);
    }

    public static String getCountryList(Context context) {
        if (context != null) {
            return CommonUtils.getPreferenceString(context, REGION_PREFERENCES, PREF_COUNTRY_LIST);
        }
        return null;
    }

    public static void saveMaritalStatusList(Context context, String maritalList) {
        if (context != null)
            CommonUtils.setPreferenceString(context, REGION_PREFERENCES, PREF_MARITAL_LIST, maritalList);
    }

    public static String getMaritalStatusList(Context context) {
        if (context != null) {
            return CommonUtils.getPreferenceString(context, REGION_PREFERENCES, PREF_MARITAL_LIST);
        }
        return null;
    }

    public static void saveFullName(Context context, String fullName) {
        if (context != null)
            CommonUtils.setPreferenceString(context, REGION_PREFERENCES, PREF_PATIENT_FULL_NAME, fullName);
    }

    public static String getFullName(Context context) {
        if (context != null) {
            return CommonUtils.getPreferenceString(context, REGION_PREFERENCES, PREF_PATIENT_FULL_NAME);
        }
        return null;
    }

    public static void saveDOB(Context context, String dob) {
        if (context != null)
            CommonUtils.setPreferenceString(context, REGION_PREFERENCES, PREF_PATIENT_DOB, dob);
    }

    public static String getDOB(Context context) {
        if (context != null) {
            return CommonUtils.getPreferenceString(context, REGION_PREFERENCES, PREF_PATIENT_DOB);
        }
        return null;
    }

    public static void saveGender(Context context, String gender) {
        if (context != null)
            CommonUtils.setPreferenceString(context, REGION_PREFERENCES, PREF_PATIENT_GENDER, gender);
    }

    public static String getGender(Context context) {
        if (context != null) {
            return CommonUtils.getPreferenceString(context, REGION_PREFERENCES, PREF_PATIENT_GENDER);
        }
        return null;
    }

    public static void savePatientResponse(Context context, Patient patientResponse, BaseResponse baseResponse) {
        if (context != null && baseResponse != null) {

            if (baseResponse.getResponseCode() == 1) {
                if (patientResponse != null) {
                    SharedPreferenceUtils.setHospitalID(context, patientResponse.getHospitalID());
                    SharedPreferenceUtils.setPatientID(context, patientResponse.getPatientId());

                    if (patientResponse.isGroupAdmin()) {
                        SharedPreferenceUtils.setParentID(context, patientResponse.getPatientId());
                    }

                    if (CommonUtils.isValidString(patientResponse.getParentID())) {
                        SharedPreferenceUtils.setParentID(context, patientResponse.getParentID());
                    }

                    SharedPreferenceUtils.setPatientPSE(context, patientResponse.getPSE_CLS_CD());
                    SharedPreferenceUtils.setPatientMobile(context, patientResponse.getMobile());
                    SharedPreferenceUtils.setPatientPME(context, patientResponse.getPME_CLS_CD());
                    SharedPreferenceUtils.saveGroupAdmin(context, String.valueOf(patientResponse.isGroupAdmin()));
                    SharedPreferenceUtils.saveGrantAccessByAdmin(context, String.valueOf(patientResponse.isGrantAccessByAdmin()));
                    SharedPreferenceUtils.saveFullName(context, patientResponse.getFullName());
                    SharedPreferenceUtils.saveGender(context, patientResponse.getGender());
                    SharedPreferenceUtils.saveDOB(context, patientResponse.getBirthDate());
                    SharedPreferenceUtils.saveBloodGroup(context, patientResponse.getBloodGroup());
                    SharedPreferenceUtils.saveLocalAccount(context, String.valueOf(patientResponse.isLocalAccount()));
                    SharedPreferenceUtils.setPatientCategory(context, patientResponse.getPatientCategory());
                    if (isValidString(patientResponse.getEligibility())) {
                        if (patientResponse.getEligibility().equalsIgnoreCase("IR")
                                || patientResponse.getEligibility().equalsIgnoreCase("CS")) {
                            SharedPreferenceUtils.setEligibility(context, "1");
                        } else {
                            SharedPreferenceUtils.setEligibility(context, "0");
                        }
                    }
                    SharedPreferenceUtils.setSessionID(context, baseResponse.getGuSessionID());
                }
            }
        }
    }

    public static void saveLocalAccount(Context context, String hasLocalAccount) {
        if (context != null)
            CommonUtils.setPreferenceString(context, REGION_PREFERENCES, PREF_LOCAL_ACCOUNT, hasLocalAccount);
    }

    public static String getLocalAccount(Context context) {
        if (context != null) {
            return CommonUtils.getPreferenceString(context, REGION_PREFERENCES, PREF_LOCAL_ACCOUNT);
        }
        return null;
    }

    public static boolean hasLocalAccount(Context context) {
        return getLocalAccount(context).equalsIgnoreCase(HAS_LOCAL_ACCOUNT);
    }


    public static void scheduleJobForFirstTime(Context context, boolean isFirstTime) {
        if (context != null) {
            setPreferenceBoolean(context, isFirstTime);
            Timber.tag("scheduleJob ").i("Started for the first time");
        }
    }

    public static boolean isJobScheduledAlready(Context context) {
        if (context != null) {
            return getPreferenceBoolean(context);
        } else {
            return false;
        }
    }

    public static void saveApplicationStatus(Context context, int appStatus) {
        if (context != null) {
            setPreferenceInteger(context, appStatus);
        }
    }

    public static String getApplicationStatus(Context context) {
        if (context != null) {
            if (getPreferenceInteger(context) == AppController.APP_FORE_GROUNDED) {
                return "APP_FORE_GROUNDED";
            } else if (getPreferenceInteger(context) == AppController.APP_BACK_GROUNDED) {
                return "APP_BACK_GROUNDED";
            } else if (getPreferenceInteger(context) == AppController.APP_DESTROYED) {
                return "APP_DESTROYED";
            } else {
                return "FAILED_TO_GET_APP_STATUS";
            }
        } else {
            return "FAILED_TO_GET_APP_STATUS";
        }
    }

    private static int getPreferenceInteger(Context context) {
        try {
            final SharedPreferences prefs = getPreferences(context);
            return prefs.getInt(SharedPreferenceUtils.PREF_APP_STATUS, -1);
        } catch (Exception e) {
            Timber.e("getPref: %s", String.valueOf(e));
            return -1;
        }
    }

    private static void setPreferenceInteger(Context context, int preferenceValue) {
        final SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(SharedPreferenceUtils.PREF_APP_STATUS, preferenceValue);
        editor.apply();
    }

    private static void setPreferenceBoolean(Context context, boolean preferenceValue) {
        final SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(SharedPreferenceUtils.PREF_SCHEDULE_JOB, preferenceValue);
        editor.apply();
    }


    private static boolean getPreferenceBoolean(Context context) {
        try {
            final SharedPreferences prefs = getPreferences(context);
            return prefs.getBoolean(SharedPreferenceUtils.PREF_SCHEDULE_JOB, false);
        } catch (Exception e) {
            Timber.e("getPref: %s", String.valueOf(e));
        }
        return false;
    }

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(SharedPreferenceUtils.REGION_PREFERENCES, Context.MODE_PRIVATE);
    }

}
