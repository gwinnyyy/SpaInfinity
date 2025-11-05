import { Component, OnInit } from '@angular/core';
import { BookingService } from '../service/booking.service';
import { SpaServiceData } from '../model/spa-service.model';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-service-management',
  templateUrl: './service-management.component.html',
  styleUrls: ['./service-management.component.css']
})
export class ServiceManagementComponent implements OnInit {

  services: SpaServiceData[] = [];
  isLoading = true;
  errorMessage: string | null = null;

  serviceModel: SpaServiceData = {
    id: 0,
    name: '',
    description: '',
    price: 0,
    durationMinutes: 60
  };

  isEditing = false;

  constructor(private bookingService: BookingService) { }

  ngOnInit(): void {
    this.loadServices();
  }

  loadServices(): void {
    this.isLoading = true;
    this.bookingService.getServices().subscribe(data => {
      this.services = data;
      this.isLoading = false;
    });
  }

  onFormSubmit(form: NgForm): void {
    if (form.invalid) {
      return;
    }

    this.errorMessage = null;

    if (this.isEditing) {
      this.bookingService.updateService(this.serviceModel.id, this.serviceModel).subscribe({
        next: (updatedService) => {
          const index = this.services.findIndex(s => s.id === updatedService.id);
          if (index !== -1) {
            this.services[index] = updatedService;
          }
          this.resetForm(form);
        },
        error: (err) => this.errorMessage = 'Failed to update service.'
      });

    } else {
      this.bookingService.createService(this.serviceModel).subscribe({
        next: (newService) => {
          this.services.push(newService);
          this.resetForm(form);
        },
        error: (err) => this.errorMessage = 'Failed to create service.'
      });
    }
  }

  onEdit(service: SpaServiceData): void {
    this.serviceModel = { ...service };
    this.isEditing = true;
    window.scrollTo(0, 0);
  }

  onDelete(service: SpaServiceData): void {
    if (!confirm(`Are you sure you want to DELETE the service: "${service.name}"?`)) {
      return;
    }

    this.errorMessage = null;
    this.bookingService.deleteService(service.id).subscribe({
      next: () => {
        this.services = this.services.filter(s => s.id !== service.id);
      },
      error: (err) => this.errorMessage = 'Failed to delete service. It may be linked to existing bookings.'
    });
  }

  resetForm(form: NgForm): void {
    form.resetForm({
      durationMinutes: 60,
      price: 0,
      name: '',
      description: ''
    });
    this.serviceModel = { id: 0, name: '', description: '', price: 0, durationMinutes: 60 };
    this.isEditing = false;
  }
}