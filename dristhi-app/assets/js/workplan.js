function Workplan(workplanBridge) {
    return {
        populateInto: function (cssIdentifierOfContentRootElement) {
            $(cssIdentifierOfContentRootElement).html(Handlebars.templates.workplan(workplanBridge.getWorkplanSummary()));
        }
    };
}

function WorkplanBridge() {
    var workplanContext = window.context;
    if (typeof workplanContext === "undefined" && typeof FakeWorkplanContext !== "undefined") {
        workplanContext = new FakeWorkplanContext();
    }

    return {
        getWorkplanSummary: function () {
            return JSON.parse(workplanContext.get());
        }
    };
}

function FakeWorkplanContext() {
    return {
        get: function () {
            return JSON.stringify({
                overdue: [
                    {beneficiaryName: "Napa", description: "OPV due", dueDate: "2012-10-24"},
                    {beneficiaryName: "Salinas", description: "ANC due", dueDate: "2012-10-24"}
                ],
                upcoming: [
                    {beneficiaryName: "Balboa", description: "TT 1 due", dueDate: "2012-10-24"}
                ],
                completed: [
                    {beneficiaryName: "Balboa", description: "IFA due", dueDate: "2012-10-24"},
                    {beneficiaryName: "Karishma", description: "HEP B1 due", dueDate: "2012-10-24"},
                    {beneficiaryName: "Nethravati", description: "IFA follow up due", dueDate: "2012-10-24"}
                ]
            });
        }
    }
}
