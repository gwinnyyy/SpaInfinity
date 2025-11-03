import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BaseHttpService } from './base-http.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TimeSlotService extends BaseHttpService {

  constructor(protected override http: HttpClient) { 
    super(http, '/api/timeslots');
  }

  getAvailableSlots(date: string): Observable<any> {
    return this.http.get<any>(`${this.apiServerUrl}/api/timeslots/available?date=${date}`);
  }

  getSlotsByTherapist(therapistId: number, date: string): Observable<any> {
    return this.http.get<any>(
      `${this.apiServerUrl}/api/timeslots/therapist/${therapistId}?date=${date}`
    );
  }

  generateSlots(date: string): Observable<any> {
    return this.http.post<any>(
      `${this.apiServerUrl}/api/timeslots/generate?date=${date}`, 
      {}
    );
  }
}