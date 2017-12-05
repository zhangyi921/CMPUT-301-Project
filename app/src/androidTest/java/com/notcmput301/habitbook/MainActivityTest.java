/*
 *  * Copyright (c) 2017 Team NOTcmput301, CMPUT301, University of Alberta - All Rights Reserved
 *  * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 *  * You can find a copy of the license in the project wiki on github. Otherwise please contact miller4@ualberta.ca.
 */

package com.notcmput301.habitbook;

import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;

import com.google.gson.Gson;
import com.robotium.solo.Solo;

import java.util.ArrayList;

/**
 * Created by shang on 10/21/2017.
 * Logout is tested in Login Activity test
 * partially done Dec 2
 */

public class MainActivityTest extends ActivityInstrumentationTestCase2 {


    private Solo solo;


    public MainActivityTest(){
        //because we have to start from here
        super(MainActivity.class);


    }


    public void setUp() throws Exception{
        //setting up intent so we don't have to go from login page every time
        Gson gson = new Gson();

        ElasticSearch.verifyLogin vl = new ElasticSearch.verifyLogin();
        vl.execute("test", "testpassword");
        HabitTypeSingleton HLS = HabitTypeSingleton.getInstance();

        User testUser = vl.get();
        Intent intent = new Intent();
        intent.putExtra("passedHList", gson.toJson(HLS));
        intent.putExtra("passedUser", gson.toJson(testUser));
        setActivityIntent(intent);


        solo = new Solo(getInstrumentation() , getActivity());
        solo.clickOnImageButton(0);//nav memu
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }


    public void test_habitType() throws Exception {
        //habit type button
        Class next_page = HabitTypeList2.class;
        for (int i=0;i<1;i++){
            solo.sendKey(KeyEvent.KEYCODE_DPAD_DOWN);
            solo.sleep(100);//wait so that it go through
        }
        solo.sendKey(KeyEvent.KEYCODE_DPAD_CENTER);


;
        assertTrue(solo.waitForActivity(next_page));
        solo.assertCurrentActivity("Not HabitType Activity",next_page);
    }


    public void test_todayHabit() throws Exception {
        //habit type button
        Class next_page = MainActivity.class;
        for (int i=0;i<2;i++){
            solo.sendKey(KeyEvent.KEYCODE_DPAD_DOWN);
            solo.sleep(100);//wait so that it go through
        }
        solo.sendKey(KeyEvent.KEYCODE_DPAD_CENTER);

        assertTrue(solo.waitForActivity(next_page));
        solo.assertCurrentActivity("Not todaysHabit Activity",next_page);
    }


    public void test_habitEventHistory() throws Exception {
        //habit type button
        Class next_page = HabitEventHistory2.class;
        for (int i=0;i<3;i++){
            solo.sendKey(KeyEvent.KEYCODE_DPAD_DOWN);
            solo.sleep(100);//wait so that it go through
        }
        solo.sendKey(KeyEvent.KEYCODE_DPAD_CENTER);

        assertTrue(solo.waitForActivity(next_page));
        solo.assertCurrentActivity("Not HabitEventHistory Activity",next_page);
    }


    public void test_Online() throws Exception {
        //habit type button
        Class next_page = Online.class;
        for (int i=0;i<4;i++){
            solo.sendKey(KeyEvent.KEYCODE_DPAD_DOWN);
            solo.sleep(100);//wait so that it go through
        }
        solo.sendKey(KeyEvent.KEYCODE_DPAD_CENTER);

        assertTrue(solo.waitForActivity(next_page));
        solo.assertCurrentActivity("Not Online Activity",next_page);
    }

    public void test_tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}