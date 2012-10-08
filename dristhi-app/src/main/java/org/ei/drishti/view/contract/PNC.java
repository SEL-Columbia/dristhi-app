package org.ei.drishti.view.contract;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class PNC {
    private String caseId;
    private String womanName;
    private String husbandName;
    private String thaayiCardNumber;
    private String villageName;
    private boolean isHighRisk;

    public PNC(String caseId, String thaayiCardNumber, String womanName, String husbandName, String villageName, boolean highRisk) {
        this.caseId = caseId;
        this.womanName = womanName;
        this.husbandName = husbandName;
        this.thaayiCardNumber = thaayiCardNumber;
        this.villageName = villageName;
        isHighRisk = highRisk;
    }

    public String womanName() {
        return womanName;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
