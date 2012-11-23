function ReportIndicatorDetail(reportIndicatorDetailBridge, cssIdOf) {
    return {
        populateInto: function () {
            $(cssIdOf.rootElement).html(Handlebars.templates.report_indicator_detail(reportIndicatorDetailBridge.getReportIndicatorDetail()));
        },
        bindEveryItemToIndicatorCaseDetailView: function () {
            $(cssIdOf.indicator).click(function () {
                reportIndicatorDetailBridge.delegateToReportIndicatorDetail($(this).data("indicator"));
            });
        }
    };
}

function ReportIndicatorDetailBridge() {
    var reportIndicatorDetailContext = window.context;
    if (typeof reportIndicatorDetailContext === "undefined" && typeof ReportIndicatorDetailContext !== "undefined") {
        reportIndicatorDetailContext = new ReportIndicatorDetailContext();
    }

    return {
        getReportIndicatorDetail: function () {
            return JSON.parse(reportIndicatorDetailContext.get());
        },
        delegateToReportIndicatorDetail: function (indicator) {
            return reportIndicatorDetailContext.startReportIndicatorCaseDetail(indicator);
        }
    };
}

function ReportIndicatorDetailContext() {
    return {
        get: function () {
            return JSON.stringify({
                    categoryDescription: "Family Planning",
                    description: "IUD Adoption",
                    identifier: "IUD",
                    annualTarget: "40",
                    monthlySummaries: [
                        {
                            currentProgress: "14",
                            month: "3",
                            aggregatedProgress: "14",
                            year: "2012",
                            percentageOfTargetAchieved: "35"
                        },
                        {
                            currentProgress: "8",
                            month: "4",
                            aggregatedProgress: "22",
                            year: "2012",
                            percentageOfTargetAchieved: "55"
                        },
                        {
                            currentProgress: "3",
                            month: "5",
                            aggregatedProgress: "25",
                            year: "2012",
                            percentageOfTargetAchieved: "63"
                        }
                    ]}
            );
        },
        startReportIndicatorCaseDetail: function (indicator) {
            window.location.href = "report_indicator_case_detail.html";
        }
    }
}
