package com.notcmput301.habitbook;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Cole on 2017-10-19.
 */

public class HabitType implements Parcelable {
    private String ownername;
    private User owner;
    private String title;
    private String reason;
    private Date creationDate;
    private Date startDate;
    private Integer eventsCompleted;
    private Integer totalEvents;
    private ArrayList<Boolean> weekdays;
    private ArrayList<HabitEvent> events;

    public HabitType(User owner, String title, String reason, Date startDate,  ArrayList<Boolean> weekdays ) {
        this.ownername = owner.getUsername();
        this.owner = owner;
        this.title = title;
        this.reason = reason;
        this.creationDate = new Date();
        this.startDate = startDate;
        this.eventsCompleted = 0;
        this.totalEvents = 0;
        this.weekdays = weekdays;
        this.events = new ArrayList<HabitEvent>();
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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
        return totalEvents;
    }

    public void setTotalEvents(Integer totalEvents) {
        this.totalEvents = totalEvents;
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

        if (!getOwner().equals(habitType.getOwner())) return false;
        return getTitle().equals(habitType.getTitle());
    }

    @Override
    public String toString(){
        return this.title;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ownername);
        dest.writeParcelable(this.owner, flags);
        dest.writeString(this.title);
        dest.writeString(this.reason);
        dest.writeLong(this.creationDate != null ? this.creationDate.getTime() : -1);
        dest.writeLong(this.startDate != null ? this.startDate.getTime() : -1);
        dest.writeValue(this.eventsCompleted);
        dest.writeValue(this.totalEvents);
        dest.writeList(this.weekdays);
        dest.writeList(this.events);
    }

    protected HabitType(Parcel in) {
        this.ownername = in.readString();
        this.owner = in.readParcelable(User.class.getClassLoader());
        this.title = in.readString();
        this.reason = in.readString();
        long tmpCreationDate = in.readLong();
        this.creationDate = tmpCreationDate == -1 ? null : new Date(tmpCreationDate);
        long tmpStartDate = in.readLong();
        this.startDate = tmpStartDate == -1 ? null : new Date(tmpStartDate);
        this.eventsCompleted = (Integer) in.readValue(Integer.class.getClassLoader());
        this.totalEvents = (Integer) in.readValue(Integer.class.getClassLoader());
        this.weekdays = new ArrayList<Boolean>();
        in.readList(this.weekdays, Boolean.class.getClassLoader());
        this.events = new ArrayList<HabitEvent>();
        in.readList(this.events, HabitEvent.class.getClassLoader());
    }

    public static final Creator<HabitType> CREATOR = new Creator<HabitType>() {
        @Override
        public HabitType createFromParcel(Parcel source) {
            return new HabitType(source);
        }

        @Override
        public HabitType[] newArray(int size) {
            return new HabitType[size];
        }
    };
}
