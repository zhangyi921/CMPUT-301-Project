package com.notcmput301.habitbook;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.media.Image;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.R.attr.bitmap;

/**
 * Created by Cole on 2017-10-20.
 */

public class HabitEvent {

    private HabitType habitType;
    private String comment;
    private Bitmap bm;
    private byte[] imageByteArray;
    private String imagePath;
    private String locationString;
    private Image image;
    private Date date;
    private int likes;
    private int dislikes;
    private Location location;

    public HabitEvent(HabitType habit, String comment){
        this.habitType = habit;
        this.comment = comment;
        this.image = null;
        this.date = new Date();
        this.likes = 0;
        this.dislikes = 0;
        this.location = null;
    }

/*    public HabitEvent(HabitType habit, String comment, String location){
        this.habitType = habit;
        this.comment = comment;
        this.image = null;
        this.date = new Date();
        this.likes = 0;
        this.dislikes = 0;
        this.locationString = location;
    }

    public HabitEvent(HabitType habit, String comment, String image){
        this.habitType = habit;
        this.comment = comment;
        this.imagePath = image;
        this.date = new Date();
        this.likes = 0;
        this.dislikes = 0;
        this.location = null;
    }*/

    public HabitEvent(HabitType habit, String comment, String image, String location){
        this.habitType = habit;
        this.comment = comment;
        if (image != null) {
            this.imagePath = image;
        }
        else{
            this.imagePath = null;
        }
        this.date = new Date();
        this.likes = 0;
        this.dislikes = 0;
        if (location != null){
            this.locationString = location;
        }
        else{
            this.locationString = null;
        }
    }

    public HabitType getHabitType() {
        return habitType;
    }

    public void setHabitType(HabitType habitType) {
        this.habitType = habitType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Bitmap getImage() {
        //return image;
        return bm;
    }

    public void setImage(String imagePath){
        this.image = image;
    }
/*
mignt use this in future.
    public void setImage(String imagePath) {
        bm= BitmapFactory.decodeFile(imagePath);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        imageByteArray = stream.toByteArray();
        int imageByteCount = imageByteArray.length;
        while (imageByteCount >= 65536) {
            bm.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        }
        this.image = image;
    }
*/

    public Date getDate() {
        return date;
    }

    public void setDate(Date creationDate) {
        this.date = date;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HabitEvent that = (HabitEvent) o;

        if (!getHabitType().equals(that.getHabitType())) return false;
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return fmt.format(this).equals(fmt.format(that));
    }

    @Override
    public String toString(){
        return this.comment;
    }
}
