package org.ei.drishti.service;

import org.ei.drishti.dto.Action;
import org.ei.drishti.domain.FetchStatus;
import org.ei.drishti.domain.Response;
import org.ei.drishti.repository.AllAlerts;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.repository.AllSettings;

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
        for (Action action : response.payload()) {
            if (action.target().equals("alert")) {
                allAlerts.handleAction(action);
            } else if (action.target().equals("child")) {
                allBeneficiaries.handleChildAction(action);
            } else if (action.target().equals("mother")) {
                allBeneficiaries.handleMotherAction(action);
            } else {
                allEligibleCouples.handleAction(action);
            }
        }
    }
}
