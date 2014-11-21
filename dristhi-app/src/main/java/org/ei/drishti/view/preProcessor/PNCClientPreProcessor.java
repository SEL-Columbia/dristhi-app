package org.ei.drishti.view.preProcessor;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import org.ei.drishti.R;
import org.ei.drishti.util.DateUtil;
import org.ei.drishti.view.contract.*;
import org.joda.time.LocalDate;

import java.util.*;

import static org.ei.drishti.util.DateUtil.dayDifference;
import static org.ei.drishti.util.DateUtil.formatDate;

public class PNCClientPreProcessor {
    private static final String PNC_IDENTIFIER = "PNC";
    private static int VISIT_END_OFFSET_DAY_COUNT = 7;

    private List<Integer> defaultVisits = new ArrayList<Integer>(Arrays.asList(1, 3, 7));
    private ArrayList<PNCCircleDatum> circleData;
    private ArrayList<PNCStatusDatum> statusData;

    public PNCClientPreProcessor() {
        circleData = new ArrayList<PNCCircleDatum>();
        statusData = new ArrayList<PNCStatusDatum>();
    }

    public PNCClient preProcess(PNCClient client) {
        populateExpectedVisitDates(client);
        processFirstSevenDaysVisit(client);
//        int currentDay = DateUtil.dayDifference(DateUtil.today(), deliveryDate());
//        List<PNCCircleDatum> circleData = new ArrayList<PNCCircleDatum>();
//        List<PNCStatusDatum> statusData = new ArrayList<PNCStatusDatum>();
//        for (ExpectedVisit expectedVisit : expectedVisits) {
//            LocalDate expectedVisitDate = getLocalDate(expectedVisit.date());
//            int expectedVisitDay = dayDifference(expectedVisitDate, deliveryDate());
//            int numberOfVisits = new Filter<ServiceProvidedDTO>().applyFilterWithClause(firstSevenDaysServices, new PNCVisitDayClause(expectedVisitDay)).size();
//
//        }
        return client;
    }

    public void populateExpectedVisitDates(PNCClient client) {
        List<ExpectedVisit> expectedVisits = new ArrayList<ExpectedVisit>();
        for (Integer offset : defaultVisits) {
            LocalDate expectedVisitDate = client.deliveryDate().plusDays(offset);
            ExpectedVisit expectedVisit = new ExpectedVisit(offset, formatDate(expectedVisitDate, "dd/MM/YYYY"));
            expectedVisits.add(expectedVisit);
        }
        client.withExpectedVisits(expectedVisits);
    }

    private void processFirstSevenDaysVisit(PNCClient client) {
        List<ServiceProvidedDTO> first7DaysVisits = getFirst7DaysVisits(client);

        LocalDate deliveryDate = client.deliveryDate(); //delivery_date_ts
        int numberOfDaysFromDeliveryDate = getNumberOfDaysFromDeliveryDate(deliveryDate); //current_day

        /// create circle data and create status icons (only on expected visits days if the visit was done)
        /// based on expected visits
        createViewElementsBasedOnExpectedVisits(client, first7DaysVisits);
        /// create circle data based on actual data
        createViewDataBasedOnServicesProvided(first7DaysVisits);

        client.withPNCVisitCircles(circleData);
        client.withPNCStatusData(statusData);

        setPNCVisitStatusColor(client, first7DaysVisits, numberOfDaysFromDeliveryDate);

/*
        // create tick data, tick only exist where there are no circles and can only exist on days 2,4,5 and 6
        var tick_days =[2, 4, 5, 6].filter(function(d) {
            return circle_datas.filter(function(circle_data) {
                return circle_data.day == = d;
            }).length == = 0;
        });

        var tick_datas =[];
        tick_days.forEach(function(tick_day) {
            /// check if its in the future (TODO: perhaps have a function that determines if we are beyond current date)
            if (tick_day > current_day) {
                tick_datas.push({
                        day:tick_day,
                        type:'expected'
                });
            } else {
                tick_datas.push({
                        day:tick_day,
                        type:'actual'
                });
            }
        });
        client.visits.first_7_days.ticks = tick_datas;

        /// create line data
        var lines_datas =[];

        /// check if we have an actual line from day 1 to current day
        if (current_day > 1) {
            lines_datas.push({
                    start:1,
                    end:Math.min(7, current_day),
                    type:'actual'
            });
        }

        /// check if we have an expected line from current_day to day 7
        if (current_day < 7) {
            lines_datas.push({
                    start:Math.max(1, current_day),
                    end:7,
                    type:'expected'
            });
        }

        client.visits.first_7_days.lines = lines_datas;

        /// generate day nos - put a number wherever we have a visit and where we have an expected visit in the future
        var day_nos = circle_datas
                .filter(function(d) {
            return d.type == = 'actual' || d.day > current_day;
        })
        .map(function(d) {
            return {
                    day:d.day,
                    type:d.type
            };
        });

        client.visits.first_7_days.day_nos = day_nos;

*/
    }

    private void setPNCVisitStatusColor(PNCClient client, List<ServiceProvidedDTO> first7DaysVisits, int numberOfDaysFromDeliveryDate) {
        client.withPNCVisitStatusColor(R.color.pnc_circle_yellow);
        if (first7DaysVisits.isEmpty() && numberOfDaysFromDeliveryDate > 1) {
            client.withPNCVisitStatusColor(R.color.pnc_circle_red);
        } else if (actualVisitsHaveBeenDoneOnExpectedDays(client,first7DaysVisits,numberOfDaysFromDeliveryDate)) {
            client.withPNCVisitStatusColor(R.color.pnc_circle_green);
        }
    }


    private boolean actualVisitsHaveBeenDoneOnExpectedDays(PNCClient client, List<ServiceProvidedDTO> first7DaysVisits, int numberOfDaysFromDeliveryDate) {
        List<Integer> expectedVisitDaysTillToday =
                getExpectedVisitDaysTillToday(client.getExpectedVisits(), numberOfDaysFromDeliveryDate); //valid_expected_visit_days

        ArrayList<Integer> actualVisitDays = getActualVisitDays(first7DaysVisits); //valid_actual_visit_days
        for (Integer expectedVisitDay : expectedVisitDaysTillToday) {
            if(!actualVisitDays.contains(expectedVisitDay)){
                return false;
            }
        }
        return true;
    }


    private ArrayList<Integer> getActualVisitDays(List<ServiceProvidedDTO> first7DaysVisits) {
        ArrayList<Integer> actualVisitDays = new ArrayList<Integer>();
        for (ServiceProvidedDTO serviceProvided : first7DaysVisits) {
            actualVisitDays.add(serviceProvided.day());
        }
        return actualVisitDays;
    }

    private ArrayList<Integer> getExpectedVisitDaysTillToday(List<ExpectedVisit> expectedVisits, int numberOfDaysFromDeliveryDate) {
        ArrayList<Integer> expectedVisitDaysTillToday = new ArrayList<Integer>();
        for (ExpectedVisit expectedVisit : expectedVisits) {
            if (expectedVisit.day() < numberOfDaysFromDeliveryDate) {
                expectedVisitDaysTillToday.add(expectedVisit.day());
            }
        }
        return expectedVisitDaysTillToday;
    }

    private void createViewDataBasedOnServicesProvided(List<ServiceProvidedDTO> first7DaysVisits) {
        for (ServiceProvidedDTO serviceProvided : first7DaysVisits) {
            circleData.add(new PNCCircleDatum(serviceProvided.day(), PNCVisitType.ACTUAL, true));
        }
    }

    private void createViewElementsBasedOnExpectedVisits(PNCClient client, List<ServiceProvidedDTO> first7DaysVisits) {
        for (ExpectedVisit expectedVisit : client.getExpectedVisits()) {
            LocalDate expectedVisitDate = DateUtil.getLocalDate(expectedVisit.date());
            int expectedVisitDay = DateUtil.dayDifference(client.deliveryDate(), expectedVisitDate);
            int currentDay = DateUtil.dayDifference(client.deliveryDate(), DateUtil.today());

            if (expectedVisitDay >= currentDay) {
                circleData.add(new PNCCircleDatum(expectedVisitDay, PNCVisitType.EXPECTED, false));
            } else if (expectedVisitDay < currentDay) {
                if (!hasVisitHappenedOn(expectedVisitDay, first7DaysVisits)) {
                    //TODO : check if this boolean here is necessary
                    circleData.add(new PNCCircleDatum(expectedVisitDay, PNCVisitType.EXPECTED, true));
                    statusData.add(new PNCStatusDatum(expectedVisitDay, PNCVisitStatus.MISSED));
                } else {
                    statusData.add(new PNCStatusDatum(expectedVisitDay, PNCVisitStatus.DONE));
                }
            }
        }
    }

    private boolean hasVisitHappenedOn(int expectedVisitDay, List<ServiceProvidedDTO> first7DaysVisits) {
        for (ServiceProvidedDTO first7DaysVisit : first7DaysVisits) {
            if (first7DaysVisit.day() == expectedVisitDay)
                return true;
        }
        return false;
    }

    private int getNumberOfDaysFromDeliveryDate(LocalDate deliveryDate) {
        return DateUtil.dayDifference(deliveryDate, new LocalDate());
    }

    private List<ServiceProvidedDTO> getFirst7DaysVisits(PNCClient client) {
        LocalDate endDate = client.deliveryDate().plusDays(VISIT_END_OFFSET_DAY_COUNT);
        List<ServiceProvidedDTO> validServices = getValidServicesProvided(client, endDate);
        validServices = removeDuplicateServicesAndReturnList(validServices);
        Collections.sort(validServices);//Ascending sort based on the date
        findAndSetTheVisitDayOfTheServicesWithRespectToDeliveryDate(validServices, client.deliveryDate());//Find the day offset from the delivery date for the visits
        return validServices;
    }

    private List<ServiceProvidedDTO> getValidServicesProvided(PNCClient client, final LocalDate visitEndDate) {
        List<ServiceProvidedDTO> results = new ArrayList<ServiceProvidedDTO>();
        Iterables.addAll(results, Iterables.filter(client.getServicesProvided(), new Predicate<ServiceProvidedDTO>() {
            @Override
            public boolean apply(ServiceProvidedDTO serviceProvided) {
                return PNC_IDENTIFIER.equalsIgnoreCase(serviceProvided.name()) && serviceProvided.localDate().isBefore(visitEndDate);
            }
        }));
        return results;
    }

    private List<ServiceProvidedDTO> removeDuplicateServicesAndReturnList(List<ServiceProvidedDTO> serviceList) {
        if (serviceList != null) {
            Set<ServiceProvidedDTO> serviceSet = new HashSet<ServiceProvidedDTO>(serviceList);
            //To function this correctly the hash code of the object and equals method should override and should return the proper values
            serviceList.clear();
            serviceList.addAll(serviceSet);
        }
        return serviceList;
    }

    private List<ServiceProvidedDTO> findAndSetTheVisitDayOfTheServicesWithRespectToDeliveryDate(
            List<ServiceProvidedDTO> services, LocalDate deliveryDate) {
        if (services != null) {
            for (ServiceProvidedDTO service : services) {
                service.withDay(dayDifference(service.localDate(), deliveryDate));
            }
        }
        return services;
    }
}
