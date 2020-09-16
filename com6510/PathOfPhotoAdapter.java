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

import uk.ac.shef.oak.com6510.Database.Data;
import uk.ac.shef.oak.com6510.Database.MapData;

public class PathOfPhotoAdapter extends RecyclerView.Adapter<PathOfPhotoAdapter.PhotoViewHolder>  {

    private final LayoutInflater mInflater;
    static private Context context;
    private List<MapData> mWords; // Cached copy of words
    private ArrayList<String> pointsLat;
    private  ArrayList<String> pointsLng;
    private Bundle extra_pointsLat;
    private Bundle extra_pointsLng;
    private int times;


    /**
     * @param context
     */
    PathOfPhotoAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        Data app = (Data)context.getApplicationContext();
        times = app.gettime();
    }

    /**
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public PathOfPhotoAdapter.PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item_path_ofphoto, parent, false);
        PathOfPhotoAdapter.PhotoViewHolder holder =  new PathOfPhotoAdapter.PhotoViewHolder(itemView);
        //return new PhotoViewHolder(itemView);

        context= parent.getContext();
        return holder;


    }
/*
PathOfPhotoAdapter(1):
The pathActivity transfered the pathname and photoName,
use photoname to get absolute path to get the photo and use setImageBitmap
to print the image to the specific ViewId.

 */


    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(PathOfPhotoAdapter.PhotoViewHolder holder, final int position) {
   // PathOfPhotoAdapter(1)
        final Data app = (Data)context.getApplicationContext();

        if (mWords != null) {

            final String loc;
            final MapData current = mWords.get(position);
            final String pressure = current.getPressure();
            final String temperature = current.getTemperature();
            final double lat = current.getLat();
            final double lng = current.getLng();
            pointsLat = current.getPointLat();
            pointsLng = current.getPointLng();

            holder.wordItemView.setText(app.getpathname());

            final String photoName = app.getphotoname();

            times = app.gettime();

            String state = Environment.getExternalStorageState();
            if (!state.equals(Environment.MEDIA_MOUNTED)) {
                String dir2 = Environment.getDataDirectory().getAbsolutePath();
                loc = dir2+"/Android/data/uk.ac.shef.oak.com6510/files/Pictures/"+photoName;
                File img = new File(dir2+"/Android/data/uk.ac.shef.oak.com6510/files/Pictures/"+photoName);
                Bitmap myBitmap = BitmapFactory.decodeFile(img.getAbsolutePath());
                holder. imageItemView_path_ofdata.setImageBitmap(myBitmap);
            }
            else{
                String dir = Environment.getExternalStorageDirectory().getAbsolutePath();
                loc = dir+"/Android/data/uk.ac.shef.oak.com6510/files/Pictures/"+photoName;
                File img = new File(dir+"/Android/data/uk.ac.shef.oak.com6510/files/Pictures/"+photoName);
                Bitmap myBitmap = BitmapFactory.decodeFile(img.getAbsolutePath());
                holder. imageItemView_path_ofdata.setImageBitmap(myBitmap);
            }
           // holder.wordItemView.setText(current.getPathName());

            extra_pointsLat = new Bundle();
            extra_pointsLat.putSerializable("objects", pointsLat);
            extra_pointsLng = new Bundle();
            extra_pointsLng.putSerializable("objects", pointsLng);


            // Set the onClickListener, when the user click the image, it will jump to ShowPhotoDetailActivity_path
            //PathOfPhotoAdapter(3)
            // when the onClickListener on, transfer the following data to the ShowPhotoDetailActivity_path
            holder.imageItemView_path_ofdata.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, ShowPhotoDetailActivity_path.class);
                    intent.putExtra("position_path", position);

                    intent.putExtra("String_Img_path",loc);
                    intent.putExtra("name_path_bypath", app.getpathname());
                    intent.putExtra("String_pressure_path",pressure);
                    intent.putExtra("String_temperature_path", temperature);
                    intent.putExtra("Lat_path",lat);
                    intent.putExtra("Lng_path", lng);
                    intent.putExtra("extraPointsLat_path", extra_pointsLat);
                    intent.putExtra("extraPointsLng_path", extra_pointsLng);
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
     //   if (mWords != null)
          //  return mWords.size();
        if (mWords != null){

            return  times;
        }


        else return 0;
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;
        private final ImageView imageItemView_path_ofdata;

        /**
         * @param itemView
         */
        private PhotoViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView_path_ofphoto);
            imageItemView_path_ofdata = itemView.findViewById(R.id.imageView_path_ofphoto);
        }
    }

}
