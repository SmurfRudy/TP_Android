package bchsdr.viewModel;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.databinding.BaseObservable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import bchsdr.JourneyDetail;
import bchsdr.JourneyNote;
import bchsdr.dao.NotesDAO;
import bchsdr.model.Journey;
import bchsdr.model.Note;
import bchsdr.tp_android_1.R;

/**
 * Created by Maxime on 12/10/2017.
 */

public class JourneyNoteViewModel extends BaseObservable {

    private Note note;
    private Activity activity;
    private Journey journey;

    public JourneyNoteViewModel(Activity activity) {
        this.activity = activity;
    }

    public JourneyNoteViewModel(Note note, Journey journey, Activity activity) {
        this.note = note;
        this.activity = activity;
        this.journey = journey;
    }

    public String getTitle (){
        return note.getTitle();
    }

    public String getDescription () {
        return note.getDescription();
    }

    public Drawable getPicture () { return activity.getResources().getDrawable(R.drawable.no_image); }

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

    public void deleteNote (View view){
        NotesDAO.getInstance().deleteNote(activity, note);
        FragmentManager manager = activity.getFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        JourneyDetail journeyDetail = new JourneyDetail();
        fragmentTransaction.setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        // Pour passer des paramètres on utilise un bundle
        Bundle bundle = new Bundle();
        // On passe un objet (qui doit etre serializable
        bundle.putSerializable("journey", journey);
        // On passe le bundle (avec l'objet) au nouveau fragment
        journeyDetail.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment_container,journeyDetail);
        fragmentTransaction.commit();
    }

}

