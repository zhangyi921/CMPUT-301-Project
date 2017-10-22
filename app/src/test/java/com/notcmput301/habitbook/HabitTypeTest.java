package com.notcmput301.habitbook;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by shang on 10/21/2017.
 */
public class HabitTypeTest {

    private User u1 = new User("test1", "carrot42"); //Test object

    @Test
    public void getOwner() throws Exception {
        HabitType h1 = new HabitType(u1, "eating less", "coming close to morbid obesity", new Date(), new ArrayList<String>());
        assertEquals("Failed to get owner", u1, h1.getOwner());
    }

    @Test
    public void setOwner() throws Exception {
        User u2 = new User("test2", "carrot42"); //Test object
        HabitType h1 = new HabitType(u1, "eating less", "coming close to morbid obesity", new Date(), new ArrayList<String>());
        h1.setOwner(u2);
        assertEquals("Failed to setOwner", u2, h1.getOwner());                                      //depends on success of getOwner
    }

    @Test
    public void getTitle() throws Exception {
        HabitType h1 = new HabitType(u1, "eating less", "coming close to morbid obesity", new Date(), new ArrayList<String>());
        assertEquals("Failed to get title", "eating less", h1.getTitle());
    }

    @Test
    public void setTitle() throws Exception {
        HabitType h1 = new HabitType(u1, "eating less", "coming close to morbid obesity", new Date(), new ArrayList<String>());
        String expectedT = "YEYEYYEE";
        h1.setTitle(expectedT);
        assertEquals("Failed to set Title", expectedT, h1.getTitle());                              //depends on the success of getTitle.
    }

    @Test
    public void getReason() throws Exception {
        HabitType h1 = new HabitType(u1, "eating less", "coming close to morbid obesity", new Date(), new ArrayList<String>());
        assertEquals("Failed to get Reason", "coming close to morbid obesity", h1.getReason());
    }

    @Test
    public void setReason() throws Exception {
        HabitType h1 = new HabitType(u1, "eating less", "coming close to morbid obesity", new Date(), new ArrayList<String>());
        String expectedR = "new reason";
        h1.setReason(expectedR);
        assertEquals("Failed to set Title", expectedR, h1.getReason());                             //depends on success of getReason
    }

    @Test
    public void getStartDate() throws Exception {
        Date d = new Date();
        HabitType h1 = new HabitType(u1, "eating less", "coming close to morbid obesity", d, new ArrayList<String>());
        assertEquals("Failed to get Date", d, h1.getStartDate());
    }

    @Test
    public void setStartDate() throws Exception {
        HabitType h1 = new HabitType(u1, "eating less", "coming close to morbid obesity", new Date(), new ArrayList<String>());
        Date expected = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(expected);
        cal.add(Calendar.YEAR, -5);
        expected = cal.getTime();
        h1.setStartDate(expected);
        assertEquals("Failed to set new date", expected, h1.getStartDate());                        //depends on the success of h1.getStartDate()
    }

    @Test
    public void getEventsCompleted() throws Exception {
        HabitType h1 = new HabitType(u1, "eating less", "coming close to morbid obesity", new Date(), new ArrayList<String>());
//        HabitEvent he1 = new HabitEvent(h1, "I bought a whole pizza today. I only ate 7 slices");
//        HabitEvent he2 = new HabitEvent(h1, "Didnt really compelte my habit but im still going to add to my habit event");
        assertEquals("failed to get", (Integer) 0, h1.getEventsCompleted());                           //depends on success of h1.setEventCompleted()
    }

    @Test
    public void setEventsCompleted() throws Exception {
        HabitType h1 = new HabitType(u1, "eating less", "coming close to morbid obesity", new Date(), new ArrayList<String>());
        Integer expected = 1000;
        h1.setEventsCompleted(expected);
        assertEquals("failed to set events completed", expected, h1.getEventsCompleted());           //depends on success of h1.getEventCompleted()
    }

    @Test
    public void getTotalEvents() throws Exception {
        HabitType h1 = new HabitType(u1, "eating less", "coming close to morbid obesity", new Date(), new ArrayList<String>());
        assertEquals("Failed to get total event", (Integer) 0, h1.getEventsCompleted());
    }

    @Test
    public void setTotalEvents() throws Exception {
        HabitType h1 = new HabitType(u1, "eating less", "coming close to morbid obesity", new Date(), new ArrayList<String>());
        Integer expected = 1000;
        h1.setTotalEvents(expected);
        assertEquals("failed to set total events", expected, h1.getTotalEvents());           //depends on success of h1.getEventCompleted()

    }

    @Test
    public void getWeekdays() throws Exception {
        HabitType h1 = new HabitType(u1, "eating less", "coming close to morbid obesity", new Date(), new ArrayList<String>());
        assertEquals("Failed to getWeekdays", new ArrayList<String>(), h1.getWeekdays());
    }

    @Test
    public void setWeekdays() throws Exception {
        HabitType h1 = new HabitType(u1, "eating less", "coming close to morbid obesity", new Date(), new ArrayList<String>());
        ArrayList<String> wkdy = new ArrayList<String>();
        wkdy.add("M"); wkdy.add("T"); wkdy.add("TR");
        h1.setWeekdays(wkdy);
        assertEquals("Failed to set weekday", wkdy, h1.getWeekdays());                              //depends on the suuccess of getWeekdays()
    }

    @Test
    public void getEvents() throws Exception {
        HabitType h1 = new HabitType(u1, "eating less", "coming close to morbid obesity", new Date(), new ArrayList<String>());
        assertEquals("Failed to get evets", new ArrayList<HabitEvent>(), h1.getEvents());
    }

    @Test
    public void setEvents() throws Exception {
        HabitType h1 = new HabitType(u1, "eating less", "coming close to morbid obesity", new Date(), new ArrayList<String>());
        HabitEvent he1 = new HabitEvent(h1, "I bought a whole pizza today. I only ate 7 slices");
        HabitEvent he2 = new HabitEvent(h1, "Didnt really compelte my habit but im still going to add to my habit event");
        ArrayList<HabitEvent> hEvent = new ArrayList<HabitEvent>();
        hEvent.add(he1); hEvent.add(he2);
        h1.setEvents(hEvent);
        assertEquals("Failed to set Events", hEvent, h1.getEvents());                               //depends on the success of h1.getEvents()
    }

    @Test
    public void addHabitEvent() throws Exception {
        HabitType h1 = new HabitType(u1, "eating less", "coming close to morbid obesity", new Date(), new ArrayList<String>());
        HabitEvent he1 = new HabitEvent(h1, "I bought a whole pizza today. I only ate 7 slices");
        h1.addHabitEvent(he1);
        assertEquals("Failed to add Habit Event", true, h1.getEvents().contains(he1));              //depends on the success of he1
    }

    @Test
    public void removeHabitEvent() throws Exception {
        HabitType h1 = new HabitType(u1, "eating less", "coming close to morbid obesity", new Date(), new ArrayList<String>());
        HabitEvent he1 = new HabitEvent(h1, "I bought a whole pizza today. I only ate 7 slices");
        HabitEvent he2 = new HabitEvent(h1, "Didnt really compelte my habit but im still going to add to my habit event");
        ArrayList<HabitEvent> hEvent = new ArrayList<HabitEvent>();
        hEvent.add(he1); hEvent.add(he2);
        h1.setEvents(hEvent);                                                                       //depends on success of setEvents
        h1.removeHabitEvent(he1);
        assertEquals("Failed to removeHabitEvent", false, h1.getEvents().contains(he1));
    }

    @Test
    public void calculateCompletion() throws Exception {
        //TODO: write definition for calculateCompletion
    }

    @Test
    public void equals() throws Exception {
        HabitType h1 = new HabitType(u1, "eating less", "coming close to morbid obesity", new Date(), new ArrayList<String>());
        HabitType h2 = new HabitType(u1, "eating less", "coming close to morbid obesity", new Date(), new ArrayList<String>());
        assertEquals("Comparison Failed on equals", true, h1.equals(h2));
    }

}