import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BaseHttpService } from './base-http.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BookingService extends BaseHttpService {

  constructor(protected override http: HttpClient) { 
    super(http, '/api/bookings');
  }

  getCustomerBookings(customerId: number): Observable<any> {
    return this.http.get<any>(`${this.apiServerUrl}/api/bookings/customer/${customerId}`);
  }

  getBooking(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiServerUrl}/api/bookings/${id}`);
  }

  confirmBooking(id: number): Observable<any> {
    return this.http.post<any>(`${this.apiServerUrl}/api/bookings/${id}/confirm`, {});
  }

  cancelBooking(id: number): Observable<any> {
    return this.http.post<any>(`${this.apiServerUrl}/api/bookings/${id}/cancel`, {});
  }

  completeBooking(id: number): Observable<any> {
    return this.http.post<any>(`${this.apiServerUrl}/api/bookings/${id}/complete`, {});
  }
}