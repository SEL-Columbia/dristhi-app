if (typeof enketo == "undefined" || !enketo) {
    var enketo = {};
}

enketo.FormDataController = function (entityRelationshipLoader, formDefinitionLoader, formModelMapper, params) {
    var self = this;

    var init = function () {
        self.entityRelationshipsJsonDefinition = entityRelationshipLoader.load();
        //TODO: inject EntityRelationships for testing purpose
        self.entities = enketo.EntityRelationships(self.entityRelationshipsJsonDefinition).determineEntitiesAndRelations();
        self.params = params;
        //TODO: if entities if null, consider taking bind_type from params, or formName
        self.formDefinition = formDefinitionLoader.load(params.formName);

    };

    this.get = function () {
        return formModelMapper.mapToFormModel(self.entities, self.formDefinition, self.params);
    };
    this.save = function (instanceId, data) {
        //dataSource.save(instanceId, data);
    };

    this.delete = function (instanceId) {
        //dataSource.remove(instanceId);
    };

    init();
};