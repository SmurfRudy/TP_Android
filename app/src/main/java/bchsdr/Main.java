package bchsdr;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.Calendar;

import bchsdr.dao.JourneysDAO;
import bchsdr.model.Journey;
import bchsdr.tp_android_1.R;
import bchsdr.tp_android_1.databinding.MainActivityBinding;


/**
 * Created by Maxime on 09/10/2017.
 */

public class Main extends AppCompatActivity {
    private MainActivityBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        this.showStartup();

    }


    public void showStartup() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        JourneysFragment fragment = new JourneysFragment();
        transaction.replace(R.id.fragment_container,fragment);
        transaction.commit();
    }

}
