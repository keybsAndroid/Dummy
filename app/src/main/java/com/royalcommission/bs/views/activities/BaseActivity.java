package com.royalcommission.bs.views.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;
import com.royalcommission.bs.R;
import com.royalcommission.bs.app.AppController;
import com.royalcommission.bs.modules.api.listener.RetrofitListener;
import com.royalcommission.bs.modules.api.parser.RetrofitResponseParser;
import com.royalcommission.bs.modules.utils.ButtonTag;
import com.royalcommission.bs.modules.utils.KeyboardUtils;
import com.royalcommission.bs.modules.utils.LocaleHelper;
import com.royalcommission.bs.modules.utils.NetworkUtils;
import com.royalcommission.bs.views.dialogs.DialogClickListener;
import com.royalcommission.bs.views.dialogs.LoggingInProgressDialogFragment;
import com.royalcommission.bs.views.dialogs.MessageDialogFragment;
import com.royalcommission.bs.views.dialogs.NoInternetDialogFragment;
import com.royalcommission.bs.views.dialogs.ProgressDialogFragment;
import com.royalcommission.bs.views.interfaces.IBaseView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class BaseActivity extends AppCompatActivity implements IBaseView {

    public int SPLASH_SCREEN_INTERVAL = 2000;
    private String TAG = BaseActivity.class.getName();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private LoggingInProgressDialogFragment loggingInProgressDialogFragment;
    private ProgressDialogFragment progressDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        resetConfiguration();
    }

    private void resetConfiguration() {
        setLocale();
        setLayoutDirection();
    }

    private void setLocale() {
        if (AppController.isEnglish()) {
            LocaleHelper.setLocale(this, AppController.getLanguage());
        } else {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            LocaleHelper.setLocale(this, AppController.getLanguage());
        }
    }

    private void setLayoutDirection() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getWindow().getDecorView().setLayoutDirection(Locale.getDefault().getLanguage().equalsIgnoreCase(AppController.LANGUAGE_ARABIC)
                    ? View.LAYOUT_DIRECTION_RTL : View.LAYOUT_DIRECTION_LTR);
        }
    }

    @Override
    public void showToastMessage(String message) {
        if (!isFinishing() && message != null)
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSnackBarMessage(View view, String message, int colorResourceId) {
        if (!isFinishing() && view != null && message != null) {
            Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
            if (colorResourceId != 0) {
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(view.getContext(), colorResourceId));
            }
            snackbar.show();
        }
    }

    @Override
    public void handleError(String title, String message) {

    }

    @Override
    public void hideKeyboard() {
        KeyboardUtils.hideSoftInputKeyBoard(this);
    }

    @Override
    public void hideKeyboard(View view) {
        if (view != null)
            KeyboardUtils.hideSoftInputKeyBoard(view);
    }

    @Override
    public void runOnUIThread(Runnable runnable, long delay) {
        if (!isFinishing()) {
            new Handler(Looper.getMainLooper()).postDelayed(runnable, delay);
        }
    }

    @Override
    public void runOnUIThread(Runnable runnable) {
        if (!isFinishing())
            runOnUIThread(runnable, 0);
    }

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(this);
    }

    @Override
    public void showNoInternetConnection() {
        if (!isFinishing()) {
            DialogFragment fragment = NoInternetDialogFragment.newInstance();
            fragment.show(getSupportFragmentManager(), TAG);
        }
    }

    @Override
    public void addHomeFragment(int containerId, Fragment fragmentHome) {
        if (fragmentHome != null && getSupportFragmentManager() != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            if (isGreaterThanLolipop())
                fragmentTransaction.setCustomAnimations(R.animator.enter, R.animator.exit, R.animator.pop_enter, R.animator.pop_exit);
            fragmentTransaction.replace(containerId, fragmentHome, fragmentHome.getClass().getName());
            fragmentTransaction.commitAllowingStateLoss();
            getSupportFragmentManager().executePendingTransactions();
        }
    }

    @Override
    public void addFragment(int containerId, Fragment fragment, boolean addToBackStack, String tag) {
        if (fragment != null && getSupportFragmentManager() != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            if (isGreaterThanLolipop())
                fragmentTransaction.setCustomAnimations(R.animator.enter, R.animator.exit, R.animator.pop_enter, R.animator.pop_exit);
            fragmentTransaction.replace(containerId, fragment);
            if (addToBackStack)
                fragmentTransaction.addToBackStack(tag);
            fragmentTransaction.commitAllowingStateLoss();
            getSupportFragmentManager().executePendingTransactions();
        }
    }


    public Fragment findFragmentByTag(String tag) {
        return getSupportFragmentManager().findFragmentByTag(tag);
    }

    @Override
    public boolean isGreaterThanLolipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    @SuppressLint("CheckResult")
    @Override
    public <T> void processRequest(Observable<Object> objectObservable, boolean isSilentRequest, boolean isNormalProgressbar, String message, RetrofitListener listener, Class<T> desiredClass) {
        if (checkNullableObservable(objectObservable, listener)) {

            if (!isSilentRequest)
                showProgress(isNormalProgressbar);

            compositeDisposable.add(objectObservable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<Object>() {
                        @Override
                        public void onNext(Object response) {
                            if (!isSilentRequest)
                                hideProgress(isNormalProgressbar);

                            if (!isFinishing() && getBaseContext() != null && listener != null) {
                                listener.onSuccess(RetrofitResponseParser.convertInstanceOfObject(response, desiredClass));
                            }
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            if (!isSilentRequest)
                                hideProgress(isNormalProgressbar);
                            if (!isFinishing() && getBaseContext() != null && throwable != null && listener != null) {
                                if (!isSilentRequest)
                                    showServerError(getResponseError(throwable));
                                listener.onError(getResponseError(throwable));
                            }
                        }

                        @Override
                        public void onComplete() {

                        }
                    }));
        } else {
            hideProgress(isNormalProgressbar);
            showMessageAlert(null, getString(R.string.internal_error));
        }
    }

    @Override
    public void showMessageAlert(String title, String message) {
        if (message != null) {
            MessageDialogFragment fragment = MessageDialogFragment.newInstance(title, message);
            if (!isFinishing() && fragment != null) {
                fragment.show(getSupportFragmentManager(), "message");
            }

        }
    }

    @Override
    public void showServerError(String responseError) {
        MessageDialogFragment fragment = MessageDialogFragment.newInstance(getString(R.string.server_error_title), isValidString(responseError) ? responseError : getString(R.string.service_error));
        if (!isFinishing() && fragment != null)
            fragment.show(getSupportFragmentManager(), "message");
    }

    private String getResponseError(Throwable throwable) {
        if (throwable != null) {
            if (throwable instanceof HttpException) {
                return throwable.getMessage();
            } else if (throwable instanceof IOException) {
                return throwable.getMessage();
            } else {
                return getString(R.string.service_error);
            }
        }
        return getString(R.string.service_error);
    }

    public boolean checkNullableObservable(Observable<Object> objectObservable, final RetrofitListener listener) {
        return compositeDisposable != null && objectObservable != null && listener != null;
    }

    public boolean checkNullableObservableList(Observable<List<Object>> objectObservable, final RetrofitListener listener) {
        return compositeDisposable != null && objectObservable != null && listener != null;
    }

    public void showProgress(boolean isNormalProgressbar) {
        if (!isFinishing()) {
            runOnUIThread(() -> {
                if (isNormalProgressbar) {
                    showCustomProgressBar(getString(R.string.please_wait));
                } else {
                    showLoggingInProgressBar();
                }
            });
        }
    }

    public void showProgressWithMessage(String message) {
        if (!isFinishing()) {
            runOnUIThread(() -> {
                if (isValidString(message))
                    showCustomProgressBar(message);
            });
        }
    }


    public void hideProgress(boolean isNormalProgressbar) {
        if (!isFinishing()) {
            runOnUIThread(() -> {
                if (isNormalProgressbar) {
                    hideCustomProgressBar();
                } else {
                    hideLoggingInProgressBar();
                }
            });
        }
    }

    @Override
    public void showLoggingInProgressBar() {
        if (!isFinishing()) {
            loggingInProgressDialogFragment = LoggingInProgressDialogFragment.newInstance(null);
            loggingInProgressDialogFragment.show(this.getSupportFragmentManager(), "login");
        }
    }

    @Override
    public void showLoggingInProgressBar(String loaderMessage) {
        if (!isFinishing()) {
            loggingInProgressDialogFragment = LoggingInProgressDialogFragment.newInstance(loaderMessage);
            loggingInProgressDialogFragment.show(this.getSupportFragmentManager(), "login");
        }
    }

    @Override
    public void hideLoggingInProgressBar() {
        if (!isFinishing() && loggingInProgressDialogFragment != null){
            //loggingInProgressDialogFragment.dismiss();
            loggingInProgressDialogFragment.dismissAllowingStateLoss();
        }

    }

    @Override
    public void showCustomProgressBar(String message) {
        if (!isFinishing() && message != null) {
            progressDialogFragment = ProgressDialogFragment.newInstance(message);
            progressDialogFragment.show(this.getSupportFragmentManager(), "");
        }
    }

    @Override
    public void hideCustomProgressBar() {
        if (!isFinishing() && progressDialogFragment != null)
            progressDialogFragment.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearDisposable();
    }

    private void clearDisposable() {
        if (compositeDisposable != null)
            compositeDisposable.clear();
    }

    @Override
    public void addHomeFragment(Fragment fragmentHome) {

    }

    @Override
    public void addFragment(Fragment fragment, boolean addToBackStack) {

    }

    @Override
    public void finishActivity() {

    }

    @Override
    public boolean isValidString(String stringToBeValid) {
        return stringToBeValid != null && !stringToBeValid.trim().equalsIgnoreCase("");
    }

    @Override
    public boolean isValidInteger(int intToBeValid) {
        return intToBeValid > -1;
    }

    public void clearBackStack() {
        if (getSupportFragmentManager() != null)
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void fullScreen() {
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().getDecorView().setSystemUiVisibility(View.VISIBLE);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(View.GONE);
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void transparentToolbar() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            setWindowFlag(this, true);
        }
    }

    private void setWindowFlag(Activity activity, boolean on) {
        if (activity != null) {
            Window win = activity.getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            if (winParams != null) {
                if (on) {
                    winParams.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                } else {
                    winParams.flags &= ~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                }
                win.setAttributes(winParams);
            }
        }
    }

    @Override
    public void showMessageAlert(final boolean isSingleButton, final ButtonTag tag, final String title, final String message, final String positiveButtonName, final String negativeButtonName, final DialogClickListener dialogClickListener) {
        runOnUIThread(() -> {
            if (message != null) {
                MessageDialogFragment fragment = MessageDialogFragment.newInstance(isSingleButton, tag, title, message, positiveButtonName, negativeButtonName, dialogClickListener);
                if (!isFinishing() && fragment != null) {
                    fragment.show(getSupportFragmentManager(), "message");
                }
            }
        });
    }

}
