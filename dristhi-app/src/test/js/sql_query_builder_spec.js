describe("Form Model Mapper", function () {
    var sqlQueryBuilder;

    beforeEach(function () {
        sqlQueryBuilder = new enketo.SQLQueryBuilder();
    });

    it("should generate a query when there is empty entity relation", function () {
        var entityRelationship = [];

        var queries = sqlQueryBuilder.getQueryFor(entityRelationship, "entity", "entity id 1");

        expect(JSON.stringify(queries)).toBe(JSON.stringify(["select * from entity where entity.entity_id = 'entity id 1'"]));
    });

    it("should generate a query when there is a single child for the given entity", function () {
        var entityRelationship = [
            {
                "parent": "entity",
                "child": "child_entity",
                "field": "child",
                "type": "one_to_one",
                "from_column": "child_entity.parent_entity_id",
                "to_column": "entity.entity_id"
            }
        ];

        var queries = sqlQueryBuilder.getQueryFor(entityRelationship, "entity", "entity id 1");

        expect(JSON.stringify(queries)).toBe(JSON.stringify(
            [
                "select * from entity where entity.entity_id = 'entity id 1'",
                "select * from child_entity, entity where child_entity.parent_entity_id = entity.entity_id " +
                    "and entity.entity_id = 'entity id 1'"
            ]
        ));
    });

    it("should generate a query when there is a single parent for the given entity", function () {
        var entityRelationship = [
            {
                "parent": "parent_entity",
                "child": "entity",
                "field": "child",
                "type": "one_to_one",
                "from_column": "entity.parent_entity_id",
                "to_column": "parent_entity.entity_id"
            }
        ];

        var queries = sqlQueryBuilder.getQueryFor(entityRelationship, "entity", "child id 1");

        expect(JSON.stringify(queries)).toBe(JSON.stringify(
            [
                "select * from entity where entity.entity_id = 'child id 1'",
                "select * from parent_entity, entity where entity.parent_entity_id = parent_entity.entity_id " +
                    "and entity.entity_id = 'child id 1'"
            ]
        ));
    });

    it("should generate a query for a given entity by loading all its children recursively", function () {
        var entityRelationship = [
            {
                "parent": "entity",
                "child": "child_entity",
                "field": "child",
                "type": "one_to_one",
                "from_column": "child_entity.parent_entity_id",
                "to_column": "entity.entity_id"
            },
            {
                "parent": "child_entity",
                "child": "grand_child_entity",
                "field": "grand_child",
                "type": "one_to_many",
                "from_column": "grand_child_entity.parent_entity_id",
                "to_column": "child_entity.entity_id"
            }
        ];
        var queries = sqlQueryBuilder.getQueryFor(entityRelationship, "entity", "entity id 1");

        expect(JSON.stringify(queries)).toBe(JSON.stringify(
            [
                "select * from entity where entity.entity_id = 'child id 1'",
                "select * from child_entity, entity where child_entity.parent_entity_id = entity.entity_id " +
                    "and entity.entity_id = 'entity id 1'",
                "select * from grand_child_entity c, child_entity where grand_child_entity.parent_entity_id = child_entity.entity_id " +
                    "and child_entity.entity_id = 'child id 1'"
            ]
        ));
    });
});
