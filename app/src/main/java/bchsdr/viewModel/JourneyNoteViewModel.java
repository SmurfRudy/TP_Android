package bchsdr.viewModel;

import android.app.Activity;
import android.databinding.BaseObservable;
import android.view.View;

import bchsdr.model.Note;

/**
 * Created by Maxime on 12/10/2017.
 */

public class JourneyNoteViewModel extends BaseObservable {
    public String labelTitle = "Title :";
    public String labelDescription = "Description :";
    public String cancel = "Close";

    private Note note;
    private Activity activity;

    public JourneyNoteViewModel(Activity activity) {
        this.activity = activity;
    }

    public JourneyNoteViewModel(Note note, Activity activity) {
        this.note = note;
        this.activity = activity;
    }

    public String getTitle (){
        return note.getTitle();
    }

    public String getDescription () {
        return note.getDescription();
    }



}

