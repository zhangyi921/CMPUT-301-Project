/*
 *  * Copyright (c) 2017 Team NOTcmput301, CMPUT301, University of Alberta - All Rights Reserved
 *  * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 *  * You can find a copy of the license in the project wiki on github. Otherwise please contact miller4@ualberta.ca.
 */

package com.notcmput301.habitbook;

/**
 * Created by shangchen on 2017-12-02.
 */

public class Username {
    private static Username instance;

    // Global variable
    private String name;

    // Restrict the constructor from being instantiated
    private Username(){}

    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return this.name;
    }

    public static synchronized Username getInstance(){
        if(instance==null){
            instance=new Username();
        }
        return instance;
    }
}
