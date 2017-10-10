package bchsdr;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bchsdr.tp_android_1.R;
import bchsdr.tp_android_1.databinding.JourneyCreateBinding;
import bchsdr.tp_android_1.databinding.JourneyDetailBinding;
import bchsdr.viewModel.JourneyViewModel;

import static bchsdr.tp_android_1.R.layout.journey_create;

/**
 * Created by Maxime on 10/10/2017.
 */

public class JourneyCreate extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        JourneyCreateBinding binding = DataBindingUtil.inflate(inflater, journey_create, container, false);
        binding.setHandler(this);
        binding.setJvm(new JourneyViewModel());
        return binding.getRoot();
    }
}
