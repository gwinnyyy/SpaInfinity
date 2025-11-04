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

  // Data from API
  services: SpaServiceData[] = [];
  timeslots: AvailableTimeSlotData[] = [];

  // Form model
  bookingRequest: BookingRequest = {
    customerName: '',
    customerEmail: '',
    customerPhone: '',
    serviceId: 0, 
    timeSlotId: 0 
  };
  
  // State variables
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
    this.bookingService.getServices().subscribe(data => {
      this.services = data;
    });
  }

  loadTimeSlots(): void {
    this.bookingService.getAvailableTimeSlots().subscribe(data => {
      this.timeslots = data;
    });
  }

  onSubmit(form: NgForm): void {
    if (form.invalid) {
      return;
    }

    this.isLoading = true;
    this.isBookingSuccess = false;
    this.errorMessage = null;

    this.bookingService.createBooking(this.bookingRequest).subscribe({
      next: (response) => {
        this.isLoading = false;
        this.isBookingSuccess = true;
        this.successResponse = response;
        form.reset();
        this.loadTimeSlots();
      },
      error: (err) => {
        this.isLoading = false;
        this.errorMessage = err.error?.message || 'An unknown error occurred. Please try again.';
      }
    });
  }
}