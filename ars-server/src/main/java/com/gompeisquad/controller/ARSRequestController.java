package com.gompeisquad.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.gompeisquad.parser.dao.ServerInterface;
import com.gompeisquad.parser.flight.Flight;
import com.gompeisquad.parser.flight.Flights;
import com.gompeisquad.parser.system.FlightController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{airPort}/{departureDate}")
    @ResponseBody
    public List<String> searchForDeparture(@PathVariable String airPort, @PathVariable String departureDate) {
        List<String> lFlight = new ArrayList<>();
//        Flights flights = ServerInterface.INSTANCE.getFlights(TEAM_NAME, airPort.toUpperCase(), departureDate);
//        FlightController controller = new FlightController();
//        controller.searchDepTimeFlight(departureCode, outBoundTime, arrivalCode, seatClass, outBoundTime);
//
//        Collections.sort(flights);
//
//        for (Flight flight : flights) {
//            lFlight.add(flight.toString());
//        }

//        List<String> list =  new ArrayList<>();
//        list.add(airPort);
//        list.add(departureDate);

        return lFlight;
    }

}
