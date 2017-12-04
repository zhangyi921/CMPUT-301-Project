/*
 * HabitEvent
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

/**
 * Represents a HabitEvent
 *
 * @author NOTcmput301
 * @version 1.0
 * @since 1.0
 */
public class HabitEvent {

    //private HabitType habitType;
    private String habit;
    private String comment;
    private String image;
    private Date date;
    private int likes;
    private int dislikes;
    private Double latitude = null;
    private Double longitude = null;

    /**
     * Constructs a HabitEvent
     *
     * @param habit HabitType that the event belongs to
     * @param comment optional comment for event
     */
    public HabitEvent(String habit, String comment){
        this.habit = habit;
        this.comment = comment;
        this.image = null;
        this.date = new Date();
        this.likes = 0;
        this.dislikes = 0;
    }

    /**
     * Constructs a HabitEvent
     *
     * @param habit HabitType that the event belongs to
     * @param comment optional comment for event
     * @param latitude latitude of event location
     * @param longitude longitude of event location
     */
    public HabitEvent(String habit,String comment, Double latitude, Double longitude){
        this.habit = habit;
        this.comment = comment;
        this.image = null;
        this.date = new Date();
        this.likes = 0;
        this.dislikes = 0;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Constructs a HabitEvent
     *
     * @param habit HabitType that the event belongs to
     * @param comment optional comment for event
     * @param image string representing event photo
     */
    public HabitEvent(String habit,String comment, String image){
        this.habit = habit;
        this.comment = comment;
        this.image = image;
        this.date = new Date();
        this.likes = 0;
        this.dislikes = 0;
    }

    /**
     * Constructs a HabitEvent
     *
     * @param habit HabitType that the event belongs to
     * @param comment optional comment for event
     * @param image string representing image
     * @param latitude latitude of event location
     * @param longitude longitude of event location
     */
    public HabitEvent(String habit, String comment, String image, Double latitude, Double longitude){
        this.habit = habit;
        this.comment = comment;
        this.image = image;
        this.date = new Date();
        this.likes = 0;
        this.dislikes = 0;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Gets the latitude of event location
     *
     * @return latitiude of event location
     */
    public Double getLatitude(){
        return latitude;
    }

    /**
     * Gets the longitude of event location
     *
     * @return longitude of event location
     */
    public Double getLongitude(){
        return longitude;
    }

    /**
     * Sets the latitude of event location
     *
     * @param latitude latitiude of event location
     */
    public void setLatitude(Double latitude){
        this.latitude = latitude;
    }

    /**
     * Sets the longitude of event location
     *
     * @param longitude longitude of event location
     */
    public void setLongitude(Double longitude){
        this.longitude = longitude;
    }

    /**
     * Sets the HabitType that the event belongs to
     *
     * @param habit the HabitType the event belongs
     */
    public void setHabit(String habit){
        this.habit = habit;
    }

    /**
     * Gets the HabitType the event belongs to
     *
     * @return HabitType event belongs to
     */
    public String getHabit(){
        return habit;
    }

    /**
     * Gets the event comment
     *
     * @returnevent comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the event comment
     *
     * @param comment event comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Gets the event image
     *
     * @return event image
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the event image
     *
     * @param image event image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Gets the levent date
     *
     * @return event date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the event date
     *
     * @param creationDate event date
     */
    public void setDate(Date creationDate) {
        this.date = date;
    }

    /**
     * Gets the number of likes the event has
     *
     * @return number of lijes the event has
     */
    public int getLikes() {
        return likes;
    }

    /**
     * Sets the number of likes
     *
     * @param likes number of likes
     */
    public void setLikes(int likes) {
        this.likes = likes;
    }

    /**
     * Gets the number of dislikes the event has
     *
     * @return number of dislikes the event has
     */
    public int getDislikes() {
        return dislikes;
    }

    /**
     * Sets the number of dislikes
     *
     * @param dislikes the number of dislikes
     */
    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    /**
     * Translates image into bitmap
     *
     * @return Bitmap representing image
     */
    public Bitmap imageToBitmap(){
        byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
        return  BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    
    // Sort by date coded by Matteo
    // returns HabitEvent with dates sorted from most recent to least recent

    /**
     * Sorts a list of HabitEvents by date.
     *
     * @return sorted list of HabitEvents
     */
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