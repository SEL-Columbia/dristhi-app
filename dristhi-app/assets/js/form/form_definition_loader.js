if (typeof enketo == "undefined" || !enketo) {
    var enketo = {};
}

enketo.FormDefinitionLoader = function () {
    return {
        load: function (formName) {
            return $.parseJSON($.ajax({
                type: "GET",
                url: formName + "/form_definition.json",
                async: false
            }).responseText);
        }
    };
};