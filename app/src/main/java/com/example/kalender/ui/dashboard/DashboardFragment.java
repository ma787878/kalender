package com.example.kalender.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.kalender.R;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.example.kalender.core.model.Event;
import com.example.kalender.databinding.FragmentDashboardBinding;
import com.example.kalender.databinding.FragmentEinfuegenTerminBinding;
import com.example.kalender.ui.EventListAdapter;
import com.example.kalender.ui.ItemSelectionListener;
import com.example.kalender.ui.dashboard.DashboardFragment;
import com.example.kalender.ui.dashboard.DashboardFragmentDirections;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.realm.Realm;
import io.realm.RealmResults;
import android.widget.Button;
import androidx.recyclerview.widget.RecyclerView;

public class DashboardFragment extends Fragment implements ItemSelectionListener {
    public CalendarView simpleCalendarView;
    private FragmentDashboardBinding binding;
    private final EventListAdapter adapter = new EventListAdapter(this);


    private DashboardViewModel dashboardViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(getLayoutInflater());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(adapter);

        FloatingActionButton floatingActionButton = (FloatingActionButton) binding.floatingActionButton;

        floatingActionButton.setOnClickListener(view -> {
            NavController navController = Navigation.findNavController(view);
            // Null because we want to create a new entry
            navController.navigate(DashboardFragmentDirections.actionNavigationDashboardToEinfuegenTermin(null));
        });

        binding.calendarView.setOnDateChangeListener((calendarView, year, month, day) -> {
            Date date = parseDate(year, month, day);
            getEventsForDate(date);
        });
        return binding.getRoot();

    }

    private void getEventsForDate(Date date) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Event> result = realm.where(Event.class).between("date", date, date).findAll();
        List<Event> copiedResult = realm.copyFromRealm(result);
        realm.close();
        adapter.submitList(copiedResult);
    }

    private void deleteEvent(Event event) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(tRealm -> {
            Event realmEvent = tRealm.where(Event.class).equalTo("id", event.getId()).findFirst();
            if (realmEvent == null) {
                return;
            }
            realmEvent.deleteFromRealm();
        });

        realm.close();
        refreshForSelectedDate();
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

    private void refreshForSelectedDate() {
        long longDate = binding.calendarView.getDate();
        Date date = new Date(longDate);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        Date parsedDate = parseDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        getEventsForDate(parsedDate);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshForSelectedDate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemSelected(Event event) {
        Navigation.findNavController(binding.getRoot()).navigate(
                DashboardFragmentDirections.actionNavigationDashboardToEinfuegenTermin(event.getId())
        );
    }

    @Override
    public void onItemLongClicked(Event event) {
        new AlertDialog.Builder(requireContext())
                .setTitle(R.string.alert_title)
                .setPositiveButton(R.string.alert_button_ok, (dialogInterface, i) -> deleteEvent(event))
                .setNegativeButton(R.string.alert_button_cancel, null)
                .show();

    }


}