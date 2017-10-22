package com.notcmput301.habitbook;

import java.util.ArrayList;

/**
 * Created by Cole on 2017-10-20.
 */

public final class UserAccountManager {

    private UserAccountManager(){}

    static User createAccount(String username, String password) {
        return new User(username, password);
    }

    static Boolean userExists(String username) {
        return false;
    }

    static User findUser(String username){
        return new User(username, "swordfish");
    }

    static void loginUser(String username, String password){
        return;
    }

    static void logoutUser(User user){
        return;
    }

    static User getLoggedInUser() {
        return null;
    }

    static ArrayList<HabitType> getOwnedHabits() {
        return new ArrayList<HabitType>();
    }

    static ArrayList<User> getFollowers() {
        return new ArrayList<User>();
    }

    static ArrayList<User> getFollowedUsers() {
        return new ArrayList<User>();
    }
    

}
