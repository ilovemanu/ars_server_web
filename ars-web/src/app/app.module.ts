import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MyOwnCustomMaterialModule} from './app.material.design.module';
import {FormsModule} from '@angular/forms';
import {ApiService} from './service/api.service';
import {HttpClientModule} from '@angular/common/http';
import {DatePipe} from '@angular/common';
// import { FlightListComponent } from './flight-list/flight-list.component';

@NgModule({
  declarations: [
    AppComponent
    // FlightListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MyOwnCustomMaterialModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [ApiService, DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule {
}
