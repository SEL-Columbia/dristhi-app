function EC(ecBridge) {
    return {
        populateInto:function (cssIdentifierOfRootElement) {
            $(cssIdentifierOfRootElement).html(Handlebars.templates.ec(ecBridge.getCurrentEC()));
        },

        bindToContacts:function (element) {
            $(element).click(function () {
                window.context.startContacts();
            });
        },

        bindToCommCare:function (element) {
            $(element).click(function () {
                window.context.startCommCare();
            });
        }
    };
}

function ECBridge() {
    return {
        getCurrentEC:function () {
            return JSON.parse(context.get());
        }
    };
}
