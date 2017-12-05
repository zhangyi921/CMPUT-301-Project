/*
 * Followers
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
 * Created by Cole on 2017-10-20.
 */

/**
 * Represents a Follower
 *
 * @author NOTcmput301
 * @version 1.0
 * @since 1.0
 */
public class Followers {
    private String requester;
    private String requestedUser;
    private int requestAccepted;

    public Followers(String requester, String requestedUser) {
        this.requester = requester;
        this.requestedUser = requestedUser;
        this.requestAccepted = 0;       //denotes unknown state
    }

    /**
     * gets the user who made the request
     *
     * @return the user who made the request
     */
    public String getRequester() {
        return requester;
    }

    /**
     * gets the user who was requested to follow
     *
     * @return the user who was requested to follow
     */
    public String getRequestedUser() {
        return requestedUser;
    }


    /**
     * accepts the follow request
     */
    public void acceptRequest() {
        this.requestAccepted = 1; // 1 denotes success
    }

    //Not needed

    /**
     * revokes follower privilege
     *
     */
    public void revokePrivilege() {
        this.requestAccepted = 2;
    } // 2 denotes fail

    /**
     * views follower request status
     *
     * @return integer representing follower request status
     */
    public int viewStatus(){return this.requestAccepted;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (o instanceof Followers){
            Followers test = (Followers) o;
            if (this.requester.equals(test.getRequester()) && this.requestedUser.equals(test.getRequestedUser())
                    && this.requestAccepted==test.viewStatus()) return true;
        }
        return false;
    }

}
