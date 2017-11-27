package com.notcmput301.habitbook;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

import io.searchbox.annotations.JestId;

/**
 * Created by Cole on 2017-10-19.
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

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.creationDate = new Date();
        this.followers = new ArrayList<User>();
        this.followedUsers = new ArrayList<User>();
        //this.habitTypes = new ArrayList<HabitType>();
    }

    public String getId(){return this.id;}

    public void setId(String id){this.id=id;}

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

/*    public ArrayList<HabitType> getHabitTypes() {
        return habitTypes;
    }

    public void setHabitTypes(ArrayList<HabitType> habitTypes) {
        this.habitTypes = habitTypes;
    }

    public void addHabitType(HabitType habit){
        this.habitTypes.add(habit);
    }

    public void removeHabitType(HabitType habit){
        this.habitTypes.remove(habit);
    }

    public void updateHabitType(HabitType OldHabit, HabitType NewHabit){

        int index = this.habitTypes.indexOf(OldHabit);
        this.habitTypes.set(index, NewHabit);
    }*/

    public void addFollower(User follower){
        this.followers.add(follower);
    }

    public void removeFollower(User follower){
        this.followers.remove(follower);
    }

    public void addFollowedUser(User followedUser){
        this.followedUsers.add(followedUser);
    }

    public void removeFollowedUser(User followedUser) {
        this.followedUsers.remove(followedUser);
    }

    public boolean isActiveUser(){                                                                  //?
        return false;
    }

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


