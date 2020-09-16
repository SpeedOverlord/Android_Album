package uk.ac.shef.oak.com6510;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

import uk.ac.shef.oak.com6510.Database.Data;
import uk.ac.shef.oak.com6510.Database.MapData;

public class PathAdapter extends RecyclerView.Adapter<PathAdapter.PhotoViewHolder>  {

    private final LayoutInflater mInflater;
    static private Context context;
    private List<MapData> mWords; // Cached copy of words
   // private String flag_name=" ";
   // private int flag_num=0;


    /**
     * @param context
     */
    PathAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    /**
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public PathAdapter.PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item_path, parent, false);
        PathAdapter.PhotoViewHolder holder =  new PathAdapter.PhotoViewHolder(itemView);
        //return new PhotoViewHolder(itemView);

        context= parent.getContext();
        return holder;


    }

/*

use the getPathName() to get all the pathname, and use setText to show the path
on the screen. set the OnClickListener, when user click the path title, the programme
will jump to  PathOfPhotoActivity,to show all the photo in the current path.

 */


    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(PathAdapter.PhotoViewHolder holder, final int position) {

        if (mWords != null) {
            // holder.textView.setText(items.get(position).path);pathName

            final MapData current = mWords.get(position);
            holder.wordItemView.setText(current.getPathName());

            final Data app = (Data) context.getApplicationContext();

            holder.wordItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    // transfer the current data to the PathOfPhotoAdapter
                    app.setpathid(current.getPathId());
                    app.setpathname(current.getPathName());
                    app.setphotoname(current.getPhotoName());

                    app.settime(current.getNumberOfPhoto());

                    Intent intent = new Intent(context, PathOfPhotoActivity.class);
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });




        } else {

            // Covers the case of data not being ready yet.
            holder.wordItemView.setText("No Photo");

        }
    }

    /**
     * @param words
     */
    void setWords(List<MapData> words){
        mWords = words;
        notifyDataSetChanged();
    }

    /**
     * @return
     */
    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mWords != null)
            return mWords.size();
        else return 0;
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;
       // private final TextView wordItemView_path;

        /**
         * @param itemView
         */
        private PhotoViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView_path);
           // wordItemView_path = itemView.findViewById(R.id.textView_time);
        }
    }

}
