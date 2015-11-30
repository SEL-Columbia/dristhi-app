package org.ei.telemedicine.dto.register;

import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class PNCVisitDTO
{
  @JsonProperty
  private String abdominalProblems;
  @JsonProperty
  private String breastProblems;
  @JsonProperty
  private List<Map<String, String>> childrenDetails;
  @JsonProperty
  private String date;
  @JsonProperty
  private String difficulties;
  @JsonProperty
  private String person;
  @JsonProperty
  private String place;
  @JsonProperty
  private String urinalProblems;
  @JsonProperty
  private String vaginalProblems;
  
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
  
  public PNCVisitDTO withAbdominalProblems(String paramString)
  {
    this.abdominalProblems = paramString;
    return this;
  }
  
  public PNCVisitDTO withBreastProblems(String paramString)
  {
    this.breastProblems = paramString;
    return this;
  }
  
  public PNCVisitDTO withChildrenDetails(List<Map<String, String>> paramList)
  {
    this.childrenDetails = paramList;
    return this;
  }
  
  public PNCVisitDTO withDate(String paramString)
  {
    this.date = paramString;
    return this;
  }
  
  public PNCVisitDTO withDifficulties(String paramString)
  {
    this.difficulties = paramString;
    return this;
  }
  
  public PNCVisitDTO withPerson(String paramString)
  {
    this.person = paramString;
    return this;
  }
  
  public PNCVisitDTO withPlace(String paramString)
  {
    this.place = paramString;
    return this;
  }
  
  public PNCVisitDTO withUrinalProblems(String paramString)
  {
    this.urinalProblems = paramString;
    return this;
  }
  
  public PNCVisitDTO withVaginalProblems(String paramString)
  {
    this.vaginalProblems = paramString;
    return this;
  }
}


/* Location:           C:\Users\appaji.r\Downloads\classes-dex2.jar
 * Qualified Name:     org.ei.drishti.dto.register.PNCVisitDTO
 * JD-Core Version:    0.7.0.1
 */