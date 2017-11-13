package com.notcmput301.habitbook;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.google.gson.Gson;
import com.robotium.solo.Solo;

/**
 * Created by shang on 10/21/2017.
 */
public class CreateHabitActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;


    public CreateHabitActivityTest() {
        //because we have to start from here
        super(CreateHabitActivity.class);
    }


    public void setUp() throws Exception {
        Gson gson = new Gson();

        ElasticSearch.verifyLogin vl = new ElasticSearch.verifyLogin();
        vl.execute("testcode", "testpassword");

        User testUser = vl.get();
        Intent intent = new Intent();

        intent.putExtra("user", gson.toJson(testUser));
        setActivityIntent(intent);


        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void test_missing() throws Exception {
        //When there are missing fields expect return msg
        solo.assertCurrentActivity("Wrong Activity", CreateHabitActivity.class);
        solo.clickOnButton("Create");
        assertTrue(solo.waitForText("Name can't be blank/ over 20 chars"));

        solo.enterText((EditText) solo.getView(R.id.createHabitTitleBar),"CreateHabitTest");
        solo.clickOnButton("Create");
        assertTrue(solo.waitForText("Reason can't be blank/ over 30 chars"));

        solo.enterText((EditText) solo.getView(R.id.createHabitReasonBar),"testreason123");
        solo.clickOnButton("Create");
        assertTrue(solo.waitForText("Invalid date format"));

        //solo.clearEditText((EditText) solo.getView(R.id.createHabitDateBar));
        solo.enterText((EditText) solo.getView(R.id.createHabitDateBar),"2000-01-01");
        solo.clickOnButton("Create");
        assertTrue(solo.waitForText("At least 1 box must be checked"));
    }

    public void test_wrongInput() throws Exception {
        //When input formats are wrong
        solo.assertCurrentActivity("Wrong Activity", CreateHabitActivity.class);
        solo.enterText((EditText) solo.getView(R.id.createHabitTitleBar),"CreateHabitTestasdawdawdw1231312312");
        solo.clickOnButton("Create");
        assertTrue(solo.waitForText("Name can't be blank/ over 20 chars"));

        solo.clearEditText((EditText) solo.getView(R.id.createHabitTitleBar));
        solo.enterText((EditText) solo.getView(R.id.createHabitTitleBar),"CreateHabitTest");
        solo.enterText((EditText) solo.getView(R.id.createHabitReasonBar),"testreason123awdawdawadsadzczxcawdawda");
        solo.clickOnButton("Create");
        assertTrue(solo.waitForText("Reason can't be blank/ over 30 chars"));

        solo.clearEditText((EditText) solo.getView(R.id.createHabitReasonBar));
        solo.enterText((EditText) solo.getView(R.id.createHabitReasonBar),"testreason123");
        solo.enterText((EditText) solo.getView(R.id.createHabitDateBar),"awdawd");
        solo.clickOnButton("Create");
        assertTrue(solo.waitForText("Invalid date format"));
    }

    //test for "Back button is already tested in HabitTypeListActivityTest

    //test for creating a new habit is in habitTypeListActivityTest because Activity will not have a
    //page to return to in this test.




    //public void test_tearDown() throws Exception{
    //    solo.finishOpenedActivities();
    //

}