package com.example.kalender.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kalender.MainActivity;
import com.example.kalender.R;

public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
        Spinner spinner = (Spinner) findViewById( R.id.day_spinner_heute);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        TextView notizDatenbank = (TextView) findViewById ( R.id.textView_viewNotizDatenbank );
        notizDatenbank.setText ("ok");
        Toast.makeText(parent.getContext(), "Spinner.itemSelected",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(parent.getContext(), "Spinner.nothingSelected",Toast.LENGTH_SHORT).show();
        // Another interface callback
    }

    public void spinnerListener(){
        Spinner spinner = (Spinner) findViewById( R.id.day_spinner_heute);
        spinner.setOnItemSelectedListener(this);
    }



}

