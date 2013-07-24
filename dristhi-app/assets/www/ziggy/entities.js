if (typeof enketo === "undefined" || !enketo) {
    var enketo = {};
}

enketo.Entities = function () {
    "use strict";

    var self = this;

    self.entities = [];

    self.add = function (entity) {
        self.entities.push(entity);
        return self;
    };

    self.addAll = function (entitiesToAdd) {
        self.entities = self.entities.concat(entitiesToAdd.entities);
        return self;
    };

    self.forEach = function (mapFunction) {
        return self.entities.forEach(mapFunction);
    };

    self.findEntityByType = function (type) {
        for (var index = 0; index < self.entities.length; index++) {
            if (self.entities[index].type === type) {
                return self.entities[index];
            }
        }
        return null;
    };

    self.findEntityByTypeAndId = function (entity) {
        for (var index = 0; index < self.entities.length; index++) {
            if (self.entities[index].type === entity.type &&
                self.entities[index].getFieldByPersistenceName("id").value === entity.getFieldByPersistenceName("id").value) {
                return self.entities[index];
            }
        }
        return null;
    };

    self.findEntitiesByType = function (type) {
        return self.entities.filter(function (entity) {
            return entity.type === type;
        });
    };

    self.contains = function (entity) {
        return enketo.hasValue(self.findEntityByTypeAndId(entity));
    };
};