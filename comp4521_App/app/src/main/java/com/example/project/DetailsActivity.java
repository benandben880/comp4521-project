package com.example.project;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

public class DetailsActivity extends AppCompatActivity {
    TextView mDetails;
    Note note;
    NoteDatabase db;
    long id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);



        Intent i = getIntent();
        id = getIntent().getLongExtra("IDName",0);
        //Log.d("id", "id is: "+id);

        //Debug -> show if id is get correctly
        Toast.makeText(this,"ID -> " + id, Toast.LENGTH_SHORT).show();

        db = new NoteDatabase(this);
        note = db.getNote(id);
        getSupportActionBar().setTitle(note.getTitle());

        mDetails = findViewById(R.id.detailsOfNote);
        mDetails.setText(note.getContent());
        mDetails.setMovementMethod(new ScrollingMovementMethod());


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                //Debug
                //Toast.makeText(DetailsActivity.this, "Delete btn is Clicked", Toast.LENGTH_SHORT).show();
                db.deleteNote(note.getID());
                //Debug
                Toast.makeText(getApplicationContext(), "Note is deleted", Toast.LENGTH_SHORT).show();
                goToMain();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.edit){
            //send user to edit activity
            Toast.makeText(this,"Edit Note",Toast.LENGTH_SHORT).show();

            Intent i = new Intent(this, EditActivity.class);
            i.putExtra("IDName", note.getID());
            //Log.d("idname", "ID name is: " + note.getID()); //work
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void goToMain() {
        Intent i = new Intent(this,MemoActivity.class);
        startActivity(i);
    }
}