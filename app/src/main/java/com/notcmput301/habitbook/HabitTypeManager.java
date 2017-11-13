package com.notcmput301.habitbook;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Cole on 2017-10-21.
 */

public final class HabitTypeManager {

    private HabitTypeManager() {
    }

    static public boolean habitTypeExists(User owner, String title) {
        // Get habit types of corresponding user
        ArrayList<HabitType> habitTypes = owner.getHabitTypes();
        // Iterate through all habitTypes of that User
        for (HabitType h : habitTypes) {
            String hTitle = h.getTitle();
            
            // Returns true if title of habitType exists for that User
            if (hTitle.equals(title)) {
                return true;
            }
        }
        // Returns false if title of habitType is unique for that user
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

