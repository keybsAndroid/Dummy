package com.royalcommission.bs.views.fragments.base;


import android.animation.Animator;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.royalcommission.bs.R;
import com.royalcommission.bs.modules.api.listener.RetrofitListener;
import com.royalcommission.bs.modules.utils.ButtonTag;
import com.royalcommission.bs.views.dialogs.DialogClickListener;
import com.royalcommission.bs.views.interfaces.IBaseView;

import java.util.List;

import io.reactivex.Observable;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment implements IBaseView {

    private Animation animation, fadeAnimation;
    public int REVEAL_THREAD_INTERVAL = 500;

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        initializeAnimation();
    }

    private void initializeAnimation() {
        if (getContext() != null) {
            animation = AnimationUtils.loadAnimation(getContext(), R.anim.home_dashboard_grid_item_fade_in_anim);
            fadeAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fadein);
        }
    }

    @Override
    public void showToastMessage(String message) {
        if (getActivity() != null && message != null)
            ((IBaseView) getActivity()).showToastMessage(message);
    }

    @Override
    public void showSnackBarMessage(View view, String message, int color) {
        if (getActivity() != null)
            ((IBaseView) getActivity()).showSnackBarMessage(view, message, color);
    }

    @Override
    public void showMessageAlert(boolean isSingleButton, ButtonTag tag, String title, String message, String positiveButtonName, String negativeButtonName, DialogClickListener dialogClickListener) {
        if (getActivity() != null)
            ((IBaseView) getActivity()).showMessageAlert(isSingleButton, tag, title, message, positiveButtonName, negativeButtonName, dialogClickListener);
    }

    @Override
    public void showMessageAlert(String title, String message) {
        if (getActivity() != null)
            ((IBaseView) getActivity()).showMessageAlert(title, message);
    }

    @Override
    public void showServerError(String responseError) {
        runOnUIThread(() -> {
            if (getActivity() != null)
                ((IBaseView) getActivity()).showServerError(responseError);
        });
    }

    @Override
    public void handleError(String title, String message) {
        if (getActivity() != null)
            ((IBaseView) getActivity()).showMessageAlert(title, message);
    }

    @Override
    public void hideKeyboard() {
        if (getActivity() != null)
            ((IBaseView) getActivity()).hideKeyboard();
    }

    @Override
    public void hideKeyboard(View view) {
        if (getActivity() != null)
            ((IBaseView) getActivity()).hideKeyboard(view);
    }

    @Override
    public void runOnUIThread(Runnable runnable, long delay) {
        if (getActivity() != null)
            ((IBaseView) getActivity()).runOnUIThread(runnable, delay);
    }

    @Override
    public void runOnUIThread(Runnable runnable) {
        if (getActivity() != null)
            ((IBaseView) getActivity()).runOnUIThread(runnable);
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
        } else {
            return false;
        }
    }

    @Override
    public void showNoInternetConnection() {
        runOnUIThread(() -> {
            if (getActivity() != null)
                ((IBaseView) getActivity()).showNoInternetConnection();
        });
    }

    @Override
    public void addHomeFragment(int containerId, Fragment fragmentHome) {
        if (getActivity() != null)
            ((IBaseView) getActivity()).addHomeFragment(containerId, fragmentHome);
    }

    @Override
    public void addFragment(int containerId, Fragment fragment, boolean addToBackStack, String tag) {
        if (getActivity() != null)
            ((IBaseView) getActivity()).addFragment(containerId, fragment, addToBackStack, tag);
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
    public void addHomeFragment(Fragment fragmentHome) {

    }

    @Override
    public void addFragment(Fragment fragment, boolean addToBackStack) {

    }

    @Override
    public void finishActivity() {

    }

    @Override
    public void onResume() {
        super.onResume();
        hideKeyboard();
    }


    public void addChildFragment(int containerId, Fragment fragment, boolean addToBackStack, String tag) {
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            Fragment fragmentByTag = getChildFragmentManager().findFragmentByTag(tag);

            List<Fragment> fragList = getChildFragmentManager().getFragments();
            for (Fragment frag : fragList) {
                if (frag == fragment) {
                    fragmentTransaction.show(fragment);
                    fragmentTransaction.commitAllowingStateLoss();
                    getChildFragmentManager().executePendingTransactions();
                    return;
                }
            }

            if (fragmentByTag != null) {
                fragmentTransaction.show(fragmentByTag).commit();
            } else {
                if (isGreaterThanLolipop())
                    fragmentTransaction.setCustomAnimations(R.animator.enter, R.animator.exit, R.animator.pop_enter, R.animator.pop_exit);
                fragmentTransaction.replace(containerId, fragment);
                if (addToBackStack)
                    fragmentTransaction.addToBackStack(tag);
                fragmentTransaction.commitAllowingStateLoss();
                getChildFragmentManager().executePendingTransactions();
            }
        }
    }

    public void reveal(final View container) {
        runOnUIThread(() -> {
            if (container != null) {
                int finalRadius = Math.max(container.getWidth(), container.getHeight());
                Animator animator;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    animator = ViewAnimationUtils.createCircularReveal(container, 0, 0, 0, finalRadius);
                    animator.setInterpolator(new AccelerateInterpolator());
                    animator.setDuration(REVEAL_THREAD_INTERVAL);
                    animator.start();
                }
            }
        });
    }

    public void startAnimation(View view) {
        if (isGreaterThanLolipop() && animation != null && view != null) {
            view.startAnimation(animation);
        }
    }

    public void startFadeAnimation(View view) {
        if (isGreaterThanLolipop() && fadeAnimation != null && view != null) {
            view.startAnimation(fadeAnimation);
        }
    }

    public boolean isValidString(String stringToBeValid) {
        return stringToBeValid != null && !stringToBeValid.equalsIgnoreCase("");
    }

    @Override
    public boolean isValidInteger(int intToBeValid) {
        return intToBeValid > -1;
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


    protected void setTextViewValues(TextView textView, String value) {
        if (textView != null) {
            textView.post(() -> {
                if (isValidString(value)) {
                    textView.setText(value);
                } else {
                    textView.setText(getString(R.string.hyphen));
                }
            });
        }

    }

}
