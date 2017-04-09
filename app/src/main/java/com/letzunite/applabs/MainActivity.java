package com.letzunite.applabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.facebook.FacebookSdk;
import com.letzunite.applabs.constants.Fragments;
import com.letzunite.applabs.fragments.IActivityFragmentInteraction;
import com.letzunite.applabs.fragments.LoginFragment;
import com.letzunite.applabs.fragments.SocialFragment;
import com.letzunite.applabs.utils.AppUtils;

public class MainActivity extends AppCompatActivity implements IActivityFragmentInteraction {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        addReplaceFragment(null, new LoginFragment(), false, false, null);
    }

    private void addReplaceFragment(String nextFragmentName, Fragment nextFragment, boolean addFragmentFlag,
                                    boolean addToBackStack, Bundle b) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Set Bundle if it is not null.
        if (null != b) {
            nextFragment.setArguments(b);
        }

        // Set CustomAnimations for Fragment Transactions.
        transaction.setCustomAnimations(R.anim.trans_right_in, R.anim.trans_right_out,
                R.anim.trans_left_in, R.anim.trans_left_out);

        if (addFragmentFlag) {
            transaction.add(R.id.container, nextFragment, nextFragmentName);
        } else {
            transaction.replace(R.id.container, nextFragment, nextFragmentName);
        }

        if (addToBackStack && !AppUtils.isStringEmpty(nextFragmentName)) {
            transaction.addToBackStack(nextFragmentName);
        }
        transaction.commit();
    }

    @Override
    public void onInteraction(Fragments fragments, View v, Bundle bundle) {
        switch (fragments) {
            case LOGIN_FRAGMENT:
//                if (null != b) {
//                    storeListBean = (StoreListBean) b.getSerializable(KeyConstants.STORE_LIST_EXTRA);
//                }
//                nextFragment = new StoreFragment();
                addReplaceFragment(fragments.name(), new SocialFragment(), false, true, null);
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
