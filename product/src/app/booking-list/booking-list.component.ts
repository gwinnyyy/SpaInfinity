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
  errorMessage: string | null = null;

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


  onApprove(booking: BookingResponse): void {
    if (!confirm(`Are you sure you want to approve this booking?`)) {
      return;
    }
    
    this.bookingService.approveBooking(booking.bookingId).subscribe({
      next: (updatedBooking) => {
        const index = this.bookings.findIndex(b => b.bookingId === updatedBooking.bookingId);
        if (index !== -1) {
          this.bookings[index] = updatedBooking;
        }
      },
      error: (err) => this.errorMessage = 'Failed to approve booking.'
    });
  }

  onCancel(booking: BookingResponse): void {
    if (!confirm(`Are you sure you want to CANCEL this booking? This will make the timeslot available again.`)) {
      return;
    }

    this.bookingService.cancelBooking(booking.bookingId).subscribe({
      next: (updatedBooking) => {
        const index = this.bookings.findIndex(b => b.bookingId === updatedBooking.bookingId);
        if (index !== -1) {
          this.bookings[index] = updatedBooking;
        }
      },
      error: (err) => this.errorMessage = 'Failed to cancel booking.'
    });
  }
}