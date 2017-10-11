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

import java.util.Calendar;

import bchsdr.dao.JourneysSQLiteHelper;
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
            binding.setJvm(new JourneyViewModel(this.journey, getActivity()));
        }else{
            binding.setJvm(new JourneyViewModel(getActivity()));
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

        // à comprendre - on sauvegarde l'état lorsque le fragment que l'on rajoute sera supprimé
        fragmentTransaction.addToBackStack(null);
        // On remplace le fragment dans le container par le nouveau fragment
        fragmentTransaction.replace(R.id.fragment_container, journeysFragment);
        fragmentTransaction.commit();
    }


    public void saveJourney (int id, String name, String start_date, String end_date) {

        Calendar test1 =null, test2 =null;
        String description = "TO DO";
        Journey newJourney =new Journey(name, test1, test2, id, description);
        edit_journey(newJourney);
        this.close(getView());

    }
}
