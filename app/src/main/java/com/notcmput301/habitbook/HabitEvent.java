package com.notcmput301.habitbook;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.media.Image;
import android.util.Base64;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Cole on 2017-10-20.
 */

public class HabitEvent {

    //private HabitType habitType;
    private String user;
    private String habit;
    private String comment;
    private String image;
    private Date date;
    private int likes;
    private int dislikes;
    private Double latitude = null;
    private Double longitude = null;

    public HabitEvent(String user, String habit, String comment){
        this.user = user;
        this.habit = habit;
        this.comment = comment;
        this.image = null;
        this.date = new Date();
        this.likes = 0;
        this.dislikes = 0;
    }

    public HabitEvent(String user, String habit,String comment, Double latitude, Double longitude){
        this.user = user;
        this.habit = habit;
        this.comment = comment;
        this.image = null;
        this.date = new Date();
        this.likes = 0;
        this.dislikes = 0;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public HabitEvent(String user, String habit,String comment, String image){
        this.user = user;
        this.habit = habit;
        this.comment = comment;
        this.image = image;
        this.date = new Date();
        this.likes = 0;
        this.dislikes = 0;
    }

    public HabitEvent(String user, String habit,String comment, String image, Double latitude, Double longitude){
        this.user = user;
        this.habit = habit;
        this.comment = comment;
        this.image = image;
        this.date = new Date();
        this.likes = 0;
        this.dislikes = 0;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getUser(){
        return user;
    }
    public Double getLatitude(){
        return latitude;
    }

    public Double getLongitude(){
        return longitude;
    }
    public void setLatitude(Double latitude){
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude){
        this.longitude = longitude;
    }

    public void setHabit(String habit){
        this.habit = habit;
    }

    public String getHabit(){
        return habit;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

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

    public Bitmap imageToBitmap(){
        byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
        return  BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    
    // Sort by date coded by Matteo
    // returns HabitEvent with dates sorted from most recent to least recent
    public ArrayList<HabitEvent> sortByDate(ArrayList<HabitEvent> inputEvents) {
        if (inputEvents.isEmpty() ) return inputEvents; // No sorting necessary if param is empty

        ArrayList<HabitEvent> outputEvents = new ArrayList<HabitEvent>(); // Initialize output list

        // Puts first element in output list
        HabitEvent temp = inputEvents.get(0);
        outputEvents.add(temp);

        // Iterates through inputList
        for (int i = 1; i < inputEvents.size(); i++) {
            // Get date for element from input list
            temp = inputEvents.get(i);
            Date inputDate = temp.getDate();

            // Iterates through outputList
            for (int j = 0; j < outputEvents.size(); j++ ) {
                // Get date for output list
                temp = outputEvents.get(j);
                Date outputDate = temp.getDate();

                // If input date is more recent than output element, put element in output
                // list, then break
                if (inputDate.after(outputDate) ) {
                    outputEvents.add(j, inputEvents.get(i) );
                    break;
                }

                // If on last output list iteration, append current input list element
                if (j == (outputEvents.size() - 1) ) outputEvents.add(inputEvents.get(i) );
            }
        }

        // Returns eventlist, sorted by dates
        return outputEvents;
    }
    
/*    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (o instanceof  HabitEvent){
            HabitEvent test = (HabitEvent) o;
            if ( this.comment.equals(test.getComment())
                    && this.image.equals(test.getImage()) &&  this.date.equals(test.getDate()) &&
                    this.likes == test.getLikes() && this.dislikes == test.getDislikes() &&
                    this.location.equals(test.getLocation())) return true; }
        return false;
    }*/

    @Override
    public String toString(){
        return this.comment;
    }
}