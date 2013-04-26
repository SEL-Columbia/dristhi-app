package org.ei.drishti.view.controller;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.ANM;
import org.ei.drishti.service.ANMService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class UpdateANMDetailsTaskTest {

    @Mock
    private ANMService anmService;

    boolean afterFetchListenerCalled = false;
    private UpdateANMDetailsTask task;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        task = new UpdateANMDetailsTask(anmService);
    }

    @Test
    public void shouldCallAfterUpdateListenerWhenUpdateIsComplete() throws Exception {
        when(anmService.fetchDetails()).thenReturn(new ANM("anm id 1", 1, 2, 3, 4, 5));

        task.fetch(new AfterANMDetailsFetchListener() {
            @Override
            public void afterFetch(ANM anm) {
                afterFetchListenerCalled = true;
                assertEquals(new ANM("anm id 1", 1, 2, 3, 4, 5), anm);
            }
        });

        assertTrue(afterFetchListenerCalled);
    }
}
