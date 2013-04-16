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
        var entityRelationshipJSON = [];
        spyOn(entityRelationshipLoader, 'load').andReturn(entityRelationshipJSON);
        spyOn(formDefinitionLoader, 'load').andReturn(formDefinition);
        spyOn(formModelMapper, 'mapToFormModel').andReturn(expectedFormModel);

        formDataController = new enketo.FormDataController(entityRelationshipLoader, formDefinitionLoader, formModelMapper, formDataRepository);
        var actualFormModel = formDataController.get(params);

        expect(actualFormModel).toBe(expectedFormModel);
        expect(formDefinitionLoader.load).toHaveBeenCalledWith("entity registration");
        expect(formModelMapper.mapToFormModel).toHaveBeenCalledWith(entityRelationshipJSON, formDefinition, params);
    });

    it("should save form submission.", function () {
        var entityRelationshipJSON = [
            {
                "parent": "ec",
                "child": "mother",
                "field": "wife",
                "kind": "one_to_one",
                "from": "ec.id",
                "to": "mother.ec_id"
            }
        ];
        var formDefinition = {};
        var formModel = {form: {
            bind_type: "ec",
            fields: [
                {
                    name: "id",
                    source: "ec.id",
                    value: "ec id 1"
                }
            ]
        }};
        var params = {};
        spyOn(entityRelationshipLoader, 'load').andReturn(entityRelationshipJSON);
        spyOn(formDefinitionLoader, 'load').andReturn(formDefinition);
        spyOn(formDataRepository, 'saveFormSubmission');
        spyOn(formModelMapper, 'mapToEntityAndSave');

        formDataController = new enketo.FormDataController(entityRelationshipLoader, formDefinitionLoader, formModelMapper, formDataRepository);
        formDataController.save(params, formModel);

        expect(formDataRepository.saveFormSubmission).toHaveBeenCalledWith(params, formModel);
        expect(formModelMapper.mapToEntityAndSave).toHaveBeenCalledWith(jasmine.any(Array), formModel);
    });

    it("should not try to map and save entities when there is no entity defined.", function () {
        var entityRelationshipJSON = [];
        var formDefinition = {};
        var formModel = {};
        var params = {};
        spyOn(entityRelationshipLoader, 'load').andReturn(entityRelationshipJSON);
        spyOn(formDefinitionLoader, 'load').andReturn(formDefinition);
        spyOn(formDataRepository, 'saveFormSubmission');
        spyOn(formModelMapper, 'mapToEntityAndSave');

        formDataController = new enketo.FormDataController(entityRelationshipLoader, formDefinitionLoader, formModelMapper, formDataRepository);
        formDataController.save(params, formModel);

        expect(formDataRepository.saveFormSubmission).toHaveBeenCalledWith(params, formModel);
        expect(formModelMapper.mapToEntityAndSave).not.toHaveBeenCalled();
    });

    it("should add entityId to params from formModel.", function () {
        var entityRelationshipJSON = [
            {
                "parent": "ec",
                "child": "mother",
                "field": "wife",
                "kind": "one_to_one",
                "from": "ec.id",
                "to": "mother.ec_id"
            }
        ];
        var formDefinition = {};
        var formModel = {};
        var params = {};
        spyOn(entityRelationshipLoader, 'load').andReturn(entityRelationshipJSON);
        spyOn(formDefinitionLoader, 'load').andReturn(formDefinition);
        spyOn(formDataRepository, 'saveFormSubmission');
        spyOn(formModelMapper, 'mapToEntityAndSave').andCallFake(function (entityRelationship, formModel) {
            formModel["form"] = {};
            formModel.form.bind_type = "ec";
            formModel.form["fields"] = [
                {
                    source: "ec.id",
                    value: "ec id 1"
                }
            ];
        });

        formDataController = new enketo.FormDataController(entityRelationshipLoader, formDefinitionLoader, formModelMapper, formDataRepository);
        formDataController.save(params, formModel);

        expect(formDataRepository.saveFormSubmission).toHaveBeenCalledWith({"entityId": "ec id 1"}, formModel);
    });
});
