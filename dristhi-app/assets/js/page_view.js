var pageView = (function () {
    var callbackForReload = function() {};
    var callbackForStartProgressIndicator = function() {};
    var callbackForStopProgressIndicator = function() {};

    return {
        reload: function() {
            callbackForReload();
        },
        startProgressIndicator: function() {
            callbackForStartProgressIndicator();
        },
        stopProgressIndicator: function() {
            callbackForStopProgressIndicator();
        },

        onReload: function(callBack) {
            callbackForReload = callBack;
        },
        onStartProgressIndicator: function(callBack) {
            callbackForStartProgressIndicator = callBack;
        },
        onStopProgressIndicator: function(callBack) {
            callbackForStopProgressIndicator = callBack;
        }
    }
})();
