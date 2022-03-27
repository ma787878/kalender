package com.example.kalender.ui.home;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import com.example.kalender.database.DatenbankService;

import java.io.Serializable;

public class HomeFragment extends Fragment implements Serializable {

    private HomeViewModel homeViewModel;
    private View view;
    private DatenbankService dbservice;
    Spinner spinner_day;
    Spinner spinner_month;
    Spinner spinner_year;



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

        spinner_day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
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