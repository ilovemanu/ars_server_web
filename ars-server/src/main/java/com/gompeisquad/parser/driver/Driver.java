/**
 *
 */
package com.gompeisquad.parser.driver;

import com.gompeisquad.parser.flight.Flight;
import com.gompeisquad.parser.system.FlightController;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


/**
 * @author blake, alex, liz and Priyanka
 * @since 2016-02-24
 * @version 1.3 2019-04-20
 *
 */
public class Driver {

    /**
     * Entry point for CS509 sample code driver
     *
     * This driver will retrieve the list of airports/airplanes from the CS509 server and print the sorted list
     * to the console
     *
     * @param args is the arguments passed to java vm in format of "CS509.sample teamName" where teamName is a valid team
     */
    public static void main(String[] args) {

        // use scan ui if no args are given
        if (args.length == 0) {
            UserInterface userinterface = new UserInterface();
            userinterface.mainMenu();

            // else use args to run
        } else {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd");

            String inBoundTime = "";

            // args: [TripType, seatClass, depCode, arrCode, outDate, inDate, sortparam]
            // tripType[one-way, round-trip]
            String tripType = args[0];
            // seatClass [coach, firstClass]
            String seatClass = args[1];
            // departure airport
            String departureCode = args[2].toUpperCase();
            // arrival airport
            String arrivalCode = args[3].toUpperCase();
            // outbound date
            String outBoundTime = args[4];
            if (tripType.equalsIgnoreCase("round-trip")) {
                // inbound date
                inBoundTime = args[5];
            }
            // sortParam: options: depTime, arrTime, travelTime, totalPrice
            String sortParam = args[args.length - 1]; // default sort by travelTime

            ArrayList<ArrayList<Flight>> outFlights;
            ArrayList<ArrayList<Flight>> inFlights;


            // get one-way search results
            FlightController controller = new FlightController();
            outFlights = controller.searchDepTimeFlight(departureCode, outBoundTime, arrivalCode, seatClass, outBoundTime);
//			String nextDay= LocalDate.parse(outBoundTime, formatter).plusDays(1).format(formatter);
//			outFlights.addAll(controller.searchDepTimeFlight(departureCode, nextDay, arrivalCode, seatClass, outBoundTime));
            // apply sorter
            controller.sortByParam(sortParam, outFlights, seatClass);


            // display results
            if (outFlights.size() == 0) {
                System.out.println("No " + seatClass + " outbound flights available.");
            } else {
                System.out.println("Outbound flights available:");
                for (ArrayList<Flight> flightList : outFlights) {

                    ArrayList<String> info = FlightController.getInfo(flightList, seatClass);
                    System.out.println("Departure:" + info.get(0) +
                            ", Arrival:" + info.get(1) +
                            ", Duration:" + info.get(2) + "min" +
                            ", Price:" + "$" + info.get(3));

                    for (Flight f : flightList) {
                        System.out.println(f.toLocalString());
                    }

                    System.out.println();
                }
                System.out.println("END of outbound");
                System.out.println();
            }

            // round-trip: test [round-trip coach bos cle 2019_05_04 2019_05_10 travelTime]
            if (tripType.equalsIgnoreCase("round-trip")) {
                inFlights = controller.searchDepTimeFlight(arrivalCode, inBoundTime, departureCode, seatClass, inBoundTime);
                // apply sorter
                controller.sortByParam(sortParam, inFlights, seatClass);

                // display results
                if (inFlights.size() == 0) {
                    System.out.println("No " + seatClass + " inbound flights available.");
                } else {
                    System.out.println("Inbound flights available:");
                    for (ArrayList<Flight> flightList : inFlights) {

                        ArrayList<String> info = FlightController.getInfo(flightList, seatClass);
                        System.out.println("Departure:" + info.get(0) +
                                ", Arrival:" + info.get(1) +
                                ", Duration:" + info.get(2) + "min" +
                                ", Price:" + "$" + info.get(3));

                        for (Flight f : flightList) {
                            System.out.println(f.toLocalString());
                        }

                        System.out.println();
                    }
                    System.out.println("END of inbound");
                }
            }


//			 test reserve: reserve the first flight/flights on the returned list.
//			ArrayList<Flight> selected = flights.get(2);
//			controller.reserveFlight(selected, seatClass);
            // TODO: implement a confirm function for reservation summary
        }

    }
}
