/*global ziggyFileLoader*/

if (typeof enketo === "undefined" || !enketo) {
    var enketo = {};
}

enketo.EntityRelationshipLoader = function () {
    "use strict";

    return {
        load: function () {
            return JSON.parse(ziggyFileLoader.loadAppData("entity_relationship.json"));
        }
    };
};