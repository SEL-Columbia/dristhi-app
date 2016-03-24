package org.ei.telemedicine.router;

import org.ei.telemedicine.domain.AlertActionRoute;
import org.ei.telemedicine.domain.MotherActionRoute;
import org.ei.telemedicine.dto.Action;

import static org.ei.telemedicine.util.Log.logWarn;

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
