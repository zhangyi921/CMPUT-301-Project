package com.notcmput301.habitbook;


import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertTrue;

public class HabitEventTest {
    @Test
    public void createEvent(){
        Date date = new Date();
        ArrayList<Integer> weekday = new ArrayList<>();
        weekday.add(1);
        User user = new User("user", "password");
        HabitType habit = new HabitType(user.getUsername(), "habit1", "test", date, weekday);
        HabitEvent event = new HabitEvent(habit, "comment");
        assertTrue("comment" == event.getComment());
        assertTrue(habit == event.getHabitType());
    }
}
