if (typeof enketo == "undefined" || !enketo) {
    var enketo = {};
}

enketo.FormDataRepository = function () {
    var repository = window.formDataRepositoryContext;

    return {
        getFormInstanceByFormTypeAndId: function (formID, formName) {
            return undefined;
        },
        queryUniqueResult: function (sql) {
            return repository.queryUniqueResult(sql);
        },
        queryList: function (sql) {
            return repository.queryList(sql);
        }
    };
};