package org.ei.telemedicine.dto.register;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class ECRegisterEntryDTO
{
  @JsonProperty
  private String caste;
  @JsonProperty
  private String currentFPMethod;
  @JsonProperty
  private String currentFPMethodStartDate;
  @JsonProperty
  private String ecNumber;
  @JsonProperty
  private String economicStatus;
  @JsonProperty
  private String gravida;
  @JsonProperty
  private String headOfHousehold;
  @JsonProperty
  private String householdAddress;
  @JsonProperty
  private String householdNumber;
  @JsonProperty
  private String husbandAge;
  @JsonProperty
  private String husbandEducationLevel;
  @JsonProperty
  private String husbandName;
  @JsonProperty
  private String isPregnant;
  @JsonProperty
  private String numberOfAbortions;
  @JsonProperty
  private String numberOfLivingChildren;
  @JsonProperty
  private String numberOfLivingFemaleChildren;
  @JsonProperty
  private String numberOfLivingMaleChildren;
  @JsonProperty
  private String numberOfStillBirths;
  @JsonProperty
  private String parity;
  @JsonProperty
  private String phc;
  @JsonProperty
  private String registrationDate;
  @JsonProperty
  private String religion;
  @JsonProperty
  private String subCenter;
  @JsonProperty
  private String village;
  @JsonProperty
  private String wifeAge;
  @JsonProperty
  private String wifeEducationLevel;
  @JsonProperty
  private String wifeName;
  @JsonProperty
  private String youngestChildAge;
  
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
  
  public ECRegisterEntryDTO withCaste(String paramString)
  {
    this.caste = paramString;
    return this;
  }
  
  public ECRegisterEntryDTO withCurrentFPMethod(String paramString)
  {
    this.currentFPMethod = paramString;
    return this;
  }
  
  public ECRegisterEntryDTO withCurrentFPMethodStartDate(String paramString)
  {
    this.currentFPMethodStartDate = paramString;
    return this;
  }
  
  public ECRegisterEntryDTO withECNumber(String paramString)
  {
    this.ecNumber = paramString;
    return this;
  }
  
  public ECRegisterEntryDTO withEconomicStatus(String paramString)
  {
    this.economicStatus = paramString;
    return this;
  }
  
  public ECRegisterEntryDTO withGravida(String paramString)
  {
    this.gravida = paramString;
    return this;
  }
  
  public ECRegisterEntryDTO withHeadOfHousehold(String paramString)
  {
    this.headOfHousehold = paramString;
    return this;
  }
  
  public ECRegisterEntryDTO withHouseholdAddress(String paramString)
  {
    this.householdAddress = paramString;
    return this;
  }
  
  public ECRegisterEntryDTO withHouseholdNumber(String paramString)
  {
    this.householdNumber = paramString;
    return this;
  }
  
  public ECRegisterEntryDTO withHusbandAge(String paramString)
  {
    this.husbandAge = paramString;
    return this;
  }
  
  public ECRegisterEntryDTO withHusbandEducationLevel(String paramString)
  {
    this.husbandEducationLevel = paramString;
    return this;
  }
  
  public ECRegisterEntryDTO withHusbandName(String paramString)
  {
    this.husbandName = paramString;
    return this;
  }
  
  public ECRegisterEntryDTO withNumberOfAbortions(String paramString)
  {
    this.numberOfAbortions = paramString;
    return this;
  }
  
  public ECRegisterEntryDTO withNumberOfLivingChildren(String paramString)
  {
    this.numberOfLivingChildren = paramString;
    return this;
  }
  
  public ECRegisterEntryDTO withNumberOfLivingFemaleChildren(String paramString)
  {
    this.numberOfLivingFemaleChildren = paramString;
    return this;
  }
  
  public ECRegisterEntryDTO withNumberOfLivingMaleChildren(String paramString)
  {
    this.numberOfLivingMaleChildren = paramString;
    return this;
  }
  
  public ECRegisterEntryDTO withNumberOfStillBirths(String paramString)
  {
    this.numberOfStillBirths = paramString;
    return this;
  }
  
  public ECRegisterEntryDTO withPHC(String paramString)
  {
    this.phc = paramString;
    return this;
  }
  
  public ECRegisterEntryDTO withParity(String paramString)
  {
    this.parity = paramString;
    return this;
  }
  
  public ECRegisterEntryDTO withPregnancyStatus(String paramString)
  {
    this.isPregnant = paramString;
    return this;
  }
  
  public ECRegisterEntryDTO withRegistrationDate(String paramString)
  {
    this.registrationDate = paramString;
    return this;
  }
  
  public ECRegisterEntryDTO withReligion(String paramString)
  {
    this.religion = paramString;
    return this;
  }
  
  public ECRegisterEntryDTO withSubCenter(String paramString)
  {
    this.subCenter = paramString;
    return this;
  }
  
  public ECRegisterEntryDTO withVillage(String paramString)
  {
    this.village = paramString;
    return this;
  }
  
  public ECRegisterEntryDTO withWifeAge(String paramString)
  {
    this.wifeAge = paramString;
    return this;
  }
  
  public ECRegisterEntryDTO withWifeEducationLevel(String paramString)
  {
    this.wifeEducationLevel = paramString;
    return this;
  }
  
  public ECRegisterEntryDTO withWifeName(String paramString)
  {
    this.wifeName = paramString;
    return this;
  }
  
  public ECRegisterEntryDTO withYoungestChildAge(String paramString)
  {
    this.youngestChildAge = paramString;
    return this;
  }
}

