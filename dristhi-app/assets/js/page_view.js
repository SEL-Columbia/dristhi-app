var pageView = (function () {
    var callbackForReload = function () {
    };
    var callbackForReloadPhoto = function (caseId, photoPath) {
    };
    var callbackForStartProgressIndicator = function () {
    };
    var callbackForStopProgressIndicator = function () {
    };

    return {
        reload: function () {
            callbackForReload();
        },
        reloadPhoto: function (caseId, photoPath) {
            callbackForReloadPhoto(caseId, photoPath);
        },
        startProgressIndicator: function () {
            callbackForStartProgressIndicator();
        },
        stopProgressIndicator: function () {
            callbackForStopProgressIndicator();
        },

        onReload: function (callBack) {
            callbackForReload = callBack;
        },
        onReloadPhoto: function (callBack) {
            callbackForReloadPhoto = callBack;
        },
        onStartProgressIndicator: function (callBack) {
            callbackForStartProgressIndicator = callBack;
        },
        onStopProgressIndicator: function (callBack) {
            callbackForStopProgressIndicator = callBack;
        }
    }
})();
