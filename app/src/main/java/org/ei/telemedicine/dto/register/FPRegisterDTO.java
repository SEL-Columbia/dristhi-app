package org.ei.telemedicine.dto.register;

import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class FPRegisterDTO
{
  @JsonProperty
  private List<CondomRegisterEntryDTO> condomRegisterEntries;
  @JsonProperty
  private List<FemaleSterilizationRegisterEntryDTO> femaleSterilizationRegisterEntries;
  @JsonProperty
  private List<IUDRegisterEntryDTO> iudRegisterEntries;
  @JsonProperty
  private List<MaleSterilizationRegisterEntryDTO> maleSterilizationRegisterEntries;
  @JsonProperty
  private List<OCPRegisterEntryDTO> ocpRegisterEntries;
  @JsonProperty
  private Integer reportingYear;
  
  public FPRegisterDTO(List<IUDRegisterEntryDTO> paramList, List<CondomRegisterEntryDTO> paramList1, List<OCPRegisterEntryDTO> paramList2, List<MaleSterilizationRegisterEntryDTO> paramList3, List<FemaleSterilizationRegisterEntryDTO> paramList4, Integer paramInteger)
  {
    this.iudRegisterEntries = paramList;
    this.condomRegisterEntries = paramList1;
    this.ocpRegisterEntries = paramList2;
    this.maleSterilizationRegisterEntries = paramList3;
    this.femaleSterilizationRegisterEntries = paramList4;
    this.reportingYear = paramInteger;
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
 * Qualified Name:     org.ei.drishti.dto.register.FPRegisterDTO
 * JD-Core Version:    0.7.0.1
 */