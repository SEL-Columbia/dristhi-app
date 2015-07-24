package org.ei.telemedicine.domain;

public enum FetchStatus implements Displayable {
    fetched("Sync with Central data completed successfully."), nothingFetched("Already up to date."), fetchedFailed("Sync with Central data failed. Please try again.");
    private String displayValue;

    private FetchStatus(String displayValue) {
        this.displayValue = displayValue;
    }

    public String displayValue() {
        return displayValue;
    }
}
