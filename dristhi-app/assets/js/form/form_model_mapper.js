if (typeof enketo == "undefined" || !enketo) {
    var enketo = {};
}

enketo.FormModelMapper = function (formDataRepository, queryBuilder) {

    var findEntityByType = function (entitiesDef, type) {
        for (var index = 0; index < entitiesDef.length; index++) {
            if (entitiesDef[index].type === type) {
                return entitiesDef[index];
            }
        }
        return null;
    };

    var addFieldToEntityInstance = function (source, value, entityInstance) {
        var pathVariables = source.split(".");
        var base = pathVariables[0];
        if (!enketo.hasValue(entityInstance[base])) {
            entityInstance[base] = {};
        }
        if (pathVariables.length > 2) {
            pathVariables.shift();
            entityInstance[base] = addFieldToEntityInstance(pathVariables.join("."), value, entityInstance[base]);
        } else {
            entityInstance[base][pathVariables[1]] = value;
        }
        return entityInstance;
    };

    var findParents = function (entityType) {
        return findRelativesWhoAre(entityType, "child");
    };

    var findChildren = function (entityType) {
        return findRelativesWhoAre(entityType, "parent");
    };

    var findRelativesWhoAre = function (entityType, relationAs) {
        return entityType.relations.filter(function (relation) {
            return relation.as === relationAs;
        });
    };

    var persist = function (entitiesDef, entityType, entitiesToSave, updatedEntities) {
        var entityTypeDef = findEntityByType(entitiesDef, entityType);
        var parentRelations = findParents(entityTypeDef);
        parentRelations.forEach(function (parentRelation) {
            var parentEntityToSave = findEntityByType(entitiesToSave, parentRelation.type);
            var isParentAlreadySaved = enketo.hasValue(findEntityByType(updatedEntities, parentRelation.type));
            if (enketo.hasValue(parentEntityToSave) && !isParentAlreadySaved) {
                persist(entitiesDef, parentRelation.type, entitiesToSave, updatedEntities);
            }
        });

        var entityToSave = findEntityByType(entitiesToSave, entityType);
        var isEntityAlreadySaved = enketo.hasValue(findEntityByType(updatedEntities, entityType));
        var entityId;
        if (enketo.hasValue(entityToSave) && !isEntityAlreadySaved) {
            entityId = formDataRepository.saveEntity(entityType, entityToSave.fields);
            updatedEntities.push(entityToSave);
        }

        var childRelations = findChildren(entityTypeDef);
        childRelations.forEach(function (childRelation) {
            var childEntityToSave = findEntityByType(entitiesToSave, childRelation.type);
            var isChildAlreadySaved = enketo.hasValue(findEntityByType(updatedEntities, childRelation.type));
            if (enketo.hasValue(childEntityToSave) && !isChildAlreadySaved) {
                var parentReferenceField = childRelation.to.split(".")[1];
                childEntityToSave.fields[parentReferenceField] = entityId;
                persist(entitiesDef, childRelation.type, entitiesToSave, updatedEntities);
            }
        });
    };

    return {
        mapToFormModel: function (entities, formDefinition, params) {
            //TODO: Handle errors, savedFormInstance could be null!
            var savedFormInstance = JSON.parse(formDataRepository.getFormInstanceByFormTypeAndId(params.id, params.formName));
            if (enketo.hasValue(savedFormInstance)) {
                return savedFormInstance;
            }
            if (!enketo.hasValue(entities)) {
                return formDefinition;
            }
            //TODO: not every case entityId maybe applicable.
            if (!enketo.hasValue(params.entityId)) {
                return formDefinition;
            }
            //TODO: pass all the params to the query builder and let it decide what it wants to use for querying.
            //TODO: Add source to each field explicitly.
            var entityHierarchy = queryBuilder.loadEntityHierarchy(entities, formDefinition.form.bind_type, params.entityId);
            formDefinition.form.fields.forEach(function (field) {
                var fieldValue;
                var entity;
                var fieldName;
                if (enketo.hasValue(field.source)) {
                    var pathVariables = field.source.split(".");
                    fieldValue = entityHierarchy;
                    for (var index in pathVariables) {
                        var pathVariable = pathVariables[index];
                        if (enketo.hasValue(fieldValue[pathVariable])) {
                            fieldValue = fieldValue[pathVariable];
                        } else {
                            fieldValue = undefined;
                            break;
                        }
                    }
                } else {
                    entity = formDefinition.form.bind_type;
                    fieldName = field.name;
                    fieldValue = entityHierarchy[entity][fieldName];
                }
                if (enketo.hasValue(fieldValue)) {
                    field.value = fieldValue;
                }
            });

            return formDefinition;
        },
        mapToEntityAndSave: function (entitiesDef, formModel) {
            var entitiesToSave = [];
            formModel.form.fields.forEach(function (field) {
                var pathVariables = field.source.split(".");
                var entityTypeOfField = pathVariables[pathVariables.length - 2];
                var entityInstance = findEntityByType(entitiesToSave, entityTypeOfField);
                if (!enketo.hasValue(entityInstance)) {
                    entityInstance = findEntityByType(entitiesDef, entityTypeOfField).createInstance();
                    entitiesToSave.push(entityInstance);
                }
                if (!enketo.hasValue(entityInstance.fields)) {
                    entityInstance.fields = {};
                }
                entityInstance.fields[pathVariables[pathVariables.length - 1]] = field.value;
            });
            var updatedEntities = [];
            persist(entitiesDef, formModel.form.bind_type, entitiesToSave, updatedEntities);
        }
    };
};