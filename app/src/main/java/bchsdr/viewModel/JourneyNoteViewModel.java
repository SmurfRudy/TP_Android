package bchsdr.viewModel;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.databinding.BaseObservable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import bchsdr.JourneyNote;
import bchsdr.model.Note;
import bchsdr.tp_android_1.R;

/**
 * Created by Maxime on 12/10/2017.
 */

public class JourneyNoteViewModel extends BaseObservable {
    public String labelTitle = "Title :";
    public String labelDescription = "Description :";
    public String labelLocation = "Location :";
    public String edit = "Edit";
    public String save = "Save";
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

    public void editNote (View view) {
        JourneyNote journeyNote = new JourneyNote();
        FragmentManager fragmentManager = activity.getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        // Pour passer des paramètres on utilise un bundle
        Bundle bundle = new Bundle();
        bundle.putSerializable("note", note);
        journeyNote.setArguments(bundle);
        fragmentTransaction.addToBackStack("journeyDetail");
        fragmentTransaction.replace(R.id.fragment_container, journeyNote);

        fragmentTransaction.commit();
    }

}

