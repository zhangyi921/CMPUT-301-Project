/*
 *  * Copyright (c) 2017 Team NOTcmput301, CMPUT301, University of Alberta - All Rights Reserved
 *  * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 *  * You can find a copy of the license in the project wiki on github. Otherwise please contact miller4@ualberta.ca.
 */

package com.notcmput301.habitbook;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.EditText;

import com.google.gson.Gson;
import com.robotium.solo.Solo;

/**
 * Created by shang on 10/21/2017.
 *
 * contains test in Habit List Activity
 * and go through
 * -create Habit Type
 * -change Habit Type
 * -add Event
 * -go to map
 * -delete Type
 * because this is one sequence
 * doesn't make sense to do it multiple times
 * through different Activity tests
 *
 * done Dec 2
 */

public class HabitTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;


    public HabitTest(){
        //because we have to start from here
        super(HabitTypeList2.class);
    }


    public void setUp() throws Exception{
        //intent injection
        Gson gson = new Gson();

        ElasticSearch.verifyLogin vl = new ElasticSearch.verifyLogin();
        vl.execute("test", "testpassword");

        User testUser = vl.get();
        Intent intent = new Intent();

        intent.putExtra("passedUser", gson.toJson(testUser));
        setActivityIntent(intent);


        solo = new Solo(getInstrumentation() , getActivity());
    }

    public void test_newHabit() throws Exception{
        // new habit button
        solo.assertCurrentActivity("Wrong Activity",HabitTypeList2.class);
        solo.clickOnView(solo.getView(R.id.fab));
        assertTrue(solo.waitForActivity(CreateHabitActivity.class));
        solo.assertCurrentActivity("Did not go to Create Habit",CreateHabitActivity.class);

        solo.goBack();
        assertTrue(solo.waitForActivity(HabitTypeList2.class));
        solo.assertCurrentActivity("Did not go back",HabitTypeList2.class);
    }

    public void test_refresh() throws Exception{
        //refresh button
        solo.assertCurrentActivity("Wrong Activity",HabitTypeList2.class);
        solo.clickOnView(solo.getView(R.id.refresh));
        solo.assertCurrentActivity("Did not go to Create Habit",HabitTypeList2.class);

    }

    public void test_seq() throws Exception{
        //get into create habit type activity
        solo.assertCurrentActivity("Wrong Activity",HabitTypeList2.class);
        solo.clickOnView(solo.getView(R.id.fab));
        assertTrue(solo.waitForActivity(CreateHabitActivity.class));
        solo.assertCurrentActivity("Did not go to Create Habit",CreateHabitActivity.class);

        //create a habit type
        //wait 0.2s before refreshing to make sure the data is changed
        solo.enterText((EditText) solo.getView(R.id.HTD_TitleE),"CreateHabitTest");
        solo.clickOnCheckBox(1);
        solo.clickOnCheckBox(2);
        solo.enterText((EditText) solo.getView(R.id.HTD_ReasonE),"testreason123");
        solo.clickOnButton("Calendar");
        solo.setDatePicker(0,2018,1,1);
        solo.clickOnText("OK");

        solo.clickOnButton("Create");
        assertTrue(solo.waitForActivity(HabitTypeList2.class));
        solo.sleep(500);
        solo.clickOnView(solo.getView(R.id.refresh));
        assertTrue(solo.waitForText("CreateHabitTest"));

        //change detail
        solo.clickInList(0);
        assertTrue(solo.waitForActivity(HabitTypeDetailsActivity.class));
        solo.clearEditText((EditText) solo.getView(R.id.HTD_TitleE));
        solo.enterText((EditText) solo.getView(R.id.HTD_TitleE),"HabitTest_New");
        solo.clickOnButton("update");
        assertTrue(solo.waitForActivity(HabitTypeList2.class));
        solo.sleep(500);
        solo.clickOnView(solo.getView(R.id.refresh));
        assertTrue(solo.waitForText("HabitTest_New"));

        //adding event
        solo.clickInList(0);
        assertTrue(solo.waitForActivity(HabitTypeDetailsActivity.class));
        solo.clickOnButton("Add Event");
        solo.assertCurrentActivity("Create habit event page error", CreateHabitEventActivity.class);
        solo.enterText((EditText) solo.getView(R.id.CHE_Comment),"test comment");
        solo.clickOnButton("Create");
        solo.assertCurrentActivity("didn't go back",HabitTypeList2.class);

        //check event
        solo.clickOnImageButton(0);//nav memu
        for (int i=0;i<3;i++){
            solo.sendKey(KeyEvent.KEYCODE_DPAD_DOWN);
            solo.sleep(100);//wait so that it go through
        }
        solo.sendKey(KeyEvent.KEYCODE_DPAD_CENTER);
        assertTrue(solo.waitForActivity(HabitEventHistory2.class));
        solo.assertCurrentActivity("Not HabitEventHistory Activity",HabitEventHistory2.class);
        assertTrue(solo.waitForText("test comment"));

        //go to map
        solo.clickOnView(solo.getView(R.id.eventmap));
        solo.assertCurrentActivity("Not in Map Page",MapsActivity.class);
        solo.goBack();
        solo.assertCurrentActivity("Not HabitEventHistory Activity",HabitEventHistory2.class);

        //delete
        solo.clickOnImageButton(0);//nav memu
        for (int i=0;i<2;i++){
            solo.sendKey(KeyEvent.KEYCODE_DPAD_DOWN);
            solo.sleep(100);//wait so that it go through
        }
        solo.sendKey(KeyEvent.KEYCODE_DPAD_UP);
        //^ this is because it will always select from the list first then travel to
        // nav menu and lands on the second item
        solo.sendKey(KeyEvent.KEYCODE_DPAD_CENTER);
        solo.sleep(500);
        solo.clickInList(0);
        assertTrue(solo.waitForActivity(HabitTypeDetailsActivity.class));
        solo.clickOnButton("delete");
        assertTrue(solo.waitForActivity(HabitTypeList2.class));
        solo.sleep(500);
        solo.clickOnView(solo.getView(R.id.refresh));
        assertFalse(solo.waitForText("HabitTest_New",1,1));
    }

    public void test_tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

}