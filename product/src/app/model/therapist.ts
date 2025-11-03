export class Therapist {
    id: number = 0;
    firstName: string = '';
    lastName: string = '';
    specialization: string = '';
    phone: string = '';
    email: string = '';
    available: boolean = true;
    
    get fullName(): string {
        return `${this.firstName} ${this.lastName}`;
    }
}