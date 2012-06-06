package org.ei.drishti.repository;

import org.ei.drishti.domain.Action;

public class AllPregnancies {
    private BeneficiaryRepository repository;

    public AllPregnancies(BeneficiaryRepository repository) {
        this.repository = repository;
    }

    public void handleAction(Action action) {
        repository.add(action);
    }
}
