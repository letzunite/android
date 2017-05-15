package com.letzunite.applabs.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.letzunite.applabs.R;
import com.letzunite.applabs.constants.Config;
import com.letzunite.applabs.constants.Fragments;
import com.letzunite.applabs.logger.Logger;
import com.letzunite.applabs.logger.LoggerEnable;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListeners();
    }

    private void setListeners() {
        currentView.findViewById(R.id.et_dob).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    final EditText et = (EditText) v;
                    GregorianCalendar c = (GregorianCalendar) v.getTag();
                    if (c == null) {
                        c = new GregorianCalendar();
                    }
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH);
                    int day = c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear,
                                              int dayOfMonth) {
                            et.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                            et.setTag(new GregorianCalendar(year, monthOfYear, dayOfMonth));
                        }
                    }, year, month, day);
                    datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                    datePickerDialog.setCancelable(false);
                    datePickerDialog.getDatePicker().setCalendarViewShown(false);
                    datePickerDialog.show();
                } catch (Exception e) {
                    Logger.logE(Config.TAG, e, CLASS_NAME, " >> setListeners >> Exception: ", e);
                }
            }
        });
    }

    public void setListener(IActivityFragmentInteraction listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
//                String occupation = ((EditText) currentView.findViewById(R.id.occupation)).getText().toString();
//                Logger.logD(Config.TAG, CLASS_NAME, " >> onClick >> Occupation: " + occupation);
                listener.onInteraction(Fragments.REGISTER2_FRAGMENT, null, null);
                break;
        }
    }
}
