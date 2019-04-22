package com.gompeisquad.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.gompeisquad.parser.flight.Flight;
import com.gompeisquad.parser.system.FlightController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * ARSRequestController.
 */
@RestController
//@CrossOrigin(origins = {"http://localhost:4200"})
@CrossOrigin(origins = "*")
@RequestMapping("api")
public class ARSRequestController {

    private static final String TEAM_NAME = "GompeiSquad";

    @GetMapping("/one-way/{seatClass}/{departureAirPort}/{arrivalAirPort}/{outboundDate}")
    @ResponseBody
    public List<String> searchOneWay(@PathVariable String seatClass, @PathVariable String departureAirPort, @PathVariable String arrivalAirPort, @PathVariable String outboundDate) {
        List<String> results = searchDepartFlightsForDisplay(seatClass, departureAirPort, arrivalAirPort, outboundDate, "travelTime",
                                                             "all");
        results.add("END OF OUTBOUND!");
        results.add("-----------------------------------");

        return results;
    }

    @GetMapping("/one-way/{seatClass}/{departureAirPort}/{arrivalAirPort}/{outboundDate}/{sortParam}/{filterParam}")
    @ResponseBody
    public List<String> sortFilterOneWay(@PathVariable String seatClass, @PathVariable String departureAirPort, @PathVariable String arrivalAirPort,
                                         @PathVariable String outboundDate, @PathVariable String sortParam, @PathVariable String filterParam) {
        List<String> results = searchDepartFlightsForDisplay(seatClass, departureAirPort, arrivalAirPort, outboundDate, sortParam,
                                                             filterParam);
        results.add("END OF OUTBOUND!");
        results.add("-----------------------------------");

        return results;
    }

    //    @GetMapping("/one-way/{seatClass}/{departureAirPort}/{arrivalAirPort}/{outboundDate}")
    //    @ResponseBody
    //    public ArrayList<ArrayList<Flight>> searchOneWay(@PathVariable String seatClass, @PathVariable String departureAirPort, @PathVariable String arrivalAirPort, @PathVariable String outboundDate) {
    //        return searchDepartFlights(seatClass, departureAirPort, arrivalAirPort, outboundDate);
    //    }

    @GetMapping("/round-trip/{seatClass}/{departureAirPort}/{arrivalAirPort}/{outboundDate}/{inboundDate}")
    @ResponseBody
    public List<String> searchRoundTrip(@PathVariable String seatClass, @PathVariable String departureAirPort,
                                        @PathVariable String arrivalAirPort, @PathVariable String outboundDate, @PathVariable String inboundDate) {
        List<String> results = searchDepartFlightsForDisplay(seatClass, departureAirPort, arrivalAirPort, outboundDate, "travelTime",
                                                             "all");
        results.add("END OF OUTBOUND!");
        results.add("----------------------------------------------------------------------");

        // default sort by travel time
        List<String> inboundResults = searchDepartFlightsForDisplay(seatClass, departureAirPort, arrivalAirPort, inboundDate, "travelTime",
                                                                    "all");
        inboundResults.add("END OF INBOUND!");
        inboundResults.add("----------------------------------------------------------------------");

        results.addAll(inboundResults);
        return results;
    }

    @GetMapping("/round-trip/{seatClass}/{departureAirPort}/{arrivalAirPort}/{outboundDate}/{inboundDate}/{sortParam}/{filterParam}")
    @ResponseBody
    public List<String> sortFilterRoundTrip(@PathVariable String seatClass, @PathVariable String departureAirPort,
                                            @PathVariable String arrivalAirPort, @PathVariable String outboundDate,
                                            @PathVariable String inboundDate, @PathVariable String sortParam, @PathVariable String filterParam) {
        List<String> results = searchDepartFlightsForDisplay(seatClass, departureAirPort, arrivalAirPort, outboundDate, sortParam,
                                                             filterParam);
        results.add("END OF OUTBOUND!");
        results.add("----------------------------------------------------------------------");

        // default sort by travel time
        List<String> inboundResults = searchDepartFlightsForDisplay(seatClass, departureAirPort, arrivalAirPort, inboundDate, sortParam,
                                                                    filterParam);
        inboundResults.add("END OF INBOUND!");
        inboundResults.add("----------------------------------------------------------------------");

        results.addAll(inboundResults);
        return results;
    }

    @GetMapping(path = "/{seatClass}/{message}/{flightNumbers}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<String> reserveFlights(@PathVariable String seatClass, @PathVariable String message, @PathVariable String flightNumbers) {
        List<String> results = new ArrayList<>();
        try {
            FlightController controller = new FlightController();
            String[] arrFligthNumber = flightNumbers.split("-");
            ArrayList<String> lFlightNumbers = new ArrayList<>();
            Collections.addAll(lFlightNumbers, arrFligthNumber);

            controller.reserveFlightByFlightNumbersAndSeatClass(lFlightNumbers, seatClass);
        } catch (Exception e) {
            System.out.println(e.toString());

            results.add("Error happened during reservation!");
            return results;
        }

        results.add("Flight(s) reservation succeeded!");
        results.add(message);
        return results;
    }

    @PostMapping(path = "/round-trip/reserve/{seatClass}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<String> reserveRoundTrip(@PathVariable String seatClass, @RequestBody ReserveRoundTripRequestBody rrtr) {
        //                public List<String> reserveRoundTrip(@PathVariable String seatClass, @RequestBody String[] roundTripMessages) {
        FlightController controller = new FlightController();
        List<String> results = new ArrayList<>();
        ArrayList<ArrayList<String>> lFlightNumbers = new ArrayList<>();

        List<String> roundTripMessages = rrtr.getRoundTripMessages();

        try {
            for (String message : roundTripMessages) {
                // "Departure: 2019-05-10T13:41, Arrival: 2019-05-10T20:57, Duration: 436 mins, Price: $92.6, Flight Number: 3005 4389 3708"
                String[] splited1 = message.split(",");
                String flightNumbers = splited1[splited1.length - 1];
                String[] splited2 = flightNumbers.split(":");
                String[] flightNumbersOnly = splited2[splited2.length - 1].split(" ");
                flightNumbersOnly = Arrays.stream(flightNumbersOnly).filter(x -> !x.isEmpty()).toArray(String[]::new);

                lFlightNumbers.add(new ArrayList<String>(Arrays.asList(flightNumbersOnly)));
            }

            for (ArrayList<String> listFlights : lFlightNumbers) {
                controller.reserveFlightByFlightNumbersAndSeatClass(listFlights, seatClass);
            }
        } catch (Exception e) {
            e.printStackTrace();

            results.add("Error happened during roundt rip reservation!");
            return results;
        }

        results.add("Round trip flights reservation succeeded, enjoy your journey!");
        results.addAll(roundTripMessages);

        return results;
    }

    //    @GetMapping("/round-trip/{seatClass}/{departureAirPort}/{arrivalAirPort}/{outboundDate}/{inboundDate}")
    //    @ResponseBody
    //    public ArrayList<ArrayList<Flight>> searchRoundTrip(@PathVariable String seatClass, @PathVariable String departureAirPort,
    //                                        @PathVariable String arrivalAirPort, @PathVariable String outboundDate, @PathVariable String inboundDate) {
    //        ArrayList<ArrayList<Flight>> results = searchDepartFlights(seatClass, departureAirPort, arrivalAirPort, outboundDate);
    //
    //        ArrayList<ArrayList<Flight>> inboundResults = searchDepartFlights(seatClass, departureAirPort, arrivalAirPort, inboundDate);
    //
    //        results.addAll(inboundResults);
    //        return results;
    //    }

    /**
     * this method is used for both of outbound and inbound search for round-trip search when search for inbound, treat the inbound params
     * as departure, leave from destination to original departure.
     *
     * @param seatClass
     * @param departureAirPort
     * @param arrivalAirPort
     * @param outboundDate
     * @param sortParam
     * @return
     */
    private List<String> searchDepartFlightsForDisplay(String seatClass, String departureAirPort, String arrivalAirPort, String outboundDate, String sortParam, String filterParam) {
        List<String> results = new ArrayList<>();
        ArrayList<ArrayList<Flight>> outFlights;

        FlightController controller = new FlightController();
        outFlights = controller.searchDepTimeFlight(departureAirPort.toUpperCase(), outboundDate, arrivalAirPort.toUpperCase(), seatClass,
                                                    outboundDate);
        controller.sortByParam(sortParam, outFlights, seatClass);

        for (ArrayList<Flight> flightList : outFlights) {
            List<String> info = FlightController.getInfo(flightList, seatClass);

            if (filterParam.equalsIgnoreCase("all") || filterParam.equals(String.valueOf(flightList.size()))) {
                String output = String.format("Departure: %s, Arrival: %s, Duration: %s mins, Price: $%s, Flight Number:", info.get(0),
                                              info.get(1), info.get(2), info.get(3));
                for (Flight f : flightList) {
                    output += " ";
                    output += f.number();
                }

                results.add(output);

                for (Flight f : flightList) {
                    results.add(f.toLocalString());
                }
                results.add("--");
            }
        }
        return results;
    }

    /**
     * this method is used for both of outbound and inbound search for round-trip search when search for inbound, treat the inbound params
     * as departure, leave from destination to original departure.
     *
     * @param seatClass
     * @param departureAirPort
     * @param arrivalAirPort
     * @param outboundDate
     * @return
     */
    private ArrayList<ArrayList<Flight>> searchDepartFlights(@PathVariable String seatClass, @PathVariable String departureAirPort, @PathVariable String arrivalAirPort, @PathVariable String outboundDate) {
        //        List<String> results = new ArrayList<>();
        //        ArrayList<ArrayList<Flight>> outFlights;

        FlightController controller = new FlightController();
        return controller.searchDepTimeFlight(departureAirPort, outboundDate, arrivalAirPort, seatClass, outboundDate);

        //        for (ArrayList<Flight> flightList : outFlights) {
        //            List<String> info = FlightController.getInfo(flightList, seatClass);
        //            String output = String.format("Departure: %s, Arrival: %s, Duration: %s mins, Price: $%s", info.get(0), info.get(1), info.get(2), info.get(3));
        //            results.add(output);
        //
        //            for (Flight f : flightList) {
        //                results.add(f.toLocalString());
        //            }
        //
        //            results.add("--");
        //        }
        //        return results;
    }

    public static class ReserveRoundTripRequestBody {

        List<String> roundTripMessages;

        public ReserveRoundTripRequestBody() {
        }

        public List<String> getRoundTripMessages() {
            return roundTripMessages;
        }

        public void setRoundTripMessages(List<String> roundTripMessages) {
            this.roundTripMessages = roundTripMessages;
        }
    }
}
