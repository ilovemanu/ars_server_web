import {Component} from '@angular/core';
import {ApiService} from './service/api.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'ars-web';
  flights: string[];
  listFlights: Flight[][];
  inboundDate: string;
  tripType: string;

  seatClass: string;
  departureAirPort: string;
  arrivalAirPort: string;
  outboundDate: string;

  reservationMessage: string;

  constructor(private api: ApiService) {
  }


  onSubmit() {
    if (this.tripType === 'oneWay') {
      // seatClass: string, departureAirPort: string, arrivalAirPort: string, outboundDate: string
      this.api.searchOneWay(this.seatClass, this.departureAirPort, this.arrivalAirPort, this.outboundDate).subscribe(data => {
        this.flights = data;

        console.log(this.tripType);
        console.log(this.seatClass);
      });
    } else {
      this.api.searchRoundTrip(this.seatClass, this.departureAirPort, this.arrivalAirPort, this.outboundDate, this.inboundDate)
          .subscribe(data => {
        this.flights = data;

        console.log(this.tripType);
        console.log(this.seatClass);
      });
    }
  }

  reserve(message: string) {
    this.api.reserve(message, this.seatClass).subscribe(data => {
      if (data) {
        this.reservationMessage = data;
        console.log(this.reservationMessage);
        console.log(this.seatClass);
      }
    });
  }
}
