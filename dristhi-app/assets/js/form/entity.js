if (typeof enketo == "undefined" || !enketo) {
    var enketo = {};
}

enketo.EntityDef = function (type) {
    this.type = type;
    //Do not modify this by doing EntityDef.relations
    this.relations = [];

    this.addRelation = function (rel) {
        this.relations.push(rel);
        return this;
    };

    this.removeAllRelations = function () {
        this.relations = [];
    };

    this.createInstance = function () {
        var instance = new enketo.EntityDef(this.type);
        this.relations.forEach(function (rel) {
            instance.relations.push(rel.createInstance())
        });
        return instance;
    };
};

enketo.RelationDef = function (type, kind, as, from, to) {
    this.type = type;
    this.kind = kind;
    this.as = as;
    this.from = from;
    this.to = to;

    this.createInstance = function () {
        return new enketo.RelationDef(this.type, this.kind, this.as, this.from, this.to);
    };
};
