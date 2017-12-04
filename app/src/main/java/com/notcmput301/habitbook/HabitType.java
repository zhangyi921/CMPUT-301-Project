/*
 * HabitType
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

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import io.searchbox.annotations.JestId;

/**
 * Created by Cole on 2017-10-19.
 */

/**
 * Class for habit type objects
 *
 * @author NOTcmput301
 * @version 1.0
 * @since 1.0
 */
public class HabitType{
    private String ownername;

    private String title;
    private String reason;
    private Date creationDate;
    private Date startDate;
    private Integer eventsCompleted;
    private Integer totalEvents;
    private ArrayList<Boolean> weekdays;
    private ArrayList<HabitEvent> events;

    /**
     * Constructs habit Type object
     *
     * @param owner string for user name
     * @param title string for title of habit
     * @param reason string for reason for starting habit
     * @param startDate date for the start of habit
     * @param weekdays array for weekdays habit occurs on
     */
    public HabitType(String owner, String title, String reason, Date startDate,  ArrayList<Boolean> weekdays ) {
        this.ownername = owner;
        this.title = title;
        this.reason = reason;
        this.creationDate = new Date();
        this.startDate = startDate;
        this.eventsCompleted = 0;
        this.totalEvents = 0;
        this.weekdays = weekdays;
        this.events = new ArrayList<HabitEvent>();
    }

    /**
     * Calculates completion progress of habit Type
     *
     * @return completion progress of Habit Type
     */
    public int getProgress(){
        Date today = new Date();
        Long diff = today.getTime()-startDate.getTime();
        diff = diff/1000/60/60/24;
        if (diff<0){
            return 0;
        }
        Integer e = events.size();
        Float l = e.floatValue();
        Float d = diff.floatValue();
        Integer i  =0;
        for (Boolean b : weekdays){
            if (b){
                i += 1;
            }
        }
        d = d*i.floatValue()/7;
        Log.e(";", d.toString());

        d = l/d;
        if (d == null){
            return 0;
        }
        d = d*100;
        return d.intValue();
    }

    /**
     * Gets title of habit
     *
     * @return habit type title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets new title of habit
     *
     * @param title string for new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets reason for habit
     *
     * @return habit type reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets new reason for habit
     *
     * @param reason string for new reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * Gets start date of habit
     *
     * @return start date of habit
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets new creation date of habit
     *
     * @param date Date for new creation date
     */
    public void setCreationDate(Date date){
        this.creationDate = date;
    }

    /**
     * Gets creation date of habit
     *
     * @return creation date of habit
     */
    public Date getCreationDate(){
        return this.creationDate;
    }

    /**
     * Sets new start date of habit
     *
     * @param startDate date for new start date of habit
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets number of habit events completed
     *
     * @return number of habit events completed
     */
    public Integer getEventsCompleted() {
        return eventsCompleted;
    }

    /**
     * Sets number of events completed
     *
     * @param eventsCompleted integer for new number of events completed
     */
    public void setEventsCompleted(Integer eventsCompleted) {
        this.eventsCompleted = eventsCompleted;
    }

    /**
     * Gets total possible events of habit
     *
     * @return total possible events of habit
     */
    public Integer getTotalEvents() {
        Integer i = this.events.size();
        return i;
    }

    /**
     * Gets weekdays habit occurs on
     *
     * @return weekdays habit occurs on
     */
    public ArrayList<Boolean> getWeekdays() {
        return weekdays;
    }

    /**
     * Sets the weekdays that the habit occurs on
     *
     * @param weekdays array used to set weekdays
     */
    public void setWeekdays(ArrayList<Boolean> weekdays) {
        this.weekdays = weekdays;
    }

    /**
     * Gets list of events for this habit
     *
     * @return list of events for this habit
     */
    public ArrayList<HabitEvent> getEvents() {
        return events;
    }

    /**
     * Sets list of events for habit
     *
     * @param events array containing events for habit
     */
    public void setEvents(ArrayList<HabitEvent> events) {
        this.events = events;
    }

    /**
     * Adds an event to the list of events
     *
     * @param event habit event to be inserted
     */
    public void addHabitEvent(HabitEvent event) {
        this.events.add(event);
        this.totalEvents += 1;
    }

    /**
     * Removes an event from the list of habits
     *
     * @param event habit event to be inserted
     */
    public void removeHabitEvent(HabitEvent event) {
        this.events.remove(event);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HabitType habitType = (HabitType) o;

        //if (!getOwner().equals(habitType.getOwner())) return false;
        return getTitle().equals(habitType.getTitle());
    }

    @Override
    public String toString(){
        return this.title;
    }

}
