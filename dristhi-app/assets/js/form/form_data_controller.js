if (typeof enketo == "undefined" || !enketo) {
    var enketo = {};
}

enketo.FormDataController = function (entityRelationshipLoader, formDefinitionLoader, formModelMapper, formDataRepository) {
    var self = this;

    var init = function (params) {
        if (!enketo.hasValue(self.entityRelationshipsJsonDefinition)) {
            self.entityRelationshipsJsonDefinition = entityRelationshipLoader.load();
        }
        if (!enketo.hasValue(self.entities)) {
            self.entities = enketo.EntityRelationships(self.entityRelationshipsJsonDefinition).determineEntitiesAndRelations();
        }
        //TODO: if entities if null, consider taking bind_type from params, or formName
        if (!enketo.hasValue(self.formDefinition)) {
            self.formDefinition = formDefinitionLoader.load(params.formName);
        }
    };

    this.get = function (params) {
        init(params);
        return formModelMapper.mapToFormModel(self.entities, self.formDefinition, params);
    };
    this.save = function (params, data) {
        init(params);
        formDataRepository.saveFormSubmission(data, params);
        if (!enketo.hasValue(self.entities) || self.entities.length == 0) {
            return;
        }
        formModelMapper.mapToEntityAndSave(data);
    };
    this.delete = function (params) {
        init(params);
        //dataSource.remove(instanceId);
    };
};
