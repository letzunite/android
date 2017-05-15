package com.letzunite.applabs.controller;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.letzunite.applabs.R;
import com.letzunite.applabs.constants.Fragments;
import com.letzunite.applabs.fragments.IActivityFragmentInteraction;
import com.letzunite.applabs.fragments.ParentRegisterFragment;
import com.letzunite.applabs.fragments.SocialFragment;
import com.letzunite.applabs.utils.FragUtils;

public class MainActivity extends AppCompatActivity implements IActivityFragmentInteraction {

    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager();
        // Checks if its new registration, then fill extra details.
        FragUtils.replaceFragment(fm, new ParentRegisterFragment());
    }

    @Override
    public void onInteraction(Fragments fragments, View v, Bundle bundle) {
        switch (fragments) {
            case LOGIN_FRAGMENT:
//                if (null != b) {
//                    storeListBean = (StoreListBean) b.getSerializable(KeyConstants.STORE_LIST_EXTRA);
//                }
//                nextFragment = new StoreFragment();
                FragUtils.replaceFragment(fm, new SocialFragment());
                break;
//            case STORE_FRAGMENT:
//                if (null != b) {
//                    storeBean = (StoreBean) b.getSerializable(KeyConstants.STORE_DETAILS_EXTRA);
//                }
//                nextFragment = new ElementListFragment();
//                addReplaceFragment(fragments.name(), nextFragment, true, true, b);
//                break;
        }
    }
}
