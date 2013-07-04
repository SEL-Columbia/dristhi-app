angular.module("smartRegistry.services")
    .service('SmartHelper', function () {
        var daysBetween = function (start_date, end_date) {
            return (end_date - start_date) / 1000 / 60 / 60 / 24;
        };

        return {
            daysBetween: daysBetween,
            zeroPad: function(num, size) {
                if(size === undefined)
                    size = 2;
                var s = "00" + num;
                return s.substr(s.length-size);
            },
            childsAge: function(dob, ref_date) {
                var days_since = daysBetween(dob, ref_date);
                if(days_since < 28)
                {
                    return Math.floor(days_since) + "d";
                }
                else if(days_since < 119)
                {
                    return Math.floor(days_since/7) + "w";
                }
                else if(days_since < 672)
                {
                    return Math.floor(days_since/7/4) + "m";
                }
                else
                {
                    return Math.floor(days_since/7/4/12) + "y";
                }
            }
        }
    });
