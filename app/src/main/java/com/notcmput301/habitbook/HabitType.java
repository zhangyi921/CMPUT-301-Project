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

import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

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
public class HabitType {
    private String ownerName;
    private String title;
    private String reason;
    private Date creationDate;
    private Date startDate;
    private Integer eventsCompleted;
    private Integer totalEvents;
    private ArrayList<Integer> weekdays;
    private ArrayList<HabitEvent> events;

    /**
     * Constructs habit Type object
     *
     * @param ownerName string for user name
     * @param title string for title of habit
     * @param reason string for reason for starting habit
     * @param startDate date for the start of habit
     * @param weekdays array for weekdays habit occurs on
     */
    public HabitType(String ownerName, String title, String reason, Date startDate, ArrayList<Integer> weekdays ) {
        this.ownerName = ownerName;
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
     * Gets owner of habit
     *
     * @return habit type owner
     *
     */
    public String getOwner() {
        return ownerName;
    }

    /**
     * Sets new owner of habit
     *
     * @param owner string for new owner
     */
    public void setOwner(String owner) {
        this.ownerName = owner;
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
        return totalEvents;
    }

    /**
     * Sets total number of possible events
     *
     * @param totalEvents integer for new total number of possible events
     */
    public void setTotalEvents(Integer totalEvents) {
        this.totalEvents = totalEvents;
    }

    /**
     * Gets weekdays habit occurs on
     *
     * @return weekdays habit occurs on
     */
    public ArrayList<Integer> getWeekdays() {
        return weekdays;
    }

    /**
     * Sets the weekdays that the habit occurs on
     *
     * @param weekdays array used to set weekdays
     */
    public void setWeekdays(ArrayList<Integer> weekdays) {
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
    }

    /**
     * Removes an event from the list of habits
     *
     * @param event habit event to be inserted
     */
    public void removeHabitEvent(HabitEvent event) {
        this.events.remove(event);
    }

    /**
     * Calculates completion percentage of habit events
     *
     * @return percentage of habit event completion
     */
    public double calculateCompletion() {
        return 0.0;
    }

    /**
     * Compares two habit types for equivalence
     *
     * @param o object to be compared
     * @return boolean representing result of equivalence
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HabitType habitType = (HabitType) o;

        if (!getOwner().equals(habitType.getOwner())) return false;
        return getTitle().equals(habitType.getTitle());
    }

    /**
     * Returns string representation of habit object
     *
     * @return string representation of habit object
     */
    @Override
    public String toString(){
        return this.title;
    }
}
