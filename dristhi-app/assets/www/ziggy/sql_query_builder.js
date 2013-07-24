if (typeof enketo === "undefined" || !enketo) {
    var enketo = {};
}

enketo.SQLQueryBuilder = function (formDataRepository) {
    "use strict";

    var loadEntityObjectAndItsRelatives = function (entitiesDefinition, parentInstance, parentType, contextRelation) {
        var baseEntity = entitiesDefinition.findEntityDefinitionByType(contextRelation.type);
        var column = contextRelation.from.split(".")[1];
        var sql = "select * from {0} where {1} = '{2}'".format(contextRelation.type, contextRelation.to, parentInstance[column]);
        var baseInstance = JSON.parse(queryMethod(contextRelation)(sql));

        if (!enketo.hasValue(baseInstance)) {
            return null;
        }
        if (!enketo.hasValue(baseEntity.relations) || baseEntity.relations.length === 0) {
            return baseInstance;
        }
        //TODO: When baseEntity is a list, relatives have to be loaded for each instance
        baseEntity.relations.forEach(function (relation) {
            if (relation.type !== parentType) {
                var relative = loadEntityObjectAndItsRelatives(entitiesDefinition, baseInstance, baseEntity.type, relation);
                if (enketo.hasValue(relative)) {
                    baseInstance[relation.type] = relative;
                }
            }
        });
        return baseInstance;
    };

    var queryMethod = function (contextRelation) {
        if (enketo.RelationKind[contextRelation.kind] === enketo.RelationKind.one_to_many) {
            return formDataRepository.queryList;
        }
        else {
            return formDataRepository.queryUniqueResult;
        }
    };

    return {
        loadEntityHierarchy: function (entitiesDefinition, baseEntityType, baseEntityId) {
            var baseEntityDefinition = entitiesDefinition.findEntityDefinitionByType(baseEntityType);
            //TODO : Need to format the sql as per the data type
            var sql = "select * from {0} where id = '{1}'".format(baseEntityType, baseEntityId);
            var baseEntity = JSON.parse(formDataRepository.queryUniqueResult(sql));
            if (!enketo.hasValue(baseEntityDefinition.relations) || baseEntityDefinition.relations.length === 0) {
                return baseEntity;
            }
            baseEntityDefinition.relations.forEach(function (relation) {
                baseEntity[relation.type] = loadEntityObjectAndItsRelatives(entitiesDefinition, baseEntity, baseEntityType, relation);
            });
            var entityWithRelatives = {};
            entityWithRelatives[baseEntityType] = baseEntity;
            return entityWithRelatives;
        }
    };
};
