package org.ei.telemedicine.dto.form;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class FormSubmissionDTO
{
  @JsonProperty
  private String anmId;
  @JsonProperty
  private String clientVersion;
  @JsonProperty
  private String entityId;
  @JsonProperty
  private String formDataDefinitionVersion;
  @JsonProperty
  private String formInstance;
  @JsonProperty
  private String formName;
  @JsonProperty
  private String instanceId;
  @JsonProperty
  private String serverVersion;
  
  public FormSubmissionDTO(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    this.anmId = paramString1;
    this.instanceId = paramString2;
    this.entityId = paramString3;
    this.formName = paramString4;
    this.formInstance = paramString5;
    this.clientVersion = paramString6;
    this.formDataDefinitionVersion = paramString7;
  }
  
  public String anmId()
  {
    return this.anmId;
  }
  
  public String clientVersion()
  {
    return this.clientVersion;
  }
  
  public String entityId()
  {
    return this.entityId;
  }
  
  public boolean equals(Object paramObject)
  {
    return EqualsBuilder.reflectionEquals(this, paramObject, new String[0]);
  }
  
  public String formDataDefinitionVersion()
  {
    return this.formDataDefinitionVersion;
  }
  
  public String formName()
  {
    return this.formName;
  }
  
  public int hashCode()
  {
    return HashCodeBuilder.reflectionHashCode(this, new String[0]);
  }
  
  public String instance()
  {
    return this.formInstance;
  }
  
  public String instanceId()
  {
    return this.instanceId;
  }
  
  public String serverVersion()
  {
    return this.serverVersion;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this);
  }
  
  public FormSubmissionDTO withServerVersion(String paramString)
  {
    this.serverVersion = paramString;
    return this;
  }
}


/* Location:           C:\Users\appaji.r\Downloads\classes-dex2.jar
 * Qualified Name:     org.ei.drishti.dto.form.FormSubmissionDTO
 * JD-Core Version:    0.7.0.1
 */