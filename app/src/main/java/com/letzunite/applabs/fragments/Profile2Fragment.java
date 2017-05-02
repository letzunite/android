package com.letzunite.applabs.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.letzunite.applabs.R;
import com.letzunite.applabs.constants.Config;
import com.letzunite.applabs.constants.Fragments;
import com.letzunite.applabs.logger.Logger;
import com.letzunite.applabs.logger.LoggerEnable;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile2Fragment extends Fragment implements View.OnClickListener {

    // For Logging
    private final LoggerEnable CLASS_NAME = LoggerEnable.Register2Fragment;

    private View currentView;
    private IActivityFragmentInteraction listener;

    public Profile2Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        currentView = inflater.inflate(R.layout.fragment_profile2, container, false);
        currentView.findViewById(R.id.btn_next).setOnClickListener(this);
        return currentView;
    }

    public void setListener(IActivityFragmentInteraction listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                String occupation = ((EditText) currentView.findViewById(R.id.occupation)).getText().toString();
                Logger.logD(Config.TAG, CLASS_NAME, " >> onClick >> Occupation: " + occupation);
                listener.onInteraction(Fragments.REGISTER2_FRAGMENT, null, null);
                break;
        }
    }
}
