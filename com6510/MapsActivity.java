package uk.ac.shef.oak.com6510;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.os.EnvironmentCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import uk.ac.shef.oak.com6510.Database.MapData;
import uk.ac.shef.oak.com6510.Database.MapDatabase;

import static android.graphics.Color.BLACK;

/**
 * @param <polylines>
 */
public class MapsActivity<polylines> extends FragmentActivity implements OnMapReadyCallback ,SensorEventListener {
    private TextView mPressureValue;
    private TextView mTemperatureValue;
    private  SensorManager mSensorManager;
    private  Sensor mTemperure;
    private Sensor mPressure;
    private GoogleMap mMap;
    private Button mButtonEnd;
    private Button mButtonStart;
    private static final int ACCESS_FINE_LOCATION = 123;
    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationClient;
    private ArrayList<LatLng> points;
    private ArrayList<LatLng> photoPoints;
    private SensorEventListener mPressureListener = null;
    private SensorEventListener mTemperatureListener = null;
    private Sensor mBarometerSensor;
    private long timePhoneWasLastRebooted = 0;
    private long BAROMETER_READING_FREQUENCY= 30000;
    private long T_lastReportTime = 0;
    private long P_lastReportTime = 0;
    private boolean started;
    private long mSamplingRateInMSecs;
    private long mSamplingRateNano = 2000000000;
    private String pressureTemp;
    private String temperatureTemp;
    private String photoName;
    private ArrayList<String> point_lat;
    private ArrayList<String> point_lng;
    private String imageName;
    private int numberOfPhoto;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        final String Title = intent.getStringExtra("String_Name");
        final String Time = intent.getStringExtra("PathTime");
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mTemperure = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        super.onCreate(savedInstanceState);

        /*
         * MapsActivity(4)
         * the MapsActivity links to the MapFragment to show the map
         * */
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mPressureValue = (TextView) findViewById(R.id.pressure);
        mTemperatureValue = (TextView) findViewById(R.id.temperature);
        //  Start background service
        // Intent intent = new Intent(this, MapService.class);
        // this.startService(intent);

        //  points for PolylineOptions options to print Polyline
        //  photoPoints for store position that user takes photo
        points = new ArrayList<LatLng>();
        photoPoints = new ArrayList<LatLng>();
        point_lat= new ArrayList();
        point_lng= new ArrayList();
        final List photoPressure = new ArrayList();
        final List photoTemperature = new ArrayList();
        LocalBroadcastManager.getInstance(this).registerReceiver(
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(final Context context, Intent intent) {
                        /*
                         * MapsActivity(3)
                         * latitude and longitude are data from MapService
                         * I transfer them into lat and lng and to get location data with double type
                         * if the user click mButtonStart, it means the user takes photo
                         * the ThreadForDatabase thread will save current lat and lng to database
                         * */
                        String latitude = intent.getStringExtra(MapService.EXTRA_LATITUDE);
                        String longitude = intent.getStringExtra(MapService.EXTRA_LONGITUDE);
                        final double lat =  Double.parseDouble(latitude);
                        final double lng =  Double.parseDouble(longitude);
                        LatLng latLng = new LatLng(lat, lng); //you already have this
                        points.add(latLng);
                        point_lat.add(String.valueOf(lat));
                        point_lng.add(String.valueOf(lng));

                        /*
                         * MapsActivity(6)
                         * moveCamera to show current position and mMap.setMyLocationEnabled(true) in onMapReady can locates the user's position
                         * */
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
                        mButtonStart = (Button) findViewById(R.id.button_takePhoto);
                        mButtonStart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                numberOfPhoto+=1;
                                String imageTempName = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                                String suffix = ".jpg";
                                String imageName = imageTempName+suffix;
                                photoName = imageName;

                                //  click button -> mark current position and store to photoPoints list
                                /*
                                 * MapsActivity(7)
                                 * use addMarker in take photo listener to Mark the position taking photo
                                 * */
                                mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)));
                                LatLng photolatLng = new LatLng(lat, lng);
                                photoPoints.add(photolatLng);
                                photoPressure.add(pressureTemp);
                                photoTemperature.add(temperatureTemp);
                                drawPolyline();

                                Intent intent = new Intent();
                                intent.setClass(MapsActivity.this, Camera.class);
                                intent.putExtra("PhotoName",imageName);
                                startActivity(intent);

                                /*
                                 * MapsActivity(8)
                                 * all the data is persisted into a Room database pressureTemp for current pressure, temperatureTemp for current temperature, lat and lng for current lat and lng
                                 * Title for path name, point_lat and point_lng for geolocated path, photoName for Name of photo, Time for path time, context for thread
                                 * */
                                ThreadForDatabase thread = new ThreadForDatabase(pressureTemp, temperatureTemp, lat, lng, Title, context, point_lat, point_lng, photoName, Time, numberOfPhoto);
                                thread.start();




                            }
                        });
                    }
                }, new IntentFilter(MapService.ACTION_LOCATION_BROADCAST)
        );
        //  start to listen button end
        mButtonEnd = (Button) findViewById(R.id.button_end);
        mButtonEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  stop updating location and back to the title activity
                mButtonEnd.setEnabled(false);
                Intent intent = new Intent();
                intent.setClass(MapsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * @return
     */
    public boolean standardPressureSensorAvailable() {
        return (mPressure != null);
    }

    /**
     * @return
     */
    public boolean standardTemperatureeSensorAvailable() {
        return (mTemperure != null);
    }

    /**
     *
     */
    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener( this, mPressure ,  (int) (mSamplingRateNano * 1000000000*1000000));
        mSensorManager.registerListener(this, mTemperure , (int) (mSamplingRateNano * 1000000000*1000000));

        startStepCheckConnect(null);
    }

    /**
     * @param dialog
     * @return
     */
    private Boolean startStepCheckConnect(DialogInterface dialog) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (checkPermissions()) { //Yes permissions are granted by the user. Go to the next step.
            startServiceIntent();
        } else {  //No user has not granted the permissions yet. Request now.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION);
        }
        return true;
    }

    /**
     *
     */
    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     *
     */
    private void startServiceIntent() {
        //Start location sharing service to app server.........
        Intent intent = new Intent(this, MapService.class);
        startService(intent);
    }

    /**
     * @return
     */
    private boolean checkPermissions() {
        int permissionState1 = ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);

        int permissionState2 = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        return permissionState1 == PackageManager.PERMISSION_GRANTED && permissionState2 == PackageManager.PERMISSION_GRANTED;
    }

    /**
     *
     */
    /*
     * MapsActivity(5)
     * I use the points saved in onReceive function which stores all the points that the user walked
     * and use PolylineOptions and addPolyline to draw the geolocated path
     * */
    private void drawPolyline(){
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

    /**
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    startServiceIntent();

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        /*
         * MapsActivity(6)
         * */
        mMap.setMyLocationEnabled(true);
    }

    /**
     *  disable  back key
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }

    /**
     *
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * @param event
     */
    /*
     * MapsActivity(2)
     * update sensor record on UI every 20 seconds
     * use T_diff to control the time to update UI
     * */
    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        int type = event.sensor.getType();


        StringBuilder sb;
        StringBuilder sb1;
        sb = new StringBuilder();
        sb1 = new StringBuilder();
        switch (type) {
            case Sensor.TYPE_AMBIENT_TEMPERATURE:

                sb.append("\nTemperature：");
                sb.append(values[0]);
                long T_diff = event.timestamp - T_lastReportTime;
                if (T_diff >= (mSamplingRateNano*10)) {
                    mTemperatureValue.setText(sb.toString());
                    // mPressureValue.setText(String.valueOf(pressureValue));
                    temperatureTemp =  String.valueOf(values[0]);
                    T_lastReportTime = event.timestamp;
                }
                break;
            case Sensor.TYPE_PRESSURE:
                sb1.append("\nPressure：");
                sb1.append(values[0]);
                long P_diff = event.timestamp - P_lastReportTime;
                if (P_diff >= (mSamplingRateNano*10)) {
                    mPressureValue.setText(sb1.toString());
                    // mPressureValue.setText(String.valueOf(pressureValue));
                    pressureTemp = String.valueOf(values[0]);
                    P_lastReportTime = event.timestamp;
                }
                break;
        }
    }

    /**
     * @param sensor
     * @param accuracy
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
