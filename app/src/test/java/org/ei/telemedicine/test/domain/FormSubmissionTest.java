package org.ei.telemedicine.test.domain;

import com.google.gson.Gson;

import org.ei.telemedicine.domain.form.FormData;
import org.ei.telemedicine.domain.form.FormField;
import org.ei.telemedicine.domain.form.FormInstance;
import org.ei.telemedicine.domain.form.FormSubmission;
import org.ei.telemedicine.domain.form.SubForm;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.ei.telemedicine.util.FormSubmissionBuilder.create;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class FormSubmissionTest {
    @Test
    public void shouldGetFieldValueByName() throws Exception {
        FormInstance formInstance = new FormInstance(new FormData("entity", "default", asList(new FormField("field1", "value1", "source1"), new FormField("field2", "value2", "source2")),
                asList(new SubForm("sub form name"))), "1");

        FormSubmission formSubmission = create().withFormInstance(new Gson().toJson(formInstance)).build();

        assertEquals("value1", formSubmission.getFieldValue("field1"));
        assertEquals("value2", formSubmission.getFieldValue("field2"));
        assertNull(formSubmission.getFieldValue("non existent field"));
    }

    @Test
    public void shouldGetSubFormByName() throws Exception {
        SubForm subForm = new SubForm("sub form name");
        FormInstance formInstance = new FormInstance(new FormData("entity", "default", asList(new FormField("field1", "value1", "source1"), new FormField("field2", "value2", "source2")),
                asList(subForm)), "1");

        FormSubmission formSubmission = create().withFormInstance(new Gson().toJson(formInstance)).build();

        assertEquals(subForm, formSubmission.getSubFormByName("sub form name"));
    }
}
