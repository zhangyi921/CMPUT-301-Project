package com.notcmput301.habitbook;

import java.util.Date;

/**
 * Created by Cole on 2017-10-21.
 */

public final class HabitEventManager {

    private HabitEventManager() {}

    static public boolean habitEventExists(HabitType habitType , Date date) {
        return false;
    }

    static public HabitEvent findEvent(HabitType habit, Date date){
        return null;
    }

    static public boolean hasImage (HabitEvent event) {
        return false;
    }

    static public boolean hasLocation (HabitEvent event) {
        return false;
    }
}
