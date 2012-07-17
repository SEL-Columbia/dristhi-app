function EC(ecBridge) {
    return {
        populateInto:function (cssIdentifierOfRootElement) {
            $(cssIdentifierOfRootElement).html(Handlebars.templates.ec(ecBridge.getCurrentEC()));
        },

        bindToContacts:function (element) {
            $(element).click(function () {
                ecBridge.delegateToContacts();
            });
        },

        bindToCommCare:function (element) {
            $(element).click(function () {
                ecBridge.delegateToCommCare();
            });
        }
    };
}

function ECBridge() {
    return {
        getCurrentEC:function () {
            return JSON.parse(context.get());
        },

        delegateToContacts: function () {
            window.context.startContacts();
        },

        delegateToCommCare: function () {
            window.context.startCommCare();
        }
    };
}
