package org.ei.telemedicine.dto.register;

import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class OCPFPDetailsDTO
{
  @JsonProperty
  private String fpAcceptanceDate;
  @JsonProperty
  private String lmpDate;
  @JsonProperty
  private List<Map<String, String>> refills;
  @JsonProperty
  private String uptResult;
  
  public OCPFPDetailsDTO(String paramString1, List<Map<String, String>> paramList, String paramString2, String paramString3)
  {
    this.fpAcceptanceDate = paramString1;
    this.refills = paramList;
    this.lmpDate = paramString2;
    this.uptResult = paramString3;
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

