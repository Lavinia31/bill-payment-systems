
export interface Invoice{
    invoiceId : number;
    client: {
            clientId: number,
            paymentMethod: {
                paymentMethodId: number;
                cardDigits : string;
                expirationDate : string;
                token: string;
                insertedDate: string;
                updatedDate: string;
            };
            lastName: string
            firstName: string;
            address:string;
            phoneNumber:string;
            email: string;
            type:string;
            balance:string;
            insertedDate:string;
            updatedDate:string;
        },
    invoiceType : string;
    issuedDate : string;
    dueDate : string;
    amount : number;
    services : string;
    paymentStatus : string;
    insertedDate: string;
    updatedDate : string;
}

