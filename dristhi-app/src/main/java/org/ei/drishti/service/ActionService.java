package org.ei.drishti.service;

import com.google.gson.Gson;
import org.ei.drishti.dto.Action;
import org.ei.drishti.domain.FetchStatus;
import org.ei.drishti.domain.Response;
import org.ei.drishti.repository.AllAlerts;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.repository.AllSettings;
import org.ei.drishti.util.Log;

import java.util.List;

import static org.ei.drishti.domain.FetchStatus.fetchedFailed;
import static org.ei.drishti.domain.FetchStatus.nothingFetched;

public class ActionService {
    private final DrishtiService drishtiService;
    private final AllSettings allSettings;
    private final AllAlerts allAlerts;
    private final AllEligibleCouples allEligibleCouples;
    private final AllBeneficiaries allBeneficiaries;

    public ActionService(DrishtiService drishtiService, AllSettings allSettings, AllAlerts allAlerts, AllEligibleCouples allEligibleCouples, AllBeneficiaries allBeneficiaries) {
        this.drishtiService = drishtiService;
        this.allSettings = allSettings;
        this.allAlerts = allAlerts;
        this.allEligibleCouples = allEligibleCouples;
        this.allBeneficiaries = allBeneficiaries;
    }

    public FetchStatus fetchNewActions() {
        String previousFetchIndex = allSettings.fetchPreviousFetchIndex();
        Response<List<Action>> response = drishtiService.fetchNewActions(allSettings.fetchRegisteredANM(), previousFetchIndex);

        if (response.isFailure()) {
            return fetchedFailed;
        }

        if (response.payload().isEmpty()) {
            return nothingFetched;
        }

        handleActions(response);
        allSettings.savePreviousFetchIndex(response.payload().get(response.payload().size() - 1).index());
        return FetchStatus.fetched;
    }

    private void handleActions(Response<List<Action>> response) {
        for (Action actionToUse : response.payload()) {
            if (actionToUse.target().equals("alert")) {
                runAction(actionToUse, new ActionHandler() {
                    @Override
                    public void run(Action action) {
                        allAlerts.handleAction(action);
                    }
                });

            } else if (actionToUse.target().equals("child")) {
                runAction(actionToUse, new ActionHandler() {
                    @Override
                    public void run(Action action) {
                        allBeneficiaries.handleChildAction(action);
                    }
                });

            } else if (actionToUse.target().equals("mother")) {
                runAction(actionToUse, new ActionHandler() {
                    @Override
                    public void run(Action action) {
                        allBeneficiaries.handleMotherAction(action);
                    }
                });

            } else {
                runAction(actionToUse, new ActionHandler() {
                    @Override
                    public void run(Action action) {
                        allEligibleCouples.handleAction(action);
                    }
                });

            }
        }
    }

    private void runAction(Action action, ActionHandler actionHandler) {
        try {
            actionHandler.run(action);
        } catch (Exception e) {
            throw new RuntimeException("Failed to run action: " + new Gson().toJson(action), e);
        }
    }

    private abstract class ActionHandler {
        public void run(Action action) {
        }
    }
}
