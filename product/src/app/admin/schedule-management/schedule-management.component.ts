import { Component, OnInit } from '@angular/core';
import { BookingService } from '../../service/booking.service';

@Component({
  selector: 'app-schedule-management',
  templateUrl: './schedule-management.component.html',
  styleUrls: ['./schedule-management.component.css']
})
export class ScheduleManagementComponent implements OnInit {

  weeklySchedule: any[] = [];
  isLoading = true;
  isGenerating = false;
  message: string | null = null;
  dayNames = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];

  constructor(private bookingService: BookingService) { }

  ngOnInit(): void {
    this.loadSchedule();
  }

  loadSchedule(): void {
    this.isLoading = true;
    this.bookingService.getSchedule().subscribe(data => {
      this.weeklySchedule = data;
      this.isLoading = false;
    });
  }

  onScheduleUpdate(daySchedule: any): void {
    this.message = null;
    this.bookingService.updateSchedule(daySchedule).subscribe({
      next: (updated) => {
        this.message = `Updated ${this.dayNames[updated.dayOfWeek]}`;
      },
      error: (err) => this.message = 'Failed to update schedule.'
    });
  }

  onGenerateSlots(): void {
    if (!confirm('This will generate all available slots for the next 30 days based on your active schedule. This cannot be undone. Continue?')) {
      return;
    }

    this.isGenerating = true;
    this.message = null;
    this.bookingService.generateTimeSlots(30).subscribe({
      next: () => {
        this.isGenerating = false;
        this.message = 'Successfully generated time slots for the next 30 days.';
      },
      error: (err) => {
        this.isGenerating = false;
        this.message = 'An error occurred while generating slots.';
      }
    });
  }
}