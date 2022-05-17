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

public class ToDoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        TextView demo = (TextView) findViewById(R.id.todoText);
        demo.setText("the todo activity");

        BottomNavigationView Nav = findViewById(R.id.bottom_nav);
        Nav.setOnItemSelectedListener(navListener);

        MenuItem selectedItem = Nav.getMenu().getItem(1);
        selectedItem.setChecked(true);
    }

    private NavigationBarView.OnItemSelectedListener navListener = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch(item.getItemId()){
                case R.id.nav_schedule:
                    Intent intent = new Intent(ToDoActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_memo:
                    Intent intent1 = new Intent(ToDoActivity.this, MemoActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.nav_todoList:
//                    Intent intent2 = new Intent(MainActivity.this, ToDoActivity.class);
//                    startActivity(intent2);
                    break;
            }
            return true;//selected item
        }
    };
}
