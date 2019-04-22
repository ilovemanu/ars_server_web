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
  returnedResults: string[];
  // listFlights: Flight[][];
  inboundDate: string;
  tripType: string;

  seatClass: string;
  departureAirPort: string;
  arrivalAirPort: string;
  outboundDate: string;

  reservationMessage: string;
  sortParam = 'travelTime';
  filterParam = 'all';

  searchDisabled = false;
  loading = false;

  constructor(private api: ApiService) {
  }


  onSubmit() {
    this.loading = true;
    if (this.tripType === 'oneWay') {
      // seatClass: string, departureAirPort: string, arrivalAirPort: string, outboundDate: string
      this.api.searchOneWay(this.seatClass, this.departureAirPort, this.arrivalAirPort, this.outboundDate).subscribe(data => {
        this.flights = data;
        this.loading = false;

        console.log(this.tripType);
        console.log(this.seatClass);
      });
    } else {
      this.api.searchRoundTrip(this.seatClass, this.departureAirPort, this.arrivalAirPort, this.outboundDate, this.inboundDate)
        .subscribe(data => {
          this.flights = data;
          this.loading = false;

          console.log(this.tripType);
          console.log(this.seatClass);
        });
    }
  }

  reserve(message: string) {
    this.loading = true;
    this.api.reserve(message, this.seatClass).subscribe(data => {
      this.loading = false;
      if (data) {
        this.flights = data;
        console.log(this.reservationMessage);
        console.log(this.seatClass);
      }
    });
  }

  onSortChange(sortParam: string) {
    this.sortParam = sortParam;
    // this.flights = null;
    this.loading = true;
    if (this.tripType === 'oneWay') {
      this.api.onSortFilterOneWay(this.seatClass, this.departureAirPort, this.arrivalAirPort,
        this.outboundDate, this.sortParam, this.filterParam)
        .subscribe(data => {
          this.flights = data;
          this.loading = false;
        });
    } else {
      this.api.onSortFilterRoundTrip(this.seatClass, this.departureAirPort, this.arrivalAirPort,
        this.outboundDate, this.inboundDate, this.sortParam, this.filterParam)
        .subscribe(data => {
          this.flights = data;
          this.loading = false;
        });
    }

  }

  onFilterChange(filterParam: string) {
    this.filterParam = filterParam;
    // this.flights = null;
    this.loading = true;
    if (this.tripType === 'oneWay') {
      this.api.onSortFilterOneWay(this.seatClass, this.departureAirPort, this.arrivalAirPort,
        this.outboundDate, this.sortParam, this.filterParam)
        .subscribe(data => {
          this.flights = data;
          this.loading = false;
        });
    } else {
      this.api.onSortFilterRoundTrip(this.seatClass, this.departureAirPort, this.arrivalAirPort,
        this.outboundDate, this.inboundDate, this.sortParam, this.filterParam)
        .subscribe(data => {
          this.flights = data;
          this.loading = false;
        });
    }
  }

  onOutboundDateChange() {
    this.searchDisabled = this.inboundDate < this.outboundDate;
  }

  onInboundDateChange() {
    // if one way trip, inboundDate is undefined, the compare always returns false;
    this.searchDisabled = this.inboundDate < this.outboundDate;
  }

  isDisabled(): boolean {
    return this.searchDisabled;
  }
}
