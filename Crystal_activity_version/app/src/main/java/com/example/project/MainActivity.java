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
        //        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_schedule);

        BottomNavigationView Nav = findViewById(R.id.bottom_nav);
        Nav.setOnItemSelectedListener(navListener);
        MenuItem selectedItem = Nav.getMenu().getItem(0);
        selectedItem.setChecked(true);

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
//                Toast.akeText(this, "clicked setting", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
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
            switch(item.getItemId()){
                case R.id.nav_schedule:
//                    Intent intent = new Intent(MainActivity.this, ScheduleActivity.class);
//                    startActivity(intent);
                    break;
                case R.id.nav_memo:
                    Intent intent1 = new Intent(MainActivity.this, MemoActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.nav_todoList:
                    Intent intent2 = new Intent(MainActivity.this, ToDoActivity.class);
                    startActivity(intent2);
                    break;
            }
            return true;//selected item
        }
    };
}