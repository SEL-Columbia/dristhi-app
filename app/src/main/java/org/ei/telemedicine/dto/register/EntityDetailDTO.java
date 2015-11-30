package org.ei.telemedicine.dto.register;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class EntityDetailDTO
{
  @JsonProperty
  private String anmIdentifier;
  @JsonProperty
  private String ecNumber;
  @JsonProperty
  private String entityId;
  @JsonProperty
  private String entityType;
  @JsonProperty
  private String thayiCardNumber;
  
  public String anmIdentifier()
  {
    return this.anmIdentifier;
  }
  
  public String ecNumber()
  {
    return this.ecNumber;
  }
  
  public String entityID()
  {
    return this.entityId;
  }
  
  public String entityType()
  {
    return this.entityType;
  }
  
  public boolean equals(Object paramObject)
  {
    return EqualsBuilder.reflectionEquals(this, paramObject, new String[0]);
  }
  
  public int hashCode()
  {
    return HashCodeBuilder.reflectionHashCode(this, new String[0]);
  }
  
  public String thayiCardNumber()
  {
    return this.thayiCardNumber;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this);
  }
  
  public EntityDetailDTO withANMIdentifier(String paramString)
  {
    this.anmIdentifier = paramString;
    return this;
  }
  
  public EntityDetailDTO withECNumber(String paramString)
  {
    this.ecNumber = paramString;
    return this;
  }
  
  public EntityDetailDTO withEntityID(String paramString)
  {
    this.entityId = paramString;
    return this;
  }
  
  public EntityDetailDTO withEntityType(String paramString)
  {
    this.entityType = paramString;
    return this;
  }
  
  public EntityDetailDTO withThayiCardNumber(String paramString)
  {
    this.thayiCardNumber = paramString;
    return this;
  }
}


/* Location:           C:\Users\appaji.r\Downloads\classes-dex2.jar
 * Qualified Name:     org.ei.drishti.dto.register.EntityDetailDTO
 * JD-Core Version:    0.7.0.1
 */