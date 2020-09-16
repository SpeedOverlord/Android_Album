package uk.ac.shef.oak.com6510;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;

import java.util.ArrayList;
import java.util.List;

import uk.ac.shef.oak.com6510.Database.MapData;
import uk.ac.shef.oak.com6510.Database.MapDatabase;

public class ThreadForDatabase extends Thread {
    private String name;
    private String pressure;
    private String temperature;
    private double lat;
    private double lng;
    private String Title;
    private int id;
    private Context context;
    private ArrayList<String> pointLat;
    private ArrayList<String> pointLng;
    private String photoName;
    private String time;
    private int numberOfPhoto;

    /**
     * @param pressure
     * @param temperature
     * @param lat
     * @param lng
     * @param Title
     * @param context
     * @param pointLat
     * @param pointLng
     * @param photoName
     * @param time
     * @param numberOfPhoto
     */
    public ThreadForDatabase(String pressure, String temperature, double lat, double lng, String Title, Context context, ArrayList<String> pointLat, ArrayList<String> pointLng, String photoName, String time, int numberOfPhoto){
        this.pressure = pressure;
        this.temperature = temperature;
        this.lat = lat;
        this.lng = lng;
        this.Title = Title;
        this.context = context;
        this.pointLat = pointLat;
        this.pointLng = pointLng;
        this.photoName = photoName;
        this.time = time;
        this.numberOfPhoto = numberOfPhoto;
    }

    /**
     *
     */
    @Override
    public void run() {
        update(pressure, temperature, lat, lng, Title, context, pointLat, pointLng, photoName, time,numberOfPhoto);
      /*  List<MapData> allMapData = MapDatabase
                .getINSTANCE()
                .getMapDao()
                .getUserByPathId();*//*
        MapData mapData = new MapData();
        mapData.setPressure(pressure);
        mapData.setTemperature(temperature);
        mapData.setLat(lat);
        mapData.setLng(lng);
        mapData.setPathName(Title);
        MapDatabase
                .getINSTANCE(context)
                .getMapDao()
                .insert(mapData);*/
    }

    /**
     * @param pressure 
     * @param temperature
     * @param lat
     * @param lng
     * @param Title
     * @param context
     * @param pointLat
     * @param pointLng
     * @param photoName
     * @param time
     * @param numberOfPhoto
     */
    private void update(String pressure, String temperature, double lat, double lng, String Title, Context context, ArrayList<String> pointLat, ArrayList<String> pointLng, String photoName, String time, int numberOfPhoto){

        MapData mapData = new MapData();
        mapData.setPressure(pressure);
        mapData.setTemperature(temperature);
        mapData.setLat(lat);
        mapData.setLng(lng);
        mapData.setPathName(Title);
        mapData.setPointLat(pointLat);
        mapData.setPointLng(pointLng);
        mapData.setPhotoName(photoName);
        mapData.setTime(time);
        mapData.setNumberOfPhoto(numberOfPhoto);
        MapDatabase
                .getINSTANCE(context)
                .getMapDao()
                .insert(mapData);
    }
}
