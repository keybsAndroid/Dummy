package com.royalcommission.bs.views.activities;

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.royalcommission.bs.R;
import com.royalcommission.bs.app.AppController;
import com.royalcommission.bs.models.Menu;
import com.royalcommission.bs.modules.api.listener.RetrofitListener;
import com.royalcommission.bs.modules.api.model.BaseResponse;
import com.royalcommission.bs.modules.api.model.InpatientInfo;
import com.royalcommission.bs.modules.api.model.InpatientResponse;
import com.royalcommission.bs.modules.api.model.NotificationMessages;
import com.royalcommission.bs.modules.api.model.NotificationMessagesResponse;
import com.royalcommission.bs.modules.api.model.RoundingDoctorResponse;
import com.royalcommission.bs.modules.event.MessageEvent;
import com.royalcommission.bs.modules.services.FetchMessageAndNotificationJob;
import com.royalcommission.bs.modules.utils.CommonUtils;
import com.royalcommission.bs.modules.utils.Constants;
import com.royalcommission.bs.modules.utils.LocaleHelper;
import com.royalcommission.bs.modules.utils.LogoutTimerUtil;
import com.royalcommission.bs.modules.utils.NotificationHelper;
import com.royalcommission.bs.modules.utils.SharedPreferenceUtils;
import com.royalcommission.bs.views.adapters.HospitalGuideMenuAdapter;
import com.royalcommission.bs.views.adapters.MenuAdapter;
import com.royalcommission.bs.views.adapters.SubMenuAdapter;
import com.royalcommission.bs.views.dialogs.AskDoctorLoginFragment;
import com.royalcommission.bs.views.dialogs.DoctorLoginDialogFragment;
import com.royalcommission.bs.views.dialogs.InternetDialogFragment;
import com.royalcommission.bs.views.dialogs.LogoutDialogFragment;
import com.royalcommission.bs.views.dialogs.NotificationsDialogFragment;
import com.royalcommission.bs.views.fragments.dashboard.DashBoardFragment;
import com.royalcommission.bs.views.fragments.dashboard.documents.AssignedDoctorsNursesFragment;
import com.royalcommission.bs.views.fragments.dashboard.documents.PhysicianRoundingDialogFragment;
import com.royalcommission.bs.views.fragments.doctor.DoctorDashBoardFragment;
import com.royalcommission.bs.views.fragments.guide.AdmissionInfoFragment;
import com.royalcommission.bs.views.fragments.guide.DischargeInfoFragment;
import com.royalcommission.bs.views.fragments.guide.HospitalInfoFragment;
import com.royalcommission.bs.views.fragments.guide.SurveysAndSuggestionsFragment;
import com.royalcommission.bs.views.fragments.task.education.EducationFragment;
import com.royalcommission.bs.views.fragments.task.meals.MealsFragment;
import com.royalcommission.bs.views.fragments.task.medicine.MedicineFragment;
import com.royalcommission.bs.views.fragments.task.nurse.NurseFragment;
import com.royalcommission.bs.views.fragments.task.physician.PhysicianRoundingFragment;
import com.royalcommission.bs.views.fragments.task.tests.MedicalTestFragment;
import com.royalcommission.bs.views.fragments.tv.TvFragment;
import com.royalcommission.bs.views.interfaces.MenuAdapterClickListener;
import com.royalcommission.bs.views.interfaces.SubMenuAdapterClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class HomeActivity extends BaseActivity implements MenuAdapterClickListener, SubMenuAdapterClickListener, HospitalGuideMenuAdapter.HospitalGuideMenuAdapterClickListener, View.OnClickListener, LogoutDialogFragment.LogoutDialogClickListener, LogoutTimerUtil.LogOutListener {

    private static final long ONE_SECOND = 60 * 1000;
    private Button logout, language;
    private AlertDialog logoutAlertDialog;
    private final String TAG = HomeActivity.this.getClass().getName();
    private LogoutDialogFragment.LogoutDialogClickListener listener;
    private LogoutTimerUtil.LogOutListener logOutListener;
    private Handler mHandler;
    private MenuAdapter menuAdapter;
    private HospitalGuideMenuAdapter hospMenuAdapter;
    private List<Menu> menuList = new ArrayList<>();
    private TextView time;
    private Timer timer;
    private Handler updateLabel;
    private List<String> doctorList = new ArrayList<>();
    public static InpatientInfo mInpatientInfo;
    private LinearLayout myPage, hospitalGuide;
    private RelativeLayout nurseLayout, physicianRoundingLayout, doctorNurseLayout;
    private TextView nurseCall, physicianRounding, doctorNurse;
    public static boolean isDoctorLoggedIn = false;
    public static String loggedInDoctorName = "";
    private TextView loggedInUserName, welcomeHint;


    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        setLanguage();
        loadHomeFragment(R.id.container);
    }

    @SuppressLint("HandlerLeak")
    private void init() {
        listener = this;
        logOutListener = this;
        menuList = getMenuList();
        menuAdapter = new MenuAdapter(this, menuList, this);
        hospMenuAdapter = new HospitalGuideMenuAdapter(this, getHospitalGuideMenuList(), this);
        time = findViewById(R.id.time);
        TextView date = findViewById(R.id.date);
        loggedInUserName = findViewById(R.id.patient_name);
        welcomeHint = findViewById(R.id.welcome_hint);
        logout = findViewById(R.id.logout);
        language = findViewById(R.id.language);
        LinearLayout contentMainLL = findViewById(R.id.content_main);
        View contentMain = contentMainLL.findViewById(R.id.layout_content_main);

        myPage = contentMain.findViewById(R.id.my_page);
        hospitalGuide = contentMain.findViewById(R.id.hospital_guide);

        nurseLayout = contentMain.findViewById(R.id.nurse_layout);
        physicianRoundingLayout = contentMain.findViewById(R.id.physician_rounding_layout);
        doctorNurseLayout = contentMain.findViewById(R.id.doctor_nurse_layout);

        nurseLayout.setOnClickListener(this);
        physicianRoundingLayout.setOnClickListener(this);
        doctorNurseLayout.setOnClickListener(this);

        nurseCall = contentMain.findViewById(R.id.nurse);
        physicianRounding = contentMain.findViewById(R.id.physician_rounding);
        doctorNurse = contentMain.findViewById(R.id.doctor_nurse);

        nurseCall.setOnClickListener(this);
        physicianRounding.setOnClickListener(this);
        doctorNurse.setOnClickListener(this);

        RecyclerView recyclerViewMenu = contentMain.findViewById(R.id.menu_recycler_view);
        RecyclerView recyclerViewSubMenu = contentMain.findViewById(R.id.today_task_recycler_view);
        RecyclerView recyclerViewHospitalGuide = contentMain.findViewById(R.id.hospital_guide_recycler_view);
        logout.setOnClickListener(this);
        language.setOnClickListener(this);


        mHandler = new Handler();
        updateLabel = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                time.setText(CommonUtils.getHomePageTime());
            }
        };
        setMenuAdapter(recyclerViewMenu);
        setHospitalGuideMenuAdapter(recyclerViewHospitalGuide);
        setSubMenuAdapter(recyclerViewSubMenu);
        date.setText(CommonUtils.getHomePageDate());
        String name = SharedPreferenceUtils.getFullName(this);
        if (isValidString(name)) {
            welcomeHint.setVisibility(View.VISIBLE);
            loggedInUserName.setText(String.format("%s %s", getString(R.string.hello), name));
        } else {
            welcomeHint.setVisibility(View.GONE);
        }

        reScheduleTimer();
        getInpatientInfo();
        getRoundingDoctors();
        new Handler().postDelayed(this::scheduleJob, 60 * 1000);
    }

    private void scheduleJob() {
        if (!SharedPreferenceUtils.isJobScheduledAlready(this)) {
            //schedule the job only once.
            scheduleVersionUpdateToServer();
            Timber.tag("scheduleJob ").i("First Time Started");
        } else {
            Timber.tag("scheduleJob ").i("Started Already");
        }
    }

    private void scheduleVersionUpdateToServer() {
        JobScheduler jobScheduler = (JobScheduler) getApplicationContext()
                .getSystemService(JOB_SCHEDULER_SERVICE);
        ComponentName componentName = new ComponentName(this,
                FetchMessageAndNotificationJob.class);
        JobInfo jobInfo = new JobInfo.Builder(1, componentName)
                .setPeriodic(FetchMessageAndNotificationJob.JOB_PERIODIC_TIME_INTERVAL).setRequiredNetworkType(
                        JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true).build();
        jobScheduler.schedule(jobInfo);
    }

    public void reScheduleTimer() {
        timer = new Timer();
        TimerTask timerTask = new MyTimerTask();
        timer.schedule(timerTask, 0, ONE_SECOND);
    }

    private void getRoundingDoctors() {
        new Handler().postDelayed(this::getDoctorList, ONE_SECOND);
    }

    @Override
    public void onSubMenuItemClickListener(int position) {
        if (position != -1) {
            if (!isDoctorLoggedIn) {
                clearBackStack();
                selectMenuFragment(position);
            } else {
                showDoctorLoginAlert();
            }
        }
    }


    private void showDoctorLoginAlert() {
        if (isValidString(loggedInDoctorName))
            showMessageAlert(getString(R.string.doctor_login_title), getString(R.string.already_logged_in) + getDoctorName(loggedInDoctorName));
    }

    private void selectMenuFragment(int position) {
        switch (position) {
            case 0:
                loadFragments(MedicineFragment.getInstance(getString(R.string.today_medicine)), true, MedicineFragment.TAG);
                break;
            case 1:
                loadFragments(new MedicalTestFragment(), true, MedicalTestFragment.TAG);
                break;
            case 2:
                loadFragments(MealsFragment.getInstance(1), true, MealsFragment.TAG);
                break;
            case 3:
                loadFragments(new PhysicianRoundingFragment(), true, PhysicianRoundingFragment.TAG);
                break;
            case 4:
                loadFragments(new NurseFragment(), true, NurseFragment.TAG);
                break;
            case 5:
                loadFragments(new EducationFragment(), true, EducationFragment.TAG);
                break;
        }
    }

    @Override
    public void onMenuItemClickListener(int position) {
        if (position != -1) {
            selectHospitalGuideFragment(position);
        }
    }


    public boolean isDoctorLoggedIn() {
        if (isDoctorLoggedIn)
            showDoctorLoginAlert();
        return isDoctorLoggedIn;
    }


    private void selectHospitalGuideFragment(int position) {

        switch (position) {
            case 0:
                //clearBackStack();
                if (!(getFragment() instanceof AdmissionInfoFragment))
                    loadFragments(new AdmissionInfoFragment(), false, AdmissionInfoFragment.TAG);
                break;
            case 1:
                if (!(getFragment() instanceof DischargeInfoFragment))
                    loadFragments(new DischargeInfoFragment(), false, DischargeInfoFragment.TAG);
                break;
            case 2:
                if (!(getFragment() instanceof HospitalInfoFragment))
                    loadFragments(new HospitalInfoFragment(), false, HospitalInfoFragment.TAG);
                break;
            case 3:
                if (!(getFragment() instanceof SurveysAndSuggestionsFragment))
                    loadFragments(new SurveysAndSuggestionsFragment(), true, SurveysAndSuggestionsFragment.TAG);
                break;
            default:
                clearBackStack();
                loadFragments(new AdmissionInfoFragment(), true, AdmissionInfoFragment.TAG);
                break;
        }
    }

    public void goBack() {
        onBackPressed();
    }

    public void goToDoctorRoundingPage(String loggedInDoctorName) {
        if (isValidString(loggedInDoctorName)) {
            loggedInUserName.setText(String.format("%s %s", getString(R.string.hello), getDoctorName(loggedInDoctorName)));
            welcomeHint.setVisibility(View.GONE);
            showMessageAlert(null, getString(R.string.logged_in_as) + " \t " + getDoctorName(loggedInDoctorName));
        } else
            showMessageAlert(null, getString(R.string.login_success));
        clearBackStack();
        loadFragments(new DoctorDashBoardFragment(), false, DoctorDashBoardFragment.TAG);
    }

    private String getDoctorName(String loggedInDoctorName) {
        return loggedInDoctorName == null ? "" : loggedInDoctorName.replace("\"", "");
    }

    public void logoutDoctorRoundingPage() {
        HomeActivity.isDoctorLoggedIn = false;
        HomeActivity.loggedInDoctorName = null;
    }

    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            updateLabel.sendEmptyMessage(0);
        }
    }

    private void setLanguage() {
        if (language != null) {
            if (AppController.isEnglish()) {
                language.setText(getString(R.string.arabic_text));
            } else {
                language.setText(getString(R.string.english_text));
            }
        }
    }

    private void setMenuAdapter(RecyclerView recyclerView) {
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(menuAdapter);
        }
    }

    private void setHospitalGuideMenuAdapter(RecyclerView recyclerView) {
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(hospMenuAdapter);
        }
    }

    private void setSubMenuAdapter(RecyclerView recyclerView) {
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(new SubMenuAdapter(this, getSubMenuList(), this));
        }
    }

    private List<String> getSubMenuList() {
        return Arrays.asList(getResources().getStringArray(R.array.sub_menu));
    }

    private List<String> getHospitalGuideMenuList() {
        return Arrays.asList(getResources().getStringArray(R.array.hosp_guide_menu));
    }

    @Override
    public void onMenuClickListener(int position) {
        if (position != -1) {
            showHideLayout(position);
            selectFragment(position);
        }
    }

    private void showHideLayout(int position) {
        if (myPage != null && hospitalGuide != null) {
            if (position == 0) {
                myPage.setVisibility(View.VISIBLE);
                hospitalGuide.setVisibility(View.GONE);
            } else if (position == 1) {
                myPage.setVisibility(View.GONE);
                hospitalGuide.setVisibility(View.VISIBLE);
            } else {
                myPage.setVisibility(View.GONE);
                hospitalGuide.setVisibility(View.GONE);
            }
        }
    }

    public void loadHomeFragment(int containerId) {
        Runnable mPendingRunnable = () -> addHomeFragment(containerId, new DashBoardFragment());
        if (mHandler == null)
            mHandler = new Handler();
        mHandler.post(mPendingRunnable);
    }

    public void loadFragment(int containerId, final Fragment fragment, final boolean addToBackStack, final String tag) {
        if (fragment != null) {
            Runnable mPendingRunnable = () -> addFragment(containerId, fragment, addToBackStack, tag);
            if (mHandler == null)
                mHandler = new Handler();
            mHandler.post(mPendingRunnable);
        }
    }

    private List<Menu> getMenuList() {
        menuList.clear();
        menuList.add(new Menu(R.drawable.ic_menu_my_page, getString(R.string.my_page)));
        menuList.add(new Menu(R.drawable.ic_guide, getString(R.string.info)));
        menuList.add(new Menu(R.drawable.ic_menu_tv, getString(R.string.tv)));
        menuList.add(new Menu(R.drawable.ic_menu_internet, getString(R.string.internet)));
        menuList.add(new Menu(R.drawable.ic_menu_msg_notification, getString(R.string.message)));
        return menuList;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == logout.getId()) {
            logoutDialog();
        } else if (v.getId() == language.getId()) {
            if (AppController.isEnglish()) {
                setLanguage(AppController.LANGUAGE_ARABIC);
            } else {
                setLanguage(AppController.LANGUAGE_ENGLISH);
            }
        } else if (v.getId() == nurseLayout.getId() || v.getId() == nurseCall.getId()) {
            if (!isDoctorLoggedIn()) {

            }
        } else if (v.getId() == physicianRoundingLayout.getId() || v.getId() == physicianRounding.getId()) {
            if (!isDoctorLoggedIn()) {
                roundingDoctorLogin();
            }
        } else if (v.getId() == doctorNurseLayout.getId() || v.getId() == doctorNurse.getId()) {
            if (!isDoctorLoggedIn()) {
                showAssignedDoctorNurseDialog();
            }
        }
    }

    private void roundingDoctorLogin() {
        AskDoctorLoginFragment askDoctorLoginFragment = new AskDoctorLoginFragment();
        askDoctorLoginFragment.show(getSupportFragmentManager(), "Doctor Login");
        getSupportFragmentManager().executePendingTransactions();
    }

    public void setLanguage(String language) {
        if (language != null && !language.equalsIgnoreCase(AppController.getLanguageCode())) {
            LocaleHelper.setLocale(this, language);
            recreate();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideKeyboard();
    }

    @Override
    protected void onDestroy() {
        logoutDoctorRoundingPage();
        clearTimer();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void clearTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }

    private void selectFragment(int position) {
        clearBackStack();
        switch (position) {
            case 0:
                if (!(getFragment() instanceof DashBoardFragment))
                    loadFragments(new DashBoardFragment(), false, DashBoardFragment.TAG);
                break;
            case 1:
                if (!(getFragment() instanceof AdmissionInfoFragment))
                    loadFragments(new AdmissionInfoFragment(), true, AdmissionInfoFragment.TAG);
                break;
            case 2:
                if (!(getFragment() instanceof TvFragment))
                    openTVApplication();
                break;
            case 3:
                showInternetDialog();
                break;
            case 4:
                if (isNetworkConnected()) {
                    getMessageNotifications();
                }
                break;
        }
    }

    private void openTVApplication() {
        if (CommonUtils.isTVInstalled(Constants.TV_PACKAGE, getPackageManager())) {
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage(Constants.TV_PACKAGE);
            startActivity(launchIntent);
        } else {
            showMessageAlert(null, getString(R.string.tv_app_not_installed));
        }
    }

    public void loadFragments(Fragment fragment, boolean addToBackStack, String tag) {
        loadFragment(R.id.container, fragment, addToBackStack, tag);
    }

    public void clearBackStack() {
        try {
            if (getSupportFragmentManager() != null)
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } catch (IllegalStateException e) {
            Timber.tag("clearBackStack").d(String.valueOf(e));
        }
    }

    @Override
    public void onBackPressed() {
        Timber.tag(TAG).d("onBackPressed: ");
        if (getSupportFragmentManager() != null && getSupportFragmentManager().getBackStackEntryCount() > 0) {
            for (Fragment frag : getSupportFragmentManager().getFragments()) {
                if (frag.isVisible()) {
                    FragmentManager childFm = frag.getChildFragmentManager();
                    if (childFm.getBackStackEntryCount() > 0) {
                        childFm.popBackStack();
                        return;
                    }
                }
            }
            super.onBackPressed();
        } else {
            logoutDialog();
        }
    }

    public Fragment getFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.container);
    }

    private void logoutDialog() {
        runOnUIThread(() -> createLogoutDialog(HomeActivity.this, getString(R.string.logout), getString(R.string.logout_message), true));
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Timber.tag(TAG).d("onStart: ");
        resetTimer();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        Timber.tag(TAG).d("onUserInteraction: ");
        resetTimer();
    }

    private void resetTimer() {
        LogoutTimerUtil.startLogoutPopupTimer(HomeActivity.this, this);
        Timber.tag("startLogoutPopupTimer").d(CommonUtils.getCurrentTime());
    }

    @Override
    public void onLogout() {
        loggingOut();
    }

    @Override
    public void onKeepMeSignedIn() {
        LogoutTimerUtil.startLogoutPopupTimer(HomeActivity.this, this);
    }

    private void loggingOut() {
        if (AppController.isMainActivityOnResume()) {
            clearBackStack();
            logout();
        }
    }

    @Override
    public void doLogout() {
        runOnUIThread(() -> {
            createLogoutDialog(HomeActivity.this, getString(R.string.session_time_out), getString(R.string.session_message), false);
            LogoutTimerUtil.startPopupDismissTimer(HomeActivity.this, logOutListener);
        });
    }

    private void logout() {
        if (isDoctorLoggedIn) {
            clearBackStack();
            logoutDoctorRoundingPage();
            showToastMessage(getString(R.string.logged_in_as) + " \t " + SharedPreferenceUtils.getFullName(AppController.getInstance()));
            loadFragments(new DashBoardFragment(), false, DashBoardFragment.TAG);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            this.finish();
        }

    }

    @Override
    public void dismissPopUpThenLogout() {
        loggingOut();
    }

    /********************LOGOUT DIALOG'S LISTENER********************/

    public void createLogoutDialog(Context context, String title, String message, final boolean isNormal) {
        if (context != null) {
            logoutAlertDialog = new AlertDialog.Builder(context).create();
            if (logoutAlertDialog != null) {
                logoutAlertDialog.setTitle(title);
                logoutAlertDialog.setMessage(message);
                logoutAlertDialog.setCancelable(false);
                logoutAlertDialog.setCanceledOnTouchOutside(false);
                if (isNormal) {
                    logoutAlertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.no), (dialog, which) -> closeDialog(logoutAlertDialog));
                    logoutAlertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.yes), (dialog, which) -> {
                    });
                } else {
                    logoutAlertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.keep_me_signed_in), (dialog, which) -> {
                    });

                    logoutAlertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.logout_caps), (dialog, which) -> {
                    });
                }

                if (logoutAlertDialog.getWindow() != null)
                    logoutAlertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationSlideUpDown;

                if (!isFinishing() && !logoutAlertDialog.isShowing())
                    logoutAlertDialog.show();

                logoutAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view -> {
                    closeDialog(logoutAlertDialog);
                    listener.onLogout();
                });
                logoutAlertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(v -> {
                    closeDialog(logoutAlertDialog);
                    if (!isNormal)
                        listener.onKeepMeSignedIn();
                });
            }
        }
    }

    private void closeDialog(AlertDialog alertDialog) {
        if (alertDialog != null && alertDialog.isShowing() && !isFinishing()) {
            alertDialog.cancel();
            alertDialog.dismiss();
            logoutAlertDialog = null;
        }
    }

    public void getDoctorList() {
        //Dummy Data
        String patientId = "80000002";
        //String patientId = SharedPreferenceUtils.getPatientID(AppController.getInstance());
        processRequest(AppController.getWebService().getTodayRoundingDoctor(patientId, SharedPreferenceUtils.getHospitalID(AppController.getInstance())), true, true, null, new RetrofitListener() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    RoundingDoctorResponse roundingDoctorResponse = (RoundingDoctorResponse) object;
                    BaseResponse baseResponse = roundingDoctorResponse.getBaseResponse();
                    if (baseResponse != null) {
                        if (baseResponse.getResponseCode() == 1) {
                            if (doctorList != null) {
                                doctorList.clear();
                                doctorList.addAll(roundingDoctorResponse.getRoundingDoctorList());
                            }
                            if (doctorList != null && !doctorList.isEmpty()) {
                                if (!isDoctorLoggedIn)
                                    showMedicalTeamDialog();
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(String error) {
            }
        }, RoundingDoctorResponse.class);

    }

    private void showMedicalTeamDialog() {
        if (AppController.isMainActivityOnResume()) {
            PhysicianRoundingDialogFragment fragment = PhysicianRoundingDialogFragment.getInstance(doctorList);
            fragment.show(getSupportFragmentManager(), "doctor rounding");
        }
    }

    private void showAssignedDoctorNurseDialog() {
        if (AppController.isMainActivityOnResume()) {
            AssignedDoctorsNursesFragment fragment = AssignedDoctorsNursesFragment.getInstance();
            fragment.show(getSupportFragmentManager(), "doctor nurse assigned");
        }
    }

    private void getInpatientInfo() {
        processRequest(AppController.getWebService().getInpatientInfo(SharedPreferenceUtils.getPatientID(AppController.getInstance()), SharedPreferenceUtils.getHospitalID(AppController.getInstance())), true, true, null, new RetrofitListener() {
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
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageAndNotificationEvent(MessageEvent messageEvent) {
        if (messageEvent != null) {
            if (messageEvent.getAppStatus() == AppController.APP_FORE_GROUNDED) {
                showNotificationsDialog(messageEvent.getNotificationMessages());
            } else if (messageEvent.getAppStatus() == AppController.APP_BACK_GROUNDED) {
                showNotifications(messageEvent.getNotificationMessages());
            }
        }
    }

    private void showNotifications(List<NotificationMessages> notificationMessages) {
        if (notificationMessages != null) {
            for (NotificationMessages notification : notificationMessages) {
                NotificationHelper.showNotification(this, notification.getSms(), null);
            }
        }
    }

    private void showNotificationsDialog(List<NotificationMessages> notificationMessages) {
        if (AppController.isMainActivityOnResume()) {
            NotificationsDialogFragment fragment = NotificationsDialogFragment.getInstance(notificationMessages);
            fragment.show(getSupportFragmentManager(), "Notifications Dialog");
        }
    }

    public void showInternetDialog() {
        if (AppController.isMainActivityOnResume()) {
            InternetDialogFragment fragment = new InternetDialogFragment();
            fragment.show(getSupportFragmentManager(), "Internet Dialog");
        }
    }

    public void showDoctorLoginDialog() {
        if (AppController.isMainActivityOnResume()) {
            DoctorLoginDialogFragment fragment = new DoctorLoginDialogFragment();
            fragment.show(getSupportFragmentManager(), "Doctor Login Dialog");
        }
    }

    private void getMessageNotifications() {
        String patientID = SharedPreferenceUtils.getPatientID(AppController.getInstance());
        String hospitalID = SharedPreferenceUtils.getHospitalID(AppController.getInstance());
        if (CommonUtils.isValidString(patientID) && CommonUtils.isValidString(hospitalID)) {
            showProgress(true);
            AppController.getWebService().getMessageNotifications(patientID, hospitalID)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableObserver<Object>() {
                        @Override
                        public void onNext(Object object) {
                            NotificationMessagesResponse response = (NotificationMessagesResponse) object;
                            BaseResponse baseResponse = response.getBaseResponse();
                            hideProgress(true);
                            if (baseResponse != null && baseResponse.getResponseCode() == 1) {
                                List<NotificationMessages> notificationMessagesList = response.getNotificationMessagesList();
                                if (notificationMessagesList != null && !notificationMessagesList.isEmpty()) {
                                    Collections.sort(notificationMessagesList, new NotificationMessages().new CompareByDate());
                                    Collections.reverse(notificationMessagesList);
                                    showNotificationsDialog(notificationMessagesList);
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            hideProgress(true);
                        }

                        @Override
                        public void onComplete() {
                            hideProgress(true);
                        }
                    });
        }


    }

}
