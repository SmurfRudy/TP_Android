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
        Calendar from1 = Calendar.getInstance();
        Calendar to1 = Calendar.getInstance();
        from1.set(2016,10,11);
        to1.set(2016,10,16);
        Journey copenhagen = new Journey("Copenhagen",from1,to1,1,"desciption 1");
        this.journeys.add(copenhagen);

        Calendar from2 = Calendar.getInstance();
        Calendar to2 = Calendar.getInstance();
        from2.set(2015,10,10);
        to2.set(2015,10,15);
        Journey dublin = new Journey("Dublin",from2,to2,2,"desciption 1");
        this.journeys.add(dublin);

        Calendar from3 = Calendar.getInstance();
        Calendar to3 = Calendar.getInstance();
        from3.set(2014,10,07);
        to3.set(2014,10,13);
        Journey prague = new Journey("Prague",from3,to3,3,"desciption 1");
        this.journeys.add(prague);
    }
    public void addJourney(View view) {

        JourneyCreate journeyCreate = new JourneyCreate();
        // Debut du changement de fragment
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        // Pour passer des paramètres on utilise un bundle
        //Bundle bundle = new Bundle();
        //Journey journeyTest = journeys.get(0);
        // On passe un objet (qui doit etre serializable
        //bundle.putSerializable("journey",journeyTest);
        // On passe le bundle (avec l'objet) au nouveau fragment
        //journeyDetail.setArguments(bundle);

        // On remplace le fragment dans le container par le nouveau fragment
        fragmentTransaction.replace(R.id.fragment_container, journeyCreate);
        // à comprendre
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void backToInit(View view) {
    }

}
