if (typeof enketo == "undefined" || !enketo) {
    var enketo = {};
}

enketo.FormModelMapper = function (formDataRepository) {
    return {
        mapToFormModel: function (entityRelationship, formDefinition, params) {
            var savedFormInstance = formDataRepository.getFormInstanceByFormTypeAndId(params.id, params.formName);
            if (enketo.hasValue(savedFormInstance)) {
                return savedFormInstance;
            }
            if (!enketo.hasValue(entityRelationship)) {
                return formDefinition;
            }

            /*
             check if there is a from instance with given id
             if instance exists create a model from that
             return submission
             else
             load the entity relation map
             if entity map does not exist
             return empty values
             else
             fetch bind.type
             write query for that bind from entity relation mapping
             run query to get values
             prepare form model object with values
             return transformed form model
             */

            //            var refData = eval(dataSource.get(instanceId));
//            var fields = this.getDataDefinition().fields;
//            for (f in fields) {
//                f.value = refData[f];
//            }
//            return redData;

        }
    };
};

enketo.hasValue = function (object) {
    return !(typeof object == "undefined" || !object);
};