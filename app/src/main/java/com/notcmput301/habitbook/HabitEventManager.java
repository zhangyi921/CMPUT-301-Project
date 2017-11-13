/*
 * HabitEventManager
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

import java.util.Date;

/**
 * Created by Cole on 2017-10-21.
 */

/**
 * Control class for functions related to habit events - Not yet implemented
 *
 * @author NOTcmput301
 * @version 1.0
 * @see HabitEvent
 * @since 1.0
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
