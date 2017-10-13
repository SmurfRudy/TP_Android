package bchsdr;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.provider.DocumentFile;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bchsdr.maps.MapsActivity;
import bchsdr.model.Note;
import bchsdr.tp_android_1.R;
import bchsdr.tp_android_1.databinding.JourneyNoteDetailBinding;
import bchsdr.viewModel.JourneyNoteViewModel;

/**
 * Created by Maxime on 13/10/2017.
 */

public class JourneyNote extends Fragment {
    private Note note;
    Location myPosition = null;
    Double defaultLatitude = 45.75;
    Double defaultLongitude = 4.85;

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

        if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this.getActivity(),permissions,1);
            LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 120, 100,myLocationListener);
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 120, 100,myLocationListener);
        }
        return binding.getRoot();
    }

    public void editLocation (View view) {
        Intent intent = new Intent(getActivity(), MapsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("latitude", defaultLatitude);
        bundle.putSerializable("longitude", defaultLongitude);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    }

    public void close (View view) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStackImmediate();
    }

    private LocationListener myLocationListener = new LocationListener(){
                @Override
                public void onLocationChanged(Location newLocation){
                    myPosition = newLocation;
                }
                @Override
                public void onStatusChanged(String provider, int
                        status, Bundle extras){
                }
                @Override
                public void onProviderEnabled(String provider){
                }
                @Override
                public void onProviderDisabled(String provider){
                }
    };
}