package com.example.kalender.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteDBHelper extends SQLiteOpenHelper {
    public static final String NOTIZ_TABLE_NAME = "notiz";

    public SQLiteDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createNotizTable = "CREATE TABLE notiz(datum STRING, notiz STRING, sport INT, verabredung INT, arbeit INt, feier INt);";
        db.execSQL(createNotizTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String createNotizTable = "DROP TABLE notiz;";
        db.execSQL(createNotizTable);
        this.onCreate(db);
    }
}
