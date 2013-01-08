package org.ei.drishti.service;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.LoginResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.ei.drishti.domain.LoginResponse.SUCCESS;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class CommCareHQServiceTest {
    @Mock
    private HTTPAgent agent;

    private CommCareHQService service;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        service = new CommCareHQService(agent, "http://base-url.org", "domainX");
    }

    @Test
    public void shouldAskAgentWhetherCredentialsAreValid() {
        String expectedURL = "http://base-url.org/a/domainX/phone/restore";
        String expectedUserName = "user@domainX.commcarehq.org";
        when(agent.urlCanBeAccessWithGivenCredentials(expectedURL, expectedUserName, "password")).thenReturn(SUCCESS);

        LoginResponse isValidUser = service.isValidUser("user", "password");

        assertEquals(SUCCESS, isValidUser);
        verify(agent).urlCanBeAccessWithGivenCredentials(expectedURL, expectedUserName, "password");
    }
}
