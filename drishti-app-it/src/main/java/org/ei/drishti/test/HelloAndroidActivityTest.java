package org.ei.drishti.test;

import android.test.ActivityInstrumentationTestCase2;
import org.ei.drishti.activity.DrishtiMainActivity;

public class HelloAndroidActivityTest extends ActivityInstrumentationTestCase2<DrishtiMainActivity> {

    public HelloAndroidActivityTest() {
        super(DrishtiMainActivity.class);
    }

    public void testActivity() {
        DrishtiMainActivity activity = getActivity();
        assertNotNull(activity);
    }
}

