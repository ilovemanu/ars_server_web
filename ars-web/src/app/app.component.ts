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
  departureAirPort: string;
  arrvingAirport: string;
  departureDate: Date;
  arrivingDate: Date;
  tripType = 'roundTrip';
  seatClass = 'coach'

  constructor(private api: ApiService) {
  }


  onSubmit() {
    this.api.findDeparture(this.departureAirPort, this.departureDate).subscribe(data => {
      this.flights = data;

      console.log(this.tripType);
      console.log(this.seatClass);
    });
  }
}
