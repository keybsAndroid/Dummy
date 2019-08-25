package com.royalcommission.bs.views.dialogs;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.royalcommission.bs.R;
import com.royalcommission.bs.modules.api.listener.RetrofitListener;
import com.royalcommission.bs.modules.utils.ButtonTag;
import com.royalcommission.bs.modules.utils.CommonUtils;
import com.royalcommission.bs.views.interfaces.IBaseView;

import io.reactivex.Observable;


/**
 * A simple {@link Fragment} subclass.
 */
public class BaseDialogFragment extends DialogFragment implements IBaseView {

    public static final String VERIFICATION_FRAGMENT_BUNDLE_KEY_NATIONAL_ID = "national_id";
    public static final String VERIFICATION_FRAGMENT_BUNDLE_KEY_MOBILE_NUMBER = "mobile_number";
    public static final String VERIFICATION_FRAGMENT_BUNDLE_KEY_REGION = "region";
    public static final String VERIFICATION_FRAGMENT_BUNDLE_KEY_OTP = "otp";
    public static final String VERIFICATION_FRAGMENT_BUNDLE_KEY_PATIENT_ID = "id";
    public static final String VERIFICATION_FRAGMENT_BUNDLE_KEY_LOGIN_TYPE = "selectedLoginType";
    public static final String UPDATE_APPOINTMENT_BUNDLE_KEY_OTP = "update_appointment";

    public BaseDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        hideKeyboard();
    }

    @Override
    public void showToastMessage(final String message) {
        runOnUIThread(() -> {
            if (getActivity() != null && message != null)
                ((IBaseView) getActivity()).showToastMessage(message);
        });
    }

    @Override
    public void showSnackBarMessage(View view, String message, int color) {

    }

    @Override
    public void handleError(final String title, final String message) {
        runOnUIThread(() -> {
            if (getActivity() != null)
                ((IBaseView) getActivity()).handleError(title, message);
        });
    }

    @Override
    public void showMessageAlert(final boolean isSingleButton, final ButtonTag tag, final String title, final String message, final String positiveButtonName, final String negativeButtonName, final DialogClickListener dialogClickListener) {
        runOnUIThread(() -> {
            if (getActivity() != null)
                ((IBaseView) getActivity()).showMessageAlert(isSingleButton, tag, title, message, positiveButtonName, negativeButtonName, dialogClickListener);
        });
    }

    @Override
    public void showMessageAlert(final String title, final String message) {
        runOnUIThread(() -> {
            if (getActivity() != null)
                ((IBaseView) getActivity()).showMessageAlert(true, null, title, message, null, null, null);
            //((IBaseView) getActivity()).showMessageAlert(title, message);
        });
    }

    @Override
    public void showServerError(String responseError) {
        runOnUIThread(() -> {
            if (getActivity() != null)
                ((IBaseView) getActivity()).showMessageAlert(true, null, null, responseError != null ? responseError : getString(R.string.internal_error), null, null, null);
            //((IBaseView) getActivity()).showServerError(responseError);
        });
    }


    @Override
    public void hideKeyboard() {
        runOnUIThread(() -> {
            if (getActivity() != null)
                ((IBaseView) getActivity()).hideKeyboard();
        });
    }

    @Override
    public void hideKeyboard(final View view) {
        runOnUIThread(() -> {
            if (getActivity() != null)
                ((IBaseView) getActivity()).hideKeyboard(view);
        });
    }

    @Override
    public void runOnUIThread(Runnable runnable, long delay) {
        if (getActivity() != null)
            ((IBaseView) getActivity()).runOnUIThread(runnable, delay);
    }

    @Override
    public void runOnUIThread(Runnable runnable) {
        if (getActivity() != null)
            ((IBaseView) getActivity()).runOnUIThread(runnable, 0);
    }

    @Override
    public boolean isNetworkConnected() {
        boolean isInternetConnected;
        if (getActivity() != null) {
            isInternetConnected = ((IBaseView) getActivity()).isNetworkConnected();
            if (isInternetConnected) {
                return true;
            } else {
                showNoInternetConnection();
                return false;
            }
        }
        return false;
    }

    @Override
    public void showNoInternetConnection() {
        if (getActivity() != null)
            ((IBaseView) getActivity()).showNoInternetConnection();
    }

    @Override
    public void addHomeFragment(int containerId, Fragment fragmentHome) {

    }

    @Override
    public void addFragment(int containerId, Fragment fragment, boolean addToBackStack, String tag) {

    }

    @Override
    public void addHomeFragment(Fragment fragmentHome) {
        if (getActivity() != null)
            ((IBaseView) getActivity()).addHomeFragment(fragmentHome);
    }

    @Override
    public void addFragment(Fragment fragment, boolean addToBackStack) {
        if (getActivity() != null)
            ((IBaseView) getActivity()).addFragment(fragment, addToBackStack);
    }

    @Override
    public void finishActivity() {
        if (getActivity() != null)
            ((IBaseView) getActivity()).finishActivity();
    }

    @Override
    public boolean isGreaterThanLolipop() {
        return getActivity() != null && ((IBaseView) getActivity()).isGreaterThanLolipop();
    }

    @Override
    public <T> void processRequest(Observable<Object> objectObservable, boolean isSilentRequest, boolean isNormalProgressbar, String message, RetrofitListener listener, Class<T> desiredClass) {
        if (isNetworkConnected()) {
            if (getActivity() != null) {
                ((IBaseView) getActivity()).processRequest(objectObservable, isSilentRequest, isNormalProgressbar, message, listener, desiredClass);
            }
        }
    }

    @Override
    public boolean isValidString(String stringToBeValid) {
        return CommonUtils.isValidString(stringToBeValid);
    }

    @Override
    public boolean isValidInteger(int intToBeValid) {
        return false;
    }

    @Override
    public void showLoggingInProgressBar() {
        if (getActivity() != null)
            ((IBaseView) getActivity()).showLoggingInProgressBar();
    }

    @Override
    public void showLoggingInProgressBar(String loaderMessage) {
        if (getActivity() != null)
            ((IBaseView) getActivity()).showLoggingInProgressBar(loaderMessage);
    }

    @Override
    public void hideLoggingInProgressBar() {
        if (getActivity() != null)
            ((IBaseView) getActivity()).hideLoggingInProgressBar();
    }

    @Override
    public void showCustomProgressBar(String message) {
        if (getActivity() != null)
            ((IBaseView) getActivity()).showCustomProgressBar(message);
    }

    @Override
    public void hideCustomProgressBar() {
        if (getActivity() != null)
            ((IBaseView) getActivity()).hideCustomProgressBar();
    }

    public void blockBackButtonPressWhenDialogOpen() {
        if (getDialog() != null) {
            getDialog().setOnKeyListener((dialog, keyCode, event) -> (keyCode == android.view.KeyEvent.KEYCODE_BACK));
        }
    }


    public void avoidCancellingDialogOnTouchOutside() {
        if (getDialog() != null) {
            getDialog().setCancelable(false);
            getDialog().setCanceledOnTouchOutside(false);
        }
    }

}
