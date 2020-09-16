package uk.ac.shef.oak.com6510;

import android.os.Bundle;
import androidx.lifecycle.Observer;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.reactivex.annotations.Nullable;
import uk.ac.shef.oak.com6510.Database.MapData;
/*

When the ShowPhoto start, it use the view activity_show_photo.xml and the PhotoAdapter
to show all the data of photos; Use the MapViewModel getAllWords() method to get all
the data sorted by date. Set onCreate mathod to let PhotoAdapter update the data.

 */

public class ShowPhoto extends AppCompatActivity {



    private MapViewModel mMapViewModel;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final PhotoAdapter adapter = new PhotoAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMapViewModel = ViewModelProviders.of(this).get(MapViewModel.class);
        mMapViewModel.getAllWords().observe(this, new Observer<List<MapData>>() {
            @Override
            public void onChanged(@Nullable final List<MapData> words) {
                // Update the cached copy of the words in the adapter.
                adapter.setWords(words);
            }
        });
    }
}
