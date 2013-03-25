describe("Form Data Controller", function () {
    var formDataController;
    var entityRelationshipLoader;
    var formDefinitionLoader;
    var formModelMapper;
    var formDataRepository;

    beforeEach(function () {
        entityRelationshipLoader = new enketo.EntityRelationshipLoader();
        formDefinitionLoader = new enketo.FormDefinitionLoader();
        formDataRepository = new enketo.FormDataRepository();
        formModelMapper = new enketo.FormModelMapper(formDataRepository, new enketo.SQLQueryBuilder(formDataRepository));
    });

    it("should get form model for given a form type when there is no instance id.", function () {
        var expectedFormModel = {};
        var formDefinition = {};
        var params = {
            "id": "id 1",
            "formName": "entity registration",
            "entityId": "entity 1 id"
        };
        var entities = [];
        spyOn(entityRelationshipLoader, 'load').andReturn(entities);
        spyOn(formDefinitionLoader, 'load').andReturn(formDefinition);
        spyOn(formModelMapper, 'mapToFormModel').andReturn(expectedFormModel);

        formDataController = new enketo.FormDataController(entityRelationshipLoader, formDefinitionLoader, formModelMapper, formDataRepository);
        var actualFormModel = formDataController.get(params);

        expect(actualFormModel).toBe(expectedFormModel);
        expect(formDefinitionLoader.load).toHaveBeenCalledWith("entity registration");
        expect(formModelMapper.mapToFormModel).toHaveBeenCalledWith(entities, formDefinition, params);
    });

    it("should save form submission.", function () {
        var entities = [];
        var formDefinition = {};
        var formModel = {};
        var params = {};
        spyOn(entityRelationshipLoader, 'load').andReturn([]);
        spyOn(formDefinitionLoader, 'load').andReturn({});
        spyOn(formDataRepository, 'saveFormSubmission');
        spyOn(formModelMapper, 'mapToEntityAndSave');

        formDataController = new enketo.FormDataController(entityRelationshipLoader, formDefinitionLoader, formModelMapper, formDataRepository);
        formDataController.save(params, formModel);

        expect(formDataRepository.saveFormSubmission).toHaveBeenCalledWith(formModel, params);
        expect(formModelMapper.mapToEntityAndSave).toHaveBeenCalledWith(entities, formDefinition, formModel, params);
    });
});