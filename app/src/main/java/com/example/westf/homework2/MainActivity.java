package com.example.westf.homework2;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    BottomSheetBehavior bottomSheetBehavior;
    Button button;
    TabLayout MyTabs;
    ViewPager MyPage;
    ViewPageAdapter Adapter;
    ClockController ck;

    Button hourAdd, hourSubtract, minuteAdd, minuteSubtract, secondAdd, secondSubtract;
    Button dayAdd, daySubtract, monthAdd, monthSubtract, yearAdd, yearSubtract;
    Button timeDate, am, pm;
    Button newDC, newAC;
    Button undo, redo, currentTime;

    EditText seconds,  minutes, hours, days, months, years;

    int dcCount = 1;
    int acCount = 1;

    Edit edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Adapter = new ViewPageAdapter(getSupportFragmentManager());

        hourAdd = findViewById(R.id.hourAdd);
        hourSubtract = findViewById(R.id.hourSubtract);
        minuteAdd = findViewById(R.id.minuteAdd);
        minuteSubtract = findViewById(R.id.minuteSubtract);
        secondAdd = findViewById(R.id.secondAdd);
        secondSubtract = findViewById(R.id.secondSubtract);
        dayAdd = findViewById(R.id.dayAdd);
        daySubtract = findViewById(R.id.daySubtract);
        monthAdd = findViewById(R.id.monthAdd);
        monthSubtract = findViewById(R.id.monthSubtract);
        yearAdd = findViewById(R.id.yearAdd);
        yearSubtract = findViewById(R.id.yearSubtract);

        timeDate = findViewById(R.id.timeDate);
        am = findViewById(R.id.am);
        pm = findViewById(R.id.pm);

        seconds = findViewById(R.id.seconds);
        minutes = findViewById(R.id.minutes);
        hours = findViewById(R.id.hours);
        days = findViewById(R.id.days);
        months = findViewById(R.id.months);
        years = findViewById(R.id.years);

        currentTime = findViewById(R.id.currTime);

        newDC = findViewById(R.id.newDC);
        newAC = findViewById(R.id.newAC);

        linearLayout = findViewById(R.id.swipeupoptions);
        bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);

        MyTabs = findViewById(R.id.MyTabs);
        MyPage = findViewById(R.id.MyPage);

        undo = findViewById(R.id.undo);
        redo = findViewById(R.id.redo);

        MyTabs.setupWithViewPager(MyPage);
        MyTabs.getSelectedTabPosition();

        SetUpViewPager();

        main();
    }

    public void main() {
        ck = new ClockController(Adapter, MyTabs);
        ck.main();

        edit = new Edit(ck);

        getTime();

        // Setup all button on click events for the options view
        hourAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(hours.getText().toString()) < 12) {
                    hours.setText(Integer.toString(Integer.parseInt(hours.getText().toString()) + 1));
                } else {
                    hours.setText("1");
                }
            }
        });

        hourSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(hours.getText().toString()) > 1)
                    hours.setText(Integer.toString(Integer.parseInt(hours.getText().toString()) - 1));
                else
                    hours.setText("12");
            }
        });

        minuteAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(minutes.getText().toString()) < 59) {
                    minutes.setText(Integer.toString(Integer.parseInt(minutes.getText().toString()) + 1));
                } else {
                    minutes.setText("00");
                }
            }
        });

        minuteSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(minutes.getText().toString()) > 0) {
                    minutes.setText(Integer.toString(Integer.parseInt(minutes.getText().toString()) - 1));
                } else {
                    minutes.setText("59");
                }
            }
        });

        secondAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(seconds.getText().toString()) < 59) {
                    seconds.setText(Integer.toString(Integer.parseInt(seconds.getText().toString()) + 1));
                } else {
                    seconds.setText("00");
                }
            }
        });

        secondSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(seconds.getText().toString()) > 0) {
                    seconds.setText(Integer.toString(Integer.parseInt(seconds.getText().toString()) - 1));
                } else {
                    seconds.setText("59");
                }
            }
        });

        dayAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(days.getText().toString()) < 28) {
                    days.setText(Integer.toString(Integer.parseInt(days.getText().toString()) + 1));
                } else if (Integer.parseInt(days.getText().toString()) < 30 && checkMonth() == 30) {
                    days.setText(Integer.toString(Integer.parseInt(days.getText().toString()) + 1));
                } else if (Integer.parseInt(days.getText().toString()) < 31 && checkMonth() == 31) {
                    days.setText(Integer.toString(Integer.parseInt(days.getText().toString()) + 1));
                } else {
                    days.setText("1");
                }
            }
        });

        daySubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(days.getText().toString()) > 1) {
                    days.setText(Integer.toString(Integer.parseInt(days.getText().toString()) - 1));
                } else {
                    days.setText(Integer.toString(checkMonth()));
                }
            }
        });

        monthAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(months.getText().toString()) < 12) {
                    months.setText(Integer.toString(Integer.parseInt(months.getText().toString()) + 1));
                } else {
                    months.setText("1");
                }
            }
        });

        monthSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(months.getText().toString()) > 1) {
                    months.setText(Integer.toString(Integer.parseInt(months.getText().toString()) - 1));
                } else {
                    months.setText("12");
                }
            }
        });

        yearAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                years.setText(String.format("%04d", (Integer.parseInt(years.getText().toString()) + 1)));
            }
        });

        yearSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(years.getText().toString()) > 1) {
                    years.setText(String.format("%04d", (Integer.parseInt(years.getText().toString()) - 1)));
                }
            }
        });

        timeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.addNewundo(ck.getDisplayTime());
                ck.toggleTime();
                ck.toggleDate();
                ck.setTime(Integer.parseInt(hours.getText().toString()), Integer.parseInt(minutes.getText().toString()), Integer.parseInt(seconds.getText().toString()));
                ck.setDate(Integer.parseInt(days.getText().toString()), Integer.parseInt(months.getText().toString()), Integer.parseInt(years.getText().toString()));
            }
        });

        am.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ck.setTimePeriod("am");
            }
        });

        pm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ck.setTimePeriod("pm");
            }
        });

        newAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment("analog");
                ck.updateTime();
                ck.updateDate(((ck.time.month + 1) + " / " + ck.time.monthDay + " / " + String.format("%04d", ck.time.year)));
            }
        });

        newDC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment("digital");
                ck.updateTime();
                ck.updateDate(((ck.time.month + 1) + " / " + ck.time.monthDay + " / " + String.format("%04d", ck.time.year)));
            }
        });

        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.undo(ck.getDisplayTime());
            }
        });

        redo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.redo(ck.getDisplayTime());
            }
        });

        currentTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ck.toggleCurrent();
            }
        });
    }

    public void SetUpViewPager () {
        Clock ac = new Analog_Clock();
        Clock dc = new Digital_Clock();
        Adapter.AddFragmentPage((Fragment) ac, "Dig 1");
        Adapter.AddFragmentPage((Fragment) dc, "analog 2");
        MyPage.setAdapter(Adapter);
    }

    //update the text view with the current time and date
    public void getTime() {
        if (ck.getHour() > 12) {
            hours.setText(Integer.toString(ck.getHour() - 12));
        } else {
            hours.setText(Integer.toString(ck.getHour()));
        }
        minutes.setText(Integer.toString(ck.getMinute()));
        seconds.setText(Integer.toString(ck.getSecond()));
        days.setText(Integer.toString(ck.getDay()));
        months.setText(Integer.toString(ck.getMonth() + 1));
        years.setText(Integer.toString(ck.getYear()));
    }

    //add a new clock view to the fragment list.
    public void addFragment(String clock) {
        if (clock.equalsIgnoreCase("digital")) {
            Clock dc = new Digital_Clock();
            Adapter.AddFragmentPage((Fragment) dc, ("Analog " + (Integer.toString(dcCount++))));
            Adapter.notifyDataSetChanged();
        } else {
            Clock ac = new Analog_Clock();
            Adapter.AddFragmentPage((Fragment) ac, ("Dig " + (Integer.toString(acCount++))));
            Adapter.notifyDataSetChanged();
        }
    }

    public int checkMonth() {

        if (months.getText().toString().equalsIgnoreCase("2")) {
            return 28;
        }
        if (months.getText().toString().equalsIgnoreCase("4") ||
                months.getText().toString().equalsIgnoreCase("6") ||
                months.getText().toString().equalsIgnoreCase("9") ||
                months.getText().toString().equalsIgnoreCase("11")) {
            return 30;
        }
        if (months.getText().toString().equalsIgnoreCase("1") ||
                months.getText().toString().equalsIgnoreCase("3") ||
                months.getText().toString().equalsIgnoreCase("5") ||
                months.getText().toString().equalsIgnoreCase("7") ||
                months.getText().toString().equalsIgnoreCase("8") ||
                months.getText().toString().equalsIgnoreCase("10") ||
                months.getText().toString().equalsIgnoreCase("12")) {
            return 31;
        }
        return 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}