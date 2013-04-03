if (typeof enketo == "undefined" || !enketo) {
    var enketo = {};
}

enketo.IdFactory = function (idFactoryBridge) {
    return{
        generateIdFor: function (entityType) {
            return idFactoryBridge.generateIdFor(entityType);
        }
    }
};

enketo.IdFactoryBridge = function () {
    var idFactoryContext = window.formDataRepositoryContext;
    if (typeof idFactoryContext === "undefined" && typeof enketo.FakeIdFactoryContext !== "undefined") {
        idFactoryContext = new enketo.FakeIdFactoryContext();
    }

    return {
        generateIdFor: function (entityType) {
            return idFactoryContext.generateIdFor(entityType);
        }
    };
};

enketo.FakeIdFactoryContext = function () {
    return {
        generateIdFor: function (entityType) {
            return "new uuid : " + entityType;
        }
    }
};