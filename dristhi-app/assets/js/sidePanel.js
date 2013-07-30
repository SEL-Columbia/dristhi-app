//TODO: #Delete
function SidePanel(anmNavigation) {
    var openSidePanel = function (animationDuration) {
        $(".affected-by-sidepanel").addClass("sidepanel-active");
        $(".mainpanel .container-fluid").addClass("container-affected-by-sidepanel").css('width', $(window).width() - 24);
        $(".page").css('height', $(window).height());
        $("#sidepanel .menu-background").css('height', $(window).height() + 2);

        $("#mainpanel").animate({"width": "16%"}, {duration: animationDuration, queue: false}).animate({"left": "84%"}, {duration: animationDuration, queue: false});
        $("#sidepanel").animate({"width": "84%"}, {duration: animationDuration, queue: false}).animate({"left": "0%"}, {duration: animationDuration, queue: false});
    };

    var closeSidePanel = function (animationDuration) {
        $("#mainpanel").animate({"width": "100%"}, {duration: animationDuration, queue: false}).animate({"left": "0%"}, {duration: animationDuration, queue: false});
        $("#sidepanel").animate({"width": "84%%"}, {duration: animationDuration, queue: false}).animate({"left": "-84%"}, {duration: animationDuration, queue: false, complete: function () {
            $(".affected-by-sidepanel").removeClass("sidepanel-active");
            $(".mainpanel .container-sidepanel.container-affected-by-sidepanel").removeClass("container-affected-by-sidepanel");
            $(".page").css('height', 'auto');
        }});

    };

    var bindToggleSidebar = function () {
        var animationDuration = 250;

        $(".sidepanel-icon").click(function () {
            openSidePanel(animationDuration);
            return false;
        });
        $("#mainpanel-overlay").click(function () {
            closeSidePanel(animationDuration);
            return false;
        });
    };

    return {
        populateInto: function (cssIdentifierOfSidePanelElement) {
            anmNavigation.populateInto(cssIdentifierOfSidePanelElement, Handlebars.templates.sidepanel, function () {
                closeSidePanel(5);
            });
            bindToggleSidebar();
        }
    }
}
