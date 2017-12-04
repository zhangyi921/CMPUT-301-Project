/*
 * User
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

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

import io.searchbox.annotations.JestId;

/**
 * Created by Cole on 2017-10-19.
 */

/**
 * Class for representing users
 *
 * @author NOTcmput301
 * @version 1.0
 * @since 1.0
 */
public class User {
    private String username;
    private String password;
    private Date creationDate;
    private ArrayList<User> followers;
    private ArrayList<User> followedUsers;
    //private ArrayList<HabitType> habitTypes;
    @JestId
    private String id;

    /**
     * Constructs a user object
     *
     * @param username String representing username
     * @param password String representing password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.creationDate = new Date();
        this.followers = new ArrayList<User>();
        this.followedUsers = new ArrayList<User>();
        //this.habitTypes = new ArrayList<HabitType>();
    }

    /**
     * Gets user ID
     *
     * @return string of user ID
     */
    public String getId(){return this.id;}

    /**
     * Sets new ID for user
     *
     * @param id new ID for user
     */
    public void setId(String id){this.id=id;}

    /**
     * Gets the username of user
     *
     * @return username of user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets new username for user
     *
     * @param username new username for user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password of user
     *
     * @return the password of user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets new password for user
     *
     * @param password new password for user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets creation date of user
     *
     * @return creation date of user
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Sets new creation date for user
     *
     * @param creationDate new creation date for user
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Gets list of user following the current user
     *
     * @return list of users following the current user
     */
    public ArrayList<User> getFollowers() {
        return followers;
    }

    /**
     * Sets new list of followers for user
     *
     * @param followers new list of followers for user
     */
    public void setFollowers(ArrayList<User> followers) {
        this.followers = followers;
    }

    /**
     * Gets list of users the current user is following
     *
     * @return list of users the current user is following
     */
    public ArrayList<User> getFollowedUsers() {
        return followedUsers;
    }

    /**
     * Sets new list of followed users
     *
     * @param followedUsers list of followed users
     */
    public void setFollowedUsers(ArrayList<User> followedUsers) {
        this.followedUsers = followedUsers;
    }

    /**
     * Adds another user as a follower
     *
     * @param follower user to be added as a follower
     */
    public void addFollower(User follower){
        this.followers.add(follower);
    }

    /**
     * Removes another user as a follower
     *
     * @param follower user to be removes as a follower
     */
    public void removeFollower(User follower){
        this.followers.remove(follower);
    }

    /**
     * Adds another user to be followed by the current user
     *
     * @param followedUser user to be followed
     */
    public void addFollowedUser(User followedUser){
        this.followedUsers.add(followedUser);
    }

    /**
     * Remove a user from being followed by the current user
     *
     * @param followedUser User to be un-followed
     */
    public void removeFollowedUser(User followedUser) {
        this.followedUsers.remove(followedUser);
    }

    /**
     * Compares two users for equality
     *
     * @param o object to be compared
     * @return boolean representing equality
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (o instanceof User){
            User test = (User) o;
            if (this.username.equals(test.getUsername()) && this.password.equals(test.getPassword())
                    && this.creationDate.equals(test.getCreationDate()) && this.followers.equals(test.getFollowers())
                    && this.followedUsers.equals(test.getFollowedUsers())) return true;
        }
        return false;
    }

    /**
     * Parcelables - Auto genereated
     */

    @Override
    public String toString(){
        return this.username;
    }
}


