/*
 * FollowerRequest
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
 * Class for follower request objects - only partially implemented
 *
 * @author NOTcmput301
 * @version 1.0
 * @since 1.0
 */

public class FollowerRequest {
    private User requester;
    private User requestedUser;
    private int requestAccepted;

    public FollowerRequest(User requester, User requestedUser) {
        this.requester = requester;
        this.requestedUser = requestedUser;
        this.requestAccepted = 0;
    }

    public User getRequester() {
        return requester;
    }

    public User getRequestedUser() {
        return requestedUser;
    }

    public void acceptRequest() {
        this.requestAccepted = 1; // 1 denotes success
    }

    public void revokePrivilege() {
        this.requestAccepted = 2;
    } // 2 denotes fail

    public int viewStatus(){return this.requestAccepted;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (o instanceof FollowerRequest){
            FollowerRequest test = (FollowerRequest) o;
            if (this.requester.equals(test.getRequester()) && this.requestedUser.equals(test.getRequestedUser())
                    && this.requestAccepted==test.viewStatus()) return true;
        }
        return false;
    }

}
