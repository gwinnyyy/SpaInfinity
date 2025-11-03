
import { BookingItem } from './booking-item';

export class Booking {
    id: number = 0;
    customerId: number = 0;
    customerName: string = '';
    therapistId: number = 0;
    therapistName: string = '';
    timeSlotId: number = 0;
    bookingDate: string = '';
    status: string = 'PENDING';
    totalAmount: number = 0;
    notes: string = '';
    paymentStatus: string = 'UNPAID';
    items: BookingItem[] = [];
    created?: Date;
    lastUpdated?: Date;
}