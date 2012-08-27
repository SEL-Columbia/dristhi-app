function Alerts() {
    return {
        bindAllCheckboxes: function(cssIdentifierOfCheckboxElementInEachTodo) {
            $(cssIdentifierOfCheckboxElementInEachTodo).click(function(event) {
                $(event.target).toggleClass("checked");
            });
        }
    };
}
