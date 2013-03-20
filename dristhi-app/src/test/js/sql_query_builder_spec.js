describe("SQL query builder", function () {
    var sqlQueryBuilder;
    var formDataRepository;

    beforeEach(function () {
        formDataRepository = new enketo.FormDataRepository();
        sqlQueryBuilder = new enketo.SQLQueryBuilder(formDataRepository);
    });

    it("should load a simple entity without any relations", function () {
        var expectedEntity = {
            "entity": {
                "id": "id 1",
                "name": "name 1"
            }
        };
        spyOn(formDataRepository, "rawQuery").andReturn(expectedEntity);

        var entityTypes = [{
            "type": "entity"
        }];

        var entities = sqlQueryBuilder.loadEntityHierarchy(entityTypes, "entity", "entity id 1");

        expect(JSON.stringify(entities)).toBe(JSON.stringify(expectedEntity));
    });

    it("should load entity with all its children", function () {
        var entities = [
            {
                "type": "ec",
                "relations": [
                    {
                        "type": "mother",
                        "kind": "one_to_one",
                        "from": "ec.id",
                        "to": "mother.ec_id"
                    }
                ]
            },
            {
                "type": "mother",
                "relations": [
                    {
                        "type": "ec",
                        "kind": "one_to_one",
                        "from": "mother.ec_id",
                        "to": "ec.id"
                    },
                    {
                        "type": "child",
                        "kind": "one_to_many",
                        "from": "mother.id",
                        "to": "child.mother_id"
                    }
                ]
            },
            {
                "type": "child",
                "relations": [
                    {
                        "type": "mother",
                        "kind": "many_to_one",
                        "from": "child.mother_id",
                        "to": "mother.id"
                    }
                ]

            }
        ];
        var expectedEntity = {
            "ec": {
                "id": "ec id 1",
                "wifeName": "asha",
                "mother": {
                    "id": "mother id 1",
                    "ec_id": "ec id 1",
                    "thayiCardNumber": "12345",
                    "child": {
                        "id": "child id 1",
                        "mother_id": "mother id 1",
                        "name": "putta"
                    }
                }
            }
        };

        spyOn(formDataRepository, 'rawQuery').andCallFake(function (query) {
            if (query === "select * from ec where id = 'ec id 1'")
                return {
                    "id": "ec id 1",
                    "wifeName": "asha"
                };
            if (query === "select * from mother where mother.ec_id = 'ec id 1'")
                return {
                    "id": "mother id 1",
                    "ec_id": "ec id 1",
                    "thayiCardNumber": "12345"
                };
            if (query === "select * from child where child.mother_id = 'mother id 1'")
                return {
                    "id": "child id 1",
                    "mother_id": "mother id 1",
                    "name": "putta"
                };
            return null;
        });

        var ec = sqlQueryBuilder.loadEntityHierarchy(entities, "ec", "ec id 1");

        expect(JSON.stringify(ec)).toBe(JSON.stringify(expectedEntity));
    });

    it("should load entity with all its parent", function () {
        var entities = [
            {
                "type": "ec",
                "relations": [
                    {
                        "type": "mother",
                        "kind": "one_to_one",
                        "from": "ec.id",
                        "to": "mother.ec_id"
                    }
                ]
            },
            {
                "type": "mother",
                "relations": [
                    {
                        "type": "ec",
                        "kind": "one_to_one",
                        "from": "mother.ec_id",
                        "to": "ec.id"
                    },
                    {
                        "type": "child",
                        "kind": "one_to_many",
                        "from": "mother.id",
                        "to": "child.mother_id"
                    }
                ]
            },
            {
                "type": "child",
                "relations": [
                    {
                        "type": "mother",
                        "kind": "many_to_one",
                        "from": "child.mother_id",
                        "to": "mother.id"
                    }
                ]

            }
        ];
        var expectedEntity = {
            "child": {
                "id": "child id 1",
                "mother_id": "mother id 1",
                "name": "putta",
                "mother": {
                    "id": "mother id 1",
                    "ec_id": "ec id 1",
                    "thayiCardNumber": "12345",
                    "ec": {
                        "id": "ec id 1",
                        "wifeName": "maanu"
                    }
                }
            }
        };

        spyOn(formDataRepository, 'rawQuery').andCallFake(function (query) {
            if (query === "select * from child where id = 'child id 1'")
                return {
                    "id": "child id 1",
                    "mother_id": "mother id 1",
                    "name": "putta"
                };
            if (query === "select * from mother where mother.id = 'mother id 1'")
                return {
                    "id": "mother id 1",
                    "ec_id": "ec id 1",
                    "thayiCardNumber": "12345"
                };
            if (query === "select * from ec where ec.id = 'ec id 1'")
                return {
                    "id": "ec id 1",
                    "wifeName": "maanu"
                };
            return null;
        });

        var child = sqlQueryBuilder.loadEntityHierarchy(entities, "child", "child id 1");

        expect(JSON.stringify(child)).toBe(JSON.stringify(expectedEntity));
    });

    it("should load entity with both its parents and children", function () {
        var entities = [
            {
                "type": "ec",
                "relations": [
                    {
                        "type": "mother",
                        "kind": "one_to_one",
                        "from": "ec.id",
                        "to": "mother.ec_id"
                    }
                ]
            },
            {
                "type": "mother",
                "relations": [
                    {
                        "type": "ec",
                        "kind": "one_to_one",
                        "from": "mother.ec_id",
                        "to": "ec.id"
                    },
                    {
                        "type": "child",
                        "kind": "one_to_many",
                        "from": "mother.id",
                        "to": "child.mother_id"
                    }
                ]
            },
            {
                "type": "child",
                "relations": [
                    {
                        "type": "mother",
                        "kind": "many_to_one",
                        "from": "child.mother_id",
                        "to": "mother.id"
                    }
                ]

            }
        ];
        var expectedEntity = {
            "mother": {
                "id": "mother id 1",
                "ec_id": "ec id 1",
                "thayiCardNumber": "12345",
                "ec": {
                    "id": "ec id 1",
                    "wifeName": "maanu"
                },
                "child": {
                    "id": "child id 1",
                    "mother_id": "mother id 1",
                    "name": "putta"
                }
            }
        };

        spyOn(formDataRepository, 'rawQuery').andCallFake(function (query) {
            if (query === "select * from mother where id = 'mother id 1'")
                return {
                    "id": "mother id 1",
                    "ec_id": "ec id 1",
                    "thayiCardNumber": "12345"
                };
            if (query === "select * from ec where ec.id = 'ec id 1'")
                return {
                    "id": "ec id 1",
                    "wifeName": "maanu"
                };
            if (query === "select * from child where child.mother_id = 'mother id 1'")
                return {
                    "id": "child id 1",
                    "mother_id": "mother id 1",
                    "name": "putta"
                };
            return null;
        });

        var child = sqlQueryBuilder.loadEntityHierarchy(entities, "mother", "mother id 1");

        expect(JSON.stringify(child)).toBe(JSON.stringify(expectedEntity));
    });
});
