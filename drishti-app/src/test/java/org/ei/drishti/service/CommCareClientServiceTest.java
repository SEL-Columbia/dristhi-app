package org.ei.drishti.service;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class CommCareClientServiceTest {
    @Mock
    Context context;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void shouldNotFailIfUnableToStartActivity() throws Exception {
        doThrow(new ActivityNotFoundException()).when(context).startActivity(any(Intent.class));

        new CommCareClientService().start(context, "ANC_SERVICES", "SOME_CASE_ID");
    }

    @Test
    public void shouldNotFailIfUnavailableFormIsProvided() throws Exception {
        new CommCareClientService().start(context, "UNAVAILABLE_FORM", "SOME_CASE_ID");
    }
}
