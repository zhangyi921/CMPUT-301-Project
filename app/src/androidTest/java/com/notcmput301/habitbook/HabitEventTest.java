package com.notcmput301.habitbook;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by Yi on 2017/10/21.
 */
@RunWith(AndroidJUnit4.class)
public class HabitEventTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.notcmput301.habitbook", appContext.getPackageName());
    }
}
