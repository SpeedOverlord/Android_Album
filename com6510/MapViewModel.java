package uk.ac.shef.oak.com6510;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.io.File;
import java.util.List;

import uk.ac.shef.oak.com6510.Database.MapData;

/*
 * MapViewModel(1)
 * MapRepository is referenced in MapViewModel
 * and the database in MapData is accessed using LiveData.
 * The getAllwords method is defined by calling application
 * and class to retrieve the entire contents of the database in Activity.
 * */

public class MapViewModel extends AndroidViewModel {

        private MapRepository mRepository;

        private LiveData<List<MapData>> mAllWords;

    /**
     * @param application
     */
        public MapViewModel (Application application) {
            super(application);
            mRepository = new MapRepository(application);
            mAllWords = mRepository.getAllWords();
        }

    /**
     * @return
     */
        LiveData<List<MapData>> getAllWords() { return mAllWords; }

    /**
     * @param mapData
     */
        public void insert(MapData mapData) { mRepository.insert(mapData); }

}