package org.ei.drishti.view.contract;

import org.ei.drishti.domain.CommCareForm;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class TodoDetailTest {

    @Test
    public void shouldOpenCommCareFormsBasedOnTodoType() {
        assertEquals(CommCareForm.ANC_DELIVERY_OUTCOME, TodoDetail.from("EDD").formToOpen());
        assertEquals(CommCareForm.CHILD_IMMUNIZATION, TodoDetail.from("OPV 0").formToOpen());
        assertEquals(CommCareForm.CHILD_IMMUNIZATION, TodoDetail.from("HEP B0").formToOpen());
        assertEquals(CommCareForm.CHILD_IMMUNIZATION, TodoDetail.from("Hepatitis B1").formToOpen());
        assertEquals(CommCareForm.CHILD_IMMUNIZATION, TodoDetail.from("DPT 0").formToOpen());
        assertEquals(CommCareForm.CHILD_IMMUNIZATION, TodoDetail.from("BCG").formToOpen());
    }

}
