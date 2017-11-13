package com.notcmput301.habitbook;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.google.gson.Gson;
import com.robotium.solo.Solo;

/**
 * Created by shang on 10/21/2017.
 */
public class HabitTypeListActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;


    public HabitTypeListActivityTest(){
        //because we have to start from here
        super(HabitTypeListActivity.class);
    }


    public void setUp() throws Exception{
        //intent injection
        Gson gson = new Gson();

        ElasticSearch.verifyLogin vl = new ElasticSearch.verifyLogin();
        vl.execute("testcode", "testpassword");

        User testUser = vl.get();
        Intent intent = new Intent();

        intent.putExtra("user", gson.toJson(testUser));
        setActivityIntent(intent);


        solo = new Solo(getInstrumentation() , getActivity());
    }

    public void test_newHabit() throws Exception{
        // new habit button
        solo.assertCurrentActivity("Wrong Activity",HabitTypeListActivity.class);
        solo.clickOnButton("New habit");
        assertTrue(solo.waitForActivity(CreateHabitActivity.class));
        solo.assertCurrentActivity("Did not go to Create Habit",CreateHabitActivity.class);

        solo.clickOnButton("Back");
        assertTrue(solo.waitForActivity(HabitTypeListActivity.class));
        solo.assertCurrentActivity("Did not go back",HabitTypeListActivity.class);
    }

    public void test_refresh() throws Exception{
        //refresh button
        solo.assertCurrentActivity("Wrong Activity",HabitTypeListActivity.class);
        solo.clickOnButton("Refresh");
        assertTrue(solo.waitForText("Updated!"));
        solo.assertCurrentActivity("Did not go to Create Habit",HabitTypeListActivity.class);

    }

    public void test_tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

    public void test_create() throws Exception{
        //create a habit type
        solo.assertCurrentActivity("Wrong Activity",HabitTypeListActivity.class);
        solo.clickOnButton("New habit");
        assertTrue(solo.waitForActivity(CreateHabitActivity.class));
        solo.assertCurrentActivity("Did not go to Create Habit",CreateHabitActivity.class);
        solo.assertCurrentActivity("Wrong Activity", CreateHabitActivity.class);
        solo.enterText((EditText) solo.getView(R.id.createHabitTitleBar),"CreateHabitTest");
        solo.clickOnCheckBox(1);
        solo.clickOnCheckBox(2);
        solo.enterText((EditText) solo.getView(R.id.createHabitReasonBar),"testreason123");
        solo.enterText((EditText) solo.getView(R.id.createHabitDateBar),"2000-01-01");
        solo.clickOnButton("Create");
        assertTrue(solo.waitForActivity(HabitTypeListActivity.class));
        assertTrue(solo.waitForText("CreateHabitTest"));

        //then delete it
        solo.clickInList(0);
        assertTrue(solo.waitForActivity(HabitTypeDetailsActivity.class));
        solo.clickOnButton("delete");
        assertTrue(solo.waitForActivity(HabitTypeListActivity.class));
        solo.clickOnButton("Refresh");
        assertFalse(solo.waitForText("CreateHabitTest",1,1));
    }


}