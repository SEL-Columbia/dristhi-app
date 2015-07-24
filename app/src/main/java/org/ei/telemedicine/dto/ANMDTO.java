package org.ei.telemedicine.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class ANMDTO
{
  @JsonProperty
  private String identifier;
  @JsonProperty
  private LocationDTO location;
  @JsonProperty
  private String name;
  
  public ANMDTO(String paramString1, String paramString2, LocationDTO paramLocationDTO)
  {
    this.identifier = paramString1;
    this.name = paramString2;
    this.location = paramLocationDTO;
  }
  
  public boolean equals(Object paramObject)
  {
    return EqualsBuilder.reflectionEquals(this, paramObject, new String[0]);
  }
  
  public int hashCode()
  {
    return HashCodeBuilder.reflectionHashCode(this, new String[0]);
  }
  
  public String identifier()
  {
    return this.identifier;
  }
  
  public LocationDTO location()
  {
    return this.location;
  }
  
  public String name()
  {
    return this.name;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this);
  }
}


/* Location:           C:\Users\appaji.r\Downloads\classes-dex2.jar
 * Qualified Name:     org.ei.drishti.dto.ANMDTO
 * JD-Core Version:    0.7.0.1
 */