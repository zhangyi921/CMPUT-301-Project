package com.notcmput301.habitbook;

/**
 * Created by Cole on 2017-10-20.
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

    public String getRequester() {
        return requester;
    }

    public String getRequestedUser() {
        return requestedUser;
    }

    public void acceptRequest() {
        this.requestAccepted = 1; // 1 denotes success
    }

    //Not needed
    public void revokePrivilege() {
        this.requestAccepted = 2;
    } // 2 denotes fail

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
