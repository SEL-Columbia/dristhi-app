package org.ei.drishti.service;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.repository.AllAlerts;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.repository.AllSettings;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class UserServiceTest {
    @Mock
    AllAlerts allAlerts;
    @Mock
    AllEligibleCouples allEligibleCouples;
    @Mock
    AllSettings allSettings;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void shouldDeleteDataAndPreviousFetchIndexWhenUserChanged() throws Exception {
        new UserService(allSettings, allAlerts, allEligibleCouples).changeUser();

        verify(allAlerts).deleteAllAlerts();
        verify(allEligibleCouples).deleteAll();
        verify(allSettings).savePreviousFetchIndex("0");
    }

}
