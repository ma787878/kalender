package com.example.kalender.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatenbankService {

    private static final String DB_NAME = "db";
    private static final int DB_VERSION = 1;
    SQLiteDatabase sqLiteDatabase;



    public DatenbankService(Context context){
        SQLiteDBHelper sqLiteDBHelper = new SQLiteDBHelper(context, DB_NAME, null, DB_VERSION);
        // Create the database tables again, this time because the database version increased so the onUpgrade() method is invoked.
        sqLiteDatabase = sqLiteDBHelper.getWritableDatabase();
    }

    public void destroy(){
        sqLiteDatabase.close();
    }

    public void notizEinfuegen(int pDatum, String pNotiz) {
        ContentValues contentValues;
        contentValues = new ContentValues();
        contentValues.put("notiz", pNotiz);
        contentValues.put("datum", pDatum);
        sqLiteDatabase.insert(SQLiteDBHelper.NOTIZ_TABLE_NAME, null, contentValues);

    }

   /* public String datenbankAuslesenNotiz(){

        try {
            String notizenauslesen1;
            String sql_select = "SELECT * FROM notiz";
            Statement stm = this.con.createStatement();
            ResultSet rs = stm.executeQuery(sql_select);
            String notizenauslesen2;
            while (rs.next()) {
                notizenauslesen2 = rs.getNString(1) + " " +
                        rs.getString(2);
                notizenauslesen1 = notizenauslesen1 + " " + notizenauslesen2;
            }

        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return notizenauslesen1;


    }*/
}

