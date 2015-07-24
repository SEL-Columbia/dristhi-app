package org.ei.telemedicine.dto;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

public class ActionData
{
  private Map<String, String> data;
  private Map<String, String> details;
  private String target;
  private String type;
  
  private ActionData(String paramString1, String paramString2)
  {
    this.target = paramString1;
    this.type = paramString2;
    this.data = new HashMap();
    this.details = new HashMap();
  }
  
  public static ActionData closeMother(String paramString)
  {
    return new ActionData("mother", "close").with("reasonForClose", paramString);
  }
  
  public static ActionData createAlert(BeneficiaryType paramBeneficiaryType, String paramString1, String paramString2, AlertStatus paramAlertStatus, DateTime paramDateTime1, DateTime paramDateTime2)
  {
    return new ActionData("alert", "createAlert").with("beneficiaryType", paramBeneficiaryType.value()).with("scheduleName", paramString1).with("visitCode", paramString2).with("alertStatus", paramAlertStatus.value()).with("startDate", paramDateTime1.toLocalDate().toString()).with("expiryDate", paramDateTime2.toLocalDate().toString());
  }
  
  public static ActionData from(String paramString1, String paramString2, Map<String, String> paramMap1, Map<String, String> paramMap2)
  {
    ActionData localActionData = new ActionData(paramString2, paramString1);
    localActionData.data.putAll(paramMap1);
    localActionData.details.putAll(paramMap2);
    return localActionData;
  }
  
  public static ActionData markAlertAsClosed(String paramString1, String paramString2)
  {
    return new ActionData("alert", "closeAlert").with("visitCode", paramString1).with("completionDate", paramString2);
  }
  
  public static ActionData reportForIndicator(String paramString1, String paramString2, String paramString3)
  {
    return new ActionData("report", paramString1).with("annualTarget", paramString2).with("monthlySummaries", paramString3);
  }
  
  private ActionData with(String paramString1, String paramString2)
  {
    this.data.put(paramString1, paramString2);
    return this;
  }
  
  private ActionData withDetails(Map<String, String> paramMap)
  {
    this.details.putAll(paramMap);
    return this;
  }
  
  public Map<String, String> data()
  {
    return this.data;
  }
  
  public Map<String, String> details()
  {
    return this.details;
  }
  
  public boolean equals(Object paramObject)
  {
    return EqualsBuilder.reflectionEquals(this, paramObject, new String[0]);
  }
  
  public int hashCode()
  {
    return HashCodeBuilder.reflectionHashCode(this, new String[0]);
  }
  
  public String target()
  {
    return this.target;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this);
  }
  
  public String type()
  {
    return this.type;
  }
}


/* Location:           C:\Users\appaji.r\Downloads\classes-dex2.jar
 * Qualified Name:     org.ei.drishti.dto.ActionData
 * JD-Core Version:    0.7.0.1
 */