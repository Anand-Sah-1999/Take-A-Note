package com.example.takeanote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class SQLiteDataBase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Take_A_Note.db";
    public static final String TABLE_NAME = "Notes";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Title";
    public static final String COL_3 = "Text";

    public SQLiteDataBase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+TABLE_NAME+ "( ID INTEGER PRIMARY KEY AUTOINCREMENT, Title TEXT, Text TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public void insert(String head, String body){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, head);
        contentValues.put(COL_3, body);
        long result = database.insert(TABLE_NAME, null, contentValues);
    }

    public Cursor view(){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return cursor;
    }

    public int delete(int position){
        SQLiteDatabase database = this.getWritableDatabase();
        return database.delete(TABLE_NAME, "ID = ?", new String[]{String.valueOf(position)});

    }

    public void edit(int id, String head, String body){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, head);
        contentValues.put(COL_3, body);
        database.update(TABLE_NAME, contentValues, "ID = ?", new String[]{String.valueOf(id)});
    }
}






