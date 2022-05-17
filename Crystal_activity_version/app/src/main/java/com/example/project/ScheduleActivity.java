package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ScheduleActivity extends AppCompatActivity {
    CustomCalendarView calendarView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        calendarView=(CustomCalendarView) findViewById(R.id.returntomain);
        //TextView demo = (TextView) findViewById(R.id.scheduletext);
        //demo.setText("the schedule activity");

        BottomNavigationView Nav = findViewById(R.id.bottom_nav);
        Nav.setOnItemSelectedListener(navListener);
    }

    private NavigationBarView.OnItemSelectedListener navListener = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch(item.getItemId()){
                case R.id.nav_schedule:
                    Intent intent = new Intent(ScheduleActivity.this, ScheduleActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_memo:
                    Intent intent1 = new Intent(ScheduleActivity.this, MemoActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.nav_todoList:
                    Intent intent2 = new Intent(ScheduleActivity.this, ToDoActivity.class);
                    startActivity(intent2);
                    break;
            }
            return true;//selected item
        }
    };
}
