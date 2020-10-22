package com.example.takeanote;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Main_Page extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    static AdapterClass adapterClass;
    static ArrayList<GetAndSetItems> notes = new ArrayList<>();
    SQLiteDataBase dataBase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        setArray();
        setViews();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Main_Page.this, New_Note.class);
                intent.putExtra("NEW","newNote");
                startActivity(intent);

            }
        });


    }

    private void setArray() {

        dataBase = new SQLiteDataBase(this);
        Cursor data = dataBase.view();
        notes = new ArrayList<>();
        while(data.moveToNext()) {
            notes.add(new GetAndSetItems(data.getInt(0),data.getString(1), data.getString(2)));
        }

    }

    private void setViews() {

        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        adapterClass = new AdapterClass(notes);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterClass);


        adapterClass.SetInterface(new AdapterClass.GetInterface() {

            @Override
            public void getDeletePos(int pos) {
//                AlertDialog alertDialog = new AlertDialog.Builder();
                dataBase.delete(notes.get(pos).getId());
                notes.remove(pos);
                adapterClass.notifyItemRemoved(pos);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_items, menu);

        final MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                String newText1 = newText.toLowerCase().trim();
                ArrayList<GetAndSetItems> searchedList = new ArrayList<>();
                for(GetAndSetItems item : notes){
                    if(item.getTitle1().toLowerCase().trim().contains(newText1)||item.getText1().toLowerCase().trim().contains(newText1)){
                        searchedList.add(item);
                    }
                }
                adapterClass = new AdapterClass(searchedList);
                recyclerView.setAdapter(adapterClass);
                return false;
            }
        });

        return true;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Exit?").setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).create().show();
    }
}
