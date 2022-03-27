package com.example.kalender.ui.einfuegen_termin;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.kalender.R;
import com.example.kalender.core.model.Event;
import com.example.kalender.databinding.FragmentEinfuegenTerminBinding;
import com.example.kalender.ui.home.HomeViewModel;

import org.bson.types.ObjectId;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;

//public class Einfuegen_TerminFragment extends Fragment {

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import org.bson.types.ObjectId;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.example.kalender.core.model.Event;
import com.example.kalender.databinding.FragmentEinfuegenTerminBinding;
import com.example.kalender.ui.einfuegen_termin.Einfuegen_TerminFragmentArgs;

import io.realm.Realm;



    public class Einfuegen_TerminFragment extends Fragment{
        private FragmentEinfuegenTerminBinding binding;
        private DatePickerDialog datePicker;
        private Event event;
        private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());




        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            binding = FragmentEinfuegenTerminBinding.inflate(inflater);
            datePicker = new DatePickerDialog(requireContext());
            ObjectId id = Einfuegen_TerminFragmentArgs.fromBundle(getArguments()).getId();
            retrieveEventAndSetupUI(id);

            datePicker.setOnDateSetListener((picker, year, month, day) -> {
                Date date = parseDate(year, month, day);
                binding.dateEditText.setText(dateFormat.format(date));
                event.setDate(date);
            });

            binding.dateEditText.setOnClickListener(view -> {
                datePicker.show();
            });

            binding.buttonBestaetigen.setOnClickListener(view -> {
                event.setTitle(binding.aktivitaetEingabe.getText().toString());

                if (event.getTitle().isEmpty()) {
                    binding.aktivitaetEingabe.setError("Bitte Aktivität eingeben");
                    return;
                }

                if (event.getDate() == null) {
                    binding.dateEditText.setError("Datum muss gesetzt sein");
                    return;
                }


                saveEvent(event);

                Navigation.findNavController(view).popBackStack();
            });
            return binding.getRoot();
        }

        private void retrieveEventAndSetupUI(ObjectId id) {
            if (id == null) {
                event = new Event();
                return;
            }

            Realm realm = Realm.getDefaultInstance();
            Event result = realm.where(Event.class).equalTo("id", id).findFirst();

            if (result == null) {
                event = new Event();
                return;
            }

            Event event = realm.copyFromRealm(result);
            realm.close();
            this.event = event;
            setupUI(event);
        }

        private void saveEvent(Event event) {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(tRealm -> tRealm.insertOrUpdate(event));
            realm.close();
        }

        private Date parseDate(int year, int month, int day) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DAY_OF_MONTH, day);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            return cal.getTime();
        }


        private void setupUI(Event event) {
            binding.aktivitaetEingabe.setText(event.getTitle());
            binding.dateEditText.setText(dateFormat.format(event.getDate()));
        }


        @Override
        public void onDestroyView() {
            super.onDestroyView();
            binding = null;
        }
    }



