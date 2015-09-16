package org.ei.telemedicine.test.service;

import android.test.AndroidTestCase;

import org.ei.telemedicine.repository.FormDataRepository;
import org.ei.telemedicine.service.PendingFormSubmissionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class PendingFormSubmissionServiceTest {
    @Mock
    private FormDataRepository repository;
    private PendingFormSubmissionService pendingFormSubmissionService;

    @Before
    public void setUp() {
        initMocks(this);
        pendingFormSubmissionService = new PendingFormSubmissionService(repository);
    }

    @Test
    public void testShouldFetchPendingFormSubmissionCount() {
        pendingFormSubmissionService.pendingFormSubmissionCount();

        verify(repository).getPendingFormSubmissionsCount();
    }
}
