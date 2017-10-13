package bchsdr.maps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import bchsdr.dao.NotesDAO;
import bchsdr.model.Note;
import bchsdr.tp_android_1.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng myPosition;
    private LatLng markerPosition;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            this.myPosition = new LatLng((Double) bundle.getSerializable("latitude"), (Double) bundle.getSerializable("longitude"));
            this.note = (Note) bundle.getSerializable("note");
            markerPosition = new LatLng(note.getLatitude(),note.getLongitude());
        }
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.addMarker(new MarkerOptions().position(latLng).title("Note Marker"));
                markerPosition = latLng;
            }
        });
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 12));
        if (markerPosition != null) {
            mMap.addMarker(new MarkerOptions().position(markerPosition).title("Note Marker"));
        }
    }

    public void saveLocation(View view) {
        if (markerPosition == null) {
            markerPosition = myPosition;
        }
        this.note.setLatitude((float) markerPosition.latitude);
        this.note.setLongitude((float) markerPosition.longitude);
        NotesDAO.getInstance().editNote(this, note);
        this.finish();
    }


}
