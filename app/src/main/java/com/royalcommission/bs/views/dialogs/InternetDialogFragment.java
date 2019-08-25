package com.royalcommission.bs.views.dialogs;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.royalcommission.bs.R;
import com.royalcommission.bs.views.activities.HomeActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class InternetDialogFragment extends BaseDialogFragment implements View.OnClickListener {

    private AlertDialog alertDialog;
    private long lastClickedTime;


    public InternetDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getActivity() != null) {
            alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setCanceledOnTouchOutside(false);
            if (!alertDialog.isShowing())
                alertDialog.show();
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_dialog_internet, null, false);
            alertDialog.setContentView(view);
            WebView webView = view.findViewById(R.id.web_view);
            webView.loadUrl(getString(R.string.google_url));
            WebSettings webSettings = webView.getSettings();
            webSettings.setBuiltInZoomControls(true);
            webSettings.setDisplayZoomControls(false);
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setUseWideViewPort(true);
            webSettings.setJavaScriptEnabled(true);
            webSettings.setSupportZoom(true);
            webView.setWebViewClient(new MyWebViewClient());
            view.findViewById(R.id.cancel).setOnClickListener(this);
            webView.setOnKeyListener((v, keyCode, event) -> {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    WebView webView1 = (WebView) v;

                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (webView1.canGoBack()) {
                            webView1.goBack();
                            return true;
                        }
                    }
                }

                return false;
            });
        }
        return alertDialog;
    }


    public class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            showHideProgress(true);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            showHideProgress(false);
        }
    }

    private void showHideProgress(boolean isShow) {
        if (getActivity() != null) {
            if (isShow) {
                ((HomeActivity) getActivity()).showProgress(true);
            } else {
                ((HomeActivity) getActivity()).hideProgress(true);
            }
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        if (getDialog().getWindow() != null) {
            WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            getDialog().getWindow().setAttributes(params);
        }
        //blockBackButtonPressWhenDialogOpen();
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            if (SystemClock.elapsedRealtime() - lastClickedTime < 1000)
                return;
            lastClickedTime = SystemClock.elapsedRealtime();
            if (view.getId() == R.id.cancel) {
                dismissAllowingStateLoss();
            }
        }
    }

}
