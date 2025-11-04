import { Component, OnInit } from '@angular/core';
import { BookingService } from '../service/booking.service';
import { BookingResponse } from '../model/booking.model';

@Component({
  selector: 'app-booking-list',
  templateUrl: './booking-list.component.html',
  styleUrls: ['./booking-list.component.css']
})
export class BookingListComponent implements OnInit {

  bookings: BookingResponse[] = [];
  isLoading = true;

  constructor(private bookingService: BookingService) { }

  ngOnInit(): void {
    this.loadAllBookings();
  }

  loadAllBookings(): void {
    this.isLoading = true;
    this.bookingService.getAllBookings().subscribe(data => {
      this.bookings = data;
      this.isLoading = false;
    });
  }
}