angular.module("smartRegistry.services")
    .service('SmartHelper', function () {
        return {
            daysBetween: function (start_date, end_date) {
                return (end_date - start_date) / 1000 / 60 / 60 / 24;
            },
            zeroPad: function(num, size) {
                if(size === undefined)
                    size = 2;
                var s = "00" + num;
                return s.substr(s.length-size);
            }
        }
    });
