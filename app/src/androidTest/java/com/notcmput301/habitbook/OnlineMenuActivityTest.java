/*
 *  * Copyright (c) 2017 Team NOTcmput301, CMPUT301, University of Alberta - All Rights Reserved
 *  * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 *  * You can find a copy of the license in the project wiki on github. Otherwise please contact miller4@ualberta.ca.
 */

package com.notcmput301.habitbook;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.google.gson.Gson;
import com.robotium.solo.Solo;

/**
 * Created by shang on 10/21/2017.
 */
public class OnlineMenuActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;


    public OnlineMenuActivityTest(){
        //because we have to start from here
        super(Online.class);
    }

    public void setUp() throws Exception{
        //setting up intent so we don't have to go from login page every time
        Gson gson = new Gson();

        ElasticSearch.verifyLogin vl = new ElasticSearch.verifyLogin();
        vl.execute("test", "testpassword");

        User testUser = vl.get();
        Intent intent = new Intent();

        intent.putExtra("passedUser", gson.toJson(testUser));
        setActivityIntent(intent);


        solo = new Solo(getInstrumentation() , getActivity());
    }

    public void test_menu() throws Exception{
        solo.assertCurrentActivity("Wrong Activity",Online.class);
        solo.sleep(500);
        solo.sendKey(Solo.MENU);
        assertTrue(solo.waitForText("Following and sharing"));
        assertTrue(solo.waitForText("Maps"));
    }

    public void test_request() throws Exception{
        solo.assertCurrentActivity("Wrong Activity",Online.class);
        solo.sleep(500);
        solo.clickOnView(solo.getView(R.id.Oln_viewRequestStatus));
        solo.assertCurrentActivity("Not in request page",FollowerRequestsActivity.class);
    }

    public void test_map() throws Exception{
        solo.assertCurrentActivity("Wrong Activity",Online.class);
        solo.clickOnView(solo.getView(R.id.oln_mapButton));
        solo.assertCurrentActivity("Not in Map Page",MapsActivity.class);
    }
    
    public void test_tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

}