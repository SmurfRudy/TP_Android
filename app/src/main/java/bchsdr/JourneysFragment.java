package bchsdr;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import bchsdr.adapter.JourneyListAdapter;
import bchsdr.dao.JourneysDAO;
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
        binding.journeysList.setAdapter(new JourneyListAdapter(journeys, getActivity()));
        binding.setHandler(this);
        return binding.getRoot();
    }

    private void initList() {
        try {
            this.journeys = JourneysDAO.getInstance().getDBJourneys(getActivity());
        }catch (Exception e) {
            //TODO gérer l'exception
        }
    }
    public void addJourney(View view) {

        JourneyDetail journeyDetail = new JourneyDetail();
        // Debut du changement de fragment
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        // à comprendre - on sauvegarde l'état lorsque le fragment que l'on rajoute sera supprimé
        fragmentTransaction.addToBackStack("listJourney");
        // On remplace le fragment dans le container par le nouveau fragment
        fragmentTransaction.replace(R.id.fragment_container, journeyDetail);

        fragmentTransaction.commit();
    }



}
