package com.notcmput301.habitbook;

/**
 * Created by Cole on 2017-10-20.
 */

public class FollowerRequest {
    private User requester;
    private User requestedUser;
    private boolean requestAccepted;

    public FollowerRequest(User requester, User requestedUser) {
        this.requester = requester;
        this.requestedUser = requestedUser;
        this.requestAccepted = false;
    }

    public User getRequester() {
        return requester;
    }

    public User getRequestedUser() {
        return requestedUser;
    }

    public void acceptRequest() {
        this.requestAccepted = true;
    }

    public void revokePrivilege() {
        this.requestAccepted = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FollowerRequest that = (FollowerRequest) o;

        if (!getRequester().equals(that.getRequester())) return false;
        return getRequestedUser().equals(that.getRequestedUser());

    }

}
