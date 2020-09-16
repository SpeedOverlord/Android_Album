package uk.ac.shef.oak.com6510.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import uk.ac.shef.oak.com6510.Converter;

/**
 *
 */
@androidx.room.Database(entities = {MapData.class}, version = 1, exportSchema = false)
@TypeConverters({Converter.class})
public abstract class MapDatabase extends RoomDatabase {
   // public abstract MapDAO mapDao();
    private static final String DB_NAME = "MapDatabase.db";
    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile MapDatabase INSTANCE;

    /**
     * @param context
     * @return
     */
   public static synchronized MapDatabase getINSTANCE(Context context){
        if (INSTANCE == null){
            INSTANCE = create(context);
        }
        return INSTANCE;
    }

    /**
     * @param context
     * @return
     */
    private  static MapDatabase create(final Context context){
        return Room.databaseBuilder(
                context,
                MapDatabase.class,
                DB_NAME).addCallback(sRoomDatabaseCallback).build();

    }

    /**
     * @return
     */
    public abstract MapDAO getMapDao();
    /*
    public static MapDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MapDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = androidx.room.Room.databaseBuilder(context.getApplicationContext(),
                            MapDatabase.class, "map_database")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this codelab.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }*/
/*
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // do any init operation about any initialisation here
        }
    };*/
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    //new PopulateDbAsync(INSTANCE).execute();
                }
            };
}
