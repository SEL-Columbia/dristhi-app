function ANCRegistryBridge() {
    var context = window.context;
    if (typeof context === "undefined" && typeof FakeANCSmartRegistryContext !== "undefined") {
        context = new FakeANCSmartRegistryContext();
    }

    return {
        getVillages: function () {
            return JSON.parse(context.villages());
        }
    };
}

function FakeANCSmartRegistryContext() {
    return {
        villages: function () {
            return JSON.stringify(
                [
                    {name: "bherya"},
                    {name: "chikkahalli"},
                    {name: "somanahalli_colony"},
                    {name: "chikkabherya"},
                    {name: "basavanapura"}
                ]
            )
        }
    };
}