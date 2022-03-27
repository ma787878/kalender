package com.example.kalender.ui.home;

import android.graphics.Paint;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.kalender.MainActivity;
import com.example.kalender.R;
import com.example.kalender.database.Datenbank;
import com.example.kalender.database.DatenbankService;

import java.io.Serializable;

public class HomeFragment extends Fragment implements Serializable {

    private HomeViewModel homeViewModel;
    private View view;
    private DatenbankService dbservice;
    Spinner spinner_day;
    Spinner spinner_month;
    Spinner spinner_year;



    /*String [] days = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
            "11", "12", "13", "14", "15", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    String [] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
            "11", "12"};
    String [] years = {"2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031",
            "2032"};*/


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        dbservice = new DatenbankService(getContext(), Boolean.FALSE);

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        view = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = view.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });


        spinner_day = (Spinner) view.findViewById(R.id.day_spinner_heute);
        spinner_month = (Spinner) view.findViewById(R.id.month_spinner_heute);
        spinner_year = (Spinner) view.findViewById(R.id.year_spinner_heute);
        //ArrayAdapter adapter= new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,days);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (spinner_day != null) {
            Toast.makeText(getContext(), "Spinnerday defined", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "Spinner not defined", Toast.LENGTH_LONG).show();
        }


       //spinner_day.setAdapter(adapter);

        spinner_day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String newItem = spinner_day.getSelectedItem().toString();
                Toast.makeText(getContext(),newItem,Toast.LENGTH_LONG).show();

                showNotizOfChoosenDate();
                setCheckboxes();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinner_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String newItem = spinner_month.getSelectedItem().toString();
                Toast.makeText(getContext(),newItem,Toast.LENGTH_LONG).show();

                showNotizOfChoosenDate();
                setCheckboxes();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String newItem = spinner_year.getSelectedItem().toString();
                Toast.makeText(getContext(),newItem,Toast.LENGTH_LONG).show();

                showNotizOfChoosenDate();
                setCheckboxes();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbservice.destroy();

    }



    public String datumToStrimg (){
        Spinner day = (Spinner) view.findViewById(R.id.day_spinner_heute);
        String dayString = day.getSelectedItem().toString();
        Spinner month = (Spinner) view.findViewById(R.id.month_spinner_heute);
        String monthString = month.getSelectedItem().toString();
        Spinner year = (Spinner) view.findViewById(R.id.year_spinner_heute);
        String yearString = year.getSelectedItem().toString();


        return yearString + monthString + dayString;
    }




    public void showNotizOfChoosenDate(){
        EditText notizfeld = (EditText) view.findViewById(R.id.edittext_notizfeldHeute);
        notizfeld.setText(dbservice.getNotizOfChosenDate(datumToStrimg()));
    }

    public void setCheckboxes(){

        CheckBox sport = (CheckBox) view.findViewById(R.id.button_sport);
        dbservice.getValueOfCheckbox(datumToStrimg(), "sport");
        sport.setChecked(dbservice.getValueOfCheckbox(datumToStrimg(), "sport"));

        CheckBox feier = (CheckBox) view.findViewById(R.id.button_feier);
        dbservice.getValueOfCheckbox(datumToStrimg(), "feier");
        feier.setChecked(dbservice.getValueOfCheckbox(datumToStrimg(), "feier"));

        CheckBox verabredet = (CheckBox) view.findViewById(R.id.button_verabredung);
        dbservice.getValueOfCheckbox(datumToStrimg(), "verabredung");
        verabredet.setChecked(dbservice.getValueOfCheckbox(datumToStrimg(), "verabredung"));

        CheckBox arbeit = (CheckBox) view.findViewById(R.id.button_arbeitSchule);
        dbservice.getValueOfCheckbox(datumToStrimg(), "arbeit");
        arbeit.setChecked(dbservice.getValueOfCheckbox(datumToStrimg(), "arbeit"));

    }



}