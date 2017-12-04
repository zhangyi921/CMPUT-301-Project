package com.notcmput301.habitbook;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by shang on 10/21/2017.
 */
public class FollowersTest {
    @Test
    public void getRequester() throws Exception {
        User requster = new User("requester", "carrot42"); //Test object
        User requstedU = new User("requestedUser", "carrot42"); //Test object
        Followers fr = new Followers("requester", "requestedUser");
        assertEquals("Failed to retrieve requester", "requester", fr.getRequester());
    }

    @Test
    public void getRequestedUser() throws Exception {
        User requster = new User("requester", "carrot42"); //Test object
        User requstedU = new User("requestedUser", "carrot42"); //Test object
        Followers fr = new Followers("requester", "requestedUser");
        assertEquals("Failed to retrieve User Requested", "requestedUser", fr.getRequestedUser());
    }
    @Test
    public void viewStatus() throws Exception {
        User requster = new User("requester", "carrot42"); //Test object
        User requstedU = new User("requestedUser", "carrot42"); //Test object
        Followers fr = new Followers("requester", "requestedUser");
        assertEquals("Failed to view status", 0, fr.viewStatus());
    }

    @Test
    public void acceptRequest() throws Exception {
        User requster = new User("requester", "carrot42"); //Test object
        User requstedU = new User("requestedUser", "carrot42"); //Test object
        Followers fr = new Followers("requester", "requestedUser");
        fr.acceptRequest();
        assertEquals("Failed to accept request", 1, fr.viewStatus());
    }

    @Test
    public void revokePrivilege() throws Exception {
        User requster = new User("requester", "carrot42"); //Test object
        User requstedU = new User("requestedUser", "carrot42"); //Test object
        Followers fr = new Followers("requester", "requestedUser");
        fr.revokePrivilege();
        assertEquals("Failed to revoke request", 2, fr.viewStatus());
    }

    @Test
    public void equals() throws Exception {
        User requster = new User("requester", "carrot42"); //Test object
        User requstedU = new User("requestedUser", "carrot42"); //Test object
        Followers fr1 = new Followers("requester", "requestedUser");
        Followers fr2 = new Followers("requester", "requestedUser");
        assertEquals("Failed object comparison equals", true, fr1.equals(fr2));

    }

}