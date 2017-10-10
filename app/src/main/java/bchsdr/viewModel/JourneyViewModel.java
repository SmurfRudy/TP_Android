package bchsdr.viewModel;

import android.databinding.BaseObservable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import bchsdr.JourneyDetail;
import bchsdr.model.Journey;

/**
 * Created by Rudy_DEAL on 09/10/2017.
 */

public class JourneyViewModel extends BaseObservable {
    public String labelName = "Name : ";
    public String labelDeparture = "Departure date : ";
    public String labelReturn = "Return date : ";

    private Journey journey;
    public JourneyViewModel(){ this.journey = new Journey(); }
    public JourneyViewModel(Journey journey) {
        this.journey = journey;
    }
    public String getName() {
        return journey.getName();
    }
    public String getFrom() {
        Calendar cal = journey.getFrom();
        DateFormat sdf = SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM,
                Locale.getDefault());
        return sdf.format(cal.getTime());
    }
    public String getTo() {
        Calendar cal = journey.getTo();
        DateFormat sdf = SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM,
                Locale.getDefault());
        return sdf.format(cal.getTime());
    }
    public void onJourneyClick(View view) {
    }


    }