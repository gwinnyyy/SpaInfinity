import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProductService } from '../service/spa-service.service';
import { BookingService } from '../service/booking.service';
import { TherapistService } from '../service/therapist.service';
import { TimeSlotService } from '../service/time-slot.service';
import { ProductCategory } from '../model/product-category';
import { SpaService } from '../model/spa-service';
import { Therapist } from '../model/therapist';
import { TimeSlot } from '../model/time-slot';

interface SelectedSpaService extends SpaService {
  quantity: number;
}

@Component({
  selector: 'app-booking-form',
  templateUrl: './booking-form.component.html',
  styleUrls: ['./booking-form.component.css']
})
export class BookingFormComponent implements OnInit {
  serviceCategories: ProductCategory[] = [];
  selectedServices: SelectedSpaService[] = [];
  therapists: Therapist[] = [];
  availableSlots: TimeSlot[] = [];
  
  bookingForm = {
    customerId: 1, // Default customer ID
    customerName: '',
    email: '',
    phone: '',
    selectedDate: '',
    selectedTime: '',
    therapistId: null as number | null,
    services: [] as any[],
    guestCount: 1,
    guests: [] as any[],
    notes: ''
  };

  totalAmount: number = 0;
  step: number = 1;
  isSubmitting: boolean = false;
  errorMessage: string = '';

  constructor(
    private productService: ProductService,
    private bookingService: BookingService,
    private therapistService: TherapistService,
    private timeSlotService: TimeSlotService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadServices();
    this.loadTherapists();
  }
  
  public getTodayDate(): string {
    return new Date().toISOString().split('T')[0];
  }

  isServiceSelected(service: SpaService): boolean {
    return !!this.selectedServices.find(s => s.id === service.id);
  }

  loadServices(): void {
    this.productService.getData().subscribe({
      next: (data: ProductCategory[]) => { 
        this.serviceCategories = data;
        console.log('Services loaded:', data);
      },
      error: (error) => {
        console.error('Error loading services:', error);
        this.errorMessage = 'Failed to load services. Please refresh the page.';
      }
    });
  }

  loadTherapists(): void {
    this.therapistService.getData().subscribe({
      next: (data: Therapist[]) => { 
        this.therapists = data;
        console.log('Therapists loaded:', data);
      },
      error: (error) => {
        console.error('Error loading therapists:', error);
      }
    });
  }

  addService(service: SpaService): void {
    const existing = this.selectedServices.find(s => s.id === service.id);
    if (!existing) {
      this.selectedServices.push({ ...service, quantity: 1 });
      this.calculateTotal();
    } else {
      this.removeService(service.id);
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
    this.bookingForm.selectedTime = ''; // Reset time selection
    if (date) {
      this.loadAvailableSlots(date);
    } else {
      this.availableSlots = [];
    }
  }

  loadAvailableSlots(date: string): void {
    this.timeSlotService.getAvailableSlots(date).subscribe({
      next: (data: TimeSlot[]) => { 
        this.availableSlots = data;
        console.log('Available slots:', data);
        if (data.length === 0) {
          // Generate slots if none exist for this date
          this.timeSlotService.generateSlots(date).subscribe({
            next: (generated: TimeSlot[]) => {
              this.availableSlots = generated;
            },
            error: (error) => {
              console.error('Error generating slots:', error);
            }
          });
        }
      },
      error: (error) => {
        console.error('Error loading slots:', error);
      }
    });
  }

  selectTimeSlot(time: string): void {
    this.bookingForm.selectedTime = time;
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
    // Validation for each step
    if (this.step === 1) {
      if (this.selectedServices.length === 0) {
        this.errorMessage = 'Please select at least one service';
        return;
      }
      if (this.bookingForm.guestCount < 1) {
        this.errorMessage = 'Guest count must be at least 1';
        return;
      }
      this.initializeGuestForms();
    }
    
    if (this.step === 2) {
      if (!this.bookingForm.customerName || !this.bookingForm.email || !this.bookingForm.phone) {
        this.errorMessage = 'Please fill in all required fields';
        return;
      }
      // Basic email validation
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (!emailRegex.test(this.bookingForm.email)) {
        this.errorMessage = 'Please enter a valid email address';
        return;
      }
    }
    
    if (this.step === 3) {
      if (!this.bookingForm.selectedDate) {
        this.errorMessage = 'Please select a date';
        return;
      }
      if (!this.bookingForm.selectedTime) {
        this.errorMessage = 'Please select a time slot';
        return;
      }
    }
    
    this.errorMessage = '';
    this.step++;
  }

  previousStep(): void {
    this.errorMessage = '';
    this.step--;
  }

  submitBooking(): void {
    if (this.isSubmitting) {
      return;
    }

    this.isSubmitting = true;
    this.errorMessage = '';

    // Prepare booking data
    const bookingData = {
      customerId: this.bookingForm.customerId,
      customerName: this.bookingForm.customerName,
      email: this.bookingForm.email,
      phone: this.bookingForm.phone,
      bookingDate: `${this.bookingForm.selectedDate}T${this.bookingForm.selectedTime}:00`,
      therapistId: this.bookingForm.therapistId,
      totalAmount: this.totalAmount,
      notes: this.bookingForm.notes,
      status: 'PENDING',
      paymentStatus: 'UNPAID',
      items: this.selectedServices.map(service => ({
        productId: service.id,
        productName: service.name,
        productDescription: service.description,
        productCategoryName: service.categoryName,
        productImageFile: service.imageFile,
        quantity: service.quantity,
        price: parseFloat(service.price),
        customerId: this.bookingForm.customerId,
        customerName: this.bookingForm.customerName
      }))
    };

    console.log('Submitting booking:', bookingData);

    this.bookingService.add(bookingData).subscribe({
      next: (response: any) => { 
        console.log('Booking response:', response);
        this.isSubmitting = false;
        alert('Booking submitted successfully! You will receive a confirmation email shortly.');
        this.router.navigate(['/bookings']);
      },
      error: (error: any) => { 
        console.error('Error submitting booking:', error);
        this.isSubmitting = false;
        this.errorMessage = error.error?.message || 'Error submitting booking. Please try again.';
        // Show error but allow user to retry
        window.scrollTo({ top: 0, behavior: 'smooth' });
      }
    });
  }

  resetForm(): void {
    this.bookingForm = {
      customerId: 1,
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
    this.errorMessage = '';
  }
}