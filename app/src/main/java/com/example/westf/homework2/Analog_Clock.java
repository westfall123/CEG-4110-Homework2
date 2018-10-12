package com.example.westf.homework2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Analog_Clock extends Fragment implements Clock {

    AnalogClockView analogClockView;
    TextView date;

    public Analog_Clock() {}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View pageTwo = inflater.inflate(R.layout.analog_clock, container, false);

        analogClockView = pageTwo.findViewById(R.id.analogClock);
        date = pageTwo.findViewById(R.id.analogDate);

        return pageTwo;
    }

    public void setTime(int[] time) {
        analogClockView.setTime(time);
    }

    public void setDate(String date) {
        this.date.setText(date);
    }
}
