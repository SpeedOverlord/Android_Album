package uk.ac.shef.oak.com6510;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String storeName;
    private static final int PERMISSION_CAMERA_REQUEST_CODE = 000;

    /**
     * @param savedInstanceState
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Main");
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        final EditText editText = (EditText)findViewById(R.id.editText);
        SimpleDateFormat   formatter   =   new SimpleDateFormat   ("yyyy/MM/dd   HH:mm");
        Date curDate =  new Date(System.currentTimeMillis());
        final String TimesString   =   formatter.format(curDate);

        checkPermissions();

        /*
         * MainActivity(1)
         * button Start listener to check whether the user clicks the button or not.
         * if click, it will activate MapsActivity and transfer some important parameters to MapsActivity
         * like path name( user's input + current time, which called finalName) and current time(which called TimesString).
         * getName is for recording Title that the user types
         * */

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getName = editText.getText().toString();
                String finalName = getName+ "   "+TimesString;
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, MapsActivity.class);
                intent.putExtra("String_Name", finalName);
                intent.putExtra("PathTime", TimesString);
                startActivity(intent);
            }
        });

        /*
         * MainActivity(2)
         * button Start listener to check whether the user clicks the button or not.
         * if click, it will activate ShowPhoto which show all photos that user take and it is order by time.
         * */
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ShowPhoto.class);
                startActivity(intent);

            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ShowPathActivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     * @param keyCode
     * @param event
     * @return
     */
    // Disable back button
    @Override

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }

    /**
     * @return
     */
    private String getDateFormat() {
        return Settings.System.getString(getContentResolver(),
                Settings.System.DATE_FORMAT);
    }


    /**
     *
     */
    private void checkPermissions() {
        List<String> permissionList = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.CAMERA);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }


        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    permissionList.toArray(new String[permissionList.size()]), 123);
        } else {
        }
    }

    /**
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 123:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] == PackageManager.PERMISSION_DENIED){
                        }
                    }
                }
                break;
        }
    }

}
