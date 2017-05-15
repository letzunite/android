package com.letzunite.applabs.fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.letzunite.applabs.R;
import com.letzunite.applabs.beans.ProfileBean;
import com.letzunite.applabs.constants.Fragments;
import com.letzunite.applabs.logger.LoggerEnable;
import com.letzunite.applabs.utils.AppUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile1Fragment extends Fragment implements View.OnClickListener {

    // For Logging
    private final LoggerEnable CLASS_NAME = LoggerEnable.Register1Fragment;

    private View currentView;
    private IActivityFragmentInteraction listener;

    // For Showing Error
    private TextInputLayout textInputLayout;

    public Profile1Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        currentView = inflater.inflate(R.layout.fragment_profile1, container, false);
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
                AppUtils.hideKeyboard(getActivity());
                if (textInputLayout != null) {
                    textInputLayout.setErrorEnabled(false);
                    textInputLayout.setError(null);
                }
                ProfileBean profile = getProfileDetails();
                boolean isAllFieldsValidated = validate(profile);
                if (isAllFieldsValidated) {
                    listener.onInteraction(Fragments.REGISTER1_FRAGMENT, null, null);
                }
                break;
        }
    }

    private ProfileBean getProfileDetails() {
        ProfileBean profile = new ProfileBean();
        profile.setFullName(((EditText) currentView.findViewById(R.id.et_full_name))
                .getText().toString());
        profile.setMobileNumber(((EditText) currentView.findViewById(R.id.et_mobile))
                .getText().toString());
        profile.setAadhaarNumber(((EditText) currentView.findViewById(R.id.et_aadhaar))
                .getText().toString());
        profile.setPresentAddress(((EditText) currentView.findViewById(R.id.et_present_address))
                .getText().toString());
        profile.setPincode(((EditText) currentView.findViewById(R.id.et_pincode))
                .getText().toString());
        profile.setState(((EditText) currentView.findViewById(R.id.et_state))
                .getText().toString());
        profile.setDistrict(((EditText) currentView.findViewById(R.id.et_district))
                .getText().toString());
        return profile;
    }

    private boolean validate(ProfileBean profile) {
        if (AppUtils.isStringEmpty(profile.getFullName())) {
            textInputLayout = ((TextInputLayout) currentView.findViewById(R.id.til_full_name));
            textInputLayout.setError("Please enter your full name");
            textInputLayout.requestFocus();
            return false;
        }
        if (AppUtils.isStringEmpty(profile.getMobileNumber())) {
            textInputLayout = ((TextInputLayout) currentView.findViewById(R.id.til_mobile));
            textInputLayout.setError("Please enter your mobile number");
            textInputLayout.requestFocus();
            return false;
        }
        if (AppUtils.isStringEmpty(profile.getAadhaarNumber())) {
            textInputLayout = ((TextInputLayout) currentView.findViewById(R.id.til_aadhaar));
            textInputLayout.setError("Please enter your aadhaar number");
            textInputLayout.requestFocus();
            return false;
        }
        if (AppUtils.isStringEmpty(profile.getPresentAddress())) {
            textInputLayout = ((TextInputLayout) currentView.findViewById(R.id.til_present_address));
            textInputLayout.setError("Please enter your present address");
            textInputLayout.requestFocus();
            return false;
        }
        if (AppUtils.isStringEmpty(profile.getPincode())) {
            textInputLayout = ((TextInputLayout) currentView.findViewById(R.id.til_pincode));
            textInputLayout.setError("Please enter your pincode");
            textInputLayout.requestFocus();
            return false;
        }
        if (AppUtils.isStringEmpty(profile.getState())) {
            textInputLayout = ((TextInputLayout) currentView.findViewById(R.id.til_state));
            textInputLayout.setError("Please enter your state");
            textInputLayout.requestFocus();
            return false;
        }
        if (AppUtils.isStringEmpty(profile.getDistrict())) {
            textInputLayout = ((TextInputLayout) currentView.findViewById(R.id.til_district));
            textInputLayout.setError("Please enter your district");
            textInputLayout.requestFocus();
            return false;
        }
        return true;
    }
}
