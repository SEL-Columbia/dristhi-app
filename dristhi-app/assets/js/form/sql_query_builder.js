if (typeof enketo == "undefined" || !enketo) {
    var enketo = {};
}

enketo.SQLQueryBuilder = function () {
    return {
        getQueryFor: function(entityRelationship, entityType, entityId) {
            return "";
        }
    };
};
