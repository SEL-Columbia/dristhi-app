package org.ei.drishti.domain;

public enum FetchStatus implements Displayable {
    fetched("Update Successful."), nothingFetched("Already up to date."), fetchedFailed("Update Failed. Please try again.");
    private String displayValue;

    private FetchStatus(String displayValue) {
        this.displayValue = displayValue;
    }

    public String displayValue() {
        return displayValue;
    }
}
