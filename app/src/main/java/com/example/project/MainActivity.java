package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView Nav = findViewById(R.id.bottom_nav);
        Nav.setOnItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ScheduleFragment()).commit();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.overflow_setting:
                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
                    Intent noti = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                    noti.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                    startActivity(noti);
                }else{
                    Intent notNoti = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    notNoti.setData(Uri.parse("package:" + getPackageName()));
                    startActivity(notNoti);
                }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private NavigationBarView.OnItemSelectedListener navListener = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch(item.getItemId()){
                case R.id.nav_schedule:
                    selectedFragment = new ScheduleFragment();
                    break;
                case R.id.nav_memo:
                    selectedFragment = new MemoFragment();
                    break;
                case R.id.nav_todoList:
                    selectedFragment = new TodoFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;//selected item
        }
    };
}