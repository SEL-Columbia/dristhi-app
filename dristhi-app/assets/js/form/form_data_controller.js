if (typeof enketo == "undefined" || !enketo) {
    var enketo = {};
}

enketo.FormDataController = function (entityRelationshipLoader, formDefinitionLoader, formModelMapper, formDataRepository) {
    var self = this;

    var init = function (params) {
        if (!enketo.hasValue(self.entityRelationshipsJsonDefinition)) {
            self.entityRelationshipsJsonDefinition = entityRelationshipLoader.load();
        }
        if (!enketo.hasValue(self.entitiesDef)) {
            self.entitiesDef = enketo.EntityRelationships(self.entityRelationshipsJsonDefinition).determineEntitiesAndRelations();
        }
        //TODO: if entities if null, consider taking bind_type from params, or formName
        if (!enketo.hasValue(self.formDefinition)) {
            self.formDefinition = formDefinitionLoader.load(params.formName);
        }
    };

    this.get = function (params) {
        init(params);
        return formModelMapper.mapToFormModel(self.entitiesDef, self.formDefinition, params);
    };
    this.save = function (params, data) {
        init(params);
        if (enketo.hasValue(self.entitiesDef) && self.entitiesDef.length != 0) {
            formModelMapper.mapToEntityAndSave(self.entitiesDef, data);
        }
        formDataRepository.saveFormSubmission(params, data);
    };
    this.delete = function (params) {
        init(params);
        //dataSource.remove(instanceId);
    };
};
