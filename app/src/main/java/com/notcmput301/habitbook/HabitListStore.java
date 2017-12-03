/*
 *  * Copyright (c) 2017 Team NOTcmput301, CMPUT301, University of Alberta - All Rights Reserved
 *  * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 *  * You can find a copy of the license in the project wiki on github. Otherwise please contact miller4@ualberta.ca.
 */

package com.notcmput301.habitbook;

import java.util.ArrayList;

/**
 * Created by shangchen on 2017-12-02.
 */

public class HabitListStore {

    private ArrayList<HabitType> habitTypes;

    public HabitListStore(ArrayList<HabitType> ht){
        this.habitTypes = ht;
    }

    public void setList(ArrayList<HabitType> ht){
        this.habitTypes = ht;
    }

    public ArrayList<HabitType> getList(){
        return habitTypes;
    }

}
