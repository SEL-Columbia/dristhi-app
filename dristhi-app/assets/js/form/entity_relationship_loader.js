if (typeof enketo == "undefined" || !enketo) {
    var enketo = {};
}

enketo.EntityRelationshipLoader = function () {
    return {
        load: function () {
            return $.parseJSON($.ajax({
                type: "GET",
                url: "entity_relationship.json",
                async: false
            }).responseText);
        }
    };
};