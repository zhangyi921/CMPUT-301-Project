package com.notcmput301.habitbook;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

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
        solo.assertCurrentActivity("Wrong Activity",HabitTypeListActivity.class);
        solo.clickOnButton("New habit");
        assertTrue(solo.waitForActivity(CreateHabitActivity.class));
        solo.assertCurrentActivity("Did not go to Create Habit",CreateHabitActivity.class);

        solo.clickOnButton("Back");
        assertTrue(solo.waitForActivity(HabitTypeListActivity.class));
        solo.assertCurrentActivity("Did not go back",HabitTypeListActivity.class);
    }

    public void test_refresh() throws Exception{
        solo.assertCurrentActivity("Wrong Activity",HabitTypeListActivity.class);
        solo.clickOnButton("Refresh");
        solo.assertCurrentActivity("Did not go to Create Habit",HabitTypeListActivity.class);

    }

    public void test_tearDown() throws Exception{
        solo.finishOpenedActivities();
    }


}