package com.notcmput301.habitbook;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class CreateHabitEventActivity extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE = 1;
    private User loggedInUser;
    private HabitType habit;
    private EditText titleE;
    private EditText commentE;
    private Button addPicB;
    private Button createB;
    private Button backB;
    private ImageView imageV;
    private Gson gson = new Gson();
    private String encodedimage;

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
        imageV = (ImageView) findViewById(R.id.CHE_Image);
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
        Intent habitdetails = new Intent(CreateHabitEventActivity.this, HabitTypeDetailsActivity.class);
        habitdetails.putExtra("passedUser", gson.toJson(loggedInUser));
        habitdetails.putExtra("passedHabitType", gson.toJson(habit));
        startActivity(habitdetails);
    }

    public void loadImage(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageV.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
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
