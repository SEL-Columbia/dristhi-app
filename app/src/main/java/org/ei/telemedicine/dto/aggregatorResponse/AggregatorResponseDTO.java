package org.ei.telemedicine.dto.aggregatorResponse;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class AggregatorResponseDTO
{
  @JsonProperty
  private String indicator;
  @JsonProperty
  private Integer nrhm_report_indicator_count;
  
  public AggregatorResponseDTO(String paramString, Integer paramInteger)
  {
    this.indicator = paramString;
    this.nrhm_report_indicator_count = paramInteger;
  }
  
  public Integer count()
  {
    return this.nrhm_report_indicator_count;
  }
  
  public boolean equals(Object paramObject)
  {
    return EqualsBuilder.reflectionEquals(this, paramObject, new String[0]);
  }
  
  public int hashCode()
  {
    return HashCodeBuilder.reflectionHashCode(this, new String[0]);
  }
  
  public String indicator()
  {
    return this.indicator;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this);
  }
}


/* Location:           C:\Users\appaji.r\Downloads\classes-dex2.jar
 * Qualified Name:     org.ei.drishti.dto.aggregatorResponse.AggregatorResponseDTO
 * JD-Core Version:    0.7.0.1
 */