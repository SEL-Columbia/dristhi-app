describe("Form Data Controller", function () {
    var formDataController;
    var entityRelationshipLoader;
    var formDefinitionLoader;
    var formModelMapper;

    beforeEach(function () {
        entityRelationshipLoader = new enketo.EntityRelationshipLoader();
        formDefinitionLoader = new enketo.FormDefinitionLoader();
        formModelMapper = new enketo.FormModelMapper(new enketo.FormDataRepository());
    });

    it("should get form model for given a form type when there is no instance id", function () {
        var expectedFormModel = {
            "form": {
                "bind_type": "entity",
                "default_bind_path": "/Entity registration/",
                "fields": [
                    {
                        "name": "field1",
                        "value": "field1_value"
                    },
                    {
                        "name": "field2",
                        "bind": "field2_bind"
                    },
                    {
                        "name": "field3",
                        "bind": "field3_bind",
                        "source": "entity.field3_source",
                        "value": "field3_value"
                    }
                ]
            }
        };
        var formDefinition = {
            "form": {
                "bind_type": "entity",
                "default_bind_path": "/Entity registration/",
                "fields": [
                    {
                        "name": "field1"
                    },
                    {
                        "name": "field2",
                        "bind": "field2_bind"
                    },
                    {
                        "name": "field3",
                        "bind": "field3_bind",
                        "source": "entity.field3_source"
                    }
                ]
            }
        };
        var params = {
            "id": "id 1",
            "formName": "entity registration",
            "entityId": "entity 1 id"
        };
        spyOn(entityRelationshipLoader, 'load').andReturn([]);
        spyOn(formDefinitionLoader, 'load').andReturn(formDefinition);
        spyOn(formModelMapper, 'mapToFormModel').andReturn(expectedFormModel);

        formDataController = new enketo.FormDataController(entityRelationshipLoader, formDefinitionLoader, formModelMapper, params);
        var actualFormModel = formDataController.get("Entity 1");

        expect(actualFormModel).toBe(expectedFormModel);
        expect(formDefinitionLoader.load).toHaveBeenCalledWith("entity registration");
        expect(formModelMapper.mapToFormModel).toHaveBeenCalledWith([], formDefinition, params);
    });
});