function SidePanel(anmNavigation) {
    var closeSidePanel = function(animationDuration) {
        $("#mainpanel").animate({"width": "100%"}, {duration: animationDuration, queue: false}).animate({"left": "0%"}, {duration: animationDuration, queue: false});
        $("#sidepanel").animate({"width": "84%%"}, {duration: animationDuration, queue: false}).animate({"left": "-84%"}, {duration: animationDuration, queue: false, complete: function() {
            $(".affected-by-sidepanel").removeClass("sidepanel-active");
            $(".affected-by-sidepanel-container").removeClass("container");
            $(".page").css('height', 'auto');
        }});
    };

    var openSidePanel = function(animationDuration) {
        $(".affected-by-sidepanel").addClass("sidepanel-active");
        $(".affected-by-sidepanel-container").addClass("container");
        $(".page").css('height', $(window).height())

        $("#mainpanel").animate({"width": "16%"}, {duration: animationDuration, queue: false}).animate({"left": "84%"}, {duration: animationDuration, queue: false});
        $("#sidepanel").animate({"width": "84%"}, {duration: animationDuration, queue: false}).animate({"left": "0%"}, {duration: animationDuration, queue: false});
    };

    var bindToggleSidebar = function() {
        var animationDuration = 250;

        $(".navbar-brand-icon-holder").click(function () {
            openSidePanel(animationDuration);
        });
        $("#mainpanel-overlay").click(function() {
            closeSidePanel(animationDuration);
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
