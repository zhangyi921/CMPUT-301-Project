package com.notcmput301.habitbook;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;


/**
 * Created by shang on 10/21/2017.
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
        solo.clearEditText((EditText) solo.getView(R.id.createAccount_password));
        solo.clearEditText((EditText) solo.getView(R.id.createAccount_passwordR));

        solo.enterText((EditText) solo.getView(R.id.createAccount_Username),"CreateActTest1");
        solo.enterText((EditText) solo.getView(R.id.createAccount_password),"123");
        solo.enterText((EditText) solo.getView(R.id.createAccount_passwordR),"123");
        solo.clickOnButton("create");
        assertTrue(solo.waitForText("password is too short"));

        solo.enterText((EditText) solo.getView(R.id.createAccount_passwordR),"4");
        solo.clickOnButton("create");
        assertTrue(solo.waitForText("passwords do not match"));
    }


    public void test_userExists() throws Exception{
        //When the username is taken
        solo.assertCurrentActivity("Wrong Activity", CreateAccountActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.createAccount_Username));
        solo.clearEditText((EditText) solo.getView(R.id.createAccount_password));
        solo.clearEditText((EditText) solo.getView(R.id.createAccount_passwordR));

        solo.enterText((EditText) solo.getView(R.id.createAccount_Username),"testcode");
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
        //delete test user and check
        /*ElasticSearch.verifyLogin vl = new ElasticSearch.verifyLogin();
        vl.execute("CreateActTest1", "testpwd123");
        User testUser = vl.get();*/
        ElasticSearch.DeleteUser deleteUser = new ElasticSearch.DeleteUser();
        deleteUser.execute("CreateActTest1");

        //this one should go through
        solo.assertCurrentActivity("Wrong Activity", CreateAccountActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.createAccount_Username));
        solo.clearEditText((EditText) solo.getView(R.id.createAccount_password));
        solo.clearEditText((EditText) solo.getView(R.id.createAccount_passwordR));

        solo.enterText((EditText) solo.getView(R.id.createAccount_Username),"CreateActTest1");
        solo.enterText((EditText) solo.getView(R.id.createAccount_password),"testpwd123");
        solo.enterText((EditText) solo.getView(R.id.createAccount_passwordR),"testpwd123");
        solo.clickOnButton("create");
        assertTrue(solo.waitForText("Welcome!"));
        assertTrue(solo.waitForActivity(MainMenuActivity.class));
        solo.assertCurrentActivity("Did not go to mainmenu", CreateAccountActivity.class);

        solo.clickOnButton("Logout");
        assertTrue(solo.waitForActivity(LoginActivity.class));
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);

        solo.clickOnButton("sign up");
        solo.waitForActivity(CreateAccountActivity.class);
        solo.assertCurrentActivity("Not Create account Activity", CreateAccountActivity.class);



    }
    public void test_tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}