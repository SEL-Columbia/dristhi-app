package org.ei.drishti.service;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.Child;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.domain.TimelineEvent;
import org.ei.drishti.dto.Action;
import org.ei.drishti.repository.AllTimelineEvents;
import org.ei.drishti.repository.ChildRepository;
import org.ei.drishti.repository.MotherRepository;
import org.ei.drishti.util.ActionBuilder;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.HashMap;

import static org.ei.drishti.domain.TimelineEvent.forChildBirthInECProfile;
import static org.ei.drishti.domain.TimelineEvent.forChildBirthInMotherProfile;
import static org.ei.drishti.util.EasyMap.mapOf;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class ChildServiceTest {
    @Mock
    private AllTimelineEvents allTimelineEvents;
    @Mock
    private ChildRepository childRepository;
    @Mock
    private MotherRepository motherRepository;

    private ChildService service;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        service = new ChildService(motherRepository, childRepository, allTimelineEvents);
    }

    @Test
    public void shouldHandlePNCVisitActionForChild() throws Exception {
        String caseId = "Case Child X";
        Action action = ActionBuilder.actionForChildPNCVisit(caseId, mapOf("some-key", "some-value"));

        service.pncVisit(action);

        verify(allTimelineEvents).add(TimelineEvent.forChildPNCVisit(caseId, "1", "2012-01-01", mapOf("some-key", "some-value")));
        verify(childRepository).updateDetails(caseId, mapOf("some-key", "some-value"));
    }

    @Test
    public void shouldRegisterChildWhenMotherIsFound() throws Exception {
        Action action = ActionBuilder.actionForCreateChild("Case Mother X");
        when(motherRepository.findOpenCaseByCaseID("Case Mother X")).thenReturn(new Mother("Case Mother X", "EC CASE 1", "TC 1", "2012-01-01"));

        service.register(action);
        verify(allTimelineEvents).add(forChildBirthInMotherProfile("Case Mother X", action.get("dateOfBirth"), action.get("gender"), action.details()));
        verify(allTimelineEvents).add(forChildBirthInECProfile("EC CASE 1", action.get("dateOfBirth"), action.get("gender"), action.details()));
        verify(childRepository).add(new Child("Case X", "Case Mother X", "TC 1", LocalDate.now().toString(), "female", new HashMap<String, String>()));
    }

    @Test
    public void shouldNotRegisterChildWhenMotherIsNotFound() throws Exception {
        Action action = ActionBuilder.actionForCreateChild("Case Mother X");
        when(motherRepository.findOpenCaseByCaseID("Case Mother X")).thenReturn(null);

        service.register(action);

        verifyZeroInteractions(childRepository);
    }

    @Test
    public void shouldHandleUpdateImmunizationsForChild() throws Exception {
        Action action = ActionBuilder.updateImmunizations("Case X", mapOf("aKey", "aValue"));

        service.updateImmunizations(action);

        verify(allTimelineEvents).add(TimelineEvent.forChildImmunization("Case X", action.get("immunizationsProvided"), action.get("immunizationsProvidedDate")
                , action.get("vitaminADose")));
        verify(childRepository).updateDetails("Case X", mapOf("aKey", "aValue"));
    }

    @Test
    public void shouldCloseChildRecordForDeleteChildAction() throws Exception {
        Action action = ActionBuilder.closeChild("Case X");

        service.delete(action);

        verify(childRepository).close("Case X");
    }
}
