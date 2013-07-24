if (typeof enketo === "undefined" || !enketo) {
    var enketo = {};
}

enketo.EntityDef = function (type) {
    "use strict";

    var self = this;

    self.type = type;
    self.relations = [];
    self.fields = [];

    self.addRelation = function (rel) {
        self.relations.push(rel);
        return self;
    };

    self.removeAllRelations = function () {
        self.relations = [];
    };

    self.createInstance = function () {
        var instance = new enketo.Entity(self.type);
        self.relations.forEach(function (rel) {
            instance.relations.push(rel.createInstance());
        });
        return instance;
    };

    self.findRelationByType = function (type) {
        return self.relations.filter(function (relation) {
            return relation.type === type;
        })[0];
    };
};

enketo.RelationDef = function (type, kind, as, from, to) {
    "use strict";

    var self = this;

    self.type = type;
    self.kind = kind;
    self.as = as;
    self.from = from;
    self.to = to;

    self.createInstance = function () {
        return new enketo.Relation(self.type, self.kind, self.as, self.from, self.to);
    };
};
