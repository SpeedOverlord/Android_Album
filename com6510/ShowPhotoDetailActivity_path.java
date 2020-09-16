package uk.ac.shef.oak.com6510;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
/*
same as the ShowPhotoDetailActivity, the difference is this activity shows the image select in the specific path.
 */
public class ShowPhotoDetailActivity_path extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private double Lat;
    private double Lng;
    private ArrayList<String> pointsLat;
    private ArrayList<String> pointsLng;
    private ArrayList<LatLng> points;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photodetail);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.bigPhotoMap);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        String String_Img = intent.getStringExtra("String_Img_path");
        String name_path = intent.getStringExtra("name_path_bypath");
        String String_pressure = intent.getStringExtra("String_pressure_path");
        String String_temperature = intent.getStringExtra("String_temperature_path");
        Bundle extraPointsLat = intent.getBundleExtra("extraPointsLat_path");
        Bundle extraPointsLng = intent.getBundleExtra("extraPointsLng_path");
        pointsLat = (ArrayList<String>) extraPointsLat.getSerializable("objects");
        pointsLng = (ArrayList<String>) extraPointsLng.getSerializable("objects");
        Lat = intent.getDoubleExtra("Lat_path", 0);
        Lng = intent.getDoubleExtra("Lng_path", 0);



        TextView wordItemView_detail_1 = findViewById(R.id.bigPhotoPath);
        TextView wordItemView_detail_2 = findViewById(R.id.bigPhotoPressure);
        TextView wordItemView_detail_3 = findViewById(R.id.bigPhotoTemperature);
        ImageView ImageItemView_detail = findViewById(R.id.bigPhoto);


        File img = new File(String_Img);
        Bitmap myBitmap = BitmapFactory.decodeFile(img.getAbsolutePath());
        ImageItemView_detail.setImageBitmap(myBitmap);

        wordItemView_detail_1.setText(name_path);
        wordItemView_detail_2.setText("Pressure: " + String_pressure);
        wordItemView_detail_3.setText("Temperature: " + String_temperature);

    }

    /**
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng place = new LatLng(Lat, Lng);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 15));
        mMap.addMarker(new MarkerOptions().position(place));
        points = new ArrayList<LatLng>();
        for (int i = 0; i < pointsLat.size(); i++) {
            double convertLat = Double.parseDouble(pointsLat.get(i));
            double convertLng = Double.parseDouble(pointsLng.get(i));
            LatLng latlng = new LatLng(convertLat, convertLng);
            points.add(latlng);
        }
        //  store polyline with list
        List<Polyline> polylines = new ArrayList<Polyline>();
        // polyDatabase = new ArrayList();
        //  build PolylineOptions to draw polyline
        PolylineOptions options = new PolylineOptions().width(10).color(Color.BLUE).geodesic(true);
        for (int i = 0; i < points.size(); i++) {
            LatLng point = points.get(i);
            options.add(point);
        }
        polylines.add(this.mMap.addPolyline(new PolylineOptions().width(5).color(Color.BLUE).geodesic(true)));
        //polyDatabase.add(this.mMap.addPolyline(new PolylineOptions().width(5).color(Color.BLUE).geodesic(true)));
        for (Polyline line : polylines) {
            polylines.remove(line);
            line.remove();
            mMap.addPolyline(options); //add Polyline
        }
    }
}