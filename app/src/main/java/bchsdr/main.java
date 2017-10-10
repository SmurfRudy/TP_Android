package bchsdr;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bchsdr.adapter.JourneyListAdapter;
import bchsdr.dao.JourneysSQLiteHelper;
import bchsdr.model.Journey;
import bchsdr.tp_android_1.R;
import bchsdr.tp_android_1.databinding.MainActivityBinding;


/**
 * Created by Maxime on 09/10/2017.
 */

public class main extends AppCompatActivity {
    private MainActivityBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        initdb();
        this.showStartup();

    }


    public void showStartup() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        JourneysFragment fragment = new JourneysFragment();
        transaction.replace(R.id.fragment_container,fragment);
        transaction.commit();
    }

    public void showDetail(Journey journey){
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        JourneyDetail detail = new JourneyDetail();
    }

    public void initdb(){
        JourneysSQLiteHelper db = new JourneysSQLiteHelper(this);
        Calendar from1 = Calendar.getInstance();
        Calendar to1 = Calendar.getInstance();
        from1.set(2016,10,11);
        to1.set(2016,10,16);
        Journey copenhagen = new Journey("Copenhagen",from1,to1,1,"desciption 1");


        Calendar from2 = Calendar.getInstance();
        Calendar to2 = Calendar.getInstance();
        from2.set(2015,10,10);
        to2.set(2015,10,15);
        Journey dublin = new Journey("Dublin",from2,to2,2,"desciption 1");


        Calendar from3 = Calendar.getInstance();
        Calendar to3 = Calendar.getInstance();
        from3.set(2014,10,07);
        to3.set(2014,10,13);
        Journey prague = new Journey("Prague",from3,to3,3,"desciption 1");

        try {
            db.insertJourney(copenhagen);
            db.insertJourney(dublin);
            db.insertJourney(prague);
        }catch (Exception e) {
            //TODO gérer l'exception
        }



        String result = db.queryJourneys().toString();
        System.out.println(result);
    }

}
