import { Component, OnInit } from '@angular/core';
import { ProductService } from '../service/spa-service.service';
import { BookingService } from '../service/booking.service';
import { TherapistService } from '../service/therapist.service';
import { TimeSlotService } from '../service/time-slot.service';
import { ProductCategory } from '../model/product-category';
import { SpaService } from '../model/spa-service';

@Component({
  selector: 'app-booking-form',
  templateUrl: './booking-form.component.html',
  styleUrls: ['./booking-form.component.css']
})
export class BookingFormComponent implements OnInit {
  serviceCategories: ProductCategory[] = [];
  selectedServices: any[] = [];
  therapists: any[] = [];
  availableSlots: any[] = [];
  
  bookingForm = {
    customerName: '',
    email: '',
    phone: '',
    selectedDate: '',
    selectedTime: '',
    therapistId: null,
    services: [] as any[],
    guestCount: 1,
    guests: [] as any[],
    notes: ''
  };

  totalAmount: number = 0;
  step: number = 1; // 1: Select Services, 2: Guest Details, 3: Date/Time, 4: Confirmation

  constructor(
    private productService: ProductService,
    private bookingService: BookingService,
    private therapistService: TherapistService,
    private timeSlotService: TimeSlotService
  ) {}

  ngOnInit(): void {
    this.loadServices();
    this.loadTherapists();
  }

  loadServices(): void {
    this.productService.getData().subscribe(data => {
      this.serviceCategories = data;
    });
  }

  loadTherapists(): void {
    this.therapistService.getData().subscribe(data => {
      this.therapists = data;
    });
  }

  addService(service: SpaService): void {
    const existing = this.selectedServices.find(s => s.id === service.id);
    if (!existing) {
      this.selectedServices.push({ ...service, quantity: 1 });
      this.calculateTotal();
    }
  }

  removeService(serviceId: number): void {
    this.selectedServices = this.selectedServices.filter(s => s.id !== serviceId);
    this.calculateTotal();
  }

  calculateTotal(): void {
    this.totalAmount = this.selectedServices.reduce((sum, service) => {
      return sum + (parseFloat(service.price) * service.quantity);
    }, 0);
  }

  onDateChange(date: string): void {
    this.bookingForm.selectedDate = date;
    if (date) {
      this.loadAvailableSlots(date);
    }
  }

  loadAvailableSlots(date: string): void {
    this.timeSlotService.getAvailableSlots(date).subscribe(data => {
      this.availableSlots = data;
    });
  }

  initializeGuestForms(): void {
    this.bookingForm.guests = [];
    for (let i = 0; i < this.bookingForm.guestCount - 1; i++) {
      this.bookingForm.guests.push({
        name: '',
        preferences: ''
      });
    }
  }

  nextStep(): void {
    if (this.step === 1 && this.selectedServices.length === 0) {
      alert('Please select at least one service');
      return;
    }
    
    if (this.step === 1 && this.bookingForm.guestCount > 1) {
      this.initializeGuestForms();
    }
    
    this.step++;
  }

  previousStep(): void {
    this.step--;
  }

  submitBooking(): void {
    const booking = {
      customerName: this.bookingForm.customerName,
      email: this.bookingForm.email,
      phone: this.bookingForm.phone,
      bookingDate: `${this.bookingForm.selectedDate} ${this.bookingForm.selectedTime}`,
      therapistId: this.bookingForm.therapistId,
      services: this.selectedServices,
      guests: this.bookingForm.guests,
      notes: this.bookingForm.notes,
      totalAmount: this.totalAmount
    };

    this.bookingService.add(booking).subscribe({
      next: (response) => {
        alert('Booking submitted successfully!');
        this.resetForm();
      },
      error: (error) => {
        alert('Error submitting booking: ' + error.message);
      }
    });
  }

  resetForm(): void {
    this.bookingForm = {
      customerName: '',
      email: '',
      phone: '',
      selectedDate: '',
      selectedTime: '',
      therapistId: null,
      services: [],
      guestCount: 1,
      guests: [],
      notes: ''
    };
    this.selectedServices = [];
    this.totalAmount = 0;
    this.step = 1;
  }
}