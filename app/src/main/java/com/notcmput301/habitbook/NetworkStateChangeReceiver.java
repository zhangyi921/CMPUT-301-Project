/*
 * NetworkStateChangeReciever
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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by shangchen on 2017-12-01.
 */

/**
 * Activity to handle changes in network status
 *
 * @author NOTcmput301
 * @version 1.0
 * @since 1.0
 */
public class NetworkStateChangeReceiver extends BroadcastReceiver{

    /**
     * Handler for recieving changes in network state
     *
     * @param context context of network status change
     * @param intent Intent from previous activity
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        NetworkHandler nH = new NetworkHandler(context);

        //if there is a network and there are items in the editor, start
        //synchronizing
        if(nH.isNetworkAvailable() && !nH.isEditorEmpty()){
            Log.e("UPDATING", "Begin");
            Log.e("UPDATING", "Toast");
            try {
                nH.doAllTasks();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
