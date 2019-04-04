import {
  MatButtonModule,
  MatCheckboxModule,
  MatDatepickerModule,
  MatIconModule,
  MatInputModule,
  MatNativeDateModule
} from '@angular/material';
import {NgModule} from '@angular/core';

@NgModule({
  imports: [MatButtonModule, MatCheckboxModule, MatIconModule, MatInputModule, MatDatepickerModule, MatNativeDateModule],
  exports: [MatButtonModule, MatCheckboxModule, MatIconModule, MatInputModule, MatDatepickerModule, MatNativeDateModule],
})
export class MyOwnCustomMaterialModule { }
