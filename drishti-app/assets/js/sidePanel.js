function SidePanel(anmNavigation) {
    var openSidePanel = function(animationDuration) {
        $(".affected-by-sidepanel").addClass("sidepanel-active");
        $(".mainpanel .container-fluid").addClass("container").addClass("container-affected-by-sidepanel");
        $(".page").css('height', $(window).height())

        $("#mainpanel").animate({"width": "16%"}, {duration: animationDuration, queue: false}).animate({"left": "84%"}, {duration: animationDuration, queue: false});
        $("#sidepanel").animate({"width": "84%"}, {duration: animationDuration, queue: false}).animate({"left": "0%"}, {duration: animationDuration, queue: false});
    };

    var closeSidePanel = function(animationDuration) {
        $("#mainpanel").animate({"width": "100%"}, {duration: animationDuration, queue: false}).animate({"left": "0%"}, {duration: animationDuration, queue: false});
        $("#sidepanel").animate({"width": "84%%"}, {duration: animationDuration, queue: false}).animate({"left": "-84%"}, {duration: animationDuration, queue: false, complete: function() {
            $(".affected-by-sidepanel").removeClass("sidepanel-active");
            $(".mainpanel .container.container-affected-by-sidepanel").removeClass("container").removeClass("container-affected-by-sidepanel");
            $(".page").css('height', 'auto');
            $(".navbar.navbar-fixed-top").css('margin-top', '-2px')
        }});
    };

    var bindToggleSidebar = function() {
        var animationDuration = 250;

        $(".navbar-brand-icon-holder").click(function () {
            openSidePanel(animationDuration);
            return false;
        });
        $("#mainpanel-overlay").click(function() {
            closeSidePanel(animationDuration);
            return false;
        });
    };

    return {
        populateInto: function(cssIdentifierOfSidePanelElement) {
            anmNavigation.populateInto(cssIdentifierOfSidePanelElement, function() {
                closeSidePanel(5);
            });
            bindToggleSidebar();
        }
    }
}
