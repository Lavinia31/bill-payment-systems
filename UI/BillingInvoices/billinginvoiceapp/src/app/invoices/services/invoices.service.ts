import { Inject, Injectable } from '@angular/core';
import { Invoice } from '../invoices';
import { APP_SERVICE_CONFIG } from '../../AppConfig/appconfig.service';
import { AppConfig } from '../../AppConfig/appconfig.interface';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class InvoicesService {

  invoice : Invoice[] = [ 
  //   {

  //   invoiceId : 1,
  //   clientId : 1,
  //   invoiceType : 'B2B1',
  //   issuedDate : new Date('11-Nov-2021'),
  //   dueDate : new Date('11-Nov-2021'),
  //   amount : 100,
  //   services : 'abonament',
  //   paymentStatus : 'pending',
  //   insertedDate: new Date('11-Nov-2021'),
  //   updatedDate : new Date('11-Nov-2021'),
  // },
  // {

  //   invoiceId : 1,
  //   clientId : 1,
  //   invoiceType : 'B2B1',
  //   issuedDate : new Date('11-Nov-2021'),
  //   dueDate : new Date('11-Nov-2021'),
  //   amount : 100,
  //   services : 'abonament',
  //   paymentStatus : 'failed',
  //   insertedDate: new Date('11-Nov-2021'),
  //   updatedDate : new Date('11-Nov-2021'),
  // },
  // {
  //   invoiceId : 2,
  //   clientId : 1,
  //   invoiceType : 'B2B',
  //   issuedDate : new Date('11-Nov-2021'),
  //   dueDate : new Date('11-Nov-2021'),
  //   amount : 100,
  //   services : 'abonament',
  //   paymentStatus : 'pending',
  //   insertedDate: new Date('11-Nov-2021'),
  //   updatedDate : new Date('11-Nov-2021'),
  // }
  ];

  constructor(@Inject(APP_SERVICE_CONFIG) private config: AppConfig,
   private http: HttpClient ) {
    console.log('Invoice Service is initialized...');
    console.log(this.config.apiEndpoint);
  }

  getInvoices():Observable<any>{
    return this.http.get<Invoice[]>('http://localhost:8080/billing/v1/invoices?invoiceType=B2C',
    {
    headers: new HttpHeaders({
    'Accept': 'application/json',
    'Content-Type': 'application/json'
    })
    });
  }
}
