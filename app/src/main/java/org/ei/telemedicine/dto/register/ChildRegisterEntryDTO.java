package org.ei.telemedicine.dto.register;

import java.util.Map;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class ChildRegisterEntryDTO
{
  @JsonProperty
  private String dob;
  @JsonProperty
  private String husbandName;
  @JsonProperty
  private Map<String, String> immunizations;
  @JsonProperty
  private String subCenter;
  @JsonProperty
  private String thayiCardNumber;
  @JsonProperty
  private String village;
  @JsonProperty
  private Map<String, String> vitaminADoses;
  @JsonProperty
  private String wifeDOB;
  @JsonProperty
  private String wifeName;
  
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
  
  public ChildRegisterEntryDTO withDOB(String paramString)
  {
    this.dob = paramString;
    return this;
  }
  
  public ChildRegisterEntryDTO withHusbandName(String paramString)
  {
    this.husbandName = paramString;
    return this;
  }
  
  public ChildRegisterEntryDTO withImmunizations(Map<String, String> paramMap)
  {
    this.immunizations = paramMap;
    return this;
  }
  
  public ChildRegisterEntryDTO withSubCenter(String paramString)
  {
    this.subCenter = paramString;
    return this;
  }
  
  public ChildRegisterEntryDTO withThayiCardNumber(String paramString)
  {
    this.thayiCardNumber = paramString;
    return this;
  }
  
  public ChildRegisterEntryDTO withVillage(String paramString)
  {
    this.village = paramString;
    return this;
  }
  
  public ChildRegisterEntryDTO withVitaminADoses(Map<String, String> paramMap)
  {
    this.vitaminADoses = paramMap;
    return this;
  }
  
  public ChildRegisterEntryDTO withWifeDOB(String paramString)
  {
    this.wifeDOB = paramString;
    return this;
  }
  
  public ChildRegisterEntryDTO withWifeName(String paramString)
  {
    this.wifeName = paramString;
    return this;
  }
}


/* Location:           C:\Users\appaji.r\Downloads\classes-dex2.jar
 * Qualified Name:     org.ei.drishti.dto.register.ChildRegisterEntryDTO
 * JD-Core Version:    0.7.0.1
 */