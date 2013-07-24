if (typeof enketo === "undefined" || !enketo) {
    var enketo = {};
}

enketo.EntityRelationships = function (jsonDefinition) {
    "use strict";

    var determineEntities = function () {
        var entityDefinitions = new enketo.EntityDefinitions();
        jsonDefinition.forEach(function (relation) {
            var entity = entityDefinitions.findEntityDefinitionByType(relation.parent);
            if (!enketo.hasValue(entity)) {
                entityDefinitions.add(new enketo.EntityDef(relation.parent));
            }
            entity = entityDefinitions.findEntityDefinitionByType(relation.child);
            if (!enketo.hasValue(entity)) {
                entityDefinitions.add(new enketo.EntityDef(relation.child));
            }
        });
        return entityDefinitions;
    };

    return {
        determineEntitiesAndRelations: function () {
            if (!enketo.hasValue(jsonDefinition)) {
                return new enketo.EntityDefinitions();
            }
            var entityDefinitions = determineEntities();
            jsonDefinition.forEach(function (relation) {
                var parentEntityDefinition = entityDefinitions.findEntityDefinitionByType(relation.parent);
                if (!enketo.hasValue(parentEntityDefinition.relations)) {
                    parentEntityDefinition.removeAllRelations();
                }
                parentEntityDefinition.addRelation(new enketo.RelationDef(relation.child, relation.kind, "parent", relation.from, relation.to));

                var childEntityDefinition = entityDefinitions.findEntityDefinitionByType(relation.child);
                if (!enketo.hasValue(childEntityDefinition.relations)) {
                    childEntityDefinition.removeAllRelations();
                }
                childEntityDefinition.addRelation(new enketo.RelationDef(relation.parent, enketo.RelationKind[relation.kind].inverse.type, "child", relation.to, relation.from));
            });
            return entityDefinitions;
        }
    };
};
