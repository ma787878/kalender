package com.example.kalender.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.PaintDrawable;
import android.view.View;
import android.widget.Button;

import com.example.kalender.R;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatenbankService {

    private static final String DB_NAME = "db";
    private static final int DB_VERSION = 5;
    SQLiteDatabase sqLiteDatabase;



    public DatenbankService(Context context, Boolean isReadOnly) {
        SQLiteDBHelper sqLiteDBHelper = new SQLiteDBHelper(context, DB_NAME, null, DB_VERSION);
        // Create the database tables again, this time because the database version increased so the onUpgrade() method is invoked.
        if (isReadOnly) {
            sqLiteDatabase = sqLiteDBHelper.getReadableDatabase();
        } else {
            sqLiteDatabase = sqLiteDBHelper.getWritableDatabase();
        }
    }

    public void destroy() {
        sqLiteDatabase.close();
    }


    public void speichern_home(String pDatum, String pNotiz, int pSport, int pVerabredung, int pArbeit, int pFeier) {
        ContentValues contentValues;
        contentValues = new ContentValues();
        contentValues.put("notiz", pNotiz);
        contentValues.put("datum", pDatum);
        contentValues.put("sport", pSport);
        contentValues.put("verabredung", pVerabredung);
        contentValues.put("arbeit", pArbeit);
        contentValues.put("feier", pFeier);

        Cursor cursor = sqLiteDatabase.query(SQLiteDBHelper.NOTIZ_TABLE_NAME, null, "datum = ?", new String[]{pDatum}, null, null, null);

        if (cursor.getCount() > 0) {
            //Notiz Updaten
            sqLiteDatabase.update(SQLiteDBHelper.NOTIZ_TABLE_NAME, contentValues, "datum = ?", new String[]{pDatum});
        } else {
            //Notiz hinzufügen
            sqLiteDatabase.insert(SQLiteDBHelper.NOTIZ_TABLE_NAME, null, contentValues);
        }
    }


    public String datenbankAuslesenNotiz() {
        Cursor cursor = sqLiteDatabase.query(SQLiteDBHelper.NOTIZ_TABLE_NAME, null, null, null, null, null, null);
        cursor.moveToLast();

        String notizenauslesen1 = "";
        String notizenauslesen2 = "";
        if (cursor.getCount() > 0) {
            while (!cursor.isBeforeFirst()) {
                notizenauslesen2 = cursor.getString(cursor.getColumnIndex("notiz")) + " " + cursor.getString(cursor.getColumnIndex("datum"));
                notizenauslesen1 = notizenauslesen1 + " " + notizenauslesen2;
                cursor.moveToPrevious();
            }
        }
        return "Gesamtanzahl: " + cursor.getCount() + notizenauslesen1;


    }

    public String getNotizOfChosenDate(String pDatum) {
        Cursor cursor = sqLiteDatabase.query(SQLiteDBHelper.NOTIZ_TABLE_NAME, null, "datum = ?", new String[]{pDatum}, null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() != 0) {
            return cursor.getString(cursor.getColumnIndex("notiz"));
        } else {
            return "";
        }
    }

    public boolean getValueOfCheckbox(String pDatum, String pCheckbox) {
        Cursor cursor = sqLiteDatabase.query(SQLiteDBHelper.NOTIZ_TABLE_NAME, null, "datum = ?", new String[]{pDatum}, null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() != 0) {
            if (cursor.getInt(cursor.getColumnIndex(pCheckbox)) == 1) {
                return true;
            } else {
                return false;
            }
        }
        else {
            return false;
        }



    }
}

