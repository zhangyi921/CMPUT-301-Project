package com.notcmput301.habitbook;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.graphics.Bitmap;
import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.Date;

public class HabitEventTest {
    private User u1 = new User("user", "carrot42");
    private HabitType h1 = new HabitType("user", "test", "reason", new Date(), new ArrayList<Boolean>());

    @Test
    public void createEvent(){
        Date date = new Date();
        ArrayList<Boolean> weekday = new ArrayList<>();
        User user = new User("user", "password");
        HabitType habit = new HabitType("user", "habit1", "test", date, weekday);
        HabitEvent event = new HabitEvent("habitJSONstring", "comment");
        assertNotNull(event);
        assertTrue("habitJSONstring".equals(event.getHabit()));
        assertTrue("comment".equals(event.getComment()));
    }

    @Test
    public void testGetLatitude() throws Exception {
        HabitEvent event = new HabitEvent("habitJsonstring", "comment", "EncodedImageString", 123.45, 678.90);
        assertEquals(123.45,event.getLatitude(),0);
    }

    @Test
    public void testSetLatitude() throws Exception {
        HabitEvent event = new HabitEvent("habitJsonstring", "comment", "EncodedImageString", 123.45, 678.90);
        event.setLatitude(543.21);
        assertEquals(543.21,event.getLatitude(),0);
    }

    @Test
    public void testGetLongitude() throws Exception {
        HabitEvent event = new HabitEvent("habitJsonstring", "comment", "EncodedImageString", 123.45, 678.90);
        assertEquals(678.90,event.getLongitude(),0);
    }

    @Test
    public void testSetLongitude() throws Exception {
        HabitEvent event = new HabitEvent("habitJsonstring", "comment", "EncodedImageString", 123.45, 678.90);
        event.setLongitude(543.21);
        assertEquals(543.21,event.getLongitude(),0);
    }

    @Test
    public void testGetHabit() throws Exception {
        HabitEvent event = new HabitEvent("habitJsonstring", "comment", "EncodedImageString", 123.45, 678.90);

        assertEquals("habitJsonstring", event.getHabit());
    }

    @Test
    public void testSetHabit() throws Exception {
        HabitEvent event = new HabitEvent("habitJsonstring", "comment", "EncodedImageString", 123.45, 678.90);
        event.setHabit("newHabitJSONstring");
        assertEquals("newHabitJSONstring", event.getHabit());
    }

    @Test
    public void testGetImage() throws Exception {
        HabitEvent event = new HabitEvent("habitJsonstring", "comment", "EncodedImageString", 123.45, 678.90);
        assertEquals("EncodedImageString", event.getImage());
    }

    @Test
    public void testSetImage() throws Exception {
        HabitEvent event = new HabitEvent("habitJsonstring", "comment", "EncodedImageString", 123.45, 678.90);
        event.setImage("newEncodedImageString");
        assertEquals("newEncodedImageString", event.getImage());
    }

    @Test
    public void testDate() throws Exception {
        HabitEvent event = new HabitEvent("habitJsonstring", "comment", "EncodedImageString", 123.45, 678.90);
        Date date = new Date();
        event.setDate(date);
        assertEquals(date, event.getDate());
    }

    @Test
    public void testGetLikes() throws Exception {
        HabitEvent event = new HabitEvent("habitJsonstring", "comment", "EncodedImageString", 123.45, 678.90);
        assertEquals(0, event.getLikes());
    }

    @Test
    public void testSetLikes() throws Exception {
        HabitEvent event = new HabitEvent("habitJsonstring", "comment", "EncodedImageString", 123.45, 678.90);
        event.setLikes(12);
        assertEquals(12, event.getLikes());
    }

    @Test
    public void testGetDislikes() throws Exception {
        HabitEvent event = new HabitEvent("habitJsonstring", "comment", "EncodedImageString", 123.45, 678.90);
        assertEquals(0, event.getDislikes());
    }

    @Test
    public void testSetDisikes() throws Exception {
        HabitEvent event = new HabitEvent("habitJsonstring", "comment", "EncodedImageString", 123.45, 678.90);
        event.setDislikes(12);
        assertEquals(12, event.getDislikes());
    }
    
}
