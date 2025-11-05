import { Component, OnInit } from '@angular/core';
import { BookingService } from '../service/booking.service';
import { SpaServiceData } from '../model/spa-service.model';

@Component({
  selector: 'app-services',
  templateUrl: './services.component.html',
  styleUrls: ['./services.component.css']
})
export class ServicesComponent implements OnInit {

  services: SpaServiceData[] = [];
  isLoading = true;

  serviceImages = [
    'assets/services/anti_aging.jpg',
    'assets/services/acne_treatment.jpg',
    'assets/services/body_scrub.jpg',
    'assets/services/brightening.jpg',
    'assets/services/prenatal.jpg',
    'assets/services/reflexology.jpg'
  ];

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

  getServiceImage(index: number): string {
    return this.serviceImages[index % this.serviceImages.length];
  }

}