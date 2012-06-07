package org.ei.drishti.domain;

public enum BeneficiaryStatus {
    PREGNANT("pregnant", "Mother is pregnant"), ABORTED("abortion", "Baby aborted"), BORN("born", "Baby born"), DELIVERED("delivery", "Delivered"), DEAD("death", "Baby dead");
    private String value;
    private String description;

    BeneficiaryStatus(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public static BeneficiaryStatus from(String value) {
        for (BeneficiaryStatus status : values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new RuntimeException("No status matched: " + value);
    }

    public String value() {
        return value;
    }

    public String description() {
        return description;
    }
}
