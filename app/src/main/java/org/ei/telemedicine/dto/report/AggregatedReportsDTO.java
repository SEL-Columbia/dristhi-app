package org.ei.telemedicine.dto.report;

import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;
import org.ei.telemedicine.dto.LocationDTO;

public class AggregatedReportsDTO
{
  @JsonProperty
  private Map<String, Integer> ind;
  @JsonProperty
  private LocationDTO loc;
  
  public AggregatedReportsDTO(Map<String, Integer> paramMap, LocationDTO paramLocationDTO)
  {
    this.ind = paramMap;
    this.loc = paramLocationDTO;
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
 * Qualified Name:     org.ei.drishti.dto.report.AggregatedReportsDTO
 * JD-Core Version:    0.7.0.1
 */