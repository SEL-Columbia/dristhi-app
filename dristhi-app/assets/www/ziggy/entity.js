if (typeof enketo === "undefined" || !enketo) {
    var enketo = {};
}

enketo.Entity = function (type) {
    "use strict";

    var self = this;

    var findRelativesWhoAre = function (relationAs) {
        return self.relations.filter(function (relation) {
            return relation.as === relationAs;
        });
    };

    self.type = type;
    self.relations = [];
    self.fields = [];

    self.addField = function (field) {
        self.fields.push(field);
        return self;
    };

    self.createField = function (name, source, persistenceName, value) {
        self.fields.push({
            "name": name,
            "source": source,
            "persistenceName": persistenceName,
            "value": value
        });
        return self;
    };

    self.findParents = function () {
        return findRelativesWhoAre("child");
    };

    self.findChildren = function () {
        return findRelativesWhoAre("parent");
    };

    self.getFieldByPersistenceName = function (name) {
        return self.fields.filter(function (field) {
            return field.persistenceName === name;
        })[0];
    };

    self.forEachField = function (mapFunction) {
        return self.fields.forEach(mapFunction);
    };
};

enketo.Relation = function (type, kind, as, from, to) {
    "use strict";

    var self = this;

    self.type = type;
    self.kind = kind;
    self.as = as;
    self.from = from;
    self.to = to;
};
