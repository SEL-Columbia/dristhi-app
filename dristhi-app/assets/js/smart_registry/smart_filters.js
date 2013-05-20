angular.module("smartRegistry.filters")
    .filter('humanize', function () {
        return function(input){
            try
            {
                var text = input.toString();
                text = text.replace(/_/g, " ");
                text = text.slice(0, 1).toUpperCase() + text.slice(1);
                return text;
            }
            catch(err)
            {
                return "";
            }
        }
    })
    .filter('camelCase', function () {
        return function(input){
            try
            {
                var text = input.toString();
                var texts = [];
                input.split(" ").forEach(function(text){
                    texts.push(text.slice(0, 1).toUpperCase() + text.slice(1));
                });
                return texts.join(" ");
            }
            catch(err)
            {
                return "";
            }
        }
    });