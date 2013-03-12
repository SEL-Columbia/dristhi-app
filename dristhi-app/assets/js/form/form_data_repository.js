if (typeof enketo == "undefined" || !enketo) {
    var enketo = {};
}

enketo.FormDataRepository = function () {
    var repository = window.formDataRepositoryContext;

    return {
        getFormInstanceByFormTypeAndId : function(formID, formName) {

        },
        getFieldValues : function(query) {

        }
    };
};