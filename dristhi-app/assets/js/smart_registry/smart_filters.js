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
    });