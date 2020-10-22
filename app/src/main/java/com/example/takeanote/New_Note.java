package com.example.takeanote;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class New_Note extends AppCompatActivity {

    EditText newNoteTitle, newNoteText;
    int selectedItemPosition;
    SQLiteDataBase dataBase;
    static ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent2 = getIntent();
        String titleText = intent2.getStringExtra("NEW");
        if(titleText.equals("newNote")){
            actionBar.setTitle("Create Note");
        }
        else{
            actionBar.setTitle("Edit Note");
        }
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        setViews();

        Intent intent = getIntent();
        newNoteTitle.setText(intent.getStringExtra("Title"));
        newNoteText.setText(intent.getStringExtra("Text"));
        selectedItemPosition = intent.getIntExtra("Position",1);
    }

    private void setViews() {
        newNoteTitle = findViewById(R.id.newNoteTitle);
        newNoteText = findViewById(R.id.newNoteText);
        dataBase = new SQLiteDataBase(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id){

            case R.id.saveNote:

                if(getIntent().getStringExtra("NEW").equals("newNote")) {

                    Toast.makeText(this, "Note Saved Successfully", Toast.LENGTH_SHORT).show();
                    dataBase.insert(newNoteTitle.getText().toString(),newNoteText.getText().toString());

                    Cursor data = dataBase.view();
                    Main_Page.notes = new ArrayList<>();

                    while(data.moveToNext()) {
                        Main_Page.notes.add(new GetAndSetItems(data.getInt(0),data.getString(1), data.getString(2)));
                  }
                    Main_Page.adapterClass.notifyDataSetChanged();
                    Intent intent = new Intent(New_Note.this, Main_Page.class);
                    startActivity(intent);
                    finishAffinity();
                }

                else if(getIntent().getStringExtra("NEW").equals("editNote")){

                    Toast.makeText(this, "Note Edited Successfully", Toast.LENGTH_SHORT).show();
                    dataBase.edit(Main_Page.notes.get(selectedItemPosition).getId() ,newNoteTitle.getText().toString() ,newNoteText.getText().toString());

                    Main_Page.notes.set(selectedItemPosition, new GetAndSetItems(Main_Page.notes.get(selectedItemPosition).getId() ,newNoteTitle.getText().toString() ,newNoteText.getText().toString()));
                    Main_Page.adapterClass.notifyDataSetChanged();
                    Intent intent = new Intent(New_Note.this, Main_Page.class);
                    startActivity(intent);
                    finishAffinity();
                }

                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
