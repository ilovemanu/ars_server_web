import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {DatePipe} from '@angular/common';


@Injectable()
export class ApiService {

  private departureDateUrl: string;

  constructor(private http: HttpClient, private datePipe: DatePipe) {
    this.departureDateUrl = 'http://localhost:8080/api';
  }

  // public findDeparture(airPort: string, departureDate: Date): Observable<string[]> {
  //   // console.log(`${this.departureDateUrl}/${departureAirPort}/${departureDate}`);
  //   const departDate = this.datePipe.transform(departureDate, 'yyyy_MM_dd');
  //   return this.http.get<string[]>(`${this.departureDateUrl}/${airPort}/${departDate}`);
  // }

  public searchOneWay(seatClass: string, departureAirPort: string, arrivalAirPort: string, outboundDate: string): Observable<string[]> {
    const departDate = this.datePipe.transform(outboundDate, 'yyyy_MM_dd');
    return this.http.get<string[]>(`${this.departureDateUrl}/one-way/${seatClass}/${departureAirPort}/${arrivalAirPort}/${departDate}`);

  }

  public searchRoundTrip(seatClass: string, departureAirPort: string, arrivalAirPort: string, outboundDate: string,
                         inboundDate: string): Observable<string[]> {
    const departDate = this.datePipe.transform(outboundDate, 'yyyy_MM_dd');
    const roundTripInboundDate = this.datePipe.transform(inboundDate, 'yyyy_MM_dd');

    return this.http.get<string[]>(
      `${this.departureDateUrl}/round-trip/${seatClass}/${departureAirPort}/${arrivalAirPort}/${departDate}/${roundTripInboundDate}`);
  }

  public reserve(message: string, seatClass: string): Observable<string[]> {
    const flightNumbers = message.split(',').pop();
    let flightNumbersOnly = flightNumbers.split(':').pop().split(' ');
    flightNumbersOnly = flightNumbersOnly.filter(x => x);

    console.log('flightNumbers: ' + flightNumbers);
    console.log('flightNumbersOnly: ' + flightNumbersOnly);

    const paramFlightNumbers = flightNumbersOnly.join('-');

    console.log(`${this.departureDateUrl}/${seatClass}/${paramFlightNumbers}`);

    return this.http.get<string[]>(`${this.departureDateUrl}/${seatClass}/${message}/${paramFlightNumbers}`);

  }

  public onSortFilterOneWay(seatClass: string, departureAirPort: string, arrivalAirPort: string,
                            outboundDate: string, sortParam: string, filterParam: string) {
    const departDate = this.datePipe.transform(outboundDate, 'yyyy_MM_dd');
    return this.http.get<string[]>(
      `${this.departureDateUrl}/one-way/${seatClass}/${departureAirPort}/${arrivalAirPort}/${departDate}/${sortParam}/${filterParam}`);
  }

  public onSortFilterRoundTrip(seatClass: string, departureAirPort: string, arrivalAirPort: string, outboundDate: string,
                               inboundDate: string, sortParam: string, filterParam: string): Observable<string[]> {
    const departDate = this.datePipe.transform(outboundDate, 'yyyy_MM_dd');
    const roundTripInboundDate = this.datePipe.transform(inboundDate, 'yyyy_MM_dd');

    return this.http.get<string[]>(
      `${this.departureDateUrl}/round-trip/${seatClass}/${departureAirPort}/${arrivalAirPort}/${departDate}/${roundTripInboundDate}/${sortParam}/${filterParam}`);
  }
}
