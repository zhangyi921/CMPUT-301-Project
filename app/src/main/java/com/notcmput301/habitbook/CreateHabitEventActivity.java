package com.notcmput301.habitbook;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

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
    private File image;

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
        imageV = (CircleImageView) findViewById(R.id.CHE_Image);
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

    public Bitmap verifySizeFromURI(Uri u) throws IOException {
        File f = new File(getRealPathFromURI(this, u));
        long size  = f.length()/1024;
        Toast.makeText(this, ""+size+" kb", Toast.LENGTH_LONG).show();
        File newf = new Compressor(this).compressToFile(f);
        size  = newf.length()/1024;
        Toast.makeText(this, "AFTER COMPRESSION: "+size+" kb", Toast.LENGTH_LONG).show();
        return new Compressor(this).compressToBitmap(newf);
    }

    public void getsizebmp(Bitmap bmp){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageInByte = stream.toByteArray();
        long lengthbmp = imageInByte.length/1024;
        Toast.makeText(this, "bmpsize "+lengthbmp+" kb", Toast.LENGTH_LONG).show();
    }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            try {
                Uri imageUri = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(imageUri);
                Bitmap img = BitmapFactory.decodeStream(imageStream);
                getsizebmp(img);
                //getSize(img);
                //getsizebmp(verifySizeFromURI(imageUri));
                Toast.makeText(this, "YEYEYEYE "+(verifySizeFromURI(imageUri).getAllocationByteCount()/1024)+" kb", Toast.LENGTH_LONG).show();
                imageV.setImageBitmap(verifySizeFromURI(imageUri));
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
