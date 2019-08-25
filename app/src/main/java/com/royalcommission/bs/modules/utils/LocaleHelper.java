package com.royalcommission.bs.modules.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import androidx.appcompat.view.ContextThemeWrapper;
import android.view.View;


import com.royalcommission.bs.app.AppController;

import java.util.Locale;

/**
 * Created by Prashant on 6/28/2018.
 */
public class LocaleHelper {


    public static void setLocale(Context context, String language) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(context, language);
            return;
        }
        updateResourcesLegacy(context, language);
    }

    private static void updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        AppController.setLanguage(language);
        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);
        //context.createConfigurationContext(configuration);
        context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (context instanceof Activity)
                ((Activity) context).getWindow().getDecorView().setLayoutDirection(Locale.getDefault().getLanguage().equalsIgnoreCase(AppController.LANGUAGE_ARABIC)
                        ? View.LAYOUT_DIRECTION_RTL : View.LAYOUT_DIRECTION_LTR);
        }
    }

    @SuppressWarnings("deprecation")
    private static void updateResourcesLegacy(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        AppController.setLanguage(language);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        configuration.setLayoutDirection(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    public static void updateConfig(ContextThemeWrapper wrapper, String language) {
        if (language != null) {
            Locale locale = new Locale(language);
            Locale.setDefault(locale);
            Configuration configuration = new Configuration();
            configuration.setLocale(locale);
            wrapper.applyOverrideConfiguration(configuration);
        }
    }
}
