if (typeof enketo === "undefined" || !enketo) {
    var enketo = {};
}

enketo.FormModelMapper = function (formDataRepository, queryBuilder, idFactory) {
    "use strict";

    var shouldPersistEntity = function (entity, entitiesToSave, updatedEntities) {
        return enketo.hasValue(entity) && entitiesToSave.contains(entity) && !updatedEntities.contains(entity);
    };

    var addParentReferenceFieldToChildEntity = function (childRelation, entitiesToSave, parentId, childEntity) {
        var parentReferenceField = childRelation.to.split(".")[1];
        var childEntityToSave = entitiesToSave.findEntityByTypeAndId(childEntity);

        childEntityToSave.createField(
            childEntityToSave.source + "." + parentReferenceField,
            childEntityToSave.source + "." + parentReferenceField,
            parentReferenceField,
            parentId
        );
    };

    var addIdValueToFormModel = function (formModel, idField) {
        var idFormField = formModel.form.fields.filter(function (formField) {
            return formField.source === idField.source;
        })[0];
        if (!enketo.hasValue(idFormField)) {
            formModel.form.fields.push(idField);
        }
        else if (!enketo.hasValue(idFormField.value)) {
            idFormField.value = idField.value;
        }
    };

    var identifyEntities = function (entitiesToSave, formModel) {
        entitiesToSave.forEach(function (entity) {
            var idField = identifyEntity(entity);
            addIdValueToFormModel(formModel, idField);
        });
    };

    var identifySubEntity = function (subEntity, subFormInstance) {
        var idField = identifyEntity(subEntity);
        subFormInstance.id = idField.value;
    };

    var identifyEntity = function (entity) {
        var idField = entity.getFieldByPersistenceName("id");
        if (!enketo.hasValue(idField)) {
            idField = {
                "name": entity.source + ".id",
                "source": entity.source + ".id",
                "persistenceName": "id",
                "value": idFactory.generateIdFor(entity.type)
            };
            entity.addField(idField);
        }
        else if (!enketo.hasValue(idField.value)) {
            idField.value = idFactory.generateIdFor(entity.type);
        }
        return idField;
    };

    var persistChildEntityIfNeeded = function (childEntity, entitiesToSave, updatedEntities, parentEntityId, childRelation) {
        if (shouldPersistEntity(childEntity, entitiesToSave, updatedEntities)) {
            addParentReferenceFieldToChildEntity(childRelation, entitiesToSave, parentEntityId, childEntity);
            persist(childEntity, entitiesToSave, updatedEntities);
        }
    };

    var persist = function (entity, entitiesToSave, updatedEntities) {
        var parentRelations = entity.findParents();
        parentRelations.forEach(function (parentRelation) {
            var parentEntity = entitiesToSave.findEntityByType(parentRelation.type);
            if (shouldPersistEntity(parentEntity, entitiesToSave, updatedEntities)) {
                persist(parentEntity, entitiesToSave, updatedEntities);
            }
        });

        var currentEntityId;
        if (shouldPersistEntity(entity, entitiesToSave, updatedEntities)) {
            var entityFields = {};
            entity.forEachField(function (field) {
                entityFields[field.persistenceName] = field.value;
            });
            formDataRepository.saveEntity(entity.type, entityFields);
            currentEntityId = entity.getFieldByPersistenceName("id").value;
            updatedEntities.add(entity);
        }

        var childRelations = entity.findChildren();
        childRelations.forEach(function (childRelation) {
            if (childRelation.kind === enketo.RelationKind.one_to_many.type) {
                var childEntities = entitiesToSave.findEntitiesByType(childRelation.type);
                childEntities.forEach(function (childEntity) {
                    persistChildEntityIfNeeded(childEntity, entitiesToSave, updatedEntities, currentEntityId, childRelation);
                });
            }
            else {
                var childEntity = entitiesToSave.findEntityByType(childRelation.type);
                persistChildEntityIfNeeded(childEntity, entitiesToSave, updatedEntities, currentEntityId, childRelation);
            }
        });
    };

    var getValueFromHierarchyByPath = function (entityHierarchy, pathVariables) {
        var value = entityHierarchy;
        for (var index = 0; index < pathVariables.length; index++) {
            var pathVariable = pathVariables[index];
            if (enketo.hasValue(value[pathVariable])) {
                value = value[pathVariable];
            } else {
                value = undefined;
                break;
            }
        }
        return value;
    };

    var findFieldByName = function (fields, name) {
        return fields.filter(function (field) {
            if (field.name === name) {
                return field;
            }
        })[0];
    };

    var mapFieldValues = function (formDefinition, entityHierarchy) {
        formDefinition.form.fields.forEach(function (field) {
            if (field.shouldLoadValue) {
                var pathVariables = field.source.split(".");
                var fieldValue = getValueFromHierarchyByPath(entityHierarchy, pathVariables);
                if (enketo.hasValue(fieldValue)) {
                    field.value = fieldValue;
                }
            }
        });
    };

    var mapFieldValuesForSubForms = function (formDefinition, entitiesDefinition, entityHierarchy) {
        if (enketo.hasValue(formDefinition.form.sub_forms)) {
            formDefinition.form.sub_forms.forEach(function (subForm) {
                var path = entitiesDefinition.findPathToBaseEntityFromSubEntity(formDefinition.form.bind_type, subForm.bind_type);
                var subEntities = getValueFromHierarchyByPath(entityHierarchy, path);
                subEntities.forEach(function (subEntity) {
                    var subEntityInstance = null;
                    subForm.fields.forEach(function (field) {
                        if (field.shouldLoadValue) {
                            var value = getValueFromHierarchyByPath(subEntity, field.source.split(".").slice(-1));
                            if (enketo.hasValue(value)) {
                                subEntityInstance = (subEntityInstance || {});
                                subEntityInstance[field.name] = value;
                            }
                        }
                    });
                    if (enketo.hasValue(subEntityInstance)) {
                        subForm.instances.push(subEntityInstance);
                    }
                });
            });
        }
    };

    var overrideGivenFieldValues = function (formDefinition, fieldOverridesJSON) {
        if (!enketo.hasValue(fieldOverridesJSON)) {
            return;
        }
        //Double decoding is a hack. Need this until Enketo stops encoding query parameters.
        var decodedJSON = decodeURIComponent(decodeURIComponent(fieldOverridesJSON));
        var fieldOverrides = JSON.parse(decodedJSON);
        for (var fieldToBeOverridden in fieldOverrides) {
            if (fieldOverrides.hasOwnProperty(fieldToBeOverridden)) {
                var formField = findFieldByName(formDefinition.form.fields, fieldToBeOverridden);
                if (enketo.hasValue(formField)) {
                    formField.value = fieldOverrides[fieldToBeOverridden];
                }
            }
        }
    };

    var addSourceToFields = function (fields, bindType) {
        fields.forEach(function (field) {
            if (!enketo.hasValue(field.source)) {
                field.source = bindType + "." + field.name;
            }
        });
    };

    var setupSubFormFieldsAndInstances = function (formDefinition) {
        if (enketo.hasValue(formDefinition.form.sub_forms)) {
            formDefinition.form.sub_forms.forEach(function (subForm) {
                addSourceToFields(subForm.fields, subForm.bind_type);
                subForm.instances = [];
            });
        }
    };

    return {
        mapToFormModel: function (entitiesDefinition, formDefinition, params) {
            //TODO: Handle errors, savedFormInstance could be null!
            var savedFormInstance = JSON.parse(formDataRepository.getFormInstanceByFormTypeAndId(params.id, params.formName));
            if (enketo.hasValue(savedFormInstance)) {
                return savedFormInstance;
            }
            if (!enketo.hasValue(entitiesDefinition)) {
                return formDefinition;
            }
            addSourceToFields(formDefinition.form.fields, formDefinition.form.bind_type);
            setupSubFormFieldsAndInstances(formDefinition);
            //TODO: not every case entityId maybe applicable.
            if (!enketo.hasValue(params.entityId)) {
                return formDefinition;
            }
            //TODO: pass all the params to the query builder and let it decide what it wants to use for querying.
            var entityHierarchy = queryBuilder.loadEntityHierarchy(entitiesDefinition, formDefinition.form.bind_type, params.entityId);
            mapFieldValues(formDefinition, entityHierarchy);
            overrideGivenFieldValues(formDefinition, params.fieldOverrides);
            mapFieldValuesForSubForms(formDefinition, entitiesDefinition, entityHierarchy);
            return formDefinition;
        },
        mapToEntityAndSave: function (entitiesDefinition, formModel) {
            var entitiesToSave = new enketo.Entities();
            formModel.form.fields.forEach(function (field) {
                var pathVariables = field.source.split(".");
                var entityTypeOfField = pathVariables[pathVariables.length - 2];
                var entityInstance = entitiesToSave.findEntityByType(entityTypeOfField);
                if (!enketo.hasValue(entityInstance)) {
                    entityInstance = entitiesDefinition.findEntityDefinitionByType(entityTypeOfField).createInstance();
                    entityInstance.source = field.source.substring(0, field.source.lastIndexOf("."));
                    entitiesToSave.add(entityInstance);
                }
                entityInstance.createField(field.name, field.source, pathVariables[pathVariables.length - 1], field.value);
            });
            var subEntitiesToSave = new enketo.Entities();
            if (enketo.hasValue(formModel.form.sub_forms)) {
                formModel.form.sub_forms.forEach(function (subForm) {
                    subForm.instances.forEach(function (instance) {
                        var subEntityInstance = entitiesDefinition.findEntityDefinitionByType(subForm.bind_type).createInstance();
                        subForm.fields.forEach(function (field) {
                            var pathVariables = field.source.split(".");
                            subEntityInstance.createField(field.name, field.source, pathVariables[pathVariables.length - 1], instance[field.name]);
                        });
                        subEntityInstance.source = subForm.bind_type;
                        identifySubEntity(subEntityInstance, instance);
                        subEntitiesToSave.add(subEntityInstance);
                    });
                });
            }
            identifyEntities(entitiesToSave, formModel);
            entitiesToSave.addAll(subEntitiesToSave);
            var updatedEntities = new enketo.Entities();
            var baseEntity = entitiesToSave.findEntityByType(formModel.form.bind_type);
            persist(baseEntity, entitiesToSave, updatedEntities);
        }
    };
};
