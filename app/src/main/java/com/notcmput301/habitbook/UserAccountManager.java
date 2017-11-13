/*
 * UserAccountManager
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
 * Control class for user account functions- Only partially implemented
 *
 * @author NOTcmput301
 * @version 1.0
 * @see User
 * @since 1.0
 */

public final class UserAccountManager {

    private UserAccountManager(){};

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

}
