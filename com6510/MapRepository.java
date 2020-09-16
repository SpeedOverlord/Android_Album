package uk.ac.shef.oak.com6510;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import uk.ac.shef.oak.com6510.Database.MapDAO;
import uk.ac.shef.oak.com6510.Database.MapData;
import uk.ac.shef.oak.com6510.Database.MapDatabase;

/*
 * MapRepository(1)
 * MapRepository uses mMapDao to reference MapDAO,
 * and uses LiveData to define the variable mAllPhoto,
 * which uses the database in MapData.
 * Through getAllWords and insert two classes
 * define the data interaction method inherited from MapDAO.
 * Finally, use insertAsyncTask to define
 * the synchronization class to call in the activity
 * to update the database.
 * */

/**
 *
 */
public class MapRepository {

        private MapDAO mMapDao;
        private LiveData<List<MapData>> mAllPhoto;


    /**
     * @param application
     */
    MapRepository(Application application) {
            MapDatabase db = MapDatabase.getINSTANCE(application);
        mMapDao = db.getMapDao();
        //get all the data by the time asc
        mAllPhoto = mMapDao.getAllPhoto();

        }


    /**
     * @return
     */
         // define a method getAllWords() to return  mAllPhoto
        LiveData<List<MapData>> getAllWords() {
            return mAllPhoto;
        }

    /**
     * @param word
     */
        public void insert (MapData word) {
            new insertAsyncTask(mMapDao).execute(word);
        }

    /**
     *
     */
        private static class insertAsyncTask extends AsyncTask<MapData, Void, Void> {

            private MapDAO mAsyncTaskDao;

        /**
         * @param dao
         */
            insertAsyncTask(MapDAO dao) {
                mAsyncTaskDao = dao;
            }

        /**
         * @param params
         * @return
         */
            @Override
            protected Void doInBackground(final MapData... params) {
                mAsyncTaskDao.insert(params[0]);
                return null;
            }
        }

}
