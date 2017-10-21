package com.notcmput301.habitbook;

import java.util.Date;

/**
 * Created by Cole on 2017-10-21.
 */

public final class HabitTypeManager {

    private HabitTypeManager() {
    }

    static public boolean habitTypeExists(User owner, String title) {
        return false;
    }

    static public boolean isHabitToday(HabitType habit) {
        return false;
    }

    static public HabitType findHabit(String title){
        return null;
    }

    static public boolean habitCompleted(HabitType habit, Date date) {
        return false;
    }


    static public  void deleteHabit(User owner, HabitType habit){
        return;
    }

    static public int calculateTotalScheduledEvents(HabitType habit){
        return 0;
    }
}

