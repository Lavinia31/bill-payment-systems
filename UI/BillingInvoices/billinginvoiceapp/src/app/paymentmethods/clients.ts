export interface Client{
        clientId: number,
        paymentMethod: {
            paymentMethodId: number;
            cardDigits : string;
            expirationDate : string;
            token: string;
            insertedDate: string;
            updatedDate: string;
        },
        lastName: string
        firstName: string;
        address:string;
        phoneNumber:string;
        email: string;
        type:string;
        balance:string;
        insertedDate:string;
        updatedDate:string;
    
}