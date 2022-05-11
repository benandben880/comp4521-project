package com.example.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    CustomCalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendarView=(CustomCalendarView) findViewById(R.id.returntomain);
    }
}