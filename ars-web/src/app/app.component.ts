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
  airPort: string;
  departureDate: Date;

  constructor(private api: ApiService) {
  }


  onSubmit() {
    this.api.findDeparture(this.airPort, this.departureDate).subscribe(data => {
      this.flights = data;
    });
  }
}
