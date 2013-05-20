package org.ei.drishti.view.contract;

import com.google.gson.Gson;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.Alert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static java.text.MessageFormat.format;
import static junit.framework.Assert.assertEquals;
import static org.ei.drishti.domain.AlertStatus.closed;
import static org.ei.drishti.domain.AlertStatus.open;
import static org.ei.drishti.dto.AlertPriority.normal;
import static org.ei.drishti.dto.AlertPriority.urgent;

@RunWith(RobolectricTestRunner.class)
public class ProfileTodoTest {
    private static final String DUE_DATE = "2012-02-11";
    private static final String COMPLETION_DATE = "2012-02-20";

    @Test
    public void shouldProvideProfileTodoDataBasedOnVisitCode() throws Exception {
        assertData(new ProfileTodo(new Alert("Case X", "ANC 1", urgent, "2012-02-02", DUE_DATE, open)), "ANC Visit #1", false, "ANC 1");
        assertData(new ProfileTodo(new Alert("Case X", "ANC 2", normal, "2012-02-02", DUE_DATE, open)), "ANC Visit #2", false, "ANC 2");
        assertData(new ProfileTodo(new Alert("Case X", "ANC 3", normal, "2012-02-02", DUE_DATE, open)), "ANC Visit #3", false, "ANC 3");
        assertData(new ProfileTodo(new Alert("Case X", "ANC 4", urgent, "2012-02-02", DUE_DATE, closed).withCompletionDate(COMPLETION_DATE)), "ANC Visit #4", true, "ANC 4");
    }

    @Test
    public void shouldProvideSensibleDefaultsIfAVisitCodeIsNotFound() throws Exception {
        assertData(new ProfileTodo(new Alert("Case X", "UNKNOWN_VISIT_CODE", urgent, "2012-02-02", DUE_DATE, open)), "UNKNOWN_VISIT_CODE", false, "UNKNOWN_VISIT_CODE");
    }

    private void assertData(ProfileTodo todo, String message, final boolean isCompleted, String visitCode) {
        String todoDatePartOfJSON = format(",\"todoDate\":\"{0}\"", COMPLETION_DATE);
        if (!isCompleted) {
            todoDatePartOfJSON = format(",\"todoDate\":\"{0}\"", DUE_DATE);
        }

        assertEquals("{\"message\":\"" + message + "\"" + ",\"isCompleted\":" + isCompleted + ",\"visitCode\":\"" + visitCode + "\"" + todoDatePartOfJSON + "}", new Gson().toJson(todo));
    }
}
