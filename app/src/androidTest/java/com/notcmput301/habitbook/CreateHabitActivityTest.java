/*
 *  * Copyright (c) 2017 Team NOTcmput301, CMPUT301, University of Alberta - All Rights Reserved
 *  * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 *  * You can find a copy of the license in the project wiki on github. Otherwise please contact miller4@ualberta.ca.
 */

package com.notcmput301.habitbook;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.google.gson.Gson;
import com.robotium.solo.Solo;

import java.util.ArrayList;

/**
 * Created by shang on 10/21/2017.
 * The successful Creating Habit is tested in HabitTypeListActivityTest
 * The tests here only faces with the fauty entries.
 * done Dec 2
 */

public class CreateHabitActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;


    public CreateHabitActivityTest() {
        //because we have to start from here
        super(HabitTypeList2.class);
    }


    public void setUp() throws Exception {
        //intent injection
        Gson gson = new Gson();

        ElasticSearch.verifyLogin vl = new ElasticSearch.verifyLogin();
        vl.execute("test", "testpassword");

        User testUser = vl.get();
        Intent intent = new Intent();

        HabitTypeSingleton HLS = HabitTypeSingleton.getInstance();
        intent.putExtra("passedHList", gson.toJson(HLS));
        intent.putExtra("passedUser", gson.toJson(testUser));
        setActivityIntent(intent);


        solo = new Solo(getInstrumentation() , getActivity());
        solo.clickOnView(solo.getView(R.id.fab));
    }

    public void test_missing() throws Exception {
        //When there are missing fields expect return msg
        solo.assertCurrentActivity("Wrong Activity", CreateHabitActivity.class);
        solo.clickOnButton("Create");
        assertTrue(solo.waitForText("Some fields are not filled out!"));

        solo.enterText((EditText) solo.getView(R.id.HTD_TitleE), "CreateHabitTest");
        solo.clickOnButton("Create");
        assertTrue(solo.waitForText("Some fields are not filled out!"));

        solo.enterText((EditText) solo.getView(R.id.HTD_ReasonE), "testreason123");
        solo.clickOnButton("Create");
        assertTrue(solo.waitForText("At least one day has to be checked"));
    }



    public void test_wrongInput() throws Exception {
        //When input formats are wrong
        solo.assertCurrentActivity("Wrong Activity", CreateHabitActivity.class);
        solo.enterText((EditText) solo.getView(R.id.HTD_TitleE),"CreateHabitTestasdawdawdw1231312312");
        solo.enterText((EditText) solo.getView(R.id.HTD_ReasonE),"test");
        solo.clickOnCheckBox(6);
        solo.clickOnButton("Create");
        assertTrue(solo.waitForText("Habit Title can't be longer than 20 characters"));

        solo.clearEditText((EditText) solo.getView(R.id.HTD_TitleE));
        solo.enterText((EditText) solo.getView(R.id.HTD_TitleE),"CreateHabitTest");
        solo.enterText((EditText) solo.getView(R.id.HTD_ReasonE),"testreason123awdawdawadsadzczxcawdawda");
        solo.clickOnButton("Create");
        assertTrue(solo.waitForText("Habit Reason can't be longer than 30 characters"));
    }




    public void test_tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

}