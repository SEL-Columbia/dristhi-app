if (typeof enketo == "undefined" || !enketo) {
    var enketo = {};
}

enketo.EntityRelationships = function (jsonDefinition) {
    var findEntityByType = function (entities, type) {
        for (var index = 0; index < entities.length; index++) {
            if (entities[index].type === type) {
                return entities[index];
            }
        }
        return null;
    };

    var determineEntities = function () {
        var entities = [];
        jsonDefinition.forEach(function (relation) {
            var entity = findEntityByType(entities, relation.parent);
            if (!enketo.hasValue(entity)) {
                entities.push(new enketo.Entity(relation.parent));
            }
            entity = findEntityByType(entities, relation.child);
            if (!enketo.hasValue(entity)) {
                entities.push(new enketo.Entity(relation.child));
            }
        });
        return entities;
    };

    return {
        determineEntitiesAndRelations: function () {
            var entities = determineEntities();
            jsonDefinition.forEach(function (relation) {
                var parentEntity = findEntityByType(entities, relation.parent);
                if (!enketo.hasValue(parentEntity.relations)) {
                    parentEntity.relations = [];
                }
                parentEntity.relations.push(
                    {
                        "type": relation.child,
                        "kind": relation.kind,
                        "from": relation.from,
                        "to": relation.to
                    });

                var childEntity = findEntityByType(entities, relation.child);
                if (!enketo.hasValue(childEntity.relations)) {
                    childEntity.relations = [];
                }
                childEntity.relations.push(
                    {
                        "type": relation.parent,
                        "kind": enketo.RelationKind[relation.kind].inverse.type,
                        "from": relation.to,
                        "to": relation.from
                    });
            });
            return entities;
        }
    };
};
