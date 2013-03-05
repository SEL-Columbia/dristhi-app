if (typeof enketo == "undefined" || !enketo) {
    var enketo = {};
}

enketo.Repository = {
    init: function () {
        var def = this.getDataDefinition();
        dataSource.getRefData(def.statement); //get reference data using map
        //convert data to enketo format using map

        // case_id
        // map
        // run query passing parameters (case_id)
        // use map and query results to create JSON output
    },
    get: function (instanceId) {

    },
    getDataDefinition: function () {
        return $.parseJSON($.ajax({
            type: "GET",
            url: getQuerystring("formName") + "/reference-data-map.json",
            async: false
        }).responseText);
    }
};


//Talk to Martijn on whether this is reusable in online cases?
function getQuerystring(key) {
    var query = window.location.search.substring(1);
    //alert(query);
    var vars = query.split("&");
    for (var i = 0; i < vars.length; i++) {
        var pair = vars[i].split("=");
        if (pair[0] == key) {
            return pair[1];
        }
    }
}