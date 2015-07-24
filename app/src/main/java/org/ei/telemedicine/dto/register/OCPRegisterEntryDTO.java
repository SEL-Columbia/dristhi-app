package org.ei.telemedicine.dto.register;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class OCPRegisterEntryDTO
{
  @JsonProperty
  private String caste;
  @JsonProperty
  private String ecNumber;
  @JsonProperty
  private OCPFPDetailsDTO fpDetails;
  @JsonProperty
  private String husbandEducationLevel;
  @JsonProperty
  private String husbandName;
  @JsonProperty
  private String lmpDate;
  @JsonProperty
  private String numberOfLivingFemaleChildren;
  @JsonProperty
  private String numberOfLivingMaleChildren;
  @JsonProperty
  private String religion;
  @JsonProperty
  private String subCenter;
  @JsonProperty
  private String uptResult;
  @JsonProperty
  private String village;
  @JsonProperty
  private String wifeAge;
  @JsonProperty
  private String wifeEducationLevel;
  @JsonProperty
  private String wifeName;
  
  public String caste()
  {
    return this.caste;
  }
  
  public String ecNumber()
  {
    return this.ecNumber;
  }
  
  public boolean equals(Object paramObject)
  {
    return EqualsBuilder.reflectionEquals(this, paramObject, new String[0]);
  }
  
  public OCPFPDetailsDTO fpDetails()
  {
    return this.fpDetails;
  }
  
  public int hashCode()
  {
    return HashCodeBuilder.reflectionHashCode(this, new String[0]);
  }
  
  public String husbandEducationLevel()
  {
    return this.husbandEducationLevel;
  }
  
  public String husbandName()
  {
    return this.husbandName;
  }
  
  public String lmpDate()
  {
    return this.lmpDate;
  }
  
  public String numberOfLivingFemaleChildren()
  {
    return this.numberOfLivingFemaleChildren;
  }
  
  public String numberOfLivingMaleChildren()
  {
    return this.numberOfLivingMaleChildren;
  }
  
  public String religion()
  {
    return this.religion;
  }
  
  public String subCenter()
  {
    return this.subCenter;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this);
  }
  
  public String uptResult()
  {
    return this.uptResult;
  }
  
  public String village()
  {
    return this.village;
  }
  
  public String wifeAge()
  {
    return this.wifeAge;
  }
  
  public String wifeEducationLevel()
  {
    return this.wifeEducationLevel;
  }
  
  public String wifeName()
  {
    return this.wifeName;
  }
  
  public OCPRegisterEntryDTO withCaste(String paramString)
  {
    this.caste = paramString;
    return this;
  }
  
  public OCPRegisterEntryDTO withEcNumber(String paramString)
  {
    this.ecNumber = paramString;
    return this;
  }
  
  public OCPRegisterEntryDTO withFpDetails(OCPFPDetailsDTO paramOCPFPDetailsDTO)
  {
    this.fpDetails = paramOCPFPDetailsDTO;
    return this;
  }
  
  public OCPRegisterEntryDTO withHusbandEducationLevel(String paramString)
  {
    this.husbandEducationLevel = paramString;
    return this;
  }
  
  public OCPRegisterEntryDTO withHusbandName(String paramString)
  {
    this.husbandName = paramString;
    return this;
  }
  
  public OCPRegisterEntryDTO withLmpDate(String paramString)
  {
    this.lmpDate = paramString;
    return this;
  }
  
  public OCPRegisterEntryDTO withNumberOfLivingFemaleChildren(String paramString)
  {
    this.numberOfLivingFemaleChildren = paramString;
    return this;
  }
  
  public OCPRegisterEntryDTO withNumberOfLivingMaleChildren(String paramString)
  {
    this.numberOfLivingMaleChildren = paramString;
    return this;
  }
  
  public OCPRegisterEntryDTO withReligion(String paramString)
  {
    this.religion = paramString;
    return this;
  }
  
  public OCPRegisterEntryDTO withSubCenter(String paramString)
  {
    this.subCenter = paramString;
    return this;
  }
  
  public OCPRegisterEntryDTO withUptResult(String paramString)
  {
    this.uptResult = paramString;
    return this;
  }
  
  public OCPRegisterEntryDTO withVillage(String paramString)
  {
    this.village = paramString;
    return this;
  }
  
  public OCPRegisterEntryDTO withWifeAge(String paramString)
  {
    this.wifeAge = paramString;
    return this;
  }
  
  public OCPRegisterEntryDTO withWifeEducationLevel(String paramString)
  {
    this.wifeEducationLevel = paramString;
    return this;
  }
  
  public OCPRegisterEntryDTO withWifeName(String paramString)
  {
    this.wifeName = paramString;
    return this;
  }
}


/* Location:           C:\Users\appaji.r\Downloads\classes-dex2.jar
 * Qualified Name:     org.ei.drishti.dto.register.OCPRegisterEntryDTO
 * JD-Core Version:    0.7.0.1
 */