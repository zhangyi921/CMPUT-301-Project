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

/*    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }*/

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setCreationDate(Date date){
        this.creationDate = date;
    }

    public Date getCreationDate(){
        return this.creationDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Integer getEventsCompleted() {
        return eventsCompleted;
    }

    public void setEventsCompleted(Integer eventsCompleted) {
        this.eventsCompleted = eventsCompleted;
    }

    public Integer getTotalEvents() {
        Integer i = this.events.size();
        return i;
    }

    public ArrayList<Boolean> getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(ArrayList<Boolean> weekdays) {
        this.weekdays = weekdays;
    }

    public ArrayList<HabitEvent> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<HabitEvent> events) {
        this.events = events;
    }

    public void addHabitEvent(HabitEvent event) {
        this.events.add(event);
        this.totalEvents += 1;
    }

    public void removeHabitEvent(HabitEvent event) {
        this.events.remove(event);
    }

    public double calculateCompletion() {
        return 0.0;
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
