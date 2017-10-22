package com.notcmput301.habitbook;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.Date;

public class HabitEventTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
/*    public HabitEventTest(){
        super(com.notcmput301.habitbook.HabitEvent.class);
    }*/
    public void createEvent1(){
        Date date = new Date();
        ArrayList<String> weekday = new ArrayList<>();
        weekday.add("Monday");
        User user = new User("user", "password");
        HabitType habit = new HabitType(user, "habit1", "test", date, weekday);
        HabitEvent event = new HabitEvent(habit, "comment");
        assertTrue("comment" == event.getComment());
        //assertTrue(habit == event.getHabitType());
        assertTrue(date == event.getDate());

    }
    @Test
    public void createEvent3(){
        Date date = new Date();
        ArrayList<String> weekday = new ArrayList<>();
        weekday.add("Monday");
        User user = new User("user", "password");
        HabitType habit = new HabitType(user, "habit1", "test", date, weekday);
        HabitEvent event = new HabitEvent(habit, "comment", "image", null);
        assertTrue("comment" == event.getComment());
        assertTrue(habit == event.getHabitType());
        assertTrue(date == event.getDate());
    }
    @Test
    public void createEvent4(){
        Date date = new Date();
        ArrayList<String> weekday = new ArrayList<>();
        weekday.add("Monday");
        User user = new User("user", "password");
        HabitType habit = new HabitType(user, "habit1", "test", date, weekday);
        HabitEvent event = new HabitEvent(habit, "comment", null, "location");
        assertTrue("comment" == event.getComment());
        assertTrue(habit == event.getHabitType());
        assertTrue(date == event.getDate());
    }
    @Test
    public void imageTest(){
        Date date = new Date();
        ArrayList<String> weekday = new ArrayList<>();
        weekday.add("Monday");
        User user = new User("user", "password");
        HabitType habit = new HabitType(user, "habit1", "test", date, weekday);
        HabitEvent event = new HabitEvent(habit, "comment", "image",null);
        assertTrue(event.getImage() == null);


    }
}
