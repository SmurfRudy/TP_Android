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

        return binding.getRoot();
    }

    private void initList() {
        Journey copenhagen = new Journey();
        copenhagen.setName("Copenhagen");
        this.journeys.add(copenhagen);

        Journey dublin = new Journey();
        dublin.setName("Dublin");
        this.journeys.add(dublin);

        Journey prague = new Journey();
        prague.setName("Prague");
        this.journeys.add(prague);
    }
    public void addJourney(View view) {
        System.out.println("Ajout d'un séjour ");
    }

}
