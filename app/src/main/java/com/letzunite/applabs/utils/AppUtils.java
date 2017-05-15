package com.letzunite.applabs.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.letzunite.applabs.constants.Config;
import com.letzunite.applabs.logger.LoggerEnable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by A15FMEWX on 1/9/2017.
 */
public class AppUtils {

    static AppUtils appUtils;
    // For Logging
    private final LoggerEnable CLASS_NAME = LoggerEnable.AppUtils;

    private AppUtils() {
    }

    public static AppUtils newInstance() {
        if (appUtils == null) {
            appUtils = new AppUtils();
        }
        return appUtils;
    }

    /**
     * Checks strings is null/empty/length==0
     *
     * @param s
     * @return
     */
    public static boolean isStringEmpty(String s) {
        return (s == null) || (s.trim().equals(""));
    }

    /**
     * Checks if the device is a tablet device or not.
     *
     * @param activity
     * @return
     */
    public static boolean isTablet(Activity activity) {
        return (activity.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * Check the context is active or not.
     *
     * @param activity
     * @return
     */
    public static boolean isContextAlive(Activity activity) {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && null != activity && !activity.isDestroyed()) ||
                (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1 && null != activity && !activity.isFinishing());
    }

    /**
     * Return desired String format
     *
     * @param time
     * @param type
     * @return
     */
    public static String changeToDateFormat(long time, int type) {
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        if (type == 1) {
            dateFormat = new SimpleDateFormat("dd MMM, yyyy"); // Store Date
        } else if (type == 2) {
            dateFormat = new SimpleDateFormat("dd MMM yy");
        }
        return dateFormat.format(new Date(time));
    }

    public static void hideKeyboard(Context context) {
        try {
            View view = ((Activity) context).getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            Log.e(Config.TAG, "Exception from hideKeyboard: " + e);
        }
    }
}
