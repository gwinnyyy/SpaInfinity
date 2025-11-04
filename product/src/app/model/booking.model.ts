export interface BookingResponse {
    bookingId: number;
    confirmationCode: string;
    bookingStatus: string;
    customerName: string;
    serviceName: string;
    servicePrice: number;
    slotDate: string;
    startTime: string;
}

export interface BookingRequest {
    customerName: string;
    customerEmail: string;
    customerPhone: string;
    serviceId: number;
    timeSlotId: number;
}