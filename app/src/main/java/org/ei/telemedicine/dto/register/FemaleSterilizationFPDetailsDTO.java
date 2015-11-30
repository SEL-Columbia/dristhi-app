package org.ei.telemedicine.dto.register;

import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class FemaleSterilizationFPDetailsDTO
{
  @JsonProperty
  private List<String> followupVisitDates;
  @JsonProperty
  private String sterilizationDate;
  @JsonProperty
  private String typeOfSterilization;
  
  public FemaleSterilizationFPDetailsDTO(String paramString1, String paramString2, List<String> paramList)
  {
    this.typeOfSterilization = paramString1;
    this.sterilizationDate = paramString2;
    this.followupVisitDates = paramList;
  }
  
  public boolean equals(Object paramObject)
  {
    return EqualsBuilder.reflectionEquals(this, paramObject, new String[0]);
  }
  
  public int hashCode()
  {
    return HashCodeBuilder.reflectionHashCode(this, new String[0]);
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this);
  }
}


/* Location:           C:\Users\appaji.r\Downloads\classes-dex2.jar
 * Qualified Name:     org.ei.drishti.dto.register.FemaleSterilizationFPDetailsDTO
 * JD-Core Version:    0.7.0.1
 */