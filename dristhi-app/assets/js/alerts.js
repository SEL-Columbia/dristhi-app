//TODO: #Delete
function Alerts() {
    return {
        bindAllCheckboxes: function(cssIdentifierOfCheckboxElementInEachTodo, callBackWhenCheckboxIsClicked) {
            $(cssIdentifierOfCheckboxElementInEachTodo).click(function(event) {
                var clickedCheckboxElement = $(event.target);
                var alert = clickedCheckboxElement.parents(".alert");

                if (!clickedCheckboxElement.hasClass("checked")) {
                    clickedCheckboxElement.addClass("checked");
                    alert.addClass("completed");
                }

                callBackWhenCheckboxIsClicked(alert);
            });
        }
    };
}
