package com.letzunite.applabs.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.letzunite.applabs.R;
import com.letzunite.applabs.constants.Config;
import com.letzunite.applabs.constants.Fragments;
import com.letzunite.applabs.logger.Logger;
import com.letzunite.applabs.logger.LoggerEnable;
import com.pixelcan.inkpageindicator.InkPageIndicator;

/**
 * A simple {@link Fragment} subclass.
 */
public class ParentRegisterFragment extends Fragment implements IActivityFragmentInteraction {

    // For Logging
    private final LoggerEnable CLASS_NAME = LoggerEnable.ParentRegisterFragment;

    private View currentView;
    private ViewPager viewPager;

    public ParentRegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        currentView = inflater.inflate(R.layout.fragment_parent_register, container, false);
        InkPageIndicator indicator = (InkPageIndicator) currentView.findViewById(R.id.indicator);
        viewPager = (ViewPager) currentView.findViewById(R.id.pager);
        viewPager.setAdapter(new RegisterPagerAdapter(getChildFragmentManager()));
//        ((CustomViewPager) viewPager).setSwipeEnabled(false);
        indicator.setViewPager(viewPager);

        return currentView;
    }

    @Override
    public void onInteraction(Fragments fragments, View v, Bundle bundle) {
        int currentPosition = viewPager.getCurrentItem();
        Logger.logD(Config.TAG, CLASS_NAME, " >> onInteraction >> currentPosition: " + currentPosition);

        switch (fragments) {
            case REGISTER1_FRAGMENT:
                viewPager.setCurrentItem(currentPosition + 1);
                break;
            case REGISTER2_FRAGMENT:
                viewPager.setCurrentItem(currentPosition + 1);
                break;
        }
    }

    private class RegisterPagerAdapter extends FragmentStatePagerAdapter {

        public RegisterPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new Profile1Fragment();
                    ((Profile1Fragment) fragment).setListener(ParentRegisterFragment.this);
                    break;
                case 1:
                    fragment = new Profile2Fragment();
                    ((Profile2Fragment) fragment).setListener(ParentRegisterFragment.this);
                    break;
                case 2:
                    fragment = new Profile3Fragment();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
