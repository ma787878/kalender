package com.example.kalender;

import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kalender.database.DatenbankService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.kalender.databinding.ActivityMainBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements Serializable {

    NavHostFragment navHostFragment;
    NavController navController;

    private ActivityMainBinding binding;
    AppBarConfiguration appBarConfiguration;

    DatenbankService dbservice;
    int checkbox_feier = 0;
    int checkbox_arbeitSchule =0;
    int checkbox_sport = 0;
    int checkbox_verabredet = 0;






    @Override
    protected void onCreate(Bundle savedInstanceState) {

        dbservice = new DatenbankService(getApplicationContext(), Boolean.FALSE);



        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbservice.destroy();

    }

    public void zurueckZurHauptansicht(View view)
    {

        NavController navController = Navigation.findNavController(view);
        navController.navigate(R.id.action_einfuegen_termin_to_navigation_dashboard);
        setContentView(binding.getRoot());

    }


    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration);
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

        Toast.makeText(this, "gespeichert",Toast.LENGTH_SHORT).show();

    }

}
