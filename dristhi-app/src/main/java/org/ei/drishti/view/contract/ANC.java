package org.ei.drishti.view.contract;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ANC {
    private  String caseId;
    private String womanName;
    private String husbandName;
    private String villageName;
    private String thaayiCardNumber;
    private boolean isHighRisk;

    public ANC(String caseId, String thaayiCardNumber, String womanName, String husbandName, String villageName, boolean highRisk) {
        this.caseId = caseId;
        this.womanName = womanName;
        this.husbandName = husbandName;
        this.villageName = villageName;
        this.thaayiCardNumber = thaayiCardNumber;
        this.isHighRisk = highRisk;
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
