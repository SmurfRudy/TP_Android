package bchsdr.viewModel;

import android.app.Activity;
import android.databinding.BaseObservable;

import bchsdr.model.Note;

/**
 * Created by Maxime on 12/10/2017.
 */

public class JourneyNoteViewModel extends BaseObservable {

    private Note note;
    private Activity activity;

    public JourneyNoteViewModel(Activity activity) {
        this.activity = activity;
    }

    public JourneyNoteViewModel(Note note, Activity activity) {
        this.note = note;
        this.activity = activity;
    }
}

