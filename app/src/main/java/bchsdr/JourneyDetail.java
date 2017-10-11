package bchsdr;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bchsdr.model.Journey;
import bchsdr.tp_android_1.R;
import bchsdr.tp_android_1.databinding.JourneyDetailBinding;
import bchsdr.viewModel.JourneyViewModel;

/**
 * Created by Maxime on 09/10/2017.
 */

public class JourneyDetail extends Fragment {
    private Journey journey;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        this.journey = null;
        JourneyDetailBinding binding = DataBindingUtil.inflate(inflater, R.layout.journey_detail, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.journey = (Journey) bundle.getSerializable("journey");
            binding.setJvm(new JourneyViewModel(this.journey));
        }else{
            binding.setJvm(new JourneyViewModel());
        }
        binding.setHandler(this);
        return binding.getRoot();
    }

    public void close(View view) {
        JourneysFragment journeysFragment = new JourneysFragment();
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

        // à comprendre - on sauvegarde l'état lorsque le fragment que l'on rajoute sera supprimé
        fragmentTransaction.addToBackStack(null);
        // On remplace le fragment dans le container par le nouveau fragment
        //fragmentTransaction.add(R.id.fragment_container, journeysFragment);
        fragmentTransaction.remove(this);

        fragmentTransaction.commit();
    }

    public void saveJourney (View view) {
        System.out.print(view);
    }
}
