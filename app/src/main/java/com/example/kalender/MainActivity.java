package com.example.kalender;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    Datenbank db;
    String day;
    String month;
    String year;
    String hour;
    String minute;
    String second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Datenbank db = new Datenbank();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        db.create();
    }



// zu Dashboard(Terminansiccht)
    public void ansichtWechseln(View v)
    {
        setContentView(R.layout.fragment_termineinfuegen);
    }
    public void zurueckZurHauptansicht(View v)
    {
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_host_fragment);rzyes5zse
    }

    // EditText name = (EditText)findViewById(R.id.aktivitaetEingabe);;
    public void neuerTermin(View v)
    {
        String pAktivitaet;
        String pDatum;
        String pUhrzeit;

        try
        {
            EditText termin = (EditText)findViewById(R.id.aktivitaetEingabe);
            pAktivitaet = termin.getText().toString();

            Spinner day_array = (Spinner) findViewById(R.id.day_spinner);
            String day = new String(String.valueOf(day_array));
            Spinner month_array = (Spinner) findViewById(R.id.month_spinner);
            String month = new String(String.valueOf(month_array));
            Spinner year_array = (Spinner) findViewById(R.id.year_spinner);
            String year = new String(String.valueOf(year_array));

            pDatum = day + "/" + month + "/" + year; //Hier anpassen

            Spinner hour_array = (Spinner) findViewById(R.id.hour_spinner);
            String hour = new String(String.valueOf(hour_array));
            Spinner minute_array = (Spinner) findViewById(R.id.minute_spinner);
            String minute = new String(String.valueOf(minute_array));
            Spinner second_array = (Spinner) findViewById(R.id.second_spinner);
            String second = new String(String.valueOf(second_array));

            pUhrzeit = hour + "/" + minute + "/" + second; //Hier anpassen

            db.datensatzEinfuegen(pDatum, pAktivitaet,pUhrzeit);
            zurueckZurHauptansicht(v);
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }


    }
    public void notizSpeichern(View view) {
        EditText notiz = (EditText) findViewById(R.id.edittext_notizfeldHeute);
        String notizString = notiz.getText().toString();
        db.notizEinfügen(24022022, notizString);
        TextView notizDatenbank = (TextView) findViewById ( R.id.textView_viewNotizDatenbank );
        notizDatenbank.setText (db.datenbankAuslesenNotiz());

    }
    public void datensatzAusgeben(View v)
    {
        CalendarView date = (CalendarView) findViewById(R.id.calendarView);
        date.getDate();
        String pDATE = new String(String.valueOf(date));

        db.datensatzAuslesen(pDATE);

    }

    public void setContentRecyclerView(View v)
    {
        CalendarView date = (CalendarView) findViewById(R.id.calendarView);
        date.getDate();
        String pDATE = new String(String.valueOf(date));
        RecyclerView content = db.datensatzAuslesen(pDATE);
    }

    public void terminAuswaehlen(View v)
    {
        RecyclerView item = (RecyclerView) findViewById(R.id.terminansicht);
        item.getChildItemId(v);
        return Inhalt von RecyclerView

    }

    public void datensatzBearbeiten(View v)
    {
        String pAktivitaet;
        String pDatum;
        String pUhrzeit;

        try
        {
            EditText termin = (EditText)findViewById(R.id.aktivitaetEingabe2);
            pAktivitaet = termin.getText().toString();

            Spinner day_array = (Spinner) findViewById(R.id.day_spinner2);
            String day = new String(String.valueOf(day_array));
            Spinner month_array = (Spinner) findViewById(R.id.month_spinner2);
            String month = new String(String.valueOf(month_array));
            Spinner year_array = (Spinner) findViewById(R.id.year_spinner2);
            String year = new String(String.valueOf(year_array));

            pDatum = day + "/" + month + "/" + year; //Hier anpassen

            Spinner hour_array = (Spinner) findViewById(R.id.hour_spinner2);
            String hour = new String(String.valueOf(hour_array));
            Spinner minute_array = (Spinner) findViewById(R.id.minute_spinner2);
            String minute = new String(String.valueOf(minute_array));
            Spinner second_array = (Spinner) findViewById(R.id.second_spinner2);
            String second = new String(String.valueOf(second_array));

            pUhrzeit = hour + "/" + minute + "/" + second; //Hier anpassen

            db.datensatzBearbeiten(pDatum, pAktivitaet,pUhrzeit, Ursprungswerte hinzufügen, die Überarbeitet werden);
            zurueckZurHauptansicht(v);
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }



    public void TerminLoeschen(View v)
    {
        String pAktivitaet;
        String pDatum;
        String pUhrzeit;

        try
        {
            EditText termin = (EditText)findViewById(R.id.aktivitaetEingabe2);
            pAktivitaet = termin.getText().toString();

            Spinner day_array = (Spinner) findViewById(R.id.day_spinner2);
            String day = new String(String.valueOf(day_array));
            Spinner month_array = (Spinner) findViewById(R.id.month_spinner2);
            String month = new String(String.valueOf(month_array));
            Spinner year_array = (Spinner) findViewById(R.id.year_spinner2);
            String year = new String(String.valueOf(year_array));

            pDatum = day + "/" + month + "/" + year; //Hier anpassen

            Spinner hour_array = (Spinner) findViewById(R.id.hour_spinner2);
            String hour = new String(String.valueOf(hour_array));
            Spinner minute_array = (Spinner) findViewById(R.id.minute_spinner2);
            String minute = new String(String.valueOf(minute_array));
            Spinner second_array = (Spinner) findViewById(R.id.second_spinner2);
            String second = new String(String.valueOf(second_array));

            pUhrzeit = hour + "/" + minute + "/" + second; //Hier anpassen

            db.datensatzLoeschen(pDatum, pAktivitaet,pUhrzeit);
            zurueckZurHauptansicht(v);
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }






}
