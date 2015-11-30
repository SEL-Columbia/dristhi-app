package org.ei.telemedicine.dto.register;

import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class CondomFPDetailsDTO
{
  @JsonProperty
  private String fpAcceptanceDate;
  @JsonProperty
  private List<Map<String, String>> refills;
  
  public CondomFPDetailsDTO(String paramString, List<Map<String, String>> paramList)
  {
    this.fpAcceptanceDate = paramString;
    this.refills = paramList;
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
 * Qualified Name:     org.ei.drishti.dto.register.CondomFPDetailsDTO
 * JD-Core Version:    0.7.0.1
 */