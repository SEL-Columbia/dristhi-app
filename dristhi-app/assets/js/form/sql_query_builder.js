if (typeof enketo == "undefined" || !enketo) {
    var enketo = {};
}

enketo.SQLQueryBuilder = function () {
    return {
        getQueryFor: function (entityRelationship, entityType, entityId) {
            var queries = ["select * from " + entityType + " where " + entityType + ".entity_id = '" + entityId + "'"];

            entityRelationship.forEach(function (relation) {
                if (relation.parent === entityType) {
                    queries.push("select * from " + relation.child + ", " + relation.parent
                        + " where "
                        + relation.from_column + " = " + relation.to_column
                        + " and "
                        + entityType + ".entity_id = '" + entityId + "'"
                    );
                } else if (relation.child === entityType) {
                    queries.push("select * from " + relation.parent + ", " + relation.child
                        + " where "
                        + relation.from_column + " = " + relation.to_column
                        + " and "
                        + entityType + ".entity_id = '" + entityId + "'"
                    );
                }
            });

            return queries;
        }
    };
};
