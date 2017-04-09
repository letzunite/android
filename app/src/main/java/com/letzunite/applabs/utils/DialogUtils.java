package com.letzunite.applabs.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class DialogUtils {

    public static void showDialog(Activity activity, String message) {
        showDialog(activity, message, "Ok", null, null, null);
    }

    public static void showDialog(Activity activity, String message, String positiveBtnText,
                                  DialogInterface.OnClickListener positiveListener) {
        showDialog(activity, message, positiveBtnText, null, positiveListener, null);
    }

    public static void showDialog(Activity activity, String message,
                                  String positiveBtnText, String negativeBtnText,
                                  DialogInterface.OnClickListener positiveListener,
                                  DialogInterface.OnClickListener negativeListener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(message);

        if (null != positiveListener) {
            builder.setPositiveButton(positiveBtnText, positiveListener);
        } else {
            builder.setPositiveButton(positiveBtnText, null);
        }

        if (null != negativeBtnText && null != negativeListener) {
            builder.setNegativeButton(negativeBtnText, negativeListener);
        }

        builder.setCancelable(false);
        builder.show();
    }
}

