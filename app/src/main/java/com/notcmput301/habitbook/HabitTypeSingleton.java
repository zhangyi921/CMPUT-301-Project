/*
 *  * Copyright (c) 2017 Team NOTcmput301, CMPUT301, University of Alberta - All Rights Reserved
 *  * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 *  * You can find a copy of the license in the project wiki on github. Otherwise please contact miller4@ualberta.ca.
 */

package com.notcmput301.habitbook;

import java.util.ArrayList;

/**
 * Created by shang on 12/4/2017.
 */

public class HabitTypeSingleton {

    private static HabitTypeSingleton instance;

    // Global variable
    private ArrayList<HabitType> habitTypes;

    // Restrict the constructor from being instantiated
    private HabitTypeSingleton(){}

    public void setHabitTypes(ArrayList<HabitType> hT){
        this.habitTypes=hT;
    }
    public ArrayList<HabitType> getHabitTypes(){
        return this.habitTypes;
    }

    public static synchronized HabitTypeSingleton getInstance(){
        if(instance==null){
            instance=new HabitTypeSingleton();
        }
        return instance;
    }
}
