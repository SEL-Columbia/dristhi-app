if (typeof enketo == "undefined" || !enketo) {
    var enketo = {};
}

enketo.FormDataController = function (entityRelationshipLoader, formDefinitionLoader, formModelMapper, params) {
    var entityRelationship;
    var formDefinition;
    var init = function () {
        entityRelationship = entityRelationshipLoader.load();
        formDefinition = formDefinitionLoader.load(params.formName);
    };

    init();

    return {
        get: function () {
            return formModelMapper.mapToFormModel(entityRelationship, formDefinition, params);
        },
        save: function (instanceId, data) {
            dataSource.save(instanceId, data);
        },
        delete: function (instanceId) {
            dataSource.remove(instanceId);
        }
    };
};