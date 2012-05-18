package org.ei.drishti.service;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.Context;
import org.ei.drishti.repository.AllSettings;
import org.ei.drishti.repository.Repository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class LoginServiceTest {
    @Mock
    private CommCareService commCareService;
    @Mock
    private Repository repository;
    @Mock
    private AllSettings allSettings;
    private LoginService loginService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        loginService = new LoginService(commCareService, repository, allSettings);
    }

    @Test
    public void shouldUseCommCareToDoRemoteLoginCheck() {
        loginService.isValidRemoteLogin("user X", "password Y");

        verify(commCareService).isValidUser("user X", "password Y");
    }

    @Test
    public void shouldConsiderALocalLoginValidWhenUsernameMatchesRegisteredUserAndPasswordMatchesTheOneInDB() {
        when(allSettings.fetchRegisteredANM()).thenReturn("ANM X");
        when(repository.canUseThisPassword("password Z")).thenReturn(true);

        assertTrue(loginService.isValidLocalLogin("ANM X", "password Z"));

        verify(allSettings).fetchRegisteredANM();
        verify(repository).canUseThisPassword("password Z");
    }

    @Test
    public void shouldConsiderALocalLoginInvalidWhenRegisteredUserDoesNotMatch() {
        when(allSettings.fetchRegisteredANM()).thenReturn("ANM X");

        assertFalse(loginService.isValidLocalLogin("SOME OTHER ANM", "password"));

        verify(allSettings).fetchRegisteredANM();
        verifyZeroInteractions(repository);
    }

    @Test
    public void shouldConsiderALocalLoginInvalidWhenRegisteredUserMatchesButNotThePassword() {
        when(allSettings.fetchRegisteredANM()).thenReturn("ANM X");
        when(repository.canUseThisPassword("password Z")).thenReturn(false);

        assertFalse(loginService.isValidLocalLogin("ANM X", "password Z"));

        verify(allSettings).fetchRegisteredANM();
        verify(repository).canUseThisPassword("password Z");
    }

    @Test
    public void shouldRegisterANewUser() {
        Context context = mock(Context.class);

        Context.setInstance(context);
        loginService.loginWith("user X", "password Y");

        verify(allSettings).registerANM("user X");
        verify(context).setPassword("password Y");
    }
}
