package com.notcmput301.habitbook;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Cole on 2017-10-19.
 */

public class User {
    private String username;
    private String password;
    private Date creationDate;
    private ArrayList<User> followers;
    private ArrayList<User> followedUsers;
    private ArrayList<HabitType> habitTypes;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.creationDate = new Date();
        this.followers = new ArrayList<User>();
        this.followedUsers = new ArrayList<User>();
        this.habitTypes = new ArrayList<HabitType>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public ArrayList<User> getFollowers() {
        return followers;
    }

    public void setFollowers(ArrayList<User> followers) {
        this.followers = followers;
    }

    public ArrayList<User> getFollowedUsers() {
        return followedUsers;
    }

    public void setFollowedUsers(ArrayList<User> followedUsers) {
        this.followedUsers = followedUsers;
    }

    public ArrayList<HabitType> getHabitTypes() {
        return habitTypes;
    }

    public void setHabitTypes(ArrayList<HabitType> habitTypes) {
        this.habitTypes = habitTypes;
    }

    public void addHabitType(HabitType habit){

    }

    public void removeHabitType(HabitType habit){

    }

    public void addFollower(User follower){

    }

    public void removeFollower(User follower){

    }

    public void addFollowedUser(User followedUser){

    }

    public void removeFollowedUser(User followedUser) {

    }

    public boolean isActiveUser(){
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return getUsername().equals(user.getUsername());

    }

    @Override
    public String toString(){
        return this.username;
    }
}


