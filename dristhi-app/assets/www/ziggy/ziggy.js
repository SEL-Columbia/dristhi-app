if (typeof enketo === "undefined" || !enketo) {
    var enketo = {};
}

enketo.hasValue = function (object) {
    "use strict";
    return !(typeof object === "undefined" || !object);
};

// String format
if (!String.prototype.format) {
    String.prototype.format = function () {
        "use strict";
        var args = arguments;
        return this.replace(/{(\d+)}/g, function (match, number) {
            return typeof args[number] !== 'undefined' ? args[number] : match;
        });
    };
}
;
/*global formDataRepositoryContext*/

if (typeof enketo === "undefined" || !enketo) {
    var enketo = {};
}

enketo.IdFactory = function (idFactoryBridge) {
    "use strict";
    return{
        generateIdFor: function (entityType) {
            return idFactoryBridge.generateIdFor(entityType);
        }
    };
};

enketo.IdFactoryBridge = function () {
    "use strict";
    var idFactoryContext;
    if (typeof formDataRepositoryContext !== "undefined") {
        idFactoryContext = formDataRepositoryContext;
    }

    return {
        generateIdFor: function (entityType) {
            return idFactoryContext.generateIdFor(entityType);
        }
    };
};
;
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
;
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
;
if (typeof enketo === "undefined" || !enketo) {
    var enketo = {};
}

enketo.Entity = function (type) {
    "use strict";

    var self = this;

    var findRelativesWhoAre = function (relationAs) {
        return self.relations.filter(function (relation) {
            return relation.as === relationAs;
        });
    };

    self.type = type;
    self.relations = [];
    self.fields = [];

    self.addField = function (field) {
        self.fields.push(field);
        return self;
    };

    self.createField = function (name, source, persistenceName, value) {
        self.fields.push({
            "name": name,
            "source": source,
            "persistenceName": persistenceName,
            "value": value
        });
        return self;
    };

    self.findParents = function () {
        return findRelativesWhoAre("child");
    };

    self.findChildren = function () {
        return findRelativesWhoAre("parent");
    };

    self.getFieldByPersistenceName = function (name) {
        return self.fields.filter(function (field) {
            return field.persistenceName === name;
        })[0];
    };

    self.forEachField = function (mapFunction) {
        return self.fields.forEach(mapFunction);
    };
};

enketo.Relation = function (type, kind, as, from, to) {
    "use strict";

    var self = this;

    self.type = type;
    self.kind = kind;
    self.as = as;
    self.from = from;
    self.to = to;
};
;
if (typeof enketo === "undefined" || !enketo) {
    var enketo = {};
}

enketo.Entities = function () {
    "use strict";

    var self = this;

    self.entities = [];

    self.add = function (entity) {
        self.entities.push(entity);
        return self;
    };

    self.addAll = function (entitiesToAdd) {
        self.entities = self.entities.concat(entitiesToAdd.entities);
        return self;
    };

    self.forEach = function (mapFunction) {
        return self.entities.forEach(mapFunction);
    };

    self.findEntityByType = function (type) {
        for (var index = 0; index < self.entities.length; index++) {
            if (self.entities[index].type === type) {
                return self.entities[index];
            }
        }
        return null;
    };

    self.findEntityByTypeAndId = function (entity) {
        for (var index = 0; index < self.entities.length; index++) {
            if (self.entities[index].type === entity.type &&
                self.entities[index].getFieldByPersistenceName("id").value === entity.getFieldByPersistenceName("id").value) {
                return self.entities[index];
            }
        }
        return null;
    };

    self.findEntitiesByType = function (type) {
        return self.entities.filter(function (entity) {
            return entity.type === type;
        });
    };

    self.contains = function (entity) {
        return enketo.hasValue(self.findEntityByTypeAndId(entity));
    };
};
;
if (typeof enketo === "undefined" || !enketo) {
    var enketo = {};
}

enketo.EntityDef = function (type) {
    "use strict";

    var self = this;

    self.type = type;
    self.relations = [];
    self.fields = [];

    self.addRelation = function (rel) {
        self.relations.push(rel);
        return self;
    };

    self.removeAllRelations = function () {
        self.relations = [];
    };

    self.createInstance = function () {
        var instance = new enketo.Entity(self.type);
        self.relations.forEach(function (rel) {
            instance.relations.push(rel.createInstance());
        });
        return instance;
    };

    self.findRelationByType = function (type) {
        return self.relations.filter(function (relation) {
            return relation.type === type;
        })[0];
    };
};

enketo.RelationDef = function (type, kind, as, from, to) {
    "use strict";

    var self = this;

    self.type = type;
    self.kind = kind;
    self.as = as;
    self.from = from;
    self.to = to;

    self.createInstance = function () {
        return new enketo.Relation(self.type, self.kind, self.as, self.from, self.to);
    };
};
;
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
;
if (typeof enketo === "undefined" || !enketo) {
    var enketo = {};
}

enketo.RelationKind = {
    one_to_one: {type: "one_to_one"},
    one_to_many: {type: "one_to_many"},
    many_to_one: {type: "many_to_one"}
};

enketo.RelationKind.one_to_one.inverse = enketo.RelationKind.one_to_one;
enketo.RelationKind.one_to_many.inverse = enketo.RelationKind.many_to_one;
enketo.RelationKind.many_to_one.inverse = enketo.RelationKind.one_to_many;
;
if (typeof enketo === "undefined" || !enketo) {
    var enketo = {};
}

enketo.EntityRelationships = function (jsonDefinition, formDefinition) {
    "use strict";

    var determineEntities = function () {
        var entityDefinitions = new enketo.EntityDefinitions();
        if (enketo.hasValue(jsonDefinition)) {
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
        }

        if (enketo.hasValue(formDefinition.form.bind_type) && !enketo.hasValue(entityDefinitions.findEntityDefinitionByType(formDefinition.form.bind_type))) {
            entityDefinitions.add(new enketo.EntityDef(formDefinition.form.bind_type));
        }
        return entityDefinitions;
    };

    return {
        determineEntitiesAndRelations: function () {
            var entityDefinitions = determineEntities();
            if (!enketo.hasValue(jsonDefinition)) {
                return entityDefinitions;
            }
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
;
/*global ziggyFileLoader*/
if (typeof enketo === "undefined" || !enketo) {
    var enketo = {};
}

enketo.FormDefinitionLoader = function () {
    "use strict";

    return {
        load: function (formName) {
            return JSON.parse(ziggyFileLoader.loadAppData(formName + "/form_definition.json"));
        }
    };
};
;
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
                var entity = {};
                entity[baseEntityType] = baseEntity;
                return entity;
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
;
/*global formDataRepositoryContext*/

if (typeof enketo === "undefined" || !enketo) {
    var enketo = {};
}

enketo.FormDataRepository = function () {
    "use strict";

    var repository;
    if (typeof formDataRepositoryContext !== "undefined") {
        repository = formDataRepositoryContext;
    }

    return {
        getFormInstanceByFormTypeAndId: function (formID, formName) {
            return null;
        },
        queryUniqueResult: function (sql) {
            return repository.queryUniqueResult(sql);
        },
        queryList: function (sql) {
            return repository.queryList(sql);
        },
        saveFormSubmission: function (params, data, formDataDefinitionVersion) {
            return repository.saveFormSubmission(JSON.stringify(params), JSON.stringify(data), formDataDefinitionVersion);
        },
        saveEntity: function (entityType, entity) {
            return repository.saveEntity(entityType, JSON.stringify(entity));
        }
    };
};
;
/*global formSubmissionRouter*/

if (typeof enketo === "undefined" || !enketo) {
    var enketo = {};
}

enketo.FormSubmissionRouter = function () {
    "use strict";
    var submissionRouter;
    if (typeof formSubmissionRouter !== "undefined") {
        submissionRouter = formSubmissionRouter;
    }

    return {
        route: function (instanceId) {
            return submissionRouter.route(instanceId);
        }
    };
};
;
if (typeof enketo === "undefined" || !enketo) {
    var enketo = {};
}

enketo.FormDataController = function (entityRelationshipLoader, formDefinitionLoader, formModelMapper, formDataRepository, submissionRouter) {
    "use strict";

    var self = this;
    var defaultFormDataDefinitionVersion = "1";

    var init = function (params) {
        if (!enketo.hasValue(self.entityRelationshipsJsonDefinition)) {
            self.entityRelationshipsJsonDefinition = entityRelationshipLoader.load();
        }
        //TODO: if entities if null, consider taking bind_type from params, or formName
        if (!enketo.hasValue(self.formDefinition)) {
            self.formDefinition = formDefinitionLoader.load(params.formName);
        }
        if (!enketo.hasValue(self.entityDefinitions)) {
            self.entityDefinitions = enketo.EntityRelationships(self.entityRelationshipsJsonDefinition, self.formDefinition).determineEntitiesAndRelations();
        }
    };

    self.get = function (params) {
        init(params);
        var mapToFormModel = formModelMapper.mapToFormModel(self.entityDefinitions, self.formDefinition, params);
        androidContext.log(JSON.stringify(mapToFormModel));
        return  mapToFormModel;
    };
    self.save = function (params, data) {
        if (typeof params !== 'object') {
            params = JSON.parse(params);
        }
        if (typeof data !== 'object') {
            data = JSON.parse(data);
        }
        params = updateEntityAndParams(params, data);
        var formSubmissionInstanceId = formDataRepository.saveFormSubmission(params, data, data.form_data_definition_version || defaultFormDataDefinitionVersion);
        if (enketo.hasValue(formSubmissionInstanceId)) {
            submissionRouter.route(params.instanceId);
        }
    };
    self.createOrUpdateEntity = function (params, data) {
        if (typeof params !== 'object') {
            params = JSON.parse(params);
        }
        if (typeof data !== 'object') {
            data = JSON.parse(data);
        }
        params = updateEntityAndParams(params, data);
        submissionRouter.route(params.instanceId);
    };
    self.deleteFormSubmission = function (params) {
        init(params);
        //dataSource.remove(instanceId);
    };

    var updateEntityAndParams = function (params, data) {
        init(params);
        if (self.entityDefinitions.hasEntityDefinitions()) {
            formModelMapper.mapToEntityAndSave(self.entityDefinitions, data);
            var baseEntityIdField = data.form.fields.filter(function (field) {
                return field.source === data.form.bind_type + ".id";
            })[0];
            params.entityId = baseEntityIdField.value;
        }
        return params;
    };
};