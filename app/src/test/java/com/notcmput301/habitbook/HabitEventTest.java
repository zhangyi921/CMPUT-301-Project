package com.notcmput301.habitbook;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.Date;

public class HabitEventTest {
    @Test
    public void createEvent(){
        Date date = new Date();
        ArrayList<String> weekday = new ArrayList<>();
        weekday.add("Monday");
        User user = new User("user", "password");
        HabitType habit = new HabitType(user, "habit1", "test", date, weekday);
        HabitEvent event = new HabitEvent(habit, "comment");
    }
}
