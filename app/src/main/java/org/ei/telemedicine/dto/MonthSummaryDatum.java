package org.ei.telemedicine.dto;

import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class MonthSummaryDatum
{
  private String aggregatedProgress;
  private String currentProgress;
  private List<String> externalIDs;
  private String month;
  private String year;
  
  public MonthSummaryDatum() {}
  
  public MonthSummaryDatum(String paramString1, String paramString2, String paramString3, String paramString4, List<String> paramList)
  {
    this.month = paramString1;
    this.year = paramString2;
    this.currentProgress = paramString3;
    this.aggregatedProgress = paramString4;
    this.externalIDs = paramList;
  }
  
  public String aggregatedProgress()
  {
    return this.aggregatedProgress;
  }
  
  public String currentProgress()
  {
    return this.currentProgress;
  }
  
  public boolean equals(Object paramObject)
  {
    return EqualsBuilder.reflectionEquals(this, paramObject, new String[0]);
  }
  
  public List<String> externalIDs()
  {
    return this.externalIDs;
  }
  
  public int hashCode()
  {
    return HashCodeBuilder.reflectionHashCode(this, new String[0]);
  }
  
  public String month()
  {
    return this.month;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this);
  }
  
  public String year()
  {
    return this.year;
  }
}


/* Location:           C:\Users\appaji.r\Downloads\classes-dex2.jar
 * Qualified Name:     org.ei.drishti.dto.MonthSummaryDatum
 * JD-Core Version:    0.7.0.1
 */