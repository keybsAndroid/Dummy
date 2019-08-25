package com.royalcommission.bs.views.fragments.internet;


import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.Fragment;

import com.royalcommission.bs.R;
import com.royalcommission.bs.views.activities.HomeActivity;
import com.royalcommission.bs.views.fragments.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class InternetFragment extends BaseFragment {

    public static String TAG = InternetFragment.class.getSimpleName();

    public InternetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_internet, container, false);
    }


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //loadUrl(view.getContext());

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
    }

    private void loadUrl(Context context) {
        if (context != null) {
            CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder().build();
            if (isValidString(getString(R.string.google_url))) {
                try {
                    customTabsIntent.launchUrl(context, Uri.parse(getString(R.string.google_url)));
                } catch (ActivityNotFoundException e) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.google_url)));
                    if (intent.resolveActivity(context.getPackageManager()) != null) {
                        context.startActivity(intent);
                    } else {
                        showToastMessage(getString(R.string.no_browser));
                    }
                }
            }
        }
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

}
