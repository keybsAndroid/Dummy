package com.royalcommission.bs.views.interfaces;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.royalcommission.bs.modules.api.listener.RetrofitListener;
import com.royalcommission.bs.modules.utils.ButtonTag;
import com.royalcommission.bs.views.dialogs.DialogClickListener;

import io.reactivex.Observable;


/**
 * Created by Prashant on 10/18/2018.
 */
public interface IBaseView {

    void showToastMessage(String message);

    void showSnackBarMessage(View view, String message, int color);

    void showMessageAlert(boolean isSingleButton, ButtonTag tag, String title, String message, String positiveButtonName, String negativeButtonName, DialogClickListener dialogClickListener);

    void showMessageAlert(String title, String message);

    void showServerError(String responseError);

    void handleError(String title, String message);

    void hideKeyboard();

    void hideKeyboard(View view);

    void runOnUIThread(Runnable runnable, long delay);

    void runOnUIThread(Runnable runnable);

    boolean isNetworkConnected();

    void showNoInternetConnection();

    void addHomeFragment(int containerId, Fragment fragmentHome);

    void addFragment(final int containerId, final Fragment fragment, final boolean addToBackStack, final String tag);

    boolean isGreaterThanLolipop();

    <T> void processRequest(Observable<Object> objectObservable, boolean isSilentRequest, boolean isNormalProgressbar, String message, RetrofitListener listener, Class<T> desiredClass);

    void addHomeFragment(Fragment fragmentHome);

    void addFragment(final Fragment fragment, final boolean addToBackStack);

    void finishActivity();

    boolean isValidString(String stringToBeValid);

    boolean isValidInteger(int intToBeValid);

    void showLoggingInProgressBar();

    void showLoggingInProgressBar(String loaderMessage);

    void hideLoggingInProgressBar();

    void showCustomProgressBar(String message);

    void hideCustomProgressBar();
}
