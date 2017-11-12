package com.notcmput301.habitbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class CreateHabitActivity extends AppCompatActivity {

    private ArrayList<Integer> tempDays = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_habit);
    }

    // Method based of similar method in this YouTube Video
    // https://www.youtube.com/watch?v=NGRV2qY9ZiU
    // Dynamically adds days to tempDays when checkBox is clicked
    public void createHabitBoxClick(View v) {
        boolean check = ((CheckBox) v).isChecked();

        switch (v.getId()) {
            case R.id.createHabitMonBox:
                if (check) tempDays.add(1);
                else tempDays.remove(1);
                break;
            case R.id.createHabitTueBox:
                if (check) tempDays.add(2);
                else tempDays.remove(2);
                break;
            case R.id.createHabitWedBox:
                if (check) tempDays.add(3);
                else tempDays.remove(3);
                break;
            case R.id.createHabitThuBox:
                if (check) tempDays.add(4);
                else tempDays.remove(4);
                break;
            case R.id.createHabitFriBox:
                if (check) tempDays.add(5);
                else tempDays.remove(5);
                break;
            case R.id.createHabitSatBox:
                if (check) tempDays.add(6);
                else tempDays.remove(6);
                break;
            case R.id.createHabitSunBox:
                if (check) tempDays.add(7);
                else tempDays.remove(7);
                break;
        }


    }

    public void createHabitButtonClick(View v) {
        // Get editTexts to be used
        EditText titleBar = (EditText) findViewById(R.id.createHabitTitleBar);
        EditText reasonBar = (EditText) findViewById(R.id.createHabitReasonBar);
        EditText dateBar = (EditText) findViewById(R.id.createHabitDateBar);

        // Extract info from editTexts
        String title = titleBar.getText().toString();
        String reason = reasonBar.getText().toString();
        String date = dateBar.getText().toString();

        // If title of habitType is blank or > 30 chars, display message and stop
        if (! HabitTypeValid.validName(title) ) {
            this.displayMessage("Name can't be blank/ over 20 chars");
            return;
        }

        // If title of habitType already exists, display message and stop
        if (! HabitTypeValid.isUnique(title) ) {
            this.displayMessage("Title must be unique");
            return;
        }

        // If habitType reason is blank or > 30 chars, display message and stop
        if (! HabitTypeValid.validReason(title) ) {
            this.displayMessage("Reason can't be blank/ over 30 chars");
            return;
        }

        // If date is not in YYYY-MM-DD format, or is incorrect, display message and stop
        if (! HabitTypeValid.validDate(title)) {
            this.displayMessage("Invalid date format");
            return;
        }

        // If no boxes are checked, display message and stop
        if (! HabitTypeValid.validCheckbox(tempDays)){
            this.displayMessage("At least 1 box must be checked");
            return;
        }

        User cUser = UserAccountManager.getLoggedInUser();

        HabitType ht = new HabitType(cUser, title, reason, date, tempDays);

        this.goBack();
    }

    public void createHabitBackButton(View v) {
        this.goBack();
    }

    private void displayMessage(String s) {
        TextView tv = (TextView) findViewById(R.id.createHabitMessage);
        tv.setText(s);
    }

    private void goBack() {
        Intent intent = new Intent(this, HabitTypeListActivity.class);
        startActivity(intent);
    }
}

