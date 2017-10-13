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

import bchsdr.model.Note;
import bchsdr.tp_android_1.R;
import bchsdr.tp_android_1.databinding.JourneyNoteDetailBinding;
import bchsdr.viewModel.JourneyNoteViewModel;

/**
 * Created by Maxime on 13/10/2017.
 */

public class JourneyNote extends Fragment {
    private Note note;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        JourneyNoteDetailBinding binding = DataBindingUtil.inflate(inflater, R.layout.journey_note_detail, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.note = (Note) bundle.getSerializable("note");
            binding.setJvm(new JourneyNoteViewModel(this.note, getActivity()));
        }else{
            binding.setJvm(new JourneyNoteViewModel(getActivity()));
        }
        binding.setHandler(this);
        return binding.getRoot();
    }

    public void close (View view) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStackImmediate();
    }
}
