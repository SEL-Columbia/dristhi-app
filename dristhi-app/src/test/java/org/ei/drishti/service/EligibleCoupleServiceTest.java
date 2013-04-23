package org.ei.drishti.service;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.TimelineEvent;
import org.ei.drishti.domain.form.FormSubmission;
import org.ei.drishti.dto.Action;
import org.ei.drishti.repository.EligibleCoupleRepository;
import org.ei.drishti.repository.TimelineEventRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;

import java.util.HashMap;

import static org.ei.drishti.util.ActionBuilder.*;
import static org.ei.drishti.util.EasyMap.mapOf;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class EligibleCoupleServiceTest {
    @Mock
    private EligibleCoupleRepository eligibleCoupleRepository;
    @Mock
    private TimelineEventRepository timelineEventRepository;

    private EligibleCoupleService service;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        service = new EligibleCoupleService(eligibleCoupleRepository, timelineEventRepository);
    }

    @Test
    public void shouldInsertUpdateAndDeleteECActions() throws Exception {
        Action firstCreateAction = actionForCreateEC("Case X", "Theresa 1", "Husband 1", "EC Number 1", "Village 1", "SubCenter 1", "PHC X");
        Action firstUpdateAction = actionForUpdateECDetails("Case X", mapOf("key", "value"));
        Action firstDeleteAction = actionForDeleteEC("Case Y");
        Action secondUpdateAction = actionForUpdateECDetails("Case Y", mapOf("key", "value"));
        Action secondCreateAction = actionForCreateEC("Case Z", "Theresa 2", "Husband 2", "EC Number 2", "Village 1", "SubCenter 1", "PHC X");
        Action secondDeleteAction = actionForDeleteEC("Case B");

        service.register(firstCreateAction);
        service.updateDetails(firstUpdateAction);
        service.register(secondCreateAction);
        service.updateDetails(secondUpdateAction);
        service.delete(firstDeleteAction);
        service.delete(secondDeleteAction);

        InOrder inOrder = inOrder(eligibleCoupleRepository);
        inOrder.verify(eligibleCoupleRepository).add(new EligibleCouple("Case X", "Theresa 1", "Husband 1", "EC Number 1", "Village 1", "SubCenter 1", new HashMap<String, String>()));
        inOrder.verify(eligibleCoupleRepository).updateDetails("Case X", mapOf("key", "value"));
        inOrder.verify(eligibleCoupleRepository).add(new EligibleCouple("Case Z", "Theresa 2", "Husband 2", "EC Number 2", "Village 1", "SubCenter 1", new HashMap<String, String>()));
        inOrder.verify(eligibleCoupleRepository).updateDetails("Case Y", mapOf("key", "value"));
        inOrder.verify(eligibleCoupleRepository).close("Case Y");
        inOrder.verify(eligibleCoupleRepository).close("Case B");
        verifyNoMoreInteractions(eligibleCoupleRepository);
    }

    @Test
    public void shouldCreateTimelineEventWhenECIsRegistered() throws Exception {
        FormSubmission submission = mock(FormSubmission.class);
        when(submission.entityId()).thenReturn("entity id 1");
        when(submission.getFieldValue("submissionDate")).thenReturn("2012-01-01");

        service.register(submission);

        verify(timelineEventRepository).add(TimelineEvent.forECRegistered("entity id 1", "2012-01-01"));
    }

    @Test
    public void shouldNotCreateTimelineEventWhenECIsRegisteredWithoutSubmissionDate() throws Exception {
        FormSubmission submission = mock(FormSubmission.class);
        when(submission.entityId()).thenReturn("entity id 1");
        when(submission.getFieldValue("submissionDate")).thenReturn(null);

        service.register(submission);

        verifyZeroInteractions(timelineEventRepository);
    }

    @Test
    public void shouldCreateTimelineEventAndUpdateEntityWhenFPChangeIsReported() throws Exception {
        FormSubmission submission = mock(FormSubmission.class);
        when(submission.entityId()).thenReturn("entity id 1");
        when(submission.getFieldValue("currentMethod")).thenReturn("condom");
        when(submission.getFieldValue("newMethod")).thenReturn("ocp");
        when(submission.getFieldValue("familyPlanningMethodChangeDate")).thenReturn("2012-01-01");

        service.fpChange(submission);

        verify(timelineEventRepository).add(TimelineEvent.forChangeOfFPMethod("entity id 1", "condom", "ocp", "2012-01-01"));
        verify(eligibleCoupleRepository).mergeDetails("entity id 1", mapOf("currentMethod", "ocp"));
    }
}
