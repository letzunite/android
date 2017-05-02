package com.letzunite.applabs.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.letzunite.applabs.R;
import com.letzunite.applabs.logger.LoggerEnable;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile3Fragment extends Fragment implements View.OnClickListener {

    // For Logging
    private final LoggerEnable CLASS_NAME = LoggerEnable.Register3Fragment;

    private View currentView;
    private IActivityFragmentInteraction listener;

    public Profile3Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        currentView = inflater.inflate(R.layout.fragment_profile3, container, false);
        currentView.findViewById(R.id.btn_submit).setOnClickListener(this);
        return currentView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                break;
        }
    }
}
