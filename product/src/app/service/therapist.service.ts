import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BaseHttpService } from './base-http.service';

@Injectable({
  providedIn: 'root'
})
export class TherapistService extends BaseHttpService {

  constructor(protected override http: HttpClient) { 
    super(http, '/api/therapists');
  }

  getAvailableTherapists() {
    return this.http.get<any>(`${this.apiServerUrl}/api/therapists/available`);
  }
}