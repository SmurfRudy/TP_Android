package bchsdr.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import bchsdr.model.Journey;
import bchsdr.model.Note;
import bchsdr.tp_android_1.R;
import bchsdr.tp_android_1.databinding.JourneyItemBinding;
import bchsdr.tp_android_1.databinding.JourneyNoteBinding;
import bchsdr.viewModel.JourneyNoteViewModel;
import bchsdr.viewModel.JourneyViewModel;

/**
 * Created by Maxime on 12/10/2017.
 */

public class JourneyNoteListAdapter extends RecyclerView.Adapter<JourneyNoteListAdapter.BindingHolder>{
    private List<Note> notes;
    private Activity activity;

    public JourneyNoteListAdapter(List<Note> notes, Activity activity) {
        this.notes = notes;
        this.activity = activity;
    }

    @Override
    public JourneyNoteListAdapter.BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        JourneyNoteBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.journey_note,parent,false);
        return new JourneyNoteListAdapter.BindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(JourneyNoteListAdapter.BindingHolder holder, int
            position) {
        JourneyNoteBinding binding = holder.binding;
        Note note = notes.get(position);
        binding.setJvm(new JourneyNoteViewModel(note, activity));
        binding.setHandler(new JourneyNoteViewModel(note, activity));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
    static class BindingHolder extends RecyclerView.ViewHolder {
        private JourneyNoteBinding binding;

        BindingHolder(JourneyNoteBinding binding) {
            super(binding.journeyNote);
            this.binding = binding;
        }
    }
}
