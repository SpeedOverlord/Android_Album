package uk.ac.shef.oak.com6510;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


import io.reactivex.annotations.Nullable;
import uk.ac.shef.oak.com6510.Database.MapData;

/*

When the ShowPathActivity start, it set the view activity_show_path, and defined
the recyclerview to R.id.recyclerview_path, and pass all the data ordered by time
to the  PathAdapter. and the programme jump to the PathAdapter to do the further step.

 */

public class ShowPathActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{


    private MapViewModel mMapViewModel;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_path);
        RecyclerView recyclerView = findViewById(R.id.recyclerview_path);
        final PathAdapter adapter = new PathAdapter(this);
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

    /**
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
