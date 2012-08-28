function Modal() {
    var hideModal = function (targetModalToShow) {
        $('#blur').remove();
        $("body").css("overflow", "auto");
        $(".page").css("height", "auto");
        $(targetModalToShow).hide();
    };

    var showModal = function (targetModalToShow) {
        $("body").prepend($('<div id="blur"></div>'));
        $("body").css("overflow", "hidden");
        $(".page").css('height', $(window).height());
        $(targetModalToShow).show();

        $("#blur").click(function (event) {
            hideModal(targetModalToShow);
            event.stopPropagation();
            return true;
        });

        $(targetModalToShow).click(function (event) {
            hideModal(targetModalToShow);
            event.stopPropagation();
            return true;
        });
    };

    return {
        bindToClick: function () {
            $('body [data-modal-target]').click(function (event) {
                $("body").scrollTop(0);
                showModal($(this).data('modal-target'));
            });
        }
    };
}
