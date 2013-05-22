var fpMethodMap = {
    condom: 'Condom',
    iud: 'IUD',

};
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
    })
    .filter('fpMethodName', function () {
        return function(input, options){
            var method;
            method = options.find(function(option){
               return option['id'] === input;
            });
            if(method !== undefined){
                return method['label'];
            }
            else
            {
                return input;
            }
        }
    })
    .filter('commaSeparated', function () {
        return function(input){
            try
            {
                var text = input.toString();
                return input.split(" ").join(", ");
            }
            catch(err)
            {
                return "";
            }
        }
    });