package bchsdr.viewModel;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.databinding.BaseObservable;
import android.os.Bundle;
import android.view.View;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import bchsdr.JourneyDetail;
import bchsdr.JourneysFragment;
import bchsdr.dao.ConnectionSQLiteHelper;
import bchsdr.dao.JourneysDAO;
import bchsdr.model.Journey;
import bchsdr.tp_android_1.R;

/**
 * Created by Rudy_DEAL on 09/10/2017.
 */

public class JourneyViewModel extends BaseObservable {

    public String labelcreatetravel = "";

    private Journey journey;

    private Activity activity;

    public JourneyViewModel(Activity activity) {
        this.journey = new Journey();
        this.activity = activity;
        this.labelcreatetravel = activity.getString(R.string.createtravel);
    }

    public JourneyViewModel(Journey journey, Activity activity) {
        this.journey = journey;
        this.activity = activity;
        this.labelcreatetravel = activity.getString(R.string.edittravel);
    }

    public String getName() {
        return this.journey.getName();
    }
    public int getId() {
        return this.journey.getId();
    }

    public String getFrom() {
        Calendar cal = this.journey.getFrom();
        DateFormat sdf = SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM,
                Locale.getDefault());
        return sdf.format(cal.getTime());
    }

    public String getTo() {
        Calendar cal = this.journey.getTo();
        DateFormat sdf = SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM,
                Locale.getDefault());
        return sdf.format(cal.getTime());
    }

    public void onJourneyClick(View view) {
        JourneyDetail journeyDetail = new JourneyDetail();
        // Debut du changement de fragment
        android.app.FragmentManager fragmentManager = activity.getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        // Pour passer des paramètres on utilise un bundle
        Bundle bundle = new Bundle();
        // On passe un objet (qui doit etre serializable
        bundle.putSerializable("journey", journey);
        // On passe le bundle (avec l'objet) au nouveau fragment
        journeyDetail.setArguments(bundle);

        // à comprendre - on sauvegarde l'état lorsque le fragment que l'on rajoute sera supprimé
        fragmentTransaction.addToBackStack("listJourney");
        // On remplace le fragment dans le container par le nouveau fragment
        fragmentTransaction.replace(R.id.fragment_container, journeyDetail);

        fragmentTransaction.commit();
    }

    public void delete (View view) {
        JourneysDAO.getInstance().deleteJourney(activity, this.journey);
        FragmentManager manager = activity.getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        JourneysFragment fragment = new JourneysFragment();
        transaction.replace(R.id.fragment_container,fragment);
        transaction.commit();
    }
}