if (typeof enketo == "undefined" || !enketo) {
    var enketo = {};
}

enketo.hasValue = function (object) {
    return !(typeof object == "undefined" || !object);
};