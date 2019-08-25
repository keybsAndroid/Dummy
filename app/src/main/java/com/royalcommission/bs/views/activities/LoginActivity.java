package com.royalcommission.bs.views.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.royalcommission.bs.R;
import com.royalcommission.bs.app.AppController;
import com.royalcommission.bs.models.Menu;
import com.royalcommission.bs.modules.utils.CommonUtils;
import com.royalcommission.bs.modules.utils.LocaleHelper;
import com.royalcommission.bs.modules.utils.LogoutTimerUtil;
import com.royalcommission.bs.views.adapters.MenuAdapter;
import com.royalcommission.bs.views.dialogs.LogoutDialogFragment;
import com.royalcommission.bs.views.fragments.DashBoardFragment;
import com.royalcommission.bs.views.fragments.InfoFragment;
import com.royalcommission.bs.views.fragments.InternetFragment;
import com.royalcommission.bs.views.fragments.MessageFragment;
import com.royalcommission.bs.views.fragments.MyPageFragment;
import com.royalcommission.bs.views.fragments.TvFragment;
import com.royalcommission.bs.views.fragments.login.LoginSelectionFragment;
import com.royalcommission.bs.views.interfaces.MenuAdapterClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends BaseActivity implements MenuAdapterClickListener, View.OnClickListener, LogoutDialogFragment.LogoutDialogClickListener, LogoutTimerUtil.LogOutListener {

    private static final long ONE_SECOND = 60 * 1000;
    private Button logout, language;
    private AlertDialog logoutAlertDialog;
    private static final String TAG = "HomeActivity";
    private LogoutDialogFragment.LogoutDialogClickListener listener;
    private LogoutTimerUtil.LogOutListener logOutListener;
    private boolean isReadyToLogout = false;
    private Handler mHandler;
    private MyPageFragment myPageFragment;
    private LoginSelectionFragment loginSelectionFragment;
    private DashBoardFragment dashBoardFragment;
    private InfoFragment infoFragment;
    private TvFragment tvFragment;
    private InternetFragment internetFragment;
    private MessageFragment messageFragment;
    private MenuAdapter menuAdapter;
    private List<Menu> menuList = new ArrayList<>();
    private TextView time;
    private Timer timer;
    private Handler updateLabel;
    private RecyclerView recyclerViewMenu;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        setLanguage();
        initializeFragments();
        loadHomeFragment(R.id.container);
    }

    @SuppressLint("HandlerLeak")
    private void init() {
        listener = this;
        logOutListener = this;
        menuList = getMenuList();
        menuAdapter = new MenuAdapter(this, menuList, this);
        time = findViewById(R.id.time);
        TextView date = findViewById(R.id.date);
        logout = findViewById(R.id.logout);
        logout.setVisibility(View.GONE);
        language = findViewById(R.id.language);
        LinearLayout contentMainLL = findViewById(R.id.content_main);
        View contentMain = contentMainLL.findViewById(R.id.layout_content_main);
        recyclerViewMenu = contentMain.findViewById(R.id.menu_recycler_view);
        showHome(false);
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
        date.setText(CommonUtils.getHomePageDate());
        reScheduleTimer();
    }

    public void reScheduleTimer() {
        timer = new Timer();
        TimerTask timerTask = new MyTimerTask();
        timer.schedule(timerTask, 0, ONE_SECOND);
    }

    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            updateLabel.sendEmptyMessage(0);
        }
    }

    private void initializeFragments() {
        myPageFragment = new MyPageFragment();
        loginSelectionFragment = new LoginSelectionFragment();
        dashBoardFragment = new DashBoardFragment();
        infoFragment = new InfoFragment();
        tvFragment = new TvFragment();
        internetFragment = new InternetFragment();
        messageFragment = new MessageFragment();
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
            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(menuAdapter);
        }
    }

    @Override
    public void onMenuClickListener(int position) {
        if (position != -1) {
            selectFragment(position);
        }
    }

    public void loadHomeFragment(int containerId) {
        Runnable mPendingRunnable = () -> addHomeFragment(containerId, new LoginSelectionFragment());
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

    private DashBoardFragment getHomeFragment() {
        if (dashBoardFragment == null) {
            dashBoardFragment = new DashBoardFragment();
        }
        return dashBoardFragment;
    }

    private List<Menu> getMenuList() {
        menuList.clear();
        menuList.add(new Menu(R.drawable.ic_menu_my_page, getString(R.string.my_page)));
        menuList.add(new Menu(R.drawable.ic_menu_hospital_guide, getString(R.string.info)));
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
        }
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
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
        super.onDestroy();
    }

    private void selectFragment(int position) {
        switch (position) {
            case 0:
                loadFragments(new MyPageFragment(), true, MyPageFragment.TAG);
                break;
            case 1:
                loadFragments(new InfoFragment(), true, InfoFragment.TAG);
                break;
            case 2:
                loadFragments(new TvFragment(), true, TvFragment.TAG);
                break;
            case 3:
                loadFragments(new InternetFragment(), true, InternetFragment.TAG);
                break;
            case 4:
                loadFragments(new MessageFragment(), true, MessageFragment.TAG);
                break;
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
            Log.d("clearBackStack", String.valueOf(e));
        }
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: ");
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

    private boolean isClearBackStack() {
        if (getFragment() != null)
            return getFragment() instanceof MyPageFragment;
        return false;
    }

    public void showHome(boolean isShow) {
        if (recyclerViewMenu != null) {
            recyclerViewMenu.post(() -> {
                if (isShow) {
                    recyclerViewMenu.setVisibility(View.VISIBLE);
                    logout.setVisibility(View.VISIBLE);
                } else {
                    recyclerViewMenu.setVisibility(View.GONE);
                    logout.setVisibility(View.GONE);
                }
            });

        }
    }

    public Fragment getFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.container);
    }

    private void logoutDialog() {
        runOnUIThread(() -> createLogoutDialog(HomeActivity.this, getString(R.string.logout), getString(R.string.logout_message), true));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
        resetTimer();
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        Log.d(TAG, "onUserInteraction: ");
        resetTimer();
    }

    private void resetTimer() {
        LogoutTimerUtil.startLogoutPopupTimer(HomeActivity.this, this);
        Log.d("startLogoutPopupTimer", CommonUtils.getCurrentTime());
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
            //showHome(false);
        } else {
            isReadyToLogout = true;
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
        //Intent intent = new Intent(this, HomeActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //startActivity(intent);
        //this.finish();
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
}
