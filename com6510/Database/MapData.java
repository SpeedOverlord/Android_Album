package uk.ac.shef.oak.com6510.Database;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import android.graphics.Bitmap;

import java.util.ArrayList;

import uk.ac.shef.oak.com6510.Converter;

/*
 * MapData(1)
 * MapRepository uses mMapDao to reference MapDAO,
 * and uses LiveData to define the variable mAllPhoto,
 * which uses the database in MapData.
 * Through getAllWords and insert two classes
 * define the data interaction method inherited from MapDAO.
 * Finally, use insertAsyncTask to define
 * the synchronization class to call in the activity
 * to update the database.
 * */

@Entity()
@TypeConverters({Converter.class})
public class MapData {
    @PrimaryKey(autoGenerate = true)
    @androidx.annotation.NonNull
    private int pathId = 0;
   // @ColumnInfo(name = "pressure")
    private String pressure;
    //@ColumnInfo(name = "temperature")
    private String temperature;
   // @ColumnInfo(name = "lat")
    private double lat;
   // @ColumnInfo(name = "lng")
    private double lng;
   // @ColumnInfo(name = "pathName")
    private String pathName;
    private ArrayList<String> pointLat;
    private ArrayList<String> pointLng;
    private String photoName;
    private String time;
    private int numberOfPhoto;


    /**
     * @return
     */
    public int getPathId() {
        return pathId;
    }

    /**
     * @param pathId
     */
    public void setPathId(int pathId) {
        this.pathId = pathId;
    }

    /**
     * @return
     */
    public String getPressure() {
        return pressure;
    }

    /**
     * @param pressure
     */
    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    /**
     * @return
     */
    public String getTemperature() {
        return temperature;
    }

    /**
     * @param temperature
     */
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    /**
     * @return
     */
    public double getLat() {
        return lat;
    }

    /**
     * @param lat
     */
    public void setLat(double lat) {
        this.lat = lat;
    }

    /**
     * @return
     */
    public double getLng() {
        return lng;
    }

    /**
     * @param lng
     */
    public void setLng(double lng) {
        this.lng = lng;
    }

    /**
     * @return
     */
    public String getPathName() {
        return pathName;
    }

    /**
     * @param pathName
     */
    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    /**
     * @return
     */
    public ArrayList<String> getPointLat() {
        return pointLat;
    }

    /**
     * @param pointLat
     */
    public void setPointLat(ArrayList<String> pointLat) {
        this.pointLat = pointLat;
    }

    /**
     * @return
     */
    public ArrayList<String> getPointLng() {
        return pointLng;
    }

    /**
     * @param pointLng
     */
    public void setPointLng(ArrayList<String> pointLng) {
        this.pointLng = pointLng;
    }

    /**
     * @return
     */
    public String getPhotoName() {
        return photoName;
    }

    /**
     * @param photoName
     */
    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    /**
     * @return
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return
     */
    public int getNumberOfPhoto() {
        return numberOfPhoto;
    }

    /**
     * @param numberOfPhoto
     */
    public void setNumberOfPhoto(int numberOfPhoto) {
        this.numberOfPhoto = numberOfPhoto;
    }

}
