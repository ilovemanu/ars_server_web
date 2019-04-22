package com.gompeisquad.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.gompeisquad.parser.flight.Flight;
import com.gompeisquad.parser.system.FlightController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
        List<String> results = searchDepartFlightsForDisplay(seatClass, departureAirPort, arrivalAirPort, outboundDate, "travelTime");
        results.add("END OF OUTBOUND!");
        results.add("-----------------------------------");

        return results;
    }

    @GetMapping("/one-way/{seatClass}/{departureAirPort}/{arrivalAirPort}/{outboundDate}/{sortParam}")
    @ResponseBody
    public List<String> sortOneWay(@PathVariable String seatClass, @PathVariable String departureAirPort, @PathVariable String arrivalAirPort,
                                   @PathVariable String outboundDate, @PathVariable String sortParam) {
        List<String> results = searchDepartFlightsForDisplay(seatClass, departureAirPort, arrivalAirPort, outboundDate, sortParam);
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
        List<String> results = searchDepartFlightsForDisplay(seatClass, departureAirPort, arrivalAirPort, outboundDate, "travelTime");
        results.add("END OF OUTBOUND!");
        results.add("----------------------------------------------------------------------");

        // default sort by travel time
        List<String> inboundResults = searchDepartFlightsForDisplay(seatClass, departureAirPort, arrivalAirPort, inboundDate, "travelTime");
        inboundResults.add("END OF INBOUND!");
        inboundResults.add("----------------------------------------------------------------------");

        results.addAll(inboundResults);
        return results;
    }

    @GetMapping("/round-trip/{seatClass}/{departureAirPort}/{arrivalAirPort}/{outboundDate}/{inboundDate}/{sortParam}")
    @ResponseBody
    public List<String> sortRoundTrip(@PathVariable String seatClass, @PathVariable String departureAirPort,
                                        @PathVariable String arrivalAirPort, @PathVariable String outboundDate,
                                        @PathVariable String inboundDate, @PathVariable String sortParam) {
        List<String> results = searchDepartFlightsForDisplay(seatClass, departureAirPort, arrivalAirPort, outboundDate, sortParam);
        results.add("END OF OUTBOUND!");
        results.add("----------------------------------------------------------------------");

        // default sort by travel time
        List<String> inboundResults = searchDepartFlightsForDisplay(seatClass, departureAirPort, arrivalAirPort, inboundDate, sortParam);
        inboundResults.add("END OF INBOUND!");
        inboundResults.add("----------------------------------------------------------------------");

        results.addAll(inboundResults);
        return results;
    }


    @GetMapping(path = "/{seatClass}/{flightNumbers}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<String> reserveFlights(@PathVariable String seatClass, @PathVariable String flightNumbers) {
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
        return results;


        //return this.http.get<string[]>(`${this.departureDateUrl}/${seatClass}/${flightNumbersOnly}`);
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
     * this method is used for both of outbound and inbound search for round-trip search
     * when search for inbound, treat the inbound params as departure, leave from destination to
     * original departure.
     *
     * @param seatClass
     * @param departureAirPort
     * @param arrivalAirPort
     * @param outboundDate
     * @param sortParam
     * @return
     */
    private List<String> searchDepartFlightsForDisplay(String seatClass, String departureAirPort, String arrivalAirPort, String outboundDate, String sortParam) {
        List<String> results = new ArrayList<>();
        ArrayList<ArrayList<Flight>> outFlights;

        FlightController controller = new FlightController();
        outFlights = controller.searchDepTimeFlight(departureAirPort, outboundDate, arrivalAirPort, seatClass, outboundDate);
        controller.sortByParam(sortParam, outFlights, seatClass);

        for (ArrayList<Flight> flightList : outFlights) {
            List<String> info = FlightController.getInfo(flightList, seatClass);
            String output = String.format("Departure: %s, Arrival: %s, Duration: %s mins, Price: $%s, Flight Number:", info.get(0), info.get(1), info.get(2), info.get(3));
            for(Flight f : flightList) {
                output += " ";
                output += f.number();
            }

            results.add(output);

            for (Flight f : flightList) {
                results.add(f.toLocalString());
            }

            results.add("--");
        }
        return results;
    }



    /**
     * this method is used for both of outbound and inbound search for round-trip search
     * when search for inbound, treat the inbound params as departure, leave from destination to
     * original departure.
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
}
