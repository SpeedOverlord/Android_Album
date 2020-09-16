package uk.ac.shef.oak.com6510;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.reactivex.annotations.Nullable;
import uk.ac.shef.oak.com6510.Database.MapData;

/*

When the PathOfPhotoActivity start, it set the view activity_show_photo_bypath, and defined
the recyclerview to R.id.recyclerview_path_ofphoto, and pass all the data ordered by time
to the  PathOfPhotoAdapter, and the programme jump to the PathOfPhotoAdapter to do the further step.

 */

public class PathOfPhotoActivity extends AppCompatActivity {

    private MapViewModel mMapViewModel;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo_bypath);
        RecyclerView recyclerView = findViewById(R.id.recyclerview_path_ofphoto);
        final  PathOfPhotoAdapter adapter = new  PathOfPhotoAdapter(this);
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
