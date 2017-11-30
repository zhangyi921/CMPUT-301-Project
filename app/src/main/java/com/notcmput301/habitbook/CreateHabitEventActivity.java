package com.notcmput301.habitbook;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class CreateHabitEventActivity extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE = 6138;
    private User loggedInUser;
    private HabitType habit;
    private EditText titleE;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_habit_event);
        Intent receiver = getIntent();
        String u = receiver.getExtras().getString("passedUser");
        String h = receiver.getExtras().getString("passedHabitType");
        loggedInUser = gson.fromJson(u, User.class);
        habit = gson.fromJson(h, HabitType.class);
        titleE = (EditText) findViewById(R.id.CHE_Title);
        commentE = (EditText) findViewById(R.id.CHE_Comment);
        addPicB = (Button) findViewById(R.id.CHE_AddPhoto);
        createB = (Button) findViewById(R.id.CHE_Create);
        backB = (Button) findViewById(R.id.CHE_Back);
        location = (Button) findViewById(R.id.location);
        imageV = (CircleImageView) findViewById(R.id.CHE_Image);


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

        configure_button();
    }

/*    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }*/


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







    public void CreateEvent(View view){

        ElasticSearch.deleteHabitType delHT = new ElasticSearch.deleteHabitType();
        delHT.execute(loggedInUser.getUsername(), habit.getTitle());
        try{
            boolean result = delHT.get();
            if (result){
                //Toast.makeText(this, "deleted item!", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(this, "Failed to delete", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Failed to delete", Toast.LENGTH_SHORT).show();
        }

        HabitEvent habitEvent = new HabitEvent(habit.getTitle(), commentE.getText().toString());

        if (currentlocation != null && b64img == null)
        {
             habitEvent = new HabitEvent(habit.getTitle(), commentE.getText().toString(), currentlocation.getLatitude(), currentlocation.getLongitude());
        }
        else if (b64img != null && currentlocation == null){
            habitEvent = new HabitEvent(habit.getTitle(), commentE.getText().toString(), b64img);

        }
        else if (currentlocation != null && b64img != null){
            habitEvent = new HabitEvent(habit.getTitle(), commentE.getText().toString(), b64img,currentlocation.getLatitude(), currentlocation.getLongitude());
        }
        habit.addHabitEvent(habitEvent);
        ElasticSearch.addHabitType aht = new ElasticSearch.addHabitType();
        aht.execute(habit);
        try{
            boolean success = aht.get();
            if (!success){
                Toast.makeText(this, "Opps, Something went wrong on our end", Toast.LENGTH_SHORT).show();
            }else{

                Toast.makeText(this, "habit event added! ", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        }catch(Exception e){
            Log.e("get failure", "Failed to retrieve");
            e.printStackTrace();
        }
        finish();
    }

    public void setDefaultimg(){
        Drawable drawable = getResources().getDrawable(getResources()
                .getIdentifier("photoicon", "drawable", getPackageName()));
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        b64img=bmpCompToBase64(100, bitmap);
        verifySize(b64img);
        imageV.setImageBitmap(image);
    }

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

    public void addImage(View view){
        requestPermision();
    }

    public void back(View view){
        Intent habittypelist = new Intent(CreateHabitEventActivity.this, HabitTypeList2.class);
        habittypelist.putExtra("passedUser", gson.toJson(loggedInUser));
        finish();
        startActivity(habittypelist);
    }

    public void loadImage(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, RESULT_LOAD_IMAGE);
    }

    //compresses image using zef..Not sure if its actually
    //effective
    public Bitmap getCompressedBitmap(Uri u) throws IOException {
        File f = new File(getRealPathFromURI(this, u));
        return new Compressor(this).compressToBitmap(f);
    }

    //get the actual file path of image file
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
    public String bmpCompToBase64(int compressAmnt, Bitmap img){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.JPEG, compressAmnt, baos);
        byte[] barr = baos.toByteArray();
        image = BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()));
        return Base64.encodeToString(barr, Base64.DEFAULT);
    }



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
