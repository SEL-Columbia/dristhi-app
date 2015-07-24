package org.ei.telemedicine.dto.register;

import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class ANCRegisterEntryDTO
{
  @JsonProperty
  private String aadharCardNumber;
  @JsonProperty
  private String address;
  @JsonProperty
  private List<Map<String, String>> ancInvestigations;
  @JsonProperty
  private String ancNumber;
  @JsonProperty
  private List<Map<String, String>> ancVisits;
  @JsonProperty
  private String bloodGroup;
  @JsonProperty
  private String bplCardNumber;
  @JsonProperty
  private String caste;
  @JsonProperty
  private String ecNumber;
  @JsonProperty
  private String economicStatus;
  @JsonProperty
  private String edd;
  @JsonProperty
  private String gravida;
  @JsonProperty
  private List<Map<String, String>> hbTests;
  @JsonProperty
  private String height;
  @JsonProperty
  private String husbandEducationLevel;
  @JsonProperty
  private String husbandName;
  @JsonProperty
  private List<Map<String, String>> ifaTablets;
  @JsonProperty
  private String isHRP;
  @JsonProperty
  private String jsyBeneficiary;
  @JsonProperty
  private String lmp;
  @JsonProperty
  private String numberOfAbortions;
  @JsonProperty
  private String numberOfLivingChildren;
  @JsonProperty
  private String numberOfStillBirths;
  @JsonProperty
  private String parity;
  @JsonProperty
  private String phoneNumber;
  @JsonProperty
  private String registrationDate;
  @JsonProperty
  private String religion;
  @JsonProperty
  private String thayiCardNumber;
  @JsonProperty
  private List<Map<String, String>> ttDoses;
  @JsonProperty
  private String wifeDOB;
  @JsonProperty
  private String wifeEducationLevel;
  @JsonProperty
  private String wifeName;
  @JsonProperty
  private String youngestChildDOB;
  
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
  
  public ANCRegisterEntryDTO withANCInvestigations(List<Map<String, String>> paramList)
  {
    this.ancInvestigations = paramList;
    return this;
  }
  
  public ANCRegisterEntryDTO withANCNumber(String paramString)
  {
    this.ancNumber = paramString;
    return this;
  }
  
  public ANCRegisterEntryDTO withANCVisits(List<Map<String, String>> paramList)
  {
    this.ancVisits = paramList;
    return this;
  }
  
  public ANCRegisterEntryDTO withAadharCardNumber(String paramString)
  {
    this.aadharCardNumber = paramString;
    return this;
  }
  
  public ANCRegisterEntryDTO withAddress(String paramString)
  {
    this.address = paramString;
    return this;
  }
  
  public ANCRegisterEntryDTO withBPLCardNumber(String paramString)
  {
    this.bplCardNumber = paramString;
    return this;
  }
  
  public ANCRegisterEntryDTO withBloodGroup(String paramString)
  {
    this.bloodGroup = paramString;
    return this;
  }
  
  public ANCRegisterEntryDTO withCaste(String paramString)
  {
    this.caste = paramString;
    return this;
  }
  
  public ANCRegisterEntryDTO withECNumber(String paramString)
  {
    this.ecNumber = paramString;
    return this;
  }
  
  public ANCRegisterEntryDTO withEDD(String paramString)
  {
    this.edd = paramString;
    return this;
  }
  
  public ANCRegisterEntryDTO withEconomicStatus(String paramString)
  {
    this.economicStatus = paramString;
    return this;
  }
  
  public ANCRegisterEntryDTO withGravida(String paramString)
  {
    this.gravida = paramString;
    return this;
  }
  
  public ANCRegisterEntryDTO withHBTests(List<Map<String, String>> paramList)
  {
    this.hbTests = paramList;
    return this;
  }
  
  public ANCRegisterEntryDTO withHeight(String paramString)
  {
    this.height = paramString;
    return this;
  }
  
  public ANCRegisterEntryDTO withHusbandEducationLevel(String paramString)
  {
    this.husbandEducationLevel = paramString;
    return this;
  }
  
  public ANCRegisterEntryDTO withHusbandName(String paramString)
  {
    this.husbandName = paramString;
    return this;
  }
  
  public ANCRegisterEntryDTO withIFATablets(List<Map<String, String>> paramList)
  {
    this.ifaTablets = paramList;
    return this;
  }
  
  public ANCRegisterEntryDTO withIsHRP(String paramString)
  {
    this.isHRP = paramString;
    return this;
  }
  
  public ANCRegisterEntryDTO withJSYBeneficiary(String paramString)
  {
    this.jsyBeneficiary = paramString;
    return this;
  }
  
  public ANCRegisterEntryDTO withLMP(String paramString)
  {
    this.lmp = paramString;
    return this;
  }
  
  public ANCRegisterEntryDTO withNumberOfAbortions(String paramString)
  {
    this.numberOfAbortions = paramString;
    return this;
  }
  
  public ANCRegisterEntryDTO withNumberOfLivingChildren(String paramString)
  {
    this.numberOfLivingChildren = paramString;
    return this;
  }
  
  public ANCRegisterEntryDTO withNumberOfStillBirths(String paramString)
  {
    this.numberOfStillBirths = paramString;
    return this;
  }
  
  public ANCRegisterEntryDTO withParity(String paramString)
  {
    this.parity = paramString;
    return this;
  }
  
  public ANCRegisterEntryDTO withPhoneNumber(String paramString)
  {
    this.phoneNumber = paramString;
    return this;
  }
  
  public ANCRegisterEntryDTO withRegistrationDate(String paramString)
  {
    this.registrationDate = paramString;
    return this;
  }
  
  public ANCRegisterEntryDTO withReligion(String paramString)
  {
    this.religion = paramString;
    return this;
  }
  
  public ANCRegisterEntryDTO withTTDoses(List<Map<String, String>> paramList)
  {
    this.ttDoses = paramList;
    return this;
  }
  
  public ANCRegisterEntryDTO withThayiCardNumber(String paramString)
  {
    this.thayiCardNumber = paramString;
    return this;
  }
  
  public ANCRegisterEntryDTO withWifeDOB(String paramString)
  {
    this.wifeDOB = paramString;
    return this;
  }
  
  public ANCRegisterEntryDTO withWifeEducationLevel(String paramString)
  {
    this.wifeEducationLevel = paramString;
    return this;
  }
  
  public ANCRegisterEntryDTO withWifeName(String paramString)
  {
    this.wifeName = paramString;
    return this;
  }
  
  public ANCRegisterEntryDTO withYoungestChildDOB(String paramString)
  {
    this.youngestChildDOB = paramString;
    return this;
  }
}


/* Location:           C:\Users\appaji.r\Downloads\classes-dex2.jar
 * Qualified Name:     org.ei.drishti.dto.register.ANCRegisterEntryDTO
 * JD-Core Version:    0.7.0.1
 */