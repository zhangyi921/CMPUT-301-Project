/*
 * HabitTypeValid
 *
 * Version 1.0
 *
 * November 12, 2017
 *
 * Copyright (c) 2017 Team NOTcmput301, CMPUT301, University of Alberta - All Rights Reserved
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in the project wiki on github. Otherwise please contact miller4@ualberta.ca.
 */

package com.notcmput301.habitbook;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by matteo on 2017-11-05.
 */

/**
 * Class for determining habit type info validity
 *
 * @author NOTcmput301
 * @version 1.0
 * @see HabitType
 * @since 1.0
 */

public class HabitTypeValid {

    /**
     * Determines validity of habit name
     *
     * @param n string representing habit name
     * @return boolean for validity
     */
    public static boolean validName(String n) {
        // Returns false if n is blank

        if (n.equals("") ) return false;
        // Return false if n is longer than 20 characters
        if (n.length() > 20) return false;

            // Return true otherwise
        else return true;
    }

    /**
     * Determines uniqueness of habit name
     *
     * @param n string representing habit name
     * @param cUser owner of habit type
     * @return boolean for validity
     */
    public static boolean isUnique(String n, User cUser) {
        boolean retVal;
        retVal = HabitTypeManager.habitTypeExists(cUser, n); // Check if hatitType exists for that user
        return (!retVal); // retVal is reversed. If that habitType exists, the habitType name cannot be valid
    }

    /**
     * Determines validity of habit reason
     *
     * @param r string representing habit reason
     * @return boolean for validity
     */
    public static boolean validReason(String r) {
        // Returns false if r is blank
        if (r.equals("") ) return false;

        // Return false if r is longer than 30 characters
        if (r.length() > 30) return false;

            // Otherwise return true
        else return true;
    }

    /**
     * Determines validity of habit reason
     *
     * @param d string representing habit reason
     * @return boolean for validity
     */
    public static boolean validDate(String d) {
        // Returns false if d isn't 10 chars long
        if (d.length() != 10) return false;

        // Returns false if dash isn't found between the year and month fields
        if (d.charAt(4) != '-' || d.charAt(7) != '-') return false;

        // Checks if every non-hyphen digit is a number
        for(int i = 0; i < 10; i++) {
            if (i == 4 || i == 7) continue; // Hyphens don't need to be numbers

            Character c = d.charAt(i); // Stores current char
            if ( ! Character.isDigit(c) ) return false; // Returns false if char NaN
        }

        // Extracts year from date and stores in year as Integer
        String temp = d.substring(0,4);
        Integer year = Integer.valueOf(temp);

        // Extracts month from date and stores in month as Integer
        temp = d.substring(5, 7);
        Integer month = Integer.valueOf(temp);

        // Extracts day from date and stores in day as Integer
        temp = d.substring(8, 10);
        Integer day = Integer.valueOf(temp);

        // leapYear is true if year is divisible by 4 (is a leap year)
        boolean leapYear = ( (year % 4) == 0);

        // Returns false if day, month or year is zero or less
        if (year <= 0 || month <= 0 || day <= 0) return false;

        // Returns false if month is greater than 12
        if (month > 12) return false;

        // Stores Integer array in is31days, which contains month values with 31 days
        Integer [] is31daysArray = new Integer[]{1, 3, 5, 7, 8, 10, 12};
        ArrayList<Integer> is31days = new ArrayList<Integer>(Arrays.asList(is31daysArray) );

        // Stores Integer array in is30days, which contains month values with 30 days
        Integer [] is30daysArray = new Integer[]{4, 6, 9, 11};
        ArrayList<Integer> is30days = new ArrayList<Integer>(Arrays.asList(is30daysArray));

        Integer dayLimit; // Will store number of days in February based on year

        // These if / else statements set dayLimit based on month value
        if (is31days.indexOf(month) != -1) dayLimit = 31;
        else if (is30days.indexOf(month) != -1) dayLimit = 30;
        else if (leapYear) dayLimit = 29;
        else dayLimit = 28;

        // Return false if day is higher than no. of days according to month value
        if (day > dayLimit) return false;

            // Returns true indiciating that input date is valid
        else return true;

    }

    /**
     * Determines validity of habit weekdays
     *
     * @param days arraylist of weekdays
     * @return boolean for validity
     */
    public static boolean validCheckbox(ArrayList<Integer> days) {
        if (days.isEmpty() ) return false;
        else return true;
    }


}
