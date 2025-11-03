import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainBodyComponent } from './main-body/main-body.component';
import { ProductCategoryComponent } from './product-category/product-category.component';
import { CustomerServiceComponent } from './customer-service/customer-service.component';
import { ContactUsComponent } from './contact-us/contact-us.component';
import { BookingFormComponent } from './booking-form/booking-form.component';
import { BookingListComponent } from './booking-list/booking-list.component';

const routes: Routes = [
  {path:'', component: MainBodyComponent}, 
  {path:'services', component: ProductCategoryComponent}, 
  {path:'booking', component: BookingFormComponent}, 
  {path:'bookings', component: BookingListComponent}, 
  {path:'customer', component: CustomerServiceComponent}, 
  {path:'contact', component: ContactUsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
