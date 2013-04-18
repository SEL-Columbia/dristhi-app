package org.ei.drishti.service;

import com.google.gson.Gson;
import org.ei.drishti.domain.FormSubmission;
import org.ei.drishti.repository.AllSettings;
import org.ei.drishti.repository.FormDataRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;

import java.util.List;

import static java.util.Arrays.asList;
import static org.ei.drishti.util.EasyMap.create;
import static org.ei.drishti.util.FormSubmissionBuilder.create;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class FormSubmissionServiceTest {
    @Mock
    private ZiggyService ziggyService;
    @Mock
    private AllSettings allSettings;
    @Mock
    private FormDataRepository formDataRepository;

    private FormSubmissionService service;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        service = new FormSubmissionService(ziggyService, formDataRepository, allSettings);
    }

    @Test
    public void shouldDelegateProcessingToZiggyService() throws Exception {
        List<FormSubmission> submissions = asList(create().withVersion("122").build(), create().withVersion("123").build());

        service.processSubmissions(submissions);

        String paramsForFirstSubmission = new Gson().toJson(
                create("instanceId", "instance id 1")
                        .put("entityId", "entity id 1")
                        .put("formName", "form name 1")
                        .put("version", "122")
                        .map());
        String paramsForSecondSubmission = new Gson().toJson(
                create("instanceId", "instance id 1")
                        .put("entityId", "entity id 1")
                        .put("formName", "form name 1")
                        .put("version", "123")
                        .map());
        InOrder inOrder = inOrder(ziggyService, allSettings);
        inOrder.verify(ziggyService).save(paramsForFirstSubmission, "{}");
        inOrder.verify(allSettings).savePreviousFormSyncIndex("122");
        inOrder.verify(ziggyService).save(paramsForSecondSubmission, "{}");
        inOrder.verify(allSettings).savePreviousFormSyncIndex("123");
    }

    @Test
    public void shouldNotDelegateProcessingToZiggyServiceForProcessedSubmissions() throws Exception {
        FormSubmission firstFormSubmission = create().withInstanceId("instance id 1").withVersion("122").build();
        FormSubmission secondFormSubmission = create().withInstanceId("instance id 2").withVersion("123").build();
        List<FormSubmission> submissions = asList(firstFormSubmission, secondFormSubmission);
        when(formDataRepository.submissionExists("instance id 1")).thenReturn(true);
        when(formDataRepository.submissionExists("instance id 2")).thenReturn(false);

        service.processSubmissions(submissions);

        String paramsForFirstSubmission = new Gson().toJson(
                create("instanceId", "instance id 1")
                        .put("entityId", "entity id 1")
                        .put("formName", "form name 1")
                        .put("version", "122")
                        .map());
        String paramsForSecondSubmission = new Gson().toJson(
                create("instanceId", "instance id 2")
                        .put("entityId", "entity id 1")
                        .put("formName", "form name 1")
                        .put("version", "123")
                        .map());
        InOrder inOrder = inOrder(ziggyService, allSettings);
        inOrder.verify(ziggyService, times(0)).save(paramsForFirstSubmission, "{}");
        inOrder.verify(allSettings).savePreviousFormSyncIndex("122");
        inOrder.verify(ziggyService).save(paramsForSecondSubmission, "{}");
        inOrder.verify(allSettings).savePreviousFormSyncIndex("123");

    }
}
