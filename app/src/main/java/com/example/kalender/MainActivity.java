package com.example.kalender;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kalender.database.Datenbank;
import com.example.kalender.database.DatenbankService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements Serializable /*implements AdapterView.OnItemSelectedListener*/{

    DatenbankService dbservice;
    Datenbank db;
    int checkbox_feier = 0;
    int checkbox_arbeitSchule =0;
    int checkbox_sport = 0;
    int checkbox_verabredet = 0;
    String day;
    String month;
    String year;
    String hour;
    String minute;
    String second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        dbservice = new DatenbankService(getApplicationContext(), Boolean.FALSE);



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



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbservice.destroy();

    }


    public String datumToStrimg (){
        Spinner day = (Spinner) findViewById(R.id.day_spinner_heute);
        String dayString = day.getSelectedItem().toString();
        Spinner month = (Spinner) findViewById(R.id.month_spinner_heute);
        String monthString = month.getSelectedItem().toString();
        Spinner year = (Spinner) findViewById(R.id.year_spinner_heute);
        String yearString = year.getSelectedItem().toString();

        return yearString + monthString + dayString;
    }


    public void notizSpeichern(View view) {
        EditText notiz = (EditText) findViewById(R.id.edittext_notizfeldHeute);
        String notizString = notiz.getText().toString();

        CheckBox feier = (CheckBox) findViewById(R.id.button_feier);
        if(feier.isChecked()){
            checkbox_feier =1;
        }
        else{
            checkbox_feier =0;
        }

        CheckBox arbeit = (CheckBox) findViewById(R.id.button_arbeitSchule);
        if(arbeit.isChecked()){
            checkbox_arbeitSchule =1;
        }
        else{
            checkbox_arbeitSchule =0;
        }

        CheckBox sport = (CheckBox) findViewById(R.id.button_sport);
        if(sport.isChecked()){
            checkbox_sport =1;
        }
        else{
            checkbox_sport =0;
        }

        CheckBox verabredet = (CheckBox) findViewById(R.id.button_verabredung);
        if(verabredet.isChecked()){
            checkbox_verabredet =1;
        }
        else{
            checkbox_verabredet =0;
        }

        dbservice.speichern_home( datumToStrimg(), notizString, checkbox_sport, checkbox_verabredet, checkbox_arbeitSchule, checkbox_feier);

        TextView notizDatenbank = (TextView) findViewById ( R.id.textView_viewNotizDatenbank );
        notizDatenbank.setText (dbservice.datenbankAuslesenNotiz());

        Toast.makeText(this, "notizSpeichern",Toast.LENGTH_SHORT).show();

    }





    // zu Dashboard(Terminansiccht)
    public void ansichtWechseln(View v)
    {
        setContentView(R.layout.fragment_termineinfuegen);
    }
    public void zurueckZurHauptansicht(View v)
    {
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_host_fragment);
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
     //   return Inhalt von RecyclerView

    }

    public void datensatzBearbeiten(View v)
    {
 /*       String pAktivitaet;
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
        }*/
    }



    public void TerminLoeschen(View v)
    {
   /*     String pAktivitaet;
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
        }*/
    }






}
