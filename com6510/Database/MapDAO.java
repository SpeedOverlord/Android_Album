package uk.ac.shef.oak.com6510.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/*
 * MapDAO(1)
 * MapDAO defines different ways of database interaction.
 * The insertAll class defines the basic way to
 * insert database data, and the insert class is
 * a commonly used way to insert database.
 * The delete class defines how to delete database data.
 * The getAllPhoto class defines the way to
 * extract the database in chronological order.
 * */

@Dao
public interface MapDAO {

    /**
     * @param mapData
     */
    @Insert
    void insertAll(MapData... mapData);

    /**
     * @param mapData
     */
    @Insert
    void insert(MapData mapData);

    /**
     * @param mapData
     */
    @Delete
    void delete(MapData mapData);


    /**
     * @return
     */
    //select all the data in MapData ordered by time in ASC
    @Query("SELECT * FROM MapData Order By Time ASC")
    LiveData<List<MapData>> getAllPhoto();


  //  @Query("SELECT * FROM MapData WHERE pathName= :pathName")
  //  LiveData<List<MapData>> getPhotoByPathId(int pathId);
}
