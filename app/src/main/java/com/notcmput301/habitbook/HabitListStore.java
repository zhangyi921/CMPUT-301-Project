/*
 * HabitTypeStore
 *
 * Version 1.0
 *
 * December 2, 2017
 *
 * Copyright (c) 2017 Team NOTcmput301, CMPUT301, University of Alberta - All Rights Reserved
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in the project wiki on github. Otherwise please contact miller4@ualberta.ca.
 */
package com.notcmput301.habitbook;

import java.util.ArrayList;

/**
 * Created by shangchen on 2017-12-02.
 */

/**
 * Holder class for storing a list of HabitTypes
 *
 * @author NOTcmput301
 * @version 1.0
 * @since 1.0
 */
public class HabitListStore {

    private ArrayList<HabitType> habitTypes;

    /**
     * Constructs a HabitListStore object
     *
     * @param ht list of HabitTypes
     */
    public HabitListStore(ArrayList<HabitType> ht){
        this.habitTypes = ht;
    }

    /**
     * Sets the list of HabitTypes
     *
     * @param ht list of HabitTypes
     */
    public void setList(ArrayList<HabitType> ht){
        this.habitTypes = ht;
    }

    /**
     * Gets the list of HabitTypes
     *
     * @return list of HabitTypes
     */
    public ArrayList<HabitType> getList(){
        return habitTypes;
    }

}
