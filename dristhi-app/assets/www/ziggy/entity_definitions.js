if (typeof enketo === "undefined" || !enketo) {
    var enketo = {};
}

enketo.EntityDefinitions = function () {
    "use strict";

    var self = this;

    self.entityDefinitions = [];

    self.add = function (entityDefinition) {
        self.entityDefinitions.push(entityDefinition);
        return self;
    };

    self.findEntityDefinitionByType = function (type) {
        for (var index = 0; index < self.entityDefinitions.length; index++) {
            if (self.entityDefinitions[index].type === type) {
                return self.entityDefinitions[index];
            }
        }
        return null;
    };

    self.hasEntityDefinitions = function () {
        return self.entityDefinitions.length !== 0;
    };

    self.findPathToBaseEntityFromSubEntity = function (baseEntityType, entityType) {
        var currentEntityDefinition = self.findEntityDefinitionByType(entityType);
        var baseEntityRelation = currentEntityDefinition.findRelationByType(baseEntityType);
        if (enketo.hasValue(baseEntityRelation)) {
            return [baseEntityRelation.type, entityType];
        }
        for (var index = 0; index < currentEntityDefinition.relations.length; index++) {
            var path = self.findPathToBaseEntityFromSubEntity(baseEntityType, currentEntityDefinition.relations[index].type);
            if (enketo.hasValue(path)) {
                path.push(entityType);
                return path;
            }
        }
        return null;
    };
};