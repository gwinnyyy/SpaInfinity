import { NgModule, LOCALE_ID } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { registerLocaleData } from '@angular/common';
import localeEnPh from '@angular/common/locales/en-PH';
registerLocaleData(localeEnPh);

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BookingFormComponent } from './booking-form/booking-form.component';

import { BookingListComponent } from './admin/booking-list/booking-list.component';
import { ServiceManagementComponent } from './admin/service-management/service-management.component';
import { AdminLayoutComponent } from './admin/admin-layout.component';
import { DashboardComponent } from './admin/dashboard/dashboard.component';
import { ScheduleManagementComponent } from './admin/schedule-management/schedule-management.component';

@NgModule({
  declarations: [
    AppComponent,
    BookingFormComponent,
    BookingListComponent,
    ServiceManagementComponent,
    AdminLayoutComponent,
    DashboardComponent,
    ScheduleManagementComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
    { provide: LOCALE_ID, useValue: 'en-PH' }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
