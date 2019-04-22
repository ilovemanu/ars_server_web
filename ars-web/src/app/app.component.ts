import {Component, OnInit} from '@angular/core';
import {ApiService} from './service/api.service';
import {del} from "selenium-webdriver/http";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
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
  // sortParam = 'travelTime';
  // filterParam = 'all';
  //
  // searchDisabled = false;
  // loading = false;
  // reserved = false;
  // roundTripMessages = [];

  sortParam: string;
  filterParam: string;

  searchDisabled: boolean;
  loading: boolean;
  reserved: boolean;
  roundTripMessages: string[];

  constructor(private api: ApiService) {
  }

  ngOnInit(): void {
    this.sortParam = 'travelTime';
    this.filterParam = 'all';

    this.searchDisabled = false;
    this.loading = false;
    this.reserved = false;
    this.roundTripMessages = [];
  }


  onSubmit() {
    this.loading = true;
    this.reserved = false;
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
      this.reserved = true;
      if (data) {
        this.flights = data;
        console.log(this.reservationMessage);
        console.log(this.seatClass);
      }
    });
  }

  reserveRoundTrip() {
    this.loading = true;
    this.api.reserveRoundTrip(this.roundTripMessages, this.seatClass).subscribe(data => {
      this.loading = false;
      this.reserved = true;
      if (data) {
        this.flights = data;
        console.log(this.reservationMessage);
        console.log(this.seatClass);
      }
    });

  }

  updateChecked(message, event) {
    if (event.checked) {
      this.roundTripMessages.push(message);
      console.log(this.roundTripMessages);
    } else {
      // delete this.roundTripMessages[message];
      this.roundTripMessages = this.roundTripMessages.filter(m => m !== message);
      console.log(this.roundTripMessages);
    }

  }

  onSortChange(sortParam: string) {
    this.sortParam = sortParam;
    // this.flights = null;
    this.loading = true;
    this.reserved = false;
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
    this.reserved = false;
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
