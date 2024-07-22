
export interface Payment{
    paymentId : number;
    invoiceId : number;
    paymentDate : Date;
    cardDigits : string;
    statusCode: string;
    statusMessage : string;
}