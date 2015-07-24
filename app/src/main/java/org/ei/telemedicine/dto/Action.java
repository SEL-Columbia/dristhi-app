package org.ei.telemedicine.dto;

import java.util.Map;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class Action
{
  @JsonProperty
  private String actionTarget;
  @JsonProperty
  private String actionType;
  @JsonProperty
  private String caseID;
  @JsonProperty
  private Map<String, String> data;
  @JsonProperty
  private Map<String, String> details;
  @JsonProperty
  private Boolean isActionActive;
  @JsonProperty
  private String timeStamp;
  
  public Action() {}
  
  public Action(String paramString1, String paramString2, String paramString3, Map<String, String> paramMap1, String paramString4, Boolean paramBoolean, Map<String, String> paramMap2)
  {
    this.caseID = paramString1;
    this.data = paramMap1;
    this.timeStamp = paramString4;
    this.actionTarget = paramString2;
    this.actionType = paramString3;
    this.isActionActive = paramBoolean;
    this.details = paramMap2;
  }
  
  public String caseID()
  {
    return this.caseID;
  }
  
  public Map<String, String> data()
  {
    return this.data;
  }
  
  public Map<String, String> details()
  {
    return this.details;
  }
  
  public boolean equals(Object paramObject)
  {
    return EqualsBuilder.reflectionEquals(this, paramObject, new String[0]);
  }
  
  public String get(String paramString)
  {
    return (String)this.data.get(paramString);
  }
  
  public int hashCode()
  {
    return HashCodeBuilder.reflectionHashCode(this, new String[0]);
  }
  
  public String index()
  {
    return this.timeStamp;
  }
  
  public Boolean isActionActive()
  {
    return this.isActionActive;
  }
  
  public String target()
  {
    return this.actionTarget;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this);
  }
  
  public String type()
  {
    return this.actionType;
  }
}


/* Location:           C:\Users\appaji.r\Downloads\classes-dex2.jar
 * Qualified Name:     org.ei.drishti.dto.Action
 * JD-Core Version:    0.7.0.1
 */