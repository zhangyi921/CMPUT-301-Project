
/*
 * Username
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

/**
 * Created by shangchen on 2017-12-02.
 */

/**
 * Singleton for holding the User in offline mode
 *
 * @author NOTcmput301
 * @version 1.0
 * @see User
 * @since 1.0
 */
public class Username {
    private static Username instance;

    // Global variable
    private String name;

    // Restrict the constructor from being instantiated
    private Username(){}

    /**
     * Sets username of user
     *
     * @param name username of user
     */
    public void setName(String name){
        this.name=name;
    }

    /**
     * Gets username of user
     *
     * @return username of user
     */
    public String getName(){
        return this.name;
    }

    /**
     * Gets singleton instance
     *
     * @return instance of singleton
     */
    public static synchronized Username getInstance(){
        if(instance==null){
            instance=new Username();
        }
        return instance;
    }
}
