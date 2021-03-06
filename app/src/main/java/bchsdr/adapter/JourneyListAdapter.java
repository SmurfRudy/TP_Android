package bchsdr.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import bchsdr.JourneyDetail;
import bchsdr.JourneysFragment;
import bchsdr.model.Journey;
import bchsdr.tp_android_1.R;
import bchsdr.tp_android_1.databinding.JourneyItemBinding;
import bchsdr.viewModel.JourneyViewModel;

/**
 * Created by Maxime on 09/10/2017.
 */

public class JourneyListAdapter extends RecyclerView.Adapter<JourneyListAdapter.BindingHolder> {
    private List<Journey> journeys;
    private Activity activity;
    public JourneyListAdapter(List<Journey> journeys, Activity activity) {
        this.journeys = journeys;
        this.activity = activity;
    }
    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        JourneyItemBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.journey_item,parent,false);
        return new BindingHolder(binding);
    }
    @Override
    public void onBindViewHolder(JourneyListAdapter.BindingHolder holder, int
            position) {
        JourneyItemBinding binding = holder.binding;
        Journey journey = journeys.get(position);
        binding.setJvm(new JourneyViewModel(journey, activity));
        binding.setHandler(new JourneyViewModel(journey, activity));
    }

    @Override
    public int getItemCount() {
        return journeys.size();
    }
    static class BindingHolder extends RecyclerView.ViewHolder {
        private JourneyItemBinding binding;

        BindingHolder(JourneyItemBinding binding) {
            super(binding.journeyItem);
            this.binding = binding;
        }
    }
}
