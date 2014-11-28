package org.ei.drishti.view.preProcessor;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import org.ei.drishti.util.DateUtil;
import org.ei.drishti.view.contract.*;
import org.ei.drishti.view.contract.pnc.*;
import org.joda.time.LocalDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.ei.drishti.util.DateUtil.dayDifference;
import static org.ei.drishti.util.DateUtil.formatDate;

public class PNCClientPreProcessor {
    private static final String PNC_IDENTIFIER = "PNC";

    private List<Integer> defaultVisitDays = new ArrayList<Integer>(Arrays.asList(1, 3, 7));
    private List<PNCCircleDatum> circleData;
    private List<PNCStatusDatum> statusData;
    private List<PNCLineDatum> lineData;
    private List<PNCTickDatum> tickData;
    private int currentDay;
    private PNCClient client;

    public PNCClientPreProcessor(PNCClient client) {
        this.client = client;
        circleData = new ArrayList<PNCCircleDatum>();
        statusData = new ArrayList<PNCStatusDatum>();
        tickData = new ArrayList<PNCTickDatum>();
        lineData = new ArrayList<PNCLineDatum>();
        currentDay = DateUtil.dayDifference(client.deliveryDate(), DateUtil.today());
    }

    public PNCClient preProcess() {
        populateExpectedVisitDates();
        List<ServiceProvidedDTO> first7DaysVisits = getFirst7DaysVisits();
        createViewElements(currentDay, first7DaysVisits);
        return client;
    }

    private void populateExpectedVisitDates() {
        List<ServiceProvidedDTO> expectedVisits = new ArrayList<ServiceProvidedDTO>();
        for (Integer visitDay : defaultVisitDays) {
            LocalDate expectedVisitDate = client.deliveryDate().plusDays(visitDay);
            ServiceProvidedDTO expectedVisit = new ServiceProvidedDTO("PNC", visitDay, formatDate(expectedVisitDate, "YYYY-MM-dd"));
            expectedVisits.add(expectedVisit);
        }
        client.withExpectedVisits(expectedVisits);
    }

    private void createViewElements(int numberOfDaysFromDeliveryDate, List<ServiceProvidedDTO> first7DaysVisits) {
        createViewElementsBasedOnExpectedVisits(client, first7DaysVisits);
        createViewDataBasedOnServicesProvided(first7DaysVisits);
        PNCStatusColor pncVisitStatusColor = getPNCVisitStatusColor(client, first7DaysVisits, numberOfDaysFromDeliveryDate);
        createTickData();
        createLineData();
        ArrayList<PNCVisitDaysDatum> pncVisitDaysData = generateDayNumbers();
        PNCFirstSevenDaysVisits pncFirstSevenDaysVisits = new PNCFirstSevenDaysVisits(circleData, statusData,
                pncVisitStatusColor, tickData, lineData, pncVisitDaysData);
        client.withFirstSevenDaysVisit(pncFirstSevenDaysVisits);
    }

    private ArrayList<PNCVisitDaysDatum> generateDayNumbers() {
        ArrayList<PNCVisitDaysDatum> visitDaysData = new ArrayList<PNCVisitDaysDatum>();
        for (PNCCircleDatum pncCircleDatum : circleData) {
            if (pncCircleDatum.day() > currentDay || pncCircleDatum.type().equals(PNCVisitType.ACTUAL)) {
                visitDaysData.add(new PNCVisitDaysDatum(pncCircleDatum.day(), pncCircleDatum.type()));
            }
        }
        return visitDaysData;
    }

    private void createLineData() {
        if (currentDay > 1) {
            lineData.add(new PNCLineDatum(1, Math.min(7, currentDay), PNCVisitType.ACTUAL));
        }
        if (currentDay < 7) {
            lineData.add(new PNCLineDatum(Math.max(1, currentDay), 7, PNCVisitType.EXPECTED));
        }
    }

    private void createTickData() {
        List<Integer> possibleTickDays = Arrays.asList(2, 4, 5, 6);
        List<Integer> circleDays = new ArrayList<Integer>();
        for (PNCCircleDatum pncCircleDatum : circleData) {
            circleDays.add(pncCircleDatum.day());
        }
        for (Integer tickDay : possibleTickDays) {
            if (!circleDays.contains(tickDay)) {
                if (tickDay > currentDay) {
                    tickData.add(new PNCTickDatum(tickDay, PNCVisitType.EXPECTED));
                } else {
                    tickData.add(new PNCTickDatum(tickDay, PNCVisitType.ACTUAL));
                }
            }
        }
    }

    private PNCStatusColor getPNCVisitStatusColor(PNCClient client, List<ServiceProvidedDTO> first7DaysVisits, int numberOfDaysFromDeliveryDate) {
        PNCStatusColor statusColor = PNCStatusColor.YELLOW;
        if (first7DaysVisits.isEmpty() && numberOfDaysFromDeliveryDate > 1) {
            statusColor = PNCStatusColor.RED;
        } else if (actualVisitsHaveBeenDoneOnExpectedDays(client, first7DaysVisits, numberOfDaysFromDeliveryDate)) {
            statusColor = PNCStatusColor.GREEN;
        }
        return statusColor;
    }

    private boolean actualVisitsHaveBeenDoneOnExpectedDays(PNCClient client, List<ServiceProvidedDTO> first7DaysVisits, int numberOfDaysFromDeliveryDate) {
        List<Integer> expectedVisitDaysTillToday =
                getExpectedVisitDaysTillToday(client.expectedVisits(), numberOfDaysFromDeliveryDate); //valid_expected_visit_days

        ArrayList<Integer> actualVisitDays = getActualVisitDays(first7DaysVisits); //valid_actual_visit_days
        for (Integer expectedVisitDay : expectedVisitDaysTillToday) {
            if (!actualVisitDays.contains(expectedVisitDay)) {
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

    private ArrayList<Integer> getExpectedVisitDaysTillToday(List<ServiceProvidedDTO> expectedVisits, int numberOfDaysFromDeliveryDate) {
        ArrayList<Integer> expectedVisitDaysTillToday = new ArrayList<Integer>();
        for (ServiceProvidedDTO expectedVisit : expectedVisits) {
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
        for (ServiceProvidedDTO expectedVisit : client.expectedVisits()) {
            LocalDate expectedVisitDate = DateUtil.getLocalDate(expectedVisit.date());
            int expectedVisitDay = DateUtil.dayDifference(client.deliveryDate(), expectedVisitDate);

            if (expectedVisitDay >= currentDay) {
                circleData.add(new PNCCircleDatum(expectedVisitDay, PNCVisitType.EXPECTED, false));
            } else if (expectedVisitDay < currentDay) {
                if (!hasVisitHappenedOn(expectedVisitDay, first7DaysVisits)) {
                    //TODO : check if this boolean here is necessary
                    PNCCircleDatum circleDatum = new PNCCircleDatum(expectedVisitDay, PNCVisitType.EXPECTED, true);
                    circleData.add(circleDatum);
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

    private List<ServiceProvidedDTO> getFirst7DaysVisits() {
        int VISIT_END_OFFSET_DAY_COUNT = 7;
        LocalDate endDate = client.deliveryDate().plusDays(VISIT_END_OFFSET_DAY_COUNT);
        List<ServiceProvidedDTO> validServices = getValidServicesProvided(client, endDate);
        validServices = removeDuplicateServicesAndReturnList(validServices);
        Collections.sort(validServices, new DateComparator());//Ascending sort based on the date
        findAndSetTheVisitDayOfTheServicesWithRespectToDeliveryDate(validServices, client.deliveryDate());//Find the day offset from the delivery date for the visits
        return validServices;
    }

    private List<ServiceProvidedDTO> getValidServicesProvided(PNCClient client, final LocalDate visitEndDate) {
        List<ServiceProvidedDTO> validServicesProvided = new ArrayList<ServiceProvidedDTO>();
        List<ServiceProvidedDTO> servicesProvided = client.servicesProvided();
        if (servicesProvided != null && servicesProvided.size() > 0) {
            Iterables.addAll(validServicesProvided, Iterables.filter(servicesProvided, new Predicate<ServiceProvidedDTO>() {
                @Override
                public boolean apply(ServiceProvidedDTO serviceProvided) {
                    LocalDate serviceProvidedDate = serviceProvided.localDate();
                    return PNC_IDENTIFIER.equalsIgnoreCase(serviceProvided.name())
                            && (serviceProvidedDate.isBefore(visitEndDate) || serviceProvidedDate.equals(visitEndDate));
                }
            }));
        }
        return validServicesProvided;
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

    class DateComparator implements Comparator<ServiceProvidedDTO> {

        @Override
        public int compare(ServiceProvidedDTO serviceProvidedDTO1, ServiceProvidedDTO serviceProvidedDTO2) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date1;
            Date date2;
            try {
                date1 = simpleDateFormat.parse(serviceProvidedDTO1.date());
                date2 = simpleDateFormat.parse(serviceProvidedDTO2.date());
                return date1.compareTo(date2);
            } catch (ParseException e) {
                //TODO
            }
            return -1;
        }
    }
}