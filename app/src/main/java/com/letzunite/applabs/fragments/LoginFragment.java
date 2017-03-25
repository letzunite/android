package com.letzunite.applabs.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.letzunite.applabs.R;
import com.letzunite.applabs.constants.Config;
import com.letzunite.applabs.logger.Logger;
import com.letzunite.applabs.logger.LoggerEnable;

public class LoginFragment extends Fragment implements View.OnClickListener {

    // For Logging
    private final LoggerEnable CLASS_NAME = LoggerEnable.GetHttpURLConnection;

    private View currentView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Try to append strings by comma not by + operator. As, it will create many string objects in
        // string pool.
        Logger.logD(Config.TAG, CLASS_NAME, " >> onCreate ", " >> onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        try {
            currentView = inflater.inflate(R.layout.fragment_login_fragment, container, false);
            setDefaultViews();
            setListeners();
            setDefaultValues();
        } catch (Exception e) {

        }
        return currentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void setDefaultValues() {

    }

    private void setDefaultViews() {
        Typeface titleFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/bebas.ttf");
        ((TextView) currentView.findViewById(R.id.tv_app_title)).setTypeface(titleFont);
    }

    private void setListeners() {
        currentView.findViewById(R.id.tv_signup).setOnClickListener(this);
        currentView.findViewById(R.id.tv_signin).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        try {
            int id = v.getId();
            switch (id) {
                case R.id.tv_signup:
                    currentView.findViewById(R.id.et_confirm_password).setVisibility(View.VISIBLE);
                    currentView.findViewById(R.id.tv_signin).setVisibility(View.VISIBLE);
                    currentView.findViewById(R.id.tv_forgot_password).setVisibility(View.GONE);
                    currentView.findViewById(R.id.tv_signup).setVisibility(View.GONE);
                    ((Button) currentView.findViewById(R.id.btn_login)).setText(getResources().getString(R.string.Register_str));
                    break;
                case R.id.tv_signin:
                    currentView.findViewById(R.id.et_confirm_password).setVisibility(View.GONE);
                    currentView.findViewById(R.id.tv_signin).setVisibility(View.GONE);
                    currentView.findViewById(R.id.tv_forgot_password).setVisibility(View.VISIBLE);
                    currentView.findViewById(R.id.tv_signup).setVisibility(View.VISIBLE);
                    ((Button) currentView.findViewById(R.id.btn_login)).setText(getResources().getString(R.string.login_str));
                    break;

                default:
                    break;
            }
        } catch (Exception e) {
        }
    }
}
