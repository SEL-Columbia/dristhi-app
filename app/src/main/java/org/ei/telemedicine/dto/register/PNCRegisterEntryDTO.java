package org.ei.telemedicine.dto.register;

import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class PNCRegisterEntryDTO
{
  @JsonProperty
  private String address;
  @JsonProperty
  private List<Map<String, String>> childrenDetails;
  @JsonProperty
  private String dateOfDelivery;
  @JsonProperty
  private String deliveryComplications;
  @JsonProperty
  private String dischargeDate;
  @JsonProperty
  private String fpMethodDate;
  @JsonProperty
  private String fpMethodName;
  @JsonProperty
  private String husbandName;
  @JsonProperty
  private String placeOfDelivery;
  @JsonProperty
  private List<PNCVisitDTO> pncVisits;
  @JsonProperty
  private String registrationDate;
  @JsonProperty
  private String thayiCardNumber;
  @JsonProperty
  private String typeOfDelivery;
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
  
  public PNCRegisterEntryDTO withAddress(String paramString)
  {
    this.address = paramString;
    return this;
  }
  
  public PNCRegisterEntryDTO withChildrenDetails(List<Map<String, String>> paramList)
  {
    this.childrenDetails = paramList;
    return this;
  }
  
  public PNCRegisterEntryDTO withDateOfDelivery(String paramString)
  {
    this.dateOfDelivery = paramString;
    return this;
  }
  
  public PNCRegisterEntryDTO withDeliveryComplications(String paramString)
  {
    this.deliveryComplications = paramString;
    return this;
  }
  
  public PNCRegisterEntryDTO withDischargeDate(String paramString)
  {
    this.dischargeDate = paramString;
    return this;
  }
  
  public PNCRegisterEntryDTO withFPMethodDate(String paramString)
  {
    this.fpMethodDate = paramString;
    return this;
  }
  
  public PNCRegisterEntryDTO withFPMethodName(String paramString)
  {
    this.fpMethodName = paramString;
    return this;
  }
  
  public PNCRegisterEntryDTO withHusbandName(String paramString)
  {
    this.husbandName = paramString;
    return this;
  }
  
  public PNCRegisterEntryDTO withPNCVisits(List<PNCVisitDTO> paramList)
  {
    this.pncVisits = paramList;
    return this;
  }
  
  public PNCRegisterEntryDTO withPlaceOfDelivery(String paramString)
  {
    this.placeOfDelivery = paramString;
    return this;
  }
  
  public PNCRegisterEntryDTO withRegistrationDate(String paramString)
  {
    this.registrationDate = paramString;
    return this;
  }
  
  public PNCRegisterEntryDTO withThayiCardNumber(String paramString)
  {
    this.thayiCardNumber = paramString;
    return this;
  }
  
  public PNCRegisterEntryDTO withTypeOfDelivery(String paramString)
  {
    this.typeOfDelivery = paramString;
    return this;
  }
  
  public PNCRegisterEntryDTO withWifeDOB(String paramString)
  {
    this.wifeDOB = paramString;
    return this;
  }
  
  public PNCRegisterEntryDTO withWifeName(String paramString)
  {
    this.wifeName = paramString;
    return this;
  }
}


/* Location:           C:\Users\appaji.r\Downloads\classes-dex2.jar
 * Qualified Name:     org.ei.drishti.dto.register.PNCRegisterEntryDTO
 * JD-Core Version:    0.7.0.1
 */