package com.notcmput301.habitbook;

import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.google.gson.Gson;
import com.robotium.solo.Solo;


/**
 * Created by shang on 10/21/2017.
 */

public class MainMenuActivityTest extends ActivityInstrumentationTestCase2{


    private Solo solo;


    public MainMenuActivityTest(){
        //because we have to start from here
        super(MainMenuActivity.class);


    }


    public void setUp() throws Exception{
        //setting up intent so we don't have to go from login page every time
        Gson gson = new Gson();

        ElasticSearch.verifyLogin vl = new ElasticSearch.verifyLogin();
        vl.execute("testcode", "testpassword");

        User testUser = vl.get();
        Intent intent = new Intent();

        intent.putExtra("passedUser", gson.toJson(testUser));
        setActivityIntent(intent);


        solo = new Solo(getInstrumentation() , getActivity());

    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }


    public void test_habitType() throws Exception {
        //habit type button

        solo.clickOnButton("Habit Types");
        assertTrue(solo.waitForActivity(HabitTypeListActivity.class));
        solo.assertCurrentActivity("Not HabitType Activity", HabitTypeListActivity.class);

        solo.goBack();
        assertTrue(solo.waitForActivity(MainMenuActivity.class));
        solo.assertCurrentActivity("Back button took you away", MainMenuActivity.class);
    }

    public void test_Logout() throws Exception {
        //log out button

        solo.clickOnButton("Logout");
        assertTrue(solo.waitForActivity(LoginActivity.class));
        solo.assertCurrentActivity("Logout isn't working", LoginActivity.class);

        solo.enterText((EditText) solo.getView(R.id.login_username), "testcode");
        solo.enterText((EditText) solo.getView(R.id.login_password), "testpassword");
        solo.clickOnButton("login");

        assertTrue(solo.waitForActivity(MainMenuActivity.class));
        solo.assertCurrentActivity("re-login error", MainMenuActivity.class);
    }


    public void test_tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}