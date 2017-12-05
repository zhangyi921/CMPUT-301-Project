/*
 *  * Copyright (c) 2017 Team NOTcmput301, CMPUT301, University of Alberta - All Rights Reserved
 *  * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 *  * You can find a copy of the license in the project wiki on github. Otherwise please contact miller4@ualberta.ca.
 */

package com.notcmput301.habitbook;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;


/**
 * Created by shang on 10/21/2017.
 * done Dec 2
 */

public class CreateAccountActivityTest extends ActivityInstrumentationTestCase2{


    private Solo solo;


    public CreateAccountActivityTest(){
        super(CreateAccountActivity.class);
    }


    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation() , getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }



    public void test_missing() throws Exception {
        //When there are missing fields expect return msg "You have missing fields"
        solo.assertCurrentActivity("Wrong Activity", CreateAccountActivity.class);
        solo.clickOnButton("create");
        assertTrue(solo.waitForText("You have missing fields"));

        solo.enterText((EditText) solo.getView(R.id.createAccount_Username),"CreateActTest1");
        solo.clickOnButton("create");
        assertTrue(solo.waitForText("You have missing fields"));

        solo.enterText((EditText) solo.getView(R.id.createAccount_password),"testpwd123");
        solo.clickOnButton("create");
        assertTrue(solo.waitForText("You have missing fields"));

        solo.clearEditText((EditText) solo.getView(R.id.createAccount_Username));
        solo.enterText((EditText) solo.getView(R.id.createAccount_passwordR),"testpwd123");
        solo.clickOnButton("create");
        assertTrue(solo.waitForText("You have missing fields"));
    }

    public void test_passwordProblem() throws Exception{
        //When the password(R) have problem
        solo.assertCurrentActivity("Wrong Activity", CreateAccountActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.createAccount_Username));
        solo.enterText((EditText) solo.getView(R.id.createAccount_Username),"CreateActTest1");
        solo.enterText((EditText) solo.getView(R.id.createAccount_password),"123");
        solo.enterText((EditText) solo.getView(R.id.createAccount_passwordR),"4");
        solo.clickOnButton("create");
        assertTrue(solo.waitForText("passwords do not match"));
    }


    public void test_userExists() throws Exception{
        //When the username is taken
        solo.assertCurrentActivity("Wrong Activity", CreateAccountActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.createAccount_Username));
        solo.enterText((EditText) solo.getView(R.id.createAccount_Username),"test");
        solo.enterText((EditText) solo.getView(R.id.createAccount_password),"testpassword");
        solo.enterText((EditText) solo.getView(R.id.createAccount_passwordR),"testpassword");
        solo.clickOnButton("create");
        assertTrue(solo.waitForText("Username exists!"));
    }

    public void test_back() throws Exception{
        //back to login and then come back to this page again
        solo.assertCurrentActivity("Wrong Activity", CreateAccountActivity.class);
        solo.clickOnButton("Back");
        assertTrue(solo.waitForActivity(LoginActivity.class));
        solo.assertCurrentActivity("Did not go back", LoginActivity.class);

        solo.clickOnButton("sign up");
        solo.waitForActivity(CreateAccountActivity.class);
        solo.assertCurrentActivity("Not Create account Activity", CreateAccountActivity.class);
    }


    public void test_attempCreate() throws Exception{

        //this one should go through
        String Username = "NewUser14";
        solo.assertCurrentActivity("Wrong Activity", CreateAccountActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.createAccount_Username));
        solo.enterText((EditText) solo.getView(R.id.createAccount_Username),Username);
        solo.enterText((EditText) solo.getView(R.id.createAccount_password),"testpwd123");
        solo.enterText((EditText) solo.getView(R.id.createAccount_passwordR),"testpwd123");
        solo.clickOnButton("create");
        assertTrue(solo.waitForText("Welcome!"));
        assertTrue(solo.waitForActivity(MainActivity.class));
        solo.assertCurrentActivity("Did not go to mainmenu", MainActivity.class);
    }
    public void test_tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}