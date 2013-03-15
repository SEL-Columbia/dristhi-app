describe("Form Model Mapper", function () {
    var formModelMapper;
    var formDataRepository;
    var formDefinition;
    var savedFormInstance;
    var queryBuilder;

    beforeEach(function () {
        savedFormInstance = {
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
        formDefinition = {
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
        formDataRepository = new enketo.FormDataRepository();
        queryBuilder = new enketo.SQLQueryBuilder(formDataRepository);
        formModelMapper = new enketo.FormModelMapper(formDataRepository, queryBuilder);
    });

    it("should get form model for a given form type from the saved form instance when it exists", function () {
        spyOn(formDataRepository, 'getFormInstanceByFormTypeAndId').andReturn(savedFormInstance);
        var entities = null;
        var params = {
            "id": "id 1",
            "formName": "entity-registration"
        };

        var formModel = formModelMapper.mapToFormModel(entities, formDefinition, params);

        expect(formModel).toBe(savedFormInstance);
        expect(formDataRepository.getFormInstanceByFormTypeAndId).toHaveBeenCalledWith(params.id, params.formName);
    });

    it("should get form model with empty field values for a given form type when there is no saved form instance and no entity", function () {
        spyOn(formDataRepository, 'getFormInstanceByFormTypeAndId').andReturn(null);
        var entities = null;
        var params = {
            "id": "id 1",
            "formName": "entity-registration"
        };

        var formModel = formModelMapper.mapToFormModel(entities, formDefinition, params);

        expect(formModel).toBe(formDefinition);
    });

    it("should get form model with empty field values for a given form type when there is no saved form instance, no entity but entity relationship exists", function () {
        spyOn(formDataRepository, 'getFormInstanceByFormTypeAndId').andReturn(null);
        var entities = [];
        var params = {
            "id": "id 1",
            "formName": "entity-registration",
            "entityId": ""
        };

        var formModel = formModelMapper.mapToFormModel(entities, formDefinition, params);

        expect(formModel).toBe(formDefinition);
    });

    it("should get form model with values loaded from an entity for a given form type when entity exists", function () {
        var entityValues = {
            entity: {
                field1: "value1",
                field2: "value2"
            }
        };
        var entities = [];
        var params = {
            "id": "id 1",
            "formName": "entity-registration",
            "entityId": "123"
        };
        var expectedFormModel = {
            "form": {
                "bind_type": "entity",
                "default_bind_path": "/Entity registration/",
                "fields": [
                    {
                        "name": "field1",
                        "value": "value1"
                    },
                    {
                        "name": "field2",
                        "bind": "field2_bind",
                        "value": "value2"
                    },
                    {
                        "name": "field3",
                        "bind": "field3_bind",
                        "source": "entity.field3_source"
                    }
                ]
            }
        };
        spyOn(formDataRepository, 'getFormInstanceByFormTypeAndId').andReturn(null);
        spyOn(queryBuilder, 'loadEntityHierarchy').andReturn(entityValues);

        var formModel = formModelMapper.mapToFormModel(entities, formDefinition, params);

        expect(JSON.stringify(formModel)).toBe(JSON.stringify(expectedFormModel));
        expect(queryBuilder.loadEntityHierarchy).toHaveBeenCalledWith(entities, formDefinition.bind_type, "123");
    });
});