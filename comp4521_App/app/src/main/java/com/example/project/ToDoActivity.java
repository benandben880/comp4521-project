package com.example.project;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import com.example.project.Adapter.ToDoAdapter;
import com.example.project.Model.ToDoModel;
import com.example.project.Utils.DatabaseHandler;

import java.security.cert.CollectionCertStoreParameters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ToDoActivity extends AppCompatActivity implements DialogCloseListener{

    private RecyclerView tasksRecyclerView;
    private ToDoAdapter tasksAdapter;
    private FloatingActionButton fab;

    private List<ToDoModel> taskList;
    private DatabaseHandler db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
//        TextView demo = (TextView) findViewById(R.id.todoText);
//        demo.setText("the todo activity");
        getSupportActionBar().hide();

        db = new DatabaseHandler(this);
        db.openDatabase();

        taskList = new ArrayList<>();

        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new ToDoAdapter(db, this);
        tasksRecyclerView.setAdapter(tasksAdapter);

        fab = findViewById(R.id.fab);

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new RecyclerItemTouchHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        tasksAdapter.setTasks((taskList));

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AddNewTask.newInstance().show(getSupportFragmentManager(),AddNewTask.TAG);
            }
        });

        //nav bar
        BottomNavigationView Nav = findViewById(R.id.bottom_nav);
        Nav.setOnItemSelectedListener(navListener);
        MenuItem selectedItem = Nav.getMenu().getItem(1);
        selectedItem.setChecked(true);
        //end nav bar
    }

    @Override
    public void handleDialogClose(DialogInterface dialog){
        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();
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
