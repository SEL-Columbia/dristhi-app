describe("Form Model Mapper", function () {
    var formModelMapper;
    var formDataRepository;
    var formDefinition;
    var savedFormInstance;
    var queryBuilder;

    beforeEach(function () {
        savedFormInstance = JSON.stringify({
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
        });
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

        expect(JSON.stringify(formModel)).toBe(savedFormInstance);
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
                field2: "value2",
                childEntity: {
                    field3: "value3",
                    grandChildEntity: {
                        field4: "value4"
                    }
                }
            }
        };
        var entities = [];
        var params = {
            "id": "id 1",
            "formName": "entity-registration",
            "entityId": "123"
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
                        "source": "entity.childEntity.field3"
                    },
                    {
                        "name": "field4",
                        "bind": "field4_bind",
                        "source": "entity.childEntity.grandChildEntity.field4"
                    },
                    {
                        "name": "field5",
                        "bind": "field4_bind",
                        "source": "entity.childEntity.field5"
                    }
                ]
            }
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
                        "source": "entity.childEntity.field3",
                        "value": "value3"
                    },
                    {
                        "name": "field4",
                        "bind": "field4_bind",
                        "source": "entity.childEntity.grandChildEntity.field4",
                        "value": "value4"
                    },
                    {
                        "name": "field5",
                        "bind": "field4_bind",
                        "source": "entity.childEntity.field5"
                    }
                ]
            }
        };
        spyOn(formDataRepository, 'getFormInstanceByFormTypeAndId').andReturn(null);
        spyOn(queryBuilder, 'loadEntityHierarchy').andReturn(entityValues);

        var formModel = formModelMapper.mapToFormModel(entities, formDefinition, params);

        expect(JSON.stringify(formModel)).toBe(JSON.stringify(expectedFormModel));
        expect(queryBuilder.loadEntityHierarchy).toHaveBeenCalledWith(entities, "entity", "123");
    });

    it("should map form model into entities and save them.", function () {
        var formModel = {
            "form": {
                "bind_type": "entity",
                "default_bind_path": "/Entity registration/",
                "fields": [
                    {
                        "name": "field1",
                        "source": "entity.f1",
                        "value": "value1"
                    },
                    {
                        "name": "field2",
                        "source": "entity.field2",
                        "bind": "field2_bind",
                        "value": "value2"
                    },
                    {
                        "name": "field3",
                        "bind": "field3_bind",
                        "source": "entity.child_entity.field3",
                        "value": "value3"
                    },
                    {
                        "name": "field4",
                        "bind": "field4_bind",
                        "source": "entity.child_entity.super_child.field4",
                        "value": "value4"
                    },
                    {
                        "name": "field5",
                        "bind": "field5_bind",
                        "source": "entity.child_entity.field5",
                        "value": "value5"
                    },
                    {
                        "name": "field6",
                        "bind": "field6_bind",
                        "source": "entity.field6",
                        "value": "value6"
                    }
                ]
            }
        };
        var expectedEntityInstance = {
            "entity": {
                "f1": "value1",
                "field2": "value2",
                "child_entity": {
                    "field3": "value3",
                    "super_child": {
                        "field4": "value4"
                    },
                    "field5": "value5"
                },
                "field6": "value6"
            }
        };
        spyOn(formDataRepository, "saveEntity");

        formModelMapper.mapToEntityAndSave(formModel);

        expect(formDataRepository.saveEntity).toHaveBeenCalledWith(expectedEntityInstance);
    });
});
