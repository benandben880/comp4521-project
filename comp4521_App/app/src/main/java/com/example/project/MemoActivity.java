package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

public class MemoActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    NoteAdapter adapter;
    List<Note> notes;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);
//        TextView demo = (TextView) findViewById(R.id.memoText);
//        demo.setText("the memo activity");
        BottomNavigationView Nav = findViewById(R.id.bottom_nav);
        Nav.setOnItemSelectedListener(navListener);

        MenuItem selectedItem = Nav.getMenu().getItem(2);
        selectedItem.setChecked(true);

        //memo -- start here~~
        toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //after setting adapter
        NoteDatabase db = new NoteDatabase(this);
        notes = db.getAllNotes();

        recyclerView = findViewById(R.id.listOfNotes);

        //after setting the adapter, we can set those to our recyclerview
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoteAdapter(this, notes);
        recyclerView.setAdapter(adapter);

    }

    private NavigationBarView.OnItemSelectedListener navListener = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch(item.getItemId()){
                case R.id.nav_schedule:
                    Intent intent = new Intent(MemoActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_memo:
//                    Intent intent1 = new Intent(MemoActivity.this, MemoActivity.class);
//                    startActivity(intent1);
                    break;
                case R.id.nav_todoList:
                    Intent intent2 = new Intent(MemoActivity.this, ToDoActivity.class);
                    startActivity(intent2);
                    break;
            }
            return true;//selected item
        }
    };

    //make the add btn onto the menu bar
    @Override
    //show add button in the menu bar
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.add){
            //click the add btn and a gray text pop up in the bottom of UI
            Toast.makeText(this, "Add btn is Clicked", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(this, AddNote.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
