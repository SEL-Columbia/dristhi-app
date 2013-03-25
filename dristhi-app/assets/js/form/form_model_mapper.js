if (typeof enketo == "undefined" || !enketo) {
    var enketo = {};
}

enketo.FormModelMapper = function (formDataRepository, queryBuilder) {
    var addFieldToEntityInstance = function (source, value, entityInstance) {
        var newVar = source.split(".");
        var base = newVar[0];
        if (!enketo.hasValue(entityInstance[base])) {
            entityInstance[base] = {};
        }
        if (newVar.length > 2) {
            newVar.shift();
            entityInstance[base] = addFieldToEntityInstance(newVar.join("."), value, entityInstance[base]);
        } else {
            entityInstance[base][newVar[1]] = value;
        }
        return entityInstance;
    }

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
        mapToEntityAndSave: function (formModel) {
            var entityInstance = {};
            formModel.form.fields.forEach(function (field) {
                entityInstance = addFieldToEntityInstance(field.source, field.value, entityInstance);
            });

            formDataRepository.saveEntity(entityInstance);
        }
    };
};
