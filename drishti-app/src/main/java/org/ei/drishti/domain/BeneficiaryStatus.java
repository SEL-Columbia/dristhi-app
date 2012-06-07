package org.ei.drishti.domain;

public enum BeneficiaryStatus {
    PREGNANT("pregnant"), ABORTED("abortion"), BORN("born"), DELIVERED("delivery"), DEAD("death");
    private String value;

    BeneficiaryStatus(String value) {
        this.value = value;
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
}
