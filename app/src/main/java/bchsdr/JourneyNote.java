package bchsdr;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.provider.DocumentFile;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;
import java.net.URI;

import bchsdr.dao.JourneysDAO;
import bchsdr.dao.NotesDAO;
import bchsdr.maps.MapsActivity;
import bchsdr.model.Journey;
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

    static int REQUEST_CODE_MAPS = 1;
    static int REQUEST_CODE_GALLERY = 2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        JourneyNoteDetailBinding binding = DataBindingUtil.inflate(inflater, R.layout.journey_note_detail, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.note = (Note) bundle.getSerializable("note");
            binding.setJvm(new JourneyNoteViewModel(this.note, (Journey) bundle.getSerializable("journey"), getActivity()));
        }else{
            binding.setJvm(new JourneyNoteViewModel(getActivity()));
        }
        binding.setHandler(this);

        if ((ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) &&
                (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)   ) {
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this.getActivity(),permissions,1);
        }
        LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 120, 100,myLocationListener);
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 120, 100,myLocationListener);
        return binding.getRoot();
    }

    @Override
    public void onResume(){
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.note = (Note) bundle.getSerializable("note");
        }
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_MAPS) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = getArguments();
                if (bundle != null) {
                    this.note = (Note) bundle.getSerializable("note");
                }
            }
        }
        if (requestCode == REQUEST_CODE_GALLERY){
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = getArguments();
                if (bundle != null){
                    // changer le src
                    ImageView imageView = (ImageView) getActivity().findViewById(R.id.image);
                    Uri uri = data.getData();
                    imageView.setImageURI(uri);
                    note.setPictureLocation(uri.toString());
                    NotesDAO.getInstance().editNote(getActivity(), note);
                }
            }
        }
    }

    public void editLocation (View view) {
        Intent intent = new Intent(getActivity(), MapsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("latitude", defaultLatitude);
        bundle.putSerializable("longitude", defaultLongitude);
        bundle.putSerializable("note", note);
        intent.putExtras(bundle);
        getActivity().startActivityForResult(intent, REQUEST_CODE_MAPS);
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

    public void saveNote (String title, String description) {
        note.setTitle(title);
        note.setDescription(description);
        try{
            NotesDAO.getInstance().editNote(getActivity(),note);
            this.close(getView());
        }

        catch (Exception e){
            this.close(getView());
        }

    }

    public void modifyPicture (View view) {
        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/*");
        startActivityForResult(i, 2);
    }
}
