package com.example.kalender;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.sql.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Time;

public class MainActivity extends AppCompatActivity {

    Datenbank db;

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
    public void zurueckZurHauptansicht(View v) {setContentView(R.layout.fragment_dashboard); }

    // EditText name = (EditText)findViewById(R.id.aktivitaetEingabe);;
    public void neuerTermin(View v, String pAktivitaet, Date pDatum)
    {
        try
        {
            EditText termin = (EditText)findViewById(R.id.aktivitaetEingabe);
            pAktivitaet = termin.getText().toString();

            pDatum = (02-02-2002);

            db.datensatzEinfuegen("", pAktivitaet,xxx);
            zurueckZurHauptansicht(v);
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }





}
