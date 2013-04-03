describe("Form Model Mapper", function () {
    var formModelMapper;
    var formDataRepository;
    var formDefinition;
    var savedFormInstance;
    var queryBuilder;
    var entitiesDef;

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
        entitiesDef = [
            new enketo.EntityDef(
                "ec").addRelation(new enketo.RelationDef(
                    "mother",
                    "one_to_one",
                    "parent",
                    "ec.id",
                    "mother.ec_id")),
            new enketo.EntityDef(
                "mother").addRelation(new enketo.RelationDef(
                    "ec",
                    "one_to_one",
                    "child",
                    "mother.ec_id",
                    "ec.id")).addRelation(new enketo.RelationDef(
                    "child",
                    "one_to_many",
                    "parent",
                    "mother.id",
                    "child.mother_id")),
            new enketo.EntityDef(
                "child").addRelation(new enketo.RelationDef(
                    "mother",
                    "many_to_one",
                    "child",
                    "child.mother_id",
                    "mother.id"))
        ];
        formDataRepository = new enketo.FormDataRepository();
        queryBuilder = new enketo.SQLQueryBuilder(formDataRepository);
        formModelMapper = new enketo.FormModelMapper(formDataRepository, queryBuilder, new enketo.IdFactory(new enketo.IdFactoryBridge()));
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
                        "source": "entity.field1",
                        "value": "value1"
                    },
                    {
                        "name": "field2",
                        "bind": "field2_bind",
                        "source": "entity.field2",
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

    it("should map form model into entities and save self, child and grand child.", function () {
        var formModel = {
            "form": {
                "bind_type": "ec",
                "default_bind_path": "/EC registration/",
                "fields": [
                    {
                        "name": "id",
                        "source": "ec.id",
                        "value": "ec id 1"
                    },
                    {
                        "name": "field2",
                        "source": "ec.field2",
                        "bind": "field2_bind",
                        "value": "value2"
                    },
                    {
                        "name": "field3",
                        "bind": "field3_bind",
                        "source": "ec.mother.field3",
                        "value": "value3"
                    },
                    {
                        "name": "field4",
                        "bind": "field4_bind",
                        "source": "ec.mother.child.field4",
                        "value": "value4"
                    },
                    {
                        "name": "field5",
                        "bind": "field5_bind",
                        "source": "ec.mother.field5",
                        "value": "value5"
                    },
                    {
                        "name": "field6",
                        "bind": "field6_bind",
                        "source": "ec.field6",
                        "value": "value6"
                    }
                ]
            }
        };
        var expectedECInstance = {
            "id": "ec id 1",
            "field2": "value2",
            "field6": "value6"
        };
        var expectedMotherInstance = {
            "field3": "value3",
            "field5": "value5",
            "id": "new uuid : mother",
            "ec_id": "ec id 1"
        };
        var expectedChildInstance = {
            "field4": "value4",
            "id": "new uuid : child",
            "mother_id": "new uuid : mother"
        };
        spyOn(formDataRepository, "saveEntity");

        formModelMapper.mapToEntityAndSave(entitiesDef, formModel);

        expect(formDataRepository.saveEntity).toHaveBeenCalledWith("ec", expectedECInstance);
        expect(formDataRepository.saveEntity).toHaveBeenCalledWith("mother", expectedMotherInstance);
        expect(formDataRepository.saveEntity).toHaveBeenCalledWith("child", expectedChildInstance);
        var motherId = formModel.form.fields.filter(function (field) {
            return field.source === "ec.mother.id";
        })[0];
        expect(motherId).toBeDefined();
        expect(motherId.value).not.toBeNull();
    });

    it("should map form model into entities and save self, parent and child.", function () {
        var formModel = {
            "form": {
                "bind_type": "mother",
                "default_bind_path": "/Mother registration/",
                "fields": [
                    {
                        "name": "id",
                        "source": "mother.id",
                        "value": "mother id 1"
                    },
                    {
                        "name": "field2",
                        "source": "mother.field2",
                        "bind": "field2_bind",
                        "value": "value2"
                    },
                    {
                        "name": "field3",
                        "bind": "field3_bind",
                        "source": "mother.ec.field3",
                        "value": "value3"
                    },
                    {
                        "name": "field4",
                        "bind": "field4_bind",
                        "source": "mother.child.field4",
                        "value": "value4"
                    },
                    {
                        "name": "field5",
                        "bind": "field5_bind",
                        "source": "mother.ec.field5",
                        "value": "value5"
                    },
                    {
                        "name": "field6",
                        "bind": "field6_bind",
                        "source": "mother.field6",
                        "value": "value6"
                    }
                ]
            }
        };
        var expectedECInstance = {
            "field3": "value3",
            "field5": "value5",
            "id": "new uuid : ec"
        };
        var expectedMotherInstance = {
            "id": "mother id 1",
            "field2": "value2",
            "field6": "value6",
            "ec_id": "new uuid : ec"
        };
        var expectedChildInstance = {
            "field4": "value4",
            "id": "new uuid : child",
            "mother_id": "mother id 1"
        };
        spyOn(formDataRepository, "saveEntity");

        formModelMapper.mapToEntityAndSave(entitiesDef, formModel);

        expect(formDataRepository.saveEntity).toHaveBeenCalledWith("ec", expectedECInstance);
        expect(formDataRepository.saveEntity).toHaveBeenCalledWith("mother", expectedMotherInstance);
        expect(formDataRepository.saveEntity).toHaveBeenCalledWith("child", expectedChildInstance);
    });

    it("should map form model into entities and save self, parent and grand parent.", function () {
        var formModel = {
            "form": {
                "bind_type": "child",
                "default_bind_path": "/Child immunization/",
                "fields": [
                    {
                        "name": "id",
                        "source": "child.id",
                        "value": ""
                    },
                    {
                        "name": "field2",
                        "source": "child.mother.field2",
                        "bind": "field2_bind",
                        "value": "value2"
                    },
                    {
                        "name": "field3",
                        "bind": "field3_bind",
                        "source": "child.mother.ec.field3",
                        "value": "value3"
                    },
                    {
                        "name": "field4",
                        "bind": "field4_bind",
                        "source": "child.field4",
                        "value": "value4"
                    },
                    {
                        "name": "field5",
                        "bind": "field5_bind",
                        "source": "child.mother.ec.field5",
                        "value": "value5"
                    },
                    {
                        "name": "field6",
                        "bind": "field6_bind",
                        "source": "child.field6",
                        "value": "value6"
                    }
                ]
            }
        };
        var expectedECInstance = {
            "field3": "value3",
            "field5": "value5",
            "id": "new uuid : ec"
        };
        var expectedMotherInstance = {
            "field2": "value2",
            "id": "new uuid : mother",
            "ec_id": "new uuid : ec"
        };
        var expectedChildInstance = {
            "field4": "value4",
            "field6": "value6",
            "mother_id": "new uuid : mother",
            "id": "new uuid : child"
        };
        spyOn(formDataRepository, "saveEntity");

        formModelMapper.mapToEntityAndSave(entitiesDef, formModel);

        expect(formDataRepository.saveEntity).toHaveBeenCalledWith("ec", expectedECInstance);
        expect(formDataRepository.saveEntity).toHaveBeenCalledWith("mother", expectedMotherInstance);
        expect(formDataRepository.saveEntity).toHaveBeenCalledWith("child", expectedChildInstance);
    });
});
