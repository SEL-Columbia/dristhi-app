if (typeof enketo === "undefined" || !enketo) {
    var enketo = {};
}

enketo.FormDataController = function (entityRelationshipLoader, formDefinitionLoader, formModelMapper, formDataRepository, submissionRouter) {
    "use strict";

    var self = this;

    var init = function (params) {
        if (!enketo.hasValue(self.entityRelationshipsJsonDefinition)) {
            self.entityRelationshipsJsonDefinition = entityRelationshipLoader.load();
        }
        if (!enketo.hasValue(self.entityDefinitions)) {
            self.entityDefinitions = enketo.EntityRelationships(self.entityRelationshipsJsonDefinition).determineEntitiesAndRelations();
        }
        //TODO: if entities if null, consider taking bind_type from params, or formName
        if (!enketo.hasValue(self.formDefinition)) {
            self.formDefinition = formDefinitionLoader.load(params.formName);
        }
    };

    self.get = function (params) {
        init(params);
        return formModelMapper.mapToFormModel(self.entityDefinitions, self.formDefinition, params);
    };
    self.save = function (params, data) {
        if (typeof params !== 'object') {
            params = JSON.parse(params);
        }
        if (typeof data !== 'object') {
            data = JSON.parse(data);
        }
        params = updateEntityAndParams(params, data);
        if (enketo.hasValue(formDataRepository.saveFormSubmission(params, data))) {
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
