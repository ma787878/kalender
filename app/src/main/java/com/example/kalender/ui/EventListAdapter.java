package com.example.kalender.ui;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kalender.core.model.Event;
import com.example.kalender.databinding.ListItemEventBinding;
import androidx.recyclerview.widget.RecyclerView;


public class EventListAdapter extends ListAdapter<Event, EventViewHolder> {
    private final ItemSelectionListener selectionListener;

    public EventListAdapter(ItemSelectionListener selectionListener) {
        super(new EventDiffUtil());
        this.selectionListener = selectionListener;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new EventViewHolder(ListItemEventBinding.inflate(inflater));
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = getItem(position);

        holder.bind(event);

        holder.itemView.setOnClickListener(view -> {
            selectionListener.onItemSelected(event);
        });

        holder.itemView.setOnLongClickListener(view -> {
            selectionListener.onItemLongClicked(event);
            return true;
        });
    }
}

class EventViewHolder extends RecyclerView.ViewHolder {
    private final ListItemEventBinding binding;

    public EventViewHolder(ListItemEventBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    protected void bind(Event event) {
        binding.titleTextView.setText(event.getTitle());
    }
}

class EventDiffUtil extends DiffUtil.ItemCallback<Event> {

    @Override
    public boolean areItemsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
        return oldItem.getTitle().equals(newItem.getTitle());
    }
}

