package uk.ac.shef.oak.com6510;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Converter {
    /**
     * @param value
     * @return
     */
    @TypeConverter
    public static ArrayList<String> fromString(String value) {
        return new Gson().fromJson(value, new TypeToken<ArrayList<String>>() {}.getType());
    }

    /**
     * @param list
     * @return
     */
    @TypeConverter
    public static String fromArrayList(ArrayList<String> list)  {
        return new Gson().toJson(list);
    }
}
