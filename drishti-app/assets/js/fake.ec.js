function FakeECListContext() {
    return {
        get: function() {
            return JSON.stringify([
                {
                    caseId: "12345",
                    wifeName: "Wife 1",
                    ecNumber: "EC Number 1",
                    village: "Village 1",
                    isHighRisk: true
                },
                {
                    caseId: "11111",
                    wifeName: "Wife 2",
                    ecNumber: "EC Number 2",
                    village: "Village 2",
                    isHighRisk: false
                }
            ]);
        },
        startEC: function(caseId) {
            alert("Got caseID: " + caseId);
        }
    }
}
