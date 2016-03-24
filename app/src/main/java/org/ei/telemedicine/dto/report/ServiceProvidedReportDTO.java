package org.ei.telemedicine.dto.report;

import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.LocalDate;

public class ServiceProvidedReportDTO
{
  @JsonProperty
  private String anm_identifier;
  @JsonProperty
  private String district;
  @JsonProperty
  private Integer id;
  @JsonProperty
  private String indicator;
  @JsonProperty
  private Integer nrhm_report_month;
  @JsonProperty
  private Integer nrhm_report_year;
  @JsonProperty
  private String phc;
  @JsonProperty
  private LocalDate reported_date;
  @JsonProperty
  private String state;
  @JsonProperty
  private String sub_center;
  @JsonProperty
  private String taluka;
  @JsonProperty
  private String type;
  @JsonProperty
  private String village;
  
  public ServiceProvidedReportDTO() {}
  
  public ServiceProvidedReportDTO(Integer paramInteger, String paramString1, String paramString2, String paramString3, LocalDate paramLocalDate, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9)
  {
    this.id = paramInteger;
    this.anm_identifier = paramString1;
    this.type = paramString2;
    this.indicator = paramString3;
    this.reported_date = paramLocalDate;
    this.village = paramString4;
    this.sub_center = paramString5;
    this.phc = paramString6;
    this.taluka = paramString7;
    this.district = paramString8;
    this.state = paramString9;
  }
  
  public ServiceProvidedReportDTO withDate(LocalDate paramLocalDate)
  {
    this.reported_date = paramLocalDate;
    return this;
  }
  
  public ServiceProvidedReportDTO withId(Integer paramInteger)
  {
    this.id = paramInteger;
    return this;
  }
  
  public ServiceProvidedReportDTO withNRHMReportMonth(Integer paramInteger)
  {
    this.nrhm_report_month = paramInteger;
    return this;
  }
  
  public ServiceProvidedReportDTO withNRHMReportYear(Integer paramInteger)
  {
    this.nrhm_report_year = paramInteger;
    return this;
  }
}


/* Location:           C:\Users\appaji.r\Downloads\classes-dex2.jar
 * Qualified Name:     org.ei.drishti.dto.report.ServiceProvidedReportDTO
 * JD-Core Version:    0.7.0.1
 */