interface Flight {
  // Airplane type of the flight
  mAirplane: string;
  // FlightTime in minutes
  mFlightTime: number;
  // Number of the flight
  mNumber: string;
  // Code for departure airport
  mDepartureAirport: string;
  // Code for arrival airport
  mArrivalAirport: string;
  /*
   * Departure and Arrival time of the flight.
   * Set to LocalDateTime.
   */
  mDepartureTime: Date;
  mArrivalTime: Date;
  mDepartureLocalTime: Date;
  mArrivalLocalTime: Date;
  /*
   * firstClass and coachPrice
   * Set to String for now.
   */
  mFirstClassPrice: string;
  mCoachPrice: string;

  // # of reserved first class seats
  mFirstClassReserved: number;
  // # of reserved coach seats
  mCoachReserved: number;
}
