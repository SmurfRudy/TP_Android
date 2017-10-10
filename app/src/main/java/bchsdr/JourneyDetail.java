package bchsdr;

import android.app.Fragment;
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
        getActivity().getFragmentManager().beginTransaction().remove(this).commit();
    }
}
