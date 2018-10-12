package com.example.westf.homework2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Digital_Clock extends Fragment implements Clock {

    TextView time;
    TextView date;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View digitalClock = inflater.inflate(R.layout.digital_clock, container, false);

        time = digitalClock.findViewById(R.id.time);
        date = digitalClock.findViewById(R.id.date);

        return digitalClock;
    }

    public void setTime(int[] time){
        String s = (check24Hour(time[0]) + " : " + String.format("%02d", time[1]) + " : " + String.format("%02d", time[2]));
        this.time.setText(s);
    }

    public void setDate(String date) {
        this.date.setText(date);
    }

    public int check24Hour(int hour) {
        if (hour > 12) {
            return hour - 12;
        } else {
            return hour;
        }
    }

    public TextView getTime() {
        return time;
    }

    public TextView getDate() {
        return date;
    }
}
