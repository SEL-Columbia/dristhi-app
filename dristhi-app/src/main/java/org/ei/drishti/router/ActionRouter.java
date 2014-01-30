package org.ei.drishti.router;

import org.ei.drishti.domain.AlertActionRoute;
import org.ei.drishti.domain.MotherActionRoute;
import org.ei.drishti.dto.Action;

import static org.ei.drishti.util.Log.logWarn;

public class ActionRouter {
    public void directAlertAction(Action action) {
        AlertActionRoute[] alertActionRoutes = AlertActionRoute.values();
        for (AlertActionRoute alertActionRoute : alertActionRoutes) {
            if (alertActionRoute.identifier().equals(action.type())) {
                alertActionRoute.direct(action);
                return;
            }
        }
        logWarn("Unknown type in Alert action: " + action);
    }

    public void directMotherAction(Action action) {
        MotherActionRoute[] motherActionRoutes = MotherActionRoute.values();
        for (MotherActionRoute motherActionRoute : motherActionRoutes) {
            if (motherActionRoute.identifier().equals(action.type())) {
                motherActionRoute.direct(action);
                return;
            }
        }
        logWarn("Unknown type in Mother action: " + action);
    }
}
