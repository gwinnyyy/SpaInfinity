import { Component } from '@angular/core';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent {

  contactInfo = {
    address: '123 Bakery Street, Bacoor, Cavite City, Philippines',
    phone: '+63 917 123 4567',
    email: 'info@spainfinity.com',
    hours: 'Mon-Sat: 9:00 AM - 8:00 PM, Sun: 10:00 AM - 6:00 PM'
  };

}