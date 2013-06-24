var fpMethodMap = {
    condom: 'Condom',
    dmpa_injectable: 'DMPA/Injectable',
    iud: 'IUD',
    ocp: 'OCP',
    female_sterilization: 'Female Sterilization',
    male_sterilization: 'Male Sterilization',
    centchroman: 'Centchroman',
    traditional_methods: 'Traditional Methods',
    lam: 'LAM'
};

angular.module("smartRegistry.filters")
    .filter('humanize', function () {
        return function (input) {
            try {
                var text = input.toString();
                text = text.replace(/_/g, " ");
                text = text.slice(0, 1).toUpperCase() + text.slice(1);
                return text;
            }
            catch (err) {
                return "";
            }
        }
    })
    .filter('camelCase', function () {
        return function (input) {
            try {
                var text = input.toString();
                var texts = [];
                text.split(" ").forEach(function (text) {
                    texts.push(text.slice(0, 1).toUpperCase() + text.slice(1));
                });
                return texts.join(" ");
            }
            catch (err) {
                return "";
            }
        }
    })
    .filter('fpMethodName', function () {
        return function (input) {
            return fpMethodMap[input] ? fpMethodMap[input] : input;

        }
    })
    .filter('commaSeparated', function () {
        return function (input) {
            try {
                var text = input.toString().trim();
                return text.split(/[\s]+/).join(", ");
            }
            catch (err) {
                return "";
            }
        }
    })
    .filter("dateFallsWithin", [function(){
        return function(input, start_date, date_field, period, invert) {
            return input.filter(function(item){
                var end_date = item[date_field];
                var val = ((Date.parse(end_date) - Date.parse(start_date)) / 1000 / 60 / 60 / 24) < period;
                // if invert is undefined or false return val, else return  !val
                if(!invert)
                    return val;
                else
                    return !val;
            });

        }
    }]);