package com.letzunite.applabs.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.letzunite.applabs.R;

/**
 * Utility Class for Fragment transactions in the app. Handles all types of scenarios by
 * exposing different overloaded functions.
 *
 * @author Akash Patra
 */
public class FragUtils {

    /**
     * Add Fragment without any bundle and not maintaining back stack.
     *
     * @param fm
     * @param nextFragment
     */
    public static void addFragment(FragmentManager fm, Fragment nextFragment) {
        addReplaceFragment(fm, null, nextFragment, true, false, false, null);
    }

    /**
     * Replace Fragment without any bundle and not maintaining back stack.
     *
     * @param fm
     * @param nextFragment
     */
    public static void replaceFragment(FragmentManager fm, Fragment nextFragment) {
        addReplaceFragment(fm, null, nextFragment, false, false, false, null);
    }

    /**
     * Add Fragment with bundle, maintaining back stack and animation.
     *
     * @param fm
     * @param nextFragment
     * @param b
     */
    public static void addFragment(FragmentManager fm, Fragment nextFragment, Bundle b) {
        addReplaceFragment(fm, null, nextFragment, true, true, true, b);
    }

    /**
     * Replace Fragment with bundle and maintaining back stack and animation.
     *
     * @param fm
     * @param nextFragment
     * @param b
     */
    public static void replaceFragment(FragmentManager fm, Fragment nextFragment, Bundle b) {
        addReplaceFragment(fm, null, nextFragment, false, true, true, b);
    }

    /**
     * Add/Replace Fragment depend on the different parameters.
     * Maintaining back stack, animation enabled and setting bundle.
     *
     * @param fm
     * @param nextFragmentName
     * @param nextFragment
     * @param addFragmentFlag
     * @param addToBackStack
     * @param animationEnabled
     * @param b
     */
    public static void addReplaceFragment(FragmentManager fm, String nextFragmentName, Fragment nextFragment, boolean addFragmentFlag,
                                          boolean addToBackStack, boolean animationEnabled, Bundle b) {
        FragmentTransaction transaction = fm.beginTransaction();

        // Set Bundle if it is not null.
        if (null != b) {
            nextFragment.setArguments(b);
        }

        // Set CustomAnimations for Fragment Transactions.
//        transaction.setCustomAnimations(R.anim.trans_left_in, R.anim.trans_right_out,
//                R.anim.trans_left_in, R.anim.trans_left_out);

        if (animationEnabled) {
            transaction.setCustomAnimations(R.anim.trans_zoom_enter, R.anim.trans_left_out,
                    R.anim.trans_left_in, R.anim.fade_out);
        }

        if (addFragmentFlag) {
            transaction.add(R.id.container, nextFragment, nextFragment.getClass().getSimpleName());
        } else {
            transaction.replace(R.id.container, nextFragment, nextFragment.getClass().getSimpleName());
        }

        if (addToBackStack && !AppUtils.isStringEmpty(nextFragment.getClass().getSimpleName())) {
            transaction.addToBackStack(nextFragment.getClass().getSimpleName());
        }
        transaction.commit();
    }

    public static void clearStack(FragmentManager fm) {
        // Remove All Fragments from the BackStack
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
