package org.ei.drishti.service;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.Context;
import org.ei.drishti.repository.AllAlerts;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.repository.AllSettings;
import org.ei.drishti.repository.Repository;
import org.ei.drishti.util.Session;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class UserServiceTest {
    @Mock
    private CommCareService commCareService;
    @Mock
    private Repository repository;
    @Mock
    private AllSettings allSettings;
    @Mock
    private AllAlerts allAlerts;
    @Mock
    private AllEligibleCouples allEligibleCouples;

    private UserService userService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        userService = new UserService(commCareService, repository, allSettings);
    }

    @Test
    public void shouldUseCommCareToDoRemoteLoginCheck() {
        userService.isValidRemoteLogin("user X", "password Y");

        verify(commCareService).isValidUser("user X", "password Y");
    }

    @Test
    public void shouldConsiderALocalLoginValidWhenUsernameMatchesRegisteredUserAndPasswordMatchesTheOneInDB() {
        when(allSettings.fetchRegisteredANM()).thenReturn("ANM X");
        when(repository.canUseThisPassword("password Z")).thenReturn(true);

        assertTrue(userService.isValidLocalLogin("ANM X", "password Z"));

        verify(allSettings).fetchRegisteredANM();
        verify(repository).canUseThisPassword("password Z");
    }

    @Test
    public void shouldConsiderALocalLoginInvalidWhenRegisteredUserDoesNotMatch() {
        when(allSettings.fetchRegisteredANM()).thenReturn("ANM X");

        assertFalse(userService.isValidLocalLogin("SOME OTHER ANM", "password"));

        verify(allSettings).fetchRegisteredANM();
        verifyZeroInteractions(repository);
    }

    @Test
    public void shouldConsiderALocalLoginInvalidWhenRegisteredUserMatchesButNotThePassword() {
        when(allSettings.fetchRegisteredANM()).thenReturn("ANM X");
        when(repository.canUseThisPassword("password Z")).thenReturn(false);

        assertFalse(userService.isValidLocalLogin("ANM X", "password Z"));

        verify(allSettings).fetchRegisteredANM();
        verify(repository).canUseThisPassword("password Z");
    }

    @Test
    public void shouldRegisterANewUser() {
        Context context = mock(Context.class);
        Session properties = mock(Session.class);
        when(context.session()).thenReturn(properties);

        Context.setInstance(context);
        userService.loginWith("user X", "password Y");

        verify(allSettings).registerANM("user X");
        verify(properties).setPassword("password Y");
    }

    @Test
    public void shouldDeleteDataAndSettingsWhenLogoutHappens() throws Exception {
        userService.logout();

        verify(repository).deleteRepository();
        verify(allSettings).savePreviousFetchIndex("0");
        verify(allSettings).registerANM("");
    }
}
