package org.ei.telemedicine.dto.register;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;
import org.ei.telemedicine.dto.LocationDTO;

public class ANMDetailDTO
{
  @JsonProperty
  private int ancCount;
  @JsonProperty
  private int childCount;
  @JsonProperty
  private int ecCount;
  @JsonProperty
  private int fpCount;
  @JsonProperty
  private String identifier;
  @JsonProperty
  private LocationDTO location;
  @JsonProperty
  private String name;
  @JsonProperty
  private int pncCount;
  
  public ANMDetailDTO(String paramString1, String paramString2, LocationDTO paramLocationDTO, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    this.identifier = paramString1;
    this.name = paramString2;
    this.location = paramLocationDTO;
    this.ecCount = paramInt1;
    this.fpCount = paramInt2;
    this.ancCount = paramInt3;
    this.pncCount = paramInt4;
    this.childCount = paramInt5;
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
 * Qualified Name:     org.ei.drishti.dto.register.ANMDetailDTO
 * JD-Core Version:    0.7.0.1
 */