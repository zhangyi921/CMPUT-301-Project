package com.notcmput301.habitbook;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Cole on 2017-10-19.
 */

public class HabitType {
    private User owner;
    private String title;
    private String reason;
    private Date creationDate;
    private Date startDate;
    private Integer eventsCompleted;
    private Integer totalEvents;
    private ArrayList<String> weekdays;
    private ArrayList<HabitEvent> events;

    public HabitType(User owner, String title, String reason, Date startDate,  ArrayList<String> weekdays ) {
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

    public ArrayList<String> getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(ArrayList<String> weekdays) {
        this.weekdays = weekdays;
    }

    public ArrayList<HabitEvent> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<HabitEvent> events) {
        this.events = events;
    }

    public void addHabitEvent(HabitEvent event) {

    }

    public void removeHabitEvent(HabitEvent event) {

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
}
