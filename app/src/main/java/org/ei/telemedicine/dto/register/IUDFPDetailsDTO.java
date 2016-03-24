package org.ei.telemedicine.dto.register;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class IUDFPDetailsDTO
{
  @JsonProperty
  private String fpAcceptanceDate;
  @JsonProperty
  private String iudPlace;
  @JsonProperty
  private String lmpDate;
  @JsonProperty
  private String uptResult;
  
  public IUDFPDetailsDTO(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.fpAcceptanceDate = paramString1;
    this.iudPlace = paramString2;
    this.lmpDate = paramString3;
    this.uptResult = paramString4;
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
 * Qualified Name:     org.ei.drishti.dto.register.IUDFPDetailsDTO
 * JD-Core Version:    0.7.0.1
 */