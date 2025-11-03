import { Component, OnInit } from '@angular/core';
import { BookingService } from '../service/booking.service';

@Component({
  selector: 'app-booking-list',
  templateUrl: './booking-list.component.html',
  styleUrls: ['./booking-list.component.css']
})
export class BookingListComponent implements OnInit {
  bookings: any[] = [];
  customerId: number = 1; 

  constructor(private bookingService: BookingService) {}

  ngOnInit(): void {
    this.loadBookings();
  }

  loadBookings(): void {
    this.bookingService.getCustomerBookings(this.customerId).subscribe({
      next: (data: any) => { // <-- Add type
        this.bookings = data;
      },
      error: (error: any) => { // <-- Add type
        console.error('Error loading bookings:', error);
      }
    });
  }

  cancelBooking(id: number): void {
    // Note: window.confirm is not ideal in an iframe, consider a modal
    if (confirm('Are you sure you want to cancel this booking?')) {
      this.bookingService.cancelBooking(id).subscribe({
        next: () => {
          alert('Booking cancelled successfully'); // Note: window.alert is not ideal
          this.loadBookings();
        },
        error: (error: any) => { // <-- Add type
          alert('Error cancelling booking: ' + error.message);
        }
      });
    }
  }

  getStatusColor(status: string): string {
    const colors: any = {
      'PENDING': '#FFA500',
      'CONFIRMED': '#4CAF50',
      'CANCELLED': '#f44336',
      'COMPLETED': '#2196F3'
    };
    return colors[status] || '#999';
  }
}
