import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SpaServiceData } from '../model/spa-service.model';
import { AvailableTimeSlotData } from '../model/timeslot.model';
import { BookingRequest, BookingResponse } from '../model/booking.model';

@Injectable({
  providedIn: 'root'
})
export class BookingService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  getServices(): Observable<SpaServiceData[]> {
    return this.http.get<SpaServiceData[]>(`${this.apiUrl}/services`);
  }

  getAvailableTimeSlots(): Observable<AvailableTimeSlotData[]> {
    return this.http.get<AvailableTimeSlotData[]>(`${this.apiUrl}/timeslots/available`);
  }

  createBooking(request: BookingRequest): Observable<BookingResponse> {
    return this.http.post<BookingResponse>(`${this.apiUrl}/bookings`, request);
  }

  getAllBookings(): Observable<BookingResponse[]> {
    return this.http.get<BookingResponse[]>(`${this.apiUrl}/bookings`);
  }
}