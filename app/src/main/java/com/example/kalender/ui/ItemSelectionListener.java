package com.example.kalender.ui;

import com.example.kalender.core.model.Event;

public interface ItemSelectionListener {
    void onItemSelected(Event event);
    void onItemLongClicked(Event event);
}
