import { Component, OnInit } from '@angular/core';
import { BookingService } from '../service/booking.service';
import { SpaServiceData } from '../model/spa-service.model';
import { AvailableTimeSlotData } from '../model/timeslot.model';
import { BookingRequest, BookingResponse } from '../model/booking.model';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-booking-form',
  templateUrl: './booking-form.component.html',
  styleUrls: ['./booking-form.component.css']
})
export class BookingFormComponent implements OnInit {

  services: SpaServiceData[] = [];
  timeslots: AvailableTimeSlotData[] = [];

  bookingRequest: BookingRequest = {
    customerName: '',
    customerEmail: '',
    customerPhone: '',
    serviceId: 0, 
    timeSlotId: 0 
  };
  
  isLoading = false;
  isBookingSuccess = false;
  successResponse: BookingResponse | null = null;
  errorMessage: string | null = null;

  constructor(private bookingService: BookingService) { }

  ngOnInit(): void {
    this.loadServices();
    this.loadTimeSlots();
  }

  loadServices(): void {
    console.log('Loading services...');
    this.bookingService.getServices().subscribe({
      next: (data) => {
        this.services = data;
        console.log('Services loaded:', data.length, 'services');
      },
      error: (err) => {
        console.error('Error loading services:', err);
        this.errorMessage = 'Failed to load services. Please refresh the page.';
      }
    });
  }

  loadTimeSlots(): void {
    console.log('Loading time slots...');
    this.bookingService.getAvailableTimeSlots().subscribe({
      next: (data) => {
        this.timeslots = data;
        console.log('Time slots loaded:', data.length, 'available slots');
        if (data.length === 0) {
          console.warn('No available time slots found!');
        }
      },
      error: (err) => {
        console.error('Error loading time slots:', err);
        this.errorMessage = 'Failed to load available time slots. Please refresh the page.';
      }
    });
  }

  onSubmit(form: NgForm): void {
    if (form.invalid) {
      return;
    }

    const selectedService = this.services.find(s => s.id === this.bookingRequest.serviceId);
    const selectedSlot = this.timeslots.find(t => t.id === this.bookingRequest.timeSlotId);

    if (!selectedService || !selectedSlot) {
      this.errorMessage = 'Please select both a service and time slot.';
      return;
    }

    const slotDate = new Date(selectedSlot.slotDate);
    const formattedDate = slotDate.toLocaleDateString('en-US', { 
      weekday: 'long', 
      year: 'numeric', 
      month: 'long', 
      day: 'numeric' 
    });

    const confirmMessage = `Please confirm your booking details:

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 BOOKING SUMMARY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

 Customer: ${this.bookingRequest.customerName}
 Email: ${this.bookingRequest.customerEmail}
${this.bookingRequest.customerPhone ? '📱 Phone: ' + this.bookingRequest.customerPhone : ''}

 Service: ${selectedService.name}
 Price: ₱${selectedService.price}
 Duration: ${selectedService.durationMinutes} minutes

 Date: ${formattedDate}
 Time: ${selectedSlot.startTime.slice(0, 5)}

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Do you want to proceed with this booking?`;

    if (!confirm(confirmMessage)) {
      console.log('Booking cancelled by user');
      return;
    }

    console.log('Submitting booking:', this.bookingRequest);

    this.isLoading = true;
    this.isBookingSuccess = false;
    this.errorMessage = null;

    this.bookingService.createBooking(this.bookingRequest).subscribe({
      next: (response) => {
        console.log('Booking successful:', response);
        this.isLoading = false;
        this.isBookingSuccess = true;
        this.successResponse = response;
        
        form.reset();
        this.bookingRequest = {
          customerName: '',
          customerEmail: '',
          customerPhone: '',
          serviceId: 0,
          timeSlotId: 0
        };
        
        this.loadTimeSlots();
        
        window.scrollTo({ top: 0, behavior: 'smooth' });
      },
      error: (err) => {
        console.error('Booking error:', err);
        this.isLoading = false;
        this.errorMessage = err.error?.message || 'An error occurred while booking. Please try again.';
        
        window.scrollTo({ top: 0, behavior: 'smooth' });
      }
    });
  }
}