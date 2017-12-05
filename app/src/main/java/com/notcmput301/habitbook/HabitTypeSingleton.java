/*
 * HabitTypeSingleton
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

/**
 * Created by shang on 12/4/2017.
 */

/**
 * Holds HabitTypes during offline mode
 *
 * @author NOTcmput301
 * @version 1.0
 * @see HabitType
 * @since 1.0
 */
public class HabitTypeSingleton {

    private static HabitTypeSingleton instance;

    // Global variable
    private ArrayList<HabitType> habitTypes;

    // Restrict the constructor from being instantiated
    private HabitTypeSingleton(){}

    /**
     * Sets the list of Habit Types for user
     *
     * @param hT list of habit types
     */
    public void setHabitTypes(ArrayList<HabitType> hT){
        this.habitTypes=hT;
    }

    /**
     * Gets the list of Habit Types for user
     *
     * @return list of habit types
     */
    public ArrayList<HabitType> getHabitTypes(){
        return this.habitTypes;
    }

    /**
     * Gets instance of singleton
     *
     * @return singleton instance
     */
    public static synchronized HabitTypeSingleton getInstance(){
        if(instance==null){
            instance=new HabitTypeSingleton();
        }
        return instance;
    }
}
