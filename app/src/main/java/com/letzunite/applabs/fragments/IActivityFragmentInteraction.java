/**
 *
 */
package com.letzunite.applabs.fragments;

import android.os.Bundle;
import android.view.View;

import com.letzunite.applabs.constants.Fragments;

/**
 * Interface to communicate between Activity & Fragment.
 *
 * @author Akash Patra
 */
public interface IActivityFragmentInteraction {
    void onInteraction(Fragments fragments, View v, Bundle bundle);
}