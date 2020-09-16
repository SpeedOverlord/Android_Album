package uk.ac.shef.oak.com6510;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


import io.reactivex.BackpressureOverflowStrategy;
import io.reactivex.internal.operators.completable.CompletableDoFinally;
import uk.ac.shef.oak.com6510.Database.MapData;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {
    private final LayoutInflater mInflater;
    static private Context context;
    private List<MapData> mWords; // Cached copy of words
   // private String loc;
   // private String pressure;
    //private String temperature;
  //  private double lat;
   // private double lng;
    private  ArrayList<String> pointsLat;
    private  ArrayList<String> pointsLng;
    private Bundle extra_pointsLat;
    private Bundle extra_pointsLng;

    /**
     * @param context
     */
    PhotoAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    /**
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        PhotoViewHolder holder =  new PhotoViewHolder(itemView);
        //return new PhotoViewHolder(itemView);

        context= parent.getContext();
        return holder;


    }

/*

Get the data sorted by time in asc order,use the current.getPhotoName() to get the photo name
After we get the Path of the photo,we use the setImageBitmap(myBitmap) to print the image to the
ImageItemView. ImageItemView is defined in the PhotoViewHolder, it connects to the specific ViewId.

 */


    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(PhotoViewHolder holder, final int position) {


        if (mWords != null) {
            final String loc;
            final MapData current = mWords.get(position);
            final String photoName = current.getPhotoName();
            final String pressure = current.getPressure();
            final String temperature = current.getTemperature();
            final double lat = current.getLat();
            final double lng = current.getLng();
            String state = Environment.getExternalStorageState();
             pointsLat = current.getPointLat();
             pointsLng = current.getPointLng();
            if (!state.equals(Environment.MEDIA_MOUNTED)) {
                String dir2 = Environment.getDataDirectory().getAbsolutePath();
                 loc = dir2+"/Android/data/uk.ac.shef.oak.com6510/files/Pictures/"+photoName;
                File img = new File(dir2+"/Android/data/uk.ac.shef.oak.com6510/files/Pictures/"+photoName);
                Bitmap myBitmap = BitmapFactory.decodeFile(img.getAbsolutePath());
                holder.ImageItemView.setImageBitmap(myBitmap);

            }
            else{
                String dir = Environment.getExternalStorageDirectory().getAbsolutePath();
                 loc = dir+"/Android/data/uk.ac.shef.oak.com6510/files/Pictures/"+photoName;
                File img = new File(dir+"/Android/data/uk.ac.shef.oak.com6510/files/Pictures/"+photoName);

                Bitmap myBitmap = BitmapFactory.decodeFile(img.getAbsolutePath());
                holder.ImageItemView.setImageBitmap(myBitmap);
            }
            holder.wordItemView.setText(current.getPathName());

            extra_pointsLat = new Bundle();
            extra_pointsLat.putSerializable("objects", pointsLat);
            extra_pointsLng = new Bundle();
            extra_pointsLng.putSerializable("objects", pointsLng);


            // Set the onClickListener, when the user click the image, it will jump to ShowPhotoDetailActivity

            // when the onClickListener on, transfer the following data to the ShowPhotoDetailActivity
            holder.ImageItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context, ShowPhotoDetailActivity.class);
                    intent.putExtra("String_Img",loc);
                    intent.putExtra("name_path", current.getPathName());
                    intent.putExtra("String_pressure",pressure);
                    intent.putExtra("String_temperature", temperature);
                    intent.putExtra("Lat",lat);
                    intent.putExtra("Lng", lng);
                    intent.putExtra("extraPointsLat", extra_pointsLat);
                    intent.putExtra("extraPointsLng", extra_pointsLng);
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
        private final ImageView ImageItemView;

        /**
         * @param itemView
         */
        private PhotoViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);
            ImageItemView = itemView.findViewById(R.id.imageView);
        }
    }

}
