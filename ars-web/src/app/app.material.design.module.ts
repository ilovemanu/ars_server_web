import {
  MatButtonModule,
  MatCheckboxModule,
  MatDatepickerModule,
  MatIconModule,
  MatInputModule,
  MatNativeDateModule,
  MatProgressSpinnerModule,
  MatCardModule
} from '@angular/material';
import {NgModule} from '@angular/core';

@NgModule({
  imports: [MatButtonModule, MatCheckboxModule, MatIconModule, MatInputModule, MatDatepickerModule,
    MatNativeDateModule, MatProgressSpinnerModule, MatCardModule],
  exports: [MatButtonModule, MatCheckboxModule, MatIconModule, MatInputModule, MatDatepickerModule,
    MatNativeDateModule, MatProgressSpinnerModule, MatCardModule],
})
export class MyOwnCustomMaterialModule {
}
