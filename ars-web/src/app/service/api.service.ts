import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Observable} from 'rxjs';
import { DatePipe } from '@angular/common';

@Injectable()
export class ApiService {

  private departureDateUrl: string;

  constructor(private http: HttpClient, private datePipe: DatePipe) {
    this.departureDateUrl = 'http://localhost:8080/api';
  }

  public findDeparture(airPort: string, departureDate: Date): Observable<string[]> {
    // console.log(`${this.departureDateUrl}/${airPort}/${departureDate}`);
    const departDate = this.datePipe.transform(departureDate,'yyyy_MM_dd');
    return this.http.get<string[]>(`${this.departureDateUrl}/${airPort}/${departDate}`);
  }

  // public save(user: User) {
  //   return this.http.post<User>(this.usersUrl, user);
  // }
}
