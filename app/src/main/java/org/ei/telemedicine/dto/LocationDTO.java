package org.ei.telemedicine.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class LocationDTO
{
  @JsonProperty
  private String district;
  @JsonProperty
  private String phc;
  @JsonProperty
  private String state;
  @JsonProperty
  private String sub_center;
  @JsonProperty
  private String taluka;
  
  public LocationDTO(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    this.state = paramString5;
    this.district = paramString4;
    this.taluka = paramString3;
    this.phc = paramString2;
    this.sub_center = paramString1;
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
 * Qualified Name:     org.ei.drishti.dto.LocationDTO
 * JD-Core Version:    0.7.0.1
 */