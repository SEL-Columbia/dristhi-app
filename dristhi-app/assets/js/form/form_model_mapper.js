if (typeof enketo == "undefined" || !enketo) {
    var enketo = {};
}

enketo.FormModelMapper = function (formDataRepository, queryBuilder) {
    return {
        mapToFormModel: function (entities, formDefinition, params) {
            var savedFormInstance = formDataRepository.getFormInstanceByFormTypeAndId(params.id, params.formName);
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
            var entityHierarchy = queryBuilder.loadEntityHierarchy(entities, formDefinition.bind_type, params.entityId);
            formDefinition.form.fields.forEach(function (field) {
                var fieldValue;
                var entity;
                var fieldName;
                if (enketo.hasValue(field.source)) {
                    var source = field.source.split(".");
                    entity = source[0];
                    fieldName = source[1];
                } else {
                    entity= formDefinition.form.bind_type;
                    fieldName=field.name;
                }
                fieldValue = entityHierarchy[entity][fieldName];
                if (enketo.hasValue(fieldValue)) {
                    field.value = fieldValue;
                }
            });

            return formDefinition;
        }
    };
};
