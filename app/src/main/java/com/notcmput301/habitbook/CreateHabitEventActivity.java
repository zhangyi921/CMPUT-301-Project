/*
 * CreateHabitEventActivity
 *
 * Version 1.0
 *
 * November 12, 2017
 *
 * Copyright (c) 2017 Team NOTcmput301, CMPUT301, University of Alberta - All Rights Reserved
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in the project wiki on github. Otherwise please contact miller4@ualberta.ca.
 */
package com.notcmput301.habitbook;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

/**
 * Activity for creating new HabitEvent
 *
 * @author NOTcmput301
 * @version 1.0
 * @see HabitEvent
 * @since 1.0
 */
public class CreateHabitEventActivity extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE = 6138;

    private HabitType habit;
    private User loggedInUser;
    private HabitTypeSingleton HTS;
    private ArrayList<HabitType> habitTypes;
    private int position;
    private NetworkHandler nH;


    private EditText commentE;
    private Button addPicB;
    private Button createB;
    private Button backB;
    private CircleImageView imageV;
    private Gson gson = new Gson();
    private Button location;
    private LocationManager locationManager;
    private LocationListener listener;
    private Location currentlocation = null;
    private Bitmap image = null;
    private String b64img = null;

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState previous instance of activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_habit_event);


        Intent receiver = getIntent();
        String u = receiver.getExtras().getString("passedUser");

        this. position = Integer.parseInt(receiver.getExtras().getString("passedPos"));
        this.loggedInUser = gson.fromJson(u, User.class);
        this.HTS = HabitTypeSingleton.getInstance();
        this.habitTypes = HTS.getHabitTypes();

        this.habit = habitTypes.get(position);
        nH = new NetworkHandler(this);

        commentE = (EditText) findViewById(R.id.HED_Comment);
        addPicB = (Button) findViewById(R.id.CHE_AddPhoto);
        createB = (Button) findViewById(R.id.CHE_Create);
        backB = (Button) findViewById(R.id.CHE_Back);
        location = (Button) findViewById(R.id.location);
        imageV = (CircleImageView) findViewById(R.id.HED_Image);
        setDefaultimg();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Double la = location.getLatitude();
                Double lo = location.getLongitude();
                String s = la.toString()+"  "+lo.toString();
                currentlocation = location;
                Toast.makeText(CreateHabitEventActivity.this, s, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

        //Checks if Network Connection is detected.
        BroadcastReceiver br = new NetworkStateChangeReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(br, filter);

        configure_button();
    }


    /**
     * Configures location button for event location
     *
     */
    void configure_button(){
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //noinspection MissingPermission
                locationManager.requestLocationUpdates("gps", 500, 0, listener);
            }
        });
    }

    /**
     * Creates a HabitEvent using provided info
     *
     * @param view View of provided input info
     */
    public void CreateEvent(View view){

        String comment = commentE.getText().toString();
        if (comment.length() > 20){
            Toast.makeText(this, "comment should be less than 20 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        HabitEvent habitEvent = new HabitEvent(habit.getTitle(),comment);
        if (currentlocation != null && b64img == null)
        {
            habitEvent = new HabitEvent(habit.getTitle(), comment, currentlocation.getLatitude(), currentlocation.getLongitude());
        }
        else if (b64img != null && currentlocation == null){
            habitEvent = new HabitEvent(habit.getTitle(), comment, b64img);

        }
        else if (currentlocation != null && b64img != null){
            habitEvent = new HabitEvent(habit.getTitle(), comment, b64img,currentlocation.getLatitude(), currentlocation.getLongitude());
        }

        if (nH.isNetworkAvailable()){
            nH.deleteHabitType(habit);
            habit.addHabitEvent(habitEvent);
            nH.addHabitType(habit);
        }else{
            nH.putString("d", gson.toJson(habit));
            habit.addHabitEvent(habitEvent);
            nH.putString("au", gson.toJson(habit));
        }
        habitTypes.set(position, habit);
        HTS.setHabitTypes(habitTypes);
        back();
        finish();
    }

    /**
     * Sets a default image if no image provided
     *
     */
    public void setDefaultimg(){
        Drawable drawable = getResources().getDrawable(getResources()
                .getIdentifier("photoicon", "drawable", getPackageName()));
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        b64img=bmpCompToBase64(100, bitmap);
        verifySize(b64img);
        imageV.setImageBitmap(image);
    }

    /**
     * Requests permission from user to access device location
     */
    public void requestPermision(){
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to read the contacts
            }
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);            //1 = MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an app-defined int constant that should be quite unique
        } else {
            loadImage();
        }
    }

    /**
     * Adds user-defined image to HabitEvent
     *
     * @param view view of current activity
     */
    public void addImage(View view){
        requestPermision();
    }

    /**
     * Returns to previous activity
     *
     */
    public void back(){
        Intent habitTypeDetail = new Intent(CreateHabitEventActivity.this, HabitTypeDetailsActivity.class);
        habitTypeDetail.putExtra("passedUser", gson.toJson(loggedInUser));
        habitTypeDetail.putExtra("passedPos", Integer.toString(position));
        startActivity(habitTypeDetail);
    }

    /**
     * Returns to previous activity
     *
     * @param view view of current activity
     */
    public void back(View view){
        back();
        finish();
    }

    /**
     * Loads image from device
     *
     */
    public void loadImage(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, RESULT_LOAD_IMAGE);
    }

    //compresses image using zef..Not sure if its actually
    //effective

    /**
     * Obtains bitmap for compressed image
     *
     * @param u Uri for representing file
     */
    public Bitmap getCompressedBitmap(Uri u) throws IOException {
        File f = new File(getRealPathFromURI(this, u));
        return new Compressor(this).compressToBitmap(f);
    }

    //get the actual file path of image file


    /**
     * Obtains actual image file path
     *
     * @param context context of image request
     * @param contentUri Uri representing image
     */
    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    //Verify if it is <65kb

    /**
     * Verifies that the file is less than the maximum size
     *
     * @param base64 string used for checking length
     */
    public Boolean verifySize(String base64){
        double n = base64.length();
        double length = 4*Math.ceil(n/3);
        if (length > 66000){
            Toast.makeText(this, "Image too large, " + Double.toString(length), Toast.LENGTH_SHORT).show();
            return false;
        }
        Toast.makeText(this, Double.toString(length), Toast.LENGTH_SHORT).show();
        return true;
    }

    //returns the base64 String and sets the image

    /**
     * Compresses image to desired size
     *
     * @param compressAmnt amount that image needs to be compressed by
     * @param img bitmap representing image
     */
    public String bmpCompToBase64(int compressAmnt, Bitmap img){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.JPEG, compressAmnt, baos);
        byte[] barr = baos.toByteArray();
        image = BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()));
        return Base64.encodeToString(barr, Base64.DEFAULT);
    }


    /**
     * Handles request activity results
     *
     * @param requestCode code for source of request
     * @param resultCode code for result of request
     * @param data intent from request source
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            try {
                Uri imageUri = data.getData();
                Bitmap img = getCompressedBitmap(imageUri);
                String b64 = bmpCompToBase64(25, img);
                imageV.setImageBitmap(image);
                if (verifySize(b64)){
                    b64img = b64;
                }else{
                    setDefaultimg();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            } catch (IOException e){
                e.printStackTrace();
            }
        }else {
            Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Returns to previous activity
     *
     * @param requestCode code for source of request
     * @param permissions array of permission requests
     * @param grantResults array of result of permission requests
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1){
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadImage();
            } else {
                // permission denied
                Toast.makeText(this, "not okay", Toast.LENGTH_LONG).show();
                new AlertDialog.Builder(this).setTitle("Gallery access denied")
                        .setMessage("For security reasons to upload images from gallery we need your permission, otherwise we cannot upload your photo")
                        .show();
            }
            return;
        }
    }

}