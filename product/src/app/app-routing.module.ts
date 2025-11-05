import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { BookingFormComponent } from './booking-form/booking-form.component';

import { AdminLayoutComponent } from './admin/admin-layout.component';
import { DashboardComponent } from './admin/dashboard/dashboard.component';
import { BookingListComponent } from './admin/booking-list/booking-list.component';
import { ServiceManagementComponent } from './admin/service-management/service-management.component';
import { ScheduleManagementComponent } from './admin/schedule-management/schedule-management.component';

const routes: Routes = [
 
  { path: 'book', component: BookingFormComponent },
  { path: '', redirectTo: '/book', pathMatch: 'full' },

  {
    path: 'admin',
    component: AdminLayoutComponent,
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { path: 'dashboard', component: DashboardComponent },
      { path: 'bookings', component: BookingListComponent },
      { path: 'services', component: ServiceManagementComponent },
      { path: 'schedule', component: ScheduleManagementComponent }
    ]
  },

  { path: '**', redirectTo: '/book' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }