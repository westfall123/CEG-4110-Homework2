package com.example.westf.homework2;


import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

public class ClockController extends AppCompatActivity {

    Handler handler;
    android.text.format.Time time;
    Runnable currTime;
    ViewPageAdapter adapter;
    List<Fragment> fragments;

    int[] dispTime = new int[3];

    int seconds, minutes, hours, days, months, years = 0;

    String timePeriod = "am";

    boolean currentTime = true;
    boolean currentDate = true;

    TabLayout myTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public ClockController(ViewPageAdapter Adapter, TabLayout tabs) {
        adapter = Adapter;
        myTabs = tabs;
    }

    public void main() {

        time = new android.text.format.Time();
        time.setToNow();

        //thread that repeats every second updating the displayed time
        currTime = new Runnable() {
            @Override
            public void run() {
                time.setToNow();

                if (currentTime) {
                    displayedTime(check24Hour(time.hour), time.minute, time.second);
                    updateTime();
                } else {
                    customTime();
                }

                if (currentDate) {
                    updateDate((time.month + 1) + " / " + time.monthDay + " / " + String.format("%04d", time.year));
                } else {
                    customDate();
                }

                handler.postDelayed(currTime, 1000);
            }
        };

        handler = new Handler();
        handler.postDelayed(currTime, 1000);
    }

    //update only the left, right, and tab in focus to the current time.
    //Android fragment destroys fragments that arent on the screen
    public void updateTime() {
        fragments = adapter.getFragments();
        Clock left = (Clock) fragments.get(myTabs.getSelectedTabPosition());
        Clock focus = (Clock) fragments.get(myTabs.getSelectedTabPosition());
        Clock right = (Clock) fragments.get(myTabs.getSelectedTabPosition());
        left.setTime(getDisplayTime());
        focus.setTime(getDisplayTime());
        right.setTime(getDisplayTime());
    }

    //update only the left, right, and tab in focus to the current date.
    //Android fragment destroys fragments that arent on the screen
    public void updateDate(String date) {
        fragments = adapter.getFragments();
        Clock left = (Clock) fragments.get(myTabs.getSelectedTabPosition());
        Clock focus = (Clock) fragments.get(myTabs.getSelectedTabPosition());
        Clock right = (Clock) fragments.get(myTabs.getSelectedTabPosition());
        left.setDate(date);
        focus.setDate(date);
        right.setDate(date);
    }

    public String[] getInfo() {
        String[] info = {Integer.toString(time.hour), Integer.toString(time.minute), Integer.toString(time.second),
                Integer.toString(time.monthDay), Integer.toString(time.month), Integer.toString(time.year)};
        return info;
    }

    public int getHour() {
        return time.hour;
    }

    public int getMinute() {
        return time.minute;
    }

    public int getSecond() {
        return time.second;
    }

    public void toggleTime() {
        currentTime = false;
    }

    public void toggleDate() {
        currentDate = false;
    }

    public int getDay() {
        return time.monthDay;
    }

    public int getMonth() {
        return time.month;
    }

    public int getYear() {
        return time.year;
    }

    public void setTimePeriod(String period) {
        timePeriod = period;
    }

    public void setTime(int hour, int minute, int second) {
        this.hours = hour;
        this.minutes = minute;
        this.seconds = second;
    }

    public void setDate(int day, int month, int year) {
        this.days = day;
        this.months = month;
        this.years = year;
        System.out.println(month + "  " + this.months);
    }

    public void customDate() {
        System.out.println(months);
        updateDate(this.months + " / " + this.days + " / " + this.years);
    }

    public void customTime() {
        this.seconds += 1;
        checkSeconds();
        checkMinutes();
        checkHours();
        displayedTime( this.hours, this.minutes, this.seconds);
        updateTime();
    }

    public void checkHours() {

        if (this.hours == 13 && timePeriod == "pm"){
            checkDay();
        }

        if (this.hours > 12)
            this.hours = this.hours - 12;
    }

    public void checkMinutes() {
        if (this.minutes > 59) {
            this.minutes = this.minutes - 60;
            this.hours += 1;
        }
    }

    public void checkSeconds() {
        if (this.seconds > 59) {
            this.seconds = this.seconds - 60;
            this.minutes += 1;
        }
    }

    public void checkDay() {
        if (days < 28) {
            days += 1;
        } else if (days < 30 && testMonths() == 30) {
            days+= 1;
        } else if (days < 31 && testMonths() == 31) {
            days += 1;
        } else {
            days = 1;
            months += 1;
            checkMonth();
        }
    }

    public void checkMonth() {
        if (months > 12) {
            months = 1;
            years += 1;
        }
    }

    public int testMonths() {
        if (months == 2) {
            return 28;
        } else if (months == 4 || months == 6 || months == 9 || months == 11) {
            return 30;
        } else {
            return 31;
        }
    }

    public int check24Hour(int hour) {
        if (hour > 12) {
            return hour - 12;
        } else {
            return hour;
        }
    }

    public void setSeconds(String seconds) {
        this.seconds = Integer.parseInt(seconds);
    }

    public void setMinutes(String minutes) {
        this.minutes = Integer.parseInt(minutes);
    }

    public void setHours(String hours) {
        this.hours = Integer.parseInt(hours);
    }

    public void setDays(String days) {
        this.days = Integer.parseInt(days);
    }

    public void setMonths(String months) {
        this.months = Integer.parseInt(months);
    }

    public void setYears(String years) {
        this.years = Integer.parseInt(years);
    }

    public int[] getTime() {
        int[] time = {this.hours, this.minutes, this.seconds};
        return time;
    }

    public void undo(int[] time) {
        this.hours = time[0];
        this.minutes = time[1];
        this.seconds = time[2];
    }

    public void displayedTime(int hour, int minute, int second) {
        int[] dispTime = {hour, minute, second};
        this.dispTime = dispTime;
    }

    public int[] getDisplayTime () {
        return dispTime;
    }

    public void toggleCurrent() {
        currentTime = true;
        currentTime = true;
    }
}