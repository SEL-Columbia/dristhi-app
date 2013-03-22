if (typeof enketo == "undefined" || !enketo) {
    var enketo = {};
}

enketo.EntityRelationshipLoader = function () {
    return {
        load: function () {
            return JSON.parse($.ajax({
                type: "GET",
                url: "entity_relationship.json",
                async: false
            }).responseText);
        }
    };
};