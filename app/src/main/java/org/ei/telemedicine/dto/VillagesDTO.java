package org.ei.telemedicine.dto;

import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class VillagesDTO
{
  @JsonProperty
  private String district;
  @JsonProperty
  private String phcIdentifier;
  @JsonProperty
  private String phcName;
  @JsonProperty
  private String subCenter;
  @JsonProperty
  private List<String> villages;
  
  public VillagesDTO(String paramString1, String paramString2, String paramString3, String paramString4, List<String> paramList)
  {
    this.district = paramString1;
    this.phcName = paramString2;
    this.phcIdentifier = paramString3;
    this.subCenter = paramString4;
    this.villages = paramList;
  }
  
  public boolean equals(Object paramObject)
  {
    return EqualsBuilder.reflectionEquals(this, paramObject, new String[0]);
  }
  
  public int hashCode()
  {
    return HashCodeBuilder.reflectionHashCode(this, new String[0]);
  }
  
  public String phcIdentifier()
  {
    return this.phcIdentifier;
  }
  
  public String phcName()
  {
    return this.phcName;
  }
  
  public String subCenter()
  {
    return this.subCenter;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this);
  }
  
  public List<String> villages()
  {
    return this.villages;
  }
}


/* Location:           C:\Users\appaji.r\Downloads\classes-dex2.jar
 * Qualified Name:     org.ei.drishti.dto.VillagesDTO
 * JD-Core Version:    0.7.0.1
 */