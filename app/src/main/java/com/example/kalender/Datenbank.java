package com.example.kalender;
import android.widget.CalendarView;

import java.sql.*;

public class Datenbank {
    Connection con;
    //Datenbank db;




    public void create (){

        try {
            //Treiber laden und verbinden 
            Class.forName("org.h2.Driver");
            this.con = DriverManager.getConnection("jdbc:h2:file:~/data/test", "user","");

            // Statement Objekt erstellen 
            Statement stm = this.con.createStatement();

            // Tabelle erstellen 
            String sql_create = "CREATE TABLE IF NOT EXISTS termin(datum STRING, aktivitaet STRING, Uhrzeit STRING);";
            stm.execute(sql_create);


            stm.execute("CREATE TABLE IF NOT EXISTS notiz(datum DATE, notiz STRING);"); //Hier später noch die Stimmung als Boolean Wert

            // Datensatz einfügen 
            String sql_insert = "INSERT INTO termin(datum, aktivitaet, uhrzeit) VALUES(yyyy-MM-dd, 'aktivität', HH-mm-ss-ns)";
            stm.execute(sql_insert);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Datensatz auslesen
    //String sql_select = "SELECT * FROM termin";
    //ResultSet rs = stm.executeQuery(sql_select);
     //       while(rs.next()){
      //  System.out.println(rs.getString(1) + " " +
        //        rs.getString(2) + " " +
            //    rs.getString(3));
    //}


    //String sql_insert2 = "INSERT INTO notiz(datum, notiz) VALUES(?, ?)";
    //
    //            PreparedStatement stm = con.prepareStatement(sql_insert2);
    //
    //            stm.setDate(1, pDatum);
    //
    //            stm.setString(2, notiz);
    //
    //            stm.execute();

    public void datenSatzAuslesen(String parameterDatum)
    {
        (CalendarView) MainActivity.findViewById(R.id.calendarView);
        CalendarView date = CalendarView.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");

        curDate = sdf.format(date.getTime());


        parameterDatum = String.valueOf
        try {

            String sql_select = "SELECT * FROM termin WHERE datum='parameterDatum'";
            Statement stm = this.con.createStatement();
            ResultSet rs = stm.executeQuery(sql_select);
            while (rs.next()) {
                System.out.println(rs.getString(1) + " " +
                        rs.getString(2) + " " +
                        rs.getString(3));
            }
        }
        catch(Exception e) {
                e.printStackTrace();
            }
    }


    public void datensatzEinfuegen(String pDatum, String pAktivitaet, String pUhrzeit)
    {
        try {
            // Statement Objekt erstellen
            String sql_insert2 = "INSERT INTO termin(datum, aktivitaet, uhrzeit) VALUES(?, ?, ?)";
            PreparedStatement stm = con.prepareStatement(sql_insert2);
            stm.setString(1, pDatum);
            stm.setString(2, pAktivitaet);
            stm.setString(3, pUhrzeit);
            stm.execute();
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }
} 
 