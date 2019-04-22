import {
  MatButtonModule,
  MatCheckboxModule,
  MatDatepickerModule,
  MatIconModule,
  MatInputModule,
  MatNativeDateModule,
  MatProgressSpinnerModule
} from '@angular/material';
import {NgModule} from '@angular/core';

@NgModule({
  imports: [MatButtonModule, MatCheckboxModule, MatIconModule, MatInputModule, MatDatepickerModule,
    MatNativeDateModule, MatProgressSpinnerModule],
  exports: [MatButtonModule, MatCheckboxModule, MatIconModule, MatInputModule, MatDatepickerModule,
    MatNativeDateModule, MatProgressSpinnerModule],
})
export class MyOwnCustomMaterialModule {
}
