package com.qanawat.views.fragments.base;


import android.app.admin.DevicePolicyManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.View;


import com.qanawat.modules.api.listener.RetrofitListener;
import com.qanawat.modules.utils.ButtonTag;
import com.qanawat.modules.utils.CommonUtils;
import com.qanawat.views.activities.base.IBaseView;
import com.qanawat.views.interfaces.DialogClickListener;

import retrofit2.Call;


/**
 * A simple {@link Fragment} subclass.
 */
public class BaseDialogFragment extends DialogFragment implements IBaseView {

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
        runOnUIThread(new Runnable() {
            @Override
            public void run() {
                if (getActivity() != null && message != null)
                    ((IBaseView) getActivity()).showToastMessage(message);
            }
        });
    }

    @Override
    public void handleError(final String title, final String message) {
        runOnUIThread(new Runnable() {
            @Override
            public void run() {
                if (getActivity() != null)
                    ((IBaseView) getActivity()).handleError(title, message);
            }
        });
    }

    @Override
    public void showMessageAlert(final boolean isSingleButton, final ButtonTag tag, final String title, final String message, final String positiveButtonName, final String negativeButtonName, final DialogClickListener dialogClickListener) {
        runOnUIThread(new Runnable() {
            @Override
            public void run() {
                if (getActivity() != null)
                    ((IBaseView) getActivity()).showMessageAlert(isSingleButton, tag, title, message, positiveButtonName, negativeButtonName, dialogClickListener);
            }
        });
    }

    @Override
    public void showAPIError() {
        runOnUIThread(new Runnable() {
            @Override
            public void run() {
                if (getActivity() != null)
                    ((IBaseView) getActivity()).showAPIError();
            }
        });
    }

    @Override
    public void showNormalProgress(final String message) {
        runOnUIThread(new Runnable() {
            @Override
            public void run() {
                if (getActivity() != null)
                    ((IBaseView) getActivity()).showNormalProgress(message);
            }
        });
    }

    @Override
    public void hideNormalProgress() {
        runOnUIThread(new Runnable() {
            @Override
            public void run() {
                if (getActivity() != null)
                    ((IBaseView) getActivity()).hideNormalProgress();
            }
        });
    }

    @Override
    public void hideKeyboard() {
        runOnUIThread(new Runnable() {
            @Override
            public void run() {
                if (getActivity() != null)
                    ((IBaseView) getActivity()).hideKeyboard();
            }
        });
    }

    @Override
    public void hideKeyboard(final View view) {
        runOnUIThread(new Runnable() {
            @Override
            public void run() {
                if (getActivity() != null)
                    ((IBaseView) getActivity()).hideKeyboard(view);
            }
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
    public <T> void processRequest(Call<Object> objectObservable, boolean isSilentRequest, boolean isNormalProgressbar, String message, RetrofitListener listener, final Class<T> desiredClass) {
        if (getActivity() != null)
            ((IBaseView) getActivity()).processRequest(objectObservable, isSilentRequest, isNormalProgressbar, message, listener, desiredClass);
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
    public boolean isValidString(String stringToBeValid) {
        return CommonUtils.isValidString(stringToBeValid);
    }

    @Override
    public boolean isValidInteger(int intToBeValid) {
        return CommonUtils.isValidInteger(intToBeValid);
    }

    @Override
    public void showProcessing(String printing) {
        if (getActivity() != null)
            ((IBaseView) getActivity()).showProcessing("Printing");
    }

    @Override
    public void hideProcessing() {
        if (getActivity() != null)
            ((IBaseView) getActivity()).hideProcessing();
    }

    @Override
    public DevicePolicyManager getDevicePolicyManager() {
        if (getActivity() != null)
            return ((IBaseView) getActivity()).getDevicePolicyManager();
        return null;
    }
}
