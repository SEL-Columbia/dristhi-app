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
                var value;
                if (enketo.hasValue(field.source)) {
                    var sourceArray = field.source.split(".");
                    value = fieldValues[sourceArray[0]][sourceArray[1]];
                } else {
                    value = fieldValues[formDefinition.form.bind_type][field.name];
                }
                if (enketo.hasValue(value)) {
                    field.value = value;
                }
            });

            return formDefinition;
        }
    };
};
