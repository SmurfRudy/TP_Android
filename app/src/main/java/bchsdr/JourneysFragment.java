package bchsdr;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bchsdr.adapter.JourneyListAdapter;
import bchsdr.model.Journey;
import bchsdr.tp_android_1.R;
import bchsdr.tp_android_1.databinding.JourneysFragmentBinding;

/**
 * Created by Maxime on 09/10/2017.
 */

public class JourneysFragment extends Fragment {
    List<Journey> journeys = new ArrayList<Journey>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        initList();
        JourneysFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.journeys_fragment, container, false);
        binding.journeysList.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        binding.journeysList.setAdapter(new JourneyListAdapter(journeys));
        binding.setHandler(this);
        return binding.getRoot();
    }

    private void initList() {
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();

        from.set(2016,10,11);
        to.set(2016,10,16);
        Journey copenhagen = new Journey("Copenhagen",from,to);
        this.journeys.add(copenhagen);

        from.set(2015,10,10);
        to.set(2015,10,15);
        Journey dublin = new Journey("Dublin",from,to);
        this.journeys.add(dublin);

        from.set(2014,10,07);
        to.set(2014,10,13);
        Journey prague = new Journey("Prague",from,to);
        this.journeys.add(prague);
    }
    public void addJourney(View view) {
        System.out.println("Ajout d'un séjour ");
    }

}
