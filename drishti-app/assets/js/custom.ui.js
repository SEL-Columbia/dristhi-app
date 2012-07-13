function toggleIconDuringCollapse(cssIdentifierOfRoot, cssIdentifierOfIcon, iconForToggleOn, iconForToggleOff) {
    $(cssIdentifierOfRoot).children(".collapse").on('shown', function() {
        $(cssIdentifierOfRoot).find(cssIdentifierOfIcon).removeClass(iconForToggleOn).addClass(iconForToggleOff);
    });
    $(cssIdentifierOfRoot).children(".collapse").on('hidden', function() {
        $(cssIdentifierOfRoot).find(cssIdentifierOfIcon).removeClass(iconForToggleOff).addClass(iconForToggleOn);
    });
}
