if (typeof enketo == "undefined" || !enketo) {
    var enketo = {};
}

enketo.FormModelMapper = function (formDataRepository, queryBuilder) {
    return {
        mapToFormModel: function (entityRelationship, formDefinition, params) {
            var savedFormInstance = formDataRepository.getFormInstanceByFormTypeAndId(params.id, params.formName);
            if (enketo.hasValue(savedFormInstance)) {
                return savedFormInstance;
            }
            if (!enketo.hasValue(entityRelationship)) {
                return formDefinition;
            }
            if (!enketo.hasValue(params.entityId)) {
                return formDefinition;
            }

            var query = queryBuilder.getQueryFor(entityRelationship, formDefinition.bind_type, params.entityId);
            var fieldValues = formDataRepository.getFieldValues(query);
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
                fieldValue = fieldValues[entity][fieldName];
                if (enketo.hasValue(fieldValue)) {
                    field.value = fieldValue;
                }
            });

            return formDefinition;
        }
    };
};
